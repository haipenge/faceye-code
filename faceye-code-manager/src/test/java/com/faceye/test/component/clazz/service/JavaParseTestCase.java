package com.faceye.test.component.clazz.service;

import java.io.FileInputStream;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.runtime.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.faceye.component.clazz.service.parse.impl.JavaParseImpl;
import com.faceye.test.feature.service.BaseServiceTestCase;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class JavaParseTestCase extends BaseServiceTestCase {

	@Autowired
	private JavaParseImpl javaParse = null;
	private String url = "";

	@Before
	public void set() throws Exception {
		url = "/work/Work/FeatureWorkSpace/springside/springside4/modules/extension/src/main/java/org/springside/modules/tools/FreeMarkers.java";
	}

	@Test
	public void testUrlIsNotEmpty() throws Exception {
		Assert.assertTrue(StringUtils.isNotEmpty(url));
	}

	@Test
	public void testJavaParse() throws Exception {
		String source = "";
		source = javaParse.read(url);
		Assert.assertTrue(StringUtils.isNotEmpty(source));
	}

	@Test
	public void testGetPackage() throws Exception {
		String source = this.javaParse.read(url);
		String pkg = this.javaParse.getPackage(source);
		Assert.assertTrue(StringUtils.isNotEmpty(pkg));
	}

	@Test
	public void testGetImportClasses() throws Exception {
		String source = this.javaParse.read(url);
		List<String> importClasses = this.javaParse.getImportClasses(source);
		Assert.assertTrue(CollectionUtils.isNotEmpty(importClasses));
	}

	@Test
	public void testGetClassNameByUrl() throws Exception {
		String className = this.javaParse.getClassNameByUrl(url);
		Assert.assertTrue(StringUtils.isNotEmpty(className));
	}

	@Test
	public void testGetLongClassName() throws Exception {
		String longName = this.javaParse.getLongClassName(this.javaParse.read(url),url);
		Assert.assertTrue(StringUtils.isNotEmpty(longName));
	}

	@Test
	public void testJavaParser() throws Exception {
		FileInputStream in = new FileInputStream(url);
		CompilationUnit cu = null;
		try {
			cu = JavaParser.parse(in);
		} catch (Exception e) {

		} finally {
			in.close();
		}
		logger.debug(">>\n" + cu.toString());
		new MethodVistor().visit(cu, null);
	}

	class MethodVistor extends VoidVisitorAdapter {
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
					sb.append(expr.getComment().getContent());
					sb.append("\r");
				}
			}
			if (StringUtils.isNotEmpty(n.getDeclarationAsString())) {
				sb.append(n.getDeclarationAsString());
			}
			
			if (n.getBody() != null && StringUtils.isNotEmpty(n.getBody().toString())) {
				sb.append(n.getBody());
				sb.append("\r");
			}
			logger.debug(">>FaceYe ---------------------Got Method ------------------------\r");
			logger.debug(sb.toString());
			logger.debug(">>FaceYe <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\r");
			
			super.visit(n, arg);
		}
//		@Override
//        public void visit(final MethodCallExpr n,Object arg){
//            System.out.println(n);
//            super.visit(n, arg);
//        }
		@Override
        public void visit(ClassOrInterfaceDeclaration typeDeclaration, Object o) {
            String signature = typeDeclaration.getName();
//            LineRange range = new LineRange(typeDeclaration.getBeginLine(), typeDeclaration.getEndLine());
            logger.debug("Parsed source of type '" + signature + "', lines: " + signature);
//            LineRangeParser.this.typesLineRange.put(signature, range);
            for (BodyDeclaration bodyDeclaration : typeDeclaration.getMembers()) {
                bodyDeclaration.accept(this, null);
            }
            super.visit(typeDeclaration, o);
        }
		
		public void visit(ImportDeclaration n, String arg) {
			String oldName = n.getName().toString();
			logger.debug(">>Impolrt:"+oldName);
//			if (oldName.startsWith(from)) {
//				n.setName(new NameExpr(StringUtils.replaceOnce(oldName,
//						from, to)));
//				flag.set(true);
//			}
			super.visit(n, arg);
		}
	}
	@Test
	public void testParse() throws Exception{
		this.javaParse.parse();
		Assert.assertTrue(true);
	}
	
}
