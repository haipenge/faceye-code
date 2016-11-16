package com.faceye.component.clazz.service.index.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.DateUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.faceye.component.clazz.entity.Methz;
import com.faceye.component.clazz.service.MethzService;
import com.faceye.component.clazz.service.index.SourceIndexService;
import com.faceye.feature.util.PathUtil;

@Service
public class SourceIndexServiceImpl implements SourceIndexService {

	@Autowired
	private MethzService methzService = null;

	@Value("#{property['source.index.dir']}")
	private String INDEX_PATH = "";

	private Analyzer analyzer = null;
	private IndexWriter indexWriter = null;
	private Directory dir = null;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void buildIndex() {
		logger.debug(">>FaceYe --> Run Index Builder Job.");
		boolean isContinue = true;
		this.init();
		while (isContinue) {
			List<Methz> methzs = this.getMethz2BuildIndex();
			logger.debug(">>FaceYe-> build source method index info.");
			if (CollectionUtils.isNotEmpty(methzs)) {
				List<Document> docs = new ArrayList<Document>(0);
				for (Methz methz : methzs) {
					Document doc = this.buildDocument(methz);
					if (doc != null) {
						docs.add(doc);
						methz.setIsIndexed(true);
					}
				}
				try {
					if (CollectionUtils.isNotEmpty(docs)) {
						if (indexWriter == null) {
							logger.debug(">>FaceYe --> IndexWriter is null");
						}
						if (analyzer == null) {
							logger.debug(">>FaceYe --> analyzer is null");
						}
						indexWriter.addDocuments(docs, analyzer);
						indexWriter.commit();
					}
					this.methzService.save(methzs);
				} catch (IOException e) {
					logger.error(">>FaceYe Throws Exception:", e);
				}

			} else {
				isContinue = false;
			}
			try {
				Thread.sleep(2000L);
			} catch (InterruptedException e) {
				logger.error(">>FaceYe Throws Exception:", e);
			}
		}
		this.destroy();
	}

	private List<Methz> getMethz2BuildIndex() {
		List<Methz> methzs = null;
		try {
			Map params = new HashMap();
			params.put("boolean|isIndexed", Boolean.FALSE);
			Page<Methz> page = this.methzService.getPage(params, 1, 500);
			if (page != null && CollectionUtils.isNotEmpty(page.getContent())) {
				methzs = page.getContent();
			}
		} catch (Exception e) {
			logger.error(">FaceYe --> Error:", e);
		}
		return methzs;
	}

	private Document buildDocument(Methz methz) {
		Document doc = new Document();
		// name=StringUtils.lowerCase(name);
		// content=StringUtils.lowerCase(content);
		try {
			Field fieldID = new StringField("id", methz.getId() == null ? "" : methz.getId().toString(), Field.Store.YES);
			doc.add(fieldID);
			Field fieldName = new TextField("name", methz.getName(), Field.Store.YES);
			doc.add(fieldName);
			Field fieldContent = new TextField("content", methz.getBody(), Field.Store.YES);
			// 向文档中加入域
			doc.add(fieldContent);
			String longClassName = methz.getClazz().getLongName();
			Field fieldLongClassName = new StringField("longClassName", longClassName, Field.Store.YES);
			doc.add(fieldLongClassName);
			String belowClassId = methz.getClazz().getId().toString();
			Field fieldBelowClassId = new StringField("belowClassId", belowClassId, Field.Store.YES);
			doc.add(fieldBelowClassId);
			Date createDate = methz.getCreateDate();
			if (createDate == null) {
				createDate = new Date();
			}
			Field fieldDate = new StringField("createDate", DateUtils.formatDate(createDate, "yyyy-MM-dd HH:mm:ss"), Field.Store.YES);
			doc.add(fieldDate);
		} catch (Exception e) {
			logger.error(">>FaceYe --> Exception when build document:", e);
		}
		return doc;
	}

	/**
	 * 全局初始化
	 * 
	 * @todo
	 * @author:@haipenge haipenge@gmail.com 2014年7月14日
	 */
	private void init() {
		this.getAnalyzer();
		this.getIndexWriter();
		this.getDir();
	}

	/**
	 * 初始化基础工具
	 * 
	 * @todo
	 * @return
	 * @author:@haipenge haipenge@gmail.com 2014年7月14日
	 */

	private Analyzer getAnalyzer() {
		if (analyzer == null) {
			analyzer = AnalyzerFactory.getAnalyzer();
		}
		return analyzer;
	}

	private IndexWriter getIndexWriter() {
		if (indexWriter == null) {
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_9, getAnalyzer());
			try {
				indexWriter = new IndexWriter(getDir(), config);
			} catch (IOException e) {
				logger.error(">>--->" + e.getMessage());
			}
		}
		return indexWriter;
	}

	private Directory getDir() {
		if (dir == null) {
			try {
				dir = FSDirectory.open(new File(this.buildIndexPath(INDEX_PATH)));
			} catch (IOException e) {
				logger.error(">>--->" + e.getMessage());
			}
		}
		return dir;
	}

	/**
	 * 创建每日存储索引目录
	 * 
	 * @todo
	 * @param parentPath
	 * @return
	 * @author:@haipenge haipenge@gmail.com 2014年7月14日
	 */
	private String buildIndexPath(String parentPath) {
		String res = "";
		if (StringUtils.isEmpty(parentPath)) {
			parentPath = this.INDEX_PATH;
		}
		String dailyPath = "";
		try {
			dailyPath = PathUtil.generatePath();
		} catch (Exception e) {
			logger.error(">>--->" + e.getMessage());
		}
		if (StringUtils.isEmpty(dailyPath)) {
			dailyPath = "DEFAULT_DAILY";
		}
		res = parentPath + "/" + dailyPath;
		PathUtil.mkdir(res);
		return res;

	}

	/**
	 * 数据清理
	 * 
	 * @todo
	 * @author:@haipenge haipenge@gmail.com 2014年7月14日
	 */
	private void destroy() {
		if (indexWriter != null) {
			try {
				indexWriter.close();
				indexWriter = null;
			} catch (IOException e) {
				logger.error(">>--->" + e.getMessage());
			}
		}
		if (dir != null) {
			try {
				dir.close();
				dir = null;
			} catch (IOException e) {
				logger.error(">>--->" + e.getMessage());
			}

		}
	}

}
