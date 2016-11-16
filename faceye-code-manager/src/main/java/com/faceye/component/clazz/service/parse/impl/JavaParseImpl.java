package com.faceye.component.clazz.service.parse.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.faceye.component.clazz.entity.Clazz;
import com.faceye.component.clazz.entity.Methz;
import com.faceye.component.clazz.entity.Pkg;
import com.faceye.component.clazz.entity.SourceFile;
import com.faceye.component.clazz.service.ClazzService;
import com.faceye.component.clazz.service.MethzService;
import com.faceye.component.clazz.service.PkgService;
import com.faceye.component.clazz.service.SourceFileService;
import com.faceye.component.clazz.service.parse.FileParse;
import com.faceye.feature.util.DirectoryUtil;
import com.faceye.feature.util.regexp.RegexpUtil;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.TokenMgrError;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.ClassExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * Java文件解析
 * 
 * @author haipenge
 *
 */
@Service
public class JavaParseImpl implements FileParse {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Value("#{property['source.repository.dir']}")
	private String sourceRepositoryDir = "";

	@Autowired
	private PkgService pkgService = null;
	@Autowired
	private ClazzService clazzService = null;
	@Autowired
	private MethzService methzService = null;
	@Autowired
	private SourceFileService sourceFileService = null;

	public String read(String url) {
		String source = "";
		try {
			source = FileUtils.readFileToString(new File(url));
			logger.debug(">>FaceYe -->Read Url is:", url);
			logger.debug(">>FaceYe --> source is:\n" + source);
		} catch (IOException e) {
			logger.error(">>FaceYe Throws Exception:", e);
		}
		return source;
	}

	/**
	 * 取得包名
	 * 
	 * @param source
	 * @return
	 * @Desc:
	 * @Author:haipenge
	 * @Date:2016年7月1日 下午12:10:44
	 */
	public String getPackage(String source) {
		String pkg = "";
		if (StringUtils.isNotEmpty(source)) {
			String pattern = "package([\\s\\S]*?);";
			try {
				List<Map<String, String>> matchers = RegexpUtil.match(source, pattern);
				if (CollectionUtils.isNotEmpty(matchers)) {
					pkg = matchers.get(0).get("1");
					pkg = StringUtils.trim(pkg);
				}
			} catch (Exception e) {
				logger.error(">>FaceYe Throws Exception:", e);
			}
		}
		logger.debug(">>FaceYe --> pkg is:" + pkg);
		return pkg;
	}

	/**
	 * 是否接口
	 * 
	 * @param source
	 * @return
	 * @Desc:
	 * @Author:haipenge
	 * @Date:2016年7月5日 下午3:09:41
	 */
	private boolean isInterface(String source) {
		boolean res = false;
		if (StringUtils.indexOf(source, "interface") != -1) {
			res = true;
		}
		return res;
	}

	/**
	 * 取得导入类的包信息
	 * 
	 * @param source
	 * @return
	 * @Desc:
	 * @Author:haipenge
	 * @Date:2016年7月1日 下午2:21:36
	 */
	public List<String> getImportClasses(String source) {
		List<String> classes = new ArrayList<String>(0);
		if (StringUtils.isNotEmpty(source)) {
			String pattern = "import([\\s\\S]*?);";
			try {
				List<Map<String, String>> matchers = RegexpUtil.match(source, pattern);
				if (CollectionUtils.isNotEmpty(matchers)) {
					for (Map<String, String> map : matchers) {
						String clazz = map.get("1");
						clazz = StringUtils.trim(clazz);
						if (!StringUtils.endsWith(clazz, ".*")) {
							logger.debug(">>FaceYe --> get Import class is:" + clazz);
							classes.add(clazz);
						}
					}
				}
			} catch (Exception e) {
				logger.error(">>FaceYe Throws Exception:", e);
			}
		}
		return classes;
	}

	/**
	 * 根据URL取得类名
	 * 
	 * @param url
	 * @return
	 * @Desc:
	 * @Author:haipenge
	 * @Date:2016年7月1日 下午2:32:06
	 */
	public String getClassNameByUrl(String url) {
		String className = "";
		if (StringUtils.isNotEmpty(url)) {
			int start = StringUtils.lastIndexOf(url, "/") + 1;
			int end = StringUtils.lastIndexOf(url, ".");
			className = StringUtils.substring(url, start, end);
			logger.debug(">>FaceYe getClassName is:" + className);
			className = StringUtils.trim(className);
		}
		return className;
	}

	public String getLongClassName(String source, String url) {
		String longName = "";
		String pkg = this.getPackage(source);
		String className = this.getClassNameByUrl(url);
		String pattern = "class([\\s]*?[A-Za-z0-9]*?)\\{";
		// List<Map<String, String>> matchers = this.match(source, pattern);
		// if (CollectionUtils.isNotEmpty(matchers)) {
		// String classDeclear = StringUtils.trim(matchers.get(0).get("1"));
		// if (StringUtils.isNotEmpty(classDeclear)) {
		// String[] splits = StringUtils.split(classDeclear, " ");
		// if (splits != null) {
		// String className = StringUtils.trim(splits[0]);
		// longName = pkg + "." + className;
		// }
		// }
		// }
		longName = pkg + "." + className;
		logger.debug(">>FaceYe --> get long class name form source is:" + longName);
		return longName;
	}

	private List<Map<String, String>> match(String content, String pattern) {
		List<Map<String, String>> matchers = null;
		try {
			matchers = RegexpUtil.match(content, pattern);
		} catch (Exception e) {
			logger.error(">>FaceYe Throws Exception:", e);
		}
		return matchers;
	}

	/**
	 * 读取源码目录，前存储文件路径
	 * 
	 * @Desc:
	 * @Author:haipenge
	 * @Date:2016年7月4日 上午10:42:15
	 */
	public void readDirectory() {
		File file = new File(sourceRepositoryDir);
		if (file.exists() && file.isDirectory()) {
			File subFiles[] = file.listFiles();
			if (subFiles != null) {
				for (File subFile : subFiles) {
					List<String> urls = new ArrayList<String>(0);
					urls = DirectoryUtil.getDirectoryFileUrls(subFile.getAbsolutePath(), ".java", urls);
					if (CollectionUtils.isNotEmpty(urls)) {
						for (String url : urls) {
							// 相对路径
							String relativePath = StringUtils.replace(url, sourceRepositoryDir, "");
							logger.debug(">>FaceYe relative path is:" + relativePath);
							SourceFile sourceFile = this.sourceFileService.getSourceFileByPath(relativePath);
							if (sourceFile == null) {
								sourceFile = new SourceFile();
								sourceFile.setIsParse(false);
								sourceFile.setPath(relativePath);
								this.sourceFileService.save(sourceFile);
							}
						}
					}
				}
			}
		}
	}

	public void parse() {
		boolean isContinue = true;
		Map searchParams = new HashMap();
		searchParams.put("boolean|isParse", Boolean.FALSE);
		while (isContinue) {
			try {
				Page<SourceFile> sourceFiles = this.sourceFileService.getPage(searchParams, 1, 100);
				if (sourceFiles != null && CollectionUtils.isNotEmpty(sourceFiles.getContent())) {
					for (SourceFile sourceFile : sourceFiles.getContent()) {
						String url = sourceRepositoryDir + sourceFile.getPath();
						logger.debug(">>FaceYe -->Url:" + url);
						this.parse(url);
						sourceFile.setIsParse(true);
					}
					this.sourceFileService.save(sourceFiles.getContent());
					try {
						Thread.sleep(3000L);
					} catch (InterruptedException e) {
						logger.error(">>FaceYe Throws Exception:", e);
					}
				} else {
					isContinue = false;
				}
			} catch (Exception e) {
				logger.error(">>FaceYe throws Excetion:", e);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void parse(String url) {
		FileInputStream in = null;
		CompilationUnit cu = null;
		String source = this.read(url);
		// 如果是接口，不进行存储和解析
		boolean isInterface = this.isInterface(source);
		if (isInterface) {
			return;
		}
		String pkgName = this.getPackage(source);
		String longClassName = this.getLongClassName(source, url);
		String className = this.getClassNameByUrl(url);
		List<String> clazzs = this.getImportClasses(source);
		clazzs.add(longClassName);
		Pkg pkg = this.pkgService.getPkgByName(pkgName);
		MethodVistor methodVistor = null;
		if (pkg == null) {
			pkg = new Pkg();
			pkg.setName(pkgName);
			this.pkgService.save(pkg);
		}
		this.saveClazzs(clazzs);
		Clazz clazz = this.clazzService.getClzzByLongName(longClassName);
		try {
			in = new FileInputStream(url);
			cu = JavaParser.parse(in);
			methodVistor = new MethodVistor(clazz);
			methodVistor.visit(cu, null);
		} catch (TokenMgrError e) {
			logger.debug(">>FaceYe --> E:", e);
		} catch (Error e) {
			logger.debug(">>FaceYe --> E:", e);
		} catch (FileNotFoundException e1) {
			logger.error(">>FaceYe Throws Exception:", e1);
		} catch (ParseException e) {
			logger.error(">>FaceYe Throws Exception:", e);
		} catch (Exception e) {
			logger.error(">>FaceYe Throws Exception:", e);
		} finally {
		}
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				logger.error(">>FaceYe Throws Exception:", e);
			}
		}
	}

	/**
	 * 保存类
	 * 
	 * @param clazzs
	 * @Desc:
	 * @Author:haipenge
	 * @Date:2016年7月7日 上午9:34:29
	 */
	private void saveClazzs(List<String> clazzs) {
		List<Clazz> items = new ArrayList<Clazz>(0);
		if (CollectionUtils.isNotEmpty(clazzs)) {
			for (String longName : clazzs) {
				if(StringUtils.length(longName)>255){
					continue;
				}
				String pkgName = StringUtils.substring(longName, 0, StringUtils.lastIndexOf(longName, "."));
				Pkg pkg = null;
				if (StringUtils.isNotEmpty(pkgName)) {
					pkg = this.pkgService.getPkgByName(pkgName);
					if (pkg == null) {
						pkg = new Pkg();
						pkg.setName(pkgName);
						this.pkgService.save(pkg);
					}
				}
				Clazz clazz = this.clazzService.getClzzByLongName(longName);
				if (clazz == null) {
					clazz = new Clazz();
					clazz.setLongName(longName);
					String name = StringUtils.substring(longName, StringUtils.lastIndexOf(longName, ".") + 1);
					clazz.setName(name);
					clazz.setPkg(pkg);
				} else {
					clazz.setTimes(clazz.getTimes() + 1);
				}
				items.add(clazz);
			}
		}
		this.clazzService.save(items);
	}

	class ClassVistor extends VoidVisitorAdapter {
		public void visit(ClassExpr n, Object arg) {
			// logger.debug(">>FaceYe -->Rn class expr." + n.getData());
			super.visit(n, arg);
		}
	}

	@SuppressWarnings("rawtypes")
	class MethodVistor extends VoidVisitorAdapter {
		private Clazz clazz = null;

		public MethodVistor(Clazz clazz) {
			this.clazz = clazz;
		}

		public void visit(MethodDeclaration n, Object arg) {
			StringBuilder sb = new StringBuilder("");
			if (n.getComment() != null) {
				String comment = n.getComment().getContent();
				if (StringUtils.isNotEmpty(comment)) {
					sb.append("/**");
					sb.append("\r");
					sb.append(comment);
					sb.append("*/");
					sb.append("\r");
				}
			}
			List<AnnotationExpr> annotationExprs = n.getAnnotations();
			if (CollectionUtils.isNotEmpty(annotationExprs)) {
				for (AnnotationExpr expr : annotationExprs) {
					if (expr.getComment() != null && expr.getComment().getContent() != null) {
						sb.append(expr.getComment().getContent());
						sb.append("\n");
					}
				}
			}
			if (StringUtils.isNotEmpty(n.getDeclarationAsString())) {
				sb.append(n.getDeclarationAsString());
				sb.append("\r");
			}

			if (n.getBody() != null && StringUtils.isNotEmpty(n.getBody().toString())) {
				String body = n.getBody().toString();
				body = StringUtils.trim(body);
				sb.append(body);
				// sb.append("\r");
			}
			
//			logger.debug(sb.toString());
//			logger.debug(">>FaceYe <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\r");
			Methz methz = null;
			String name = StringUtils.trim(n.getDeclarationAsString());
			if(StringUtils.length(name)>255){
				return;
			}
			String mBody = StringUtils.trim(sb.toString());
			logger.debug(">>FaceYe ---Got Method ---"+name+",of clazz is:"+clazz.getLongName());
			if (StringUtils.isNotEmpty(name)) {
				List<Methz> methzs = methzService.getMethzsByName(name);
				if (CollectionUtils.isNotEmpty(methzs)) {
					boolean isDif = false;
					for (Methz m : methzs) {
						String inBody = m.getBody();
						Integer rate = StringUtils.length(inBody) - StringUtils.length(mBody);
						int absRate = Math.abs(rate);
						if (absRate > 10) {
							isDif = true;
							break;
						}
					}
					if (isDif) {
						methz = new Methz();
					}
				} else {
					methz = new Methz();
				}
				if (methz != null) {
					methz.setBody(mBody);
					methz.setClazz(clazz);
					methz.setName(n.getDeclarationAsString());
					methzService.save(methz);
				}
			}
			super.visit(n, arg);
		}

		//
	}
	//// @Override
	//// public void visit(final MethodCallExpr n,Object arg){
	//// System.out.println(n);
	//// super.visit(n, arg);
	//// }
	// @Override
	// public void visit(ClassOrInterfaceDeclaration typeDeclaration, Object o) {
	// String signature = typeDeclaration.getName();
	//// LineRange range = new LineRange(typeDeclaration.getBeginLine(), typeDeclaration.getEndLine());
	// logger.debug("Parsed source of type '" + signature + "', lines: " + signature);
	//// LineRangeParser.this.typesLineRange.put(signature, range);
	// for (BodyDeclaration bodyDeclaration : typeDeclaration.getMembers()) {
	// bodyDeclaration.accept(this, null);
	// }
	// super.visit(typeDeclaration, o);
	// }
	//
	// public void visit(ImportDeclaration n, String arg) {
	// String oldName = n.getName().toString();
	// logger.debug(">>Impolrt:"+oldName);
	//// if (oldName.startsWith(from)) {
	//// n.setName(new NameExpr(StringUtils.replaceOnce(oldName,
	//// from, to)));
	//// flag.set(true);
	//// }
	// super.visit(n, arg);
	// }
	// }
}
