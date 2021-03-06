package com.faceye.component.clazz.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.FieldCache.Parser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.faceye.component.clazz.service.CodeSearchService;
import com.faceye.feature.util.DirectoryUtil;

/**
 * 搜索服务实现类
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年7月14日
 */
@Service
public class CodeSearchServiceImpl implements CodeSearchService {
	@Value("#{property['source.index.dir']}")
	private String INDEX_PATH = "";
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public Page<SearchResult> search(String key, Integer page, Integer size) {
		logger.debug(">>Faceye --> Search key is:["+key+"]");
		IndexReader indexReader = null;
		Analyzer analyzer = AnalyzerFactory.getAnalyzer();
		IndexSearcher searcher = null;
		List<SearchResult> searchResults = new ArrayList<SearchResult>(0);
		Page<SearchResult> pageResult = null;
		try {
			indexReader = this.buildIndexReader();
			searcher = new IndexSearcher(indexReader);
			Query query = null;
			if (StringUtils.contains(key, ",")) {
				String[] keys = StringUtils.split(key, ",");
				List<String> notEmptyKeys = new ArrayList<String>(0);
				List<String> queryFields = new ArrayList<String>(0);
				List<BooleanClause.Occur> clausesItems = new ArrayList<BooleanClause.Occur>(0);
				for (int i = 0; i < keys.length; i++) {
					if (StringUtils.isNotEmpty(org.apache.commons.lang.StringUtils.trim(keys[i]))) {
						notEmptyKeys.add(keys[i]);
						queryFields.add("content");
						clausesItems.add(BooleanClause.Occur.SHOULD);
					}
				}
				BooleanClause.Occur[] clauses = clausesItems.toArray(new BooleanClause.Occur[queryFields.size()]);
				query = MultiFieldQueryParser.parse(Version.LUCENE_4_9, notEmptyKeys.toArray(new String[notEmptyKeys.size()]),
						queryFields.toArray(new String[queryFields.size()]), clauses, AnalyzerFactory.getAnalyzer());
			} else {
				BooleanClause.Occur[] clauses = { BooleanClause.Occur.SHOULD, BooleanClause.Occur.MUST };
				query = MultiFieldQueryParser.parse(Version.LUCENE_4_9, key, new String[] { "name", "content" }, clauses,
						AnalyzerFactory.getAnalyzer());
			}
			SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
			Highlighter highlighter = new Highlighter(formatter, new QueryScorer(query));
			highlighter.setTextFragmenter(new SimpleFragmenter(100));
//			SortField sortField=new SortField("content",new SortComparatorSource(),true);
//			Sort sort=new Sort(sortField);
//			SortField sortField1=new SortField("content",SortField.Type.DOC,true);
			SortField sortField2=new SortField("content",SortField.Type.SCORE,true);
			SortField sortFields[]=new SortField[]{sortField2};
			Sort sort=new Sort(sortFields);
			TopDocs topdocs =searcher.search(query, size, sort);
//			TopDocs topdocs = searcher.search(query, size);
			ScoreDoc[] scoreDocs = topdocs.scoreDocs;
			if (scoreDocs != null && scoreDocs.length > 0) {
				logger.debug(">>FaceYe -->Query result is:"+scoreDocs.length+",key is:"+key);
				for (ScoreDoc doc : scoreDocs) {
					SearchResult result = new SearchResult();
					Document document = searcher.doc(doc.doc);
					String id = document.get("id");
					String name = document.get("name");
					String content = document.get("content");
//					String bestFragment = highlighter.getBestFragment(analyzer, "content", content);
					result.setId(StringUtils.isEmpty(id) ? null : Long.parseLong(id));
					result.setName(name);
					result.getContents().add(content);
					searchResults.add(result);
				}
				pageResult = new PageImpl<SearchResult>(searchResults);
			}else{
				logger.debug(">>FaceYe --> have not got any query result by key:"+key);
			}
		} catch (IOException e) {
			logger.error(">>--->" + e.getMessage());
		} catch (ParseException e) {
			logger.error(">>--->" + e.getMessage());
		}finally {

			if (indexReader != null) {
				try {
					indexReader.close();
				} catch (IOException e) {
					logger.error(">>--->" + e.getMessage());
				}
			}
			// if (analyzer != null) {
			// analyzer.close();
			// }
		}
		return pageResult;
	}

	private IndexSearcher getSearchers(String key) throws IOException, ParseException {

		return null;
	}

	/**
	 * 取得索引
	 * @todo
	 * @return
	 * @throws IOException
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月15日
	 */
	private IndexReader buildIndexReader() throws IOException {
		IndexReader readers = null;
		String[] dirs = DirectoryUtil.getChildrenDirs(INDEX_PATH);
		List<DirectoryReader> items = new ArrayList<DirectoryReader>(0);
		if (dirs != null && dirs.length > 0) {
			for (String dir : dirs) {
				String path =  dir;
				Directory directory = FSDirectory.open(new File(path));
				DirectoryReader reader = DirectoryReader.open(directory);
				items.add(reader);
			}
		}
		if (CollectionUtils.isNotEmpty(items)) {
			readers = new MultiReader(items.toArray(new DirectoryReader[items.size()]));
		}
		return readers;
	}

	@Override
	public String[] getAnalyzerResult(String str) {
		String[] res = null;
		Analyzer analyzer = AnalyzerFactory.getAnalyzer();
		try {
			List<String> words = new ArrayList<String>(0);
			TokenStream tokenStream = analyzer.tokenStream("word", new StringReader(str));
			CharTermAttribute term = tokenStream.addAttribute(CharTermAttribute.class);
			tokenStream.reset();
			while (tokenStream.incrementToken()) {
				words.add(term.toString());
			}
			tokenStream.end();
			tokenStream.close();
			// TokenFilter filter =(TokenFilter) tokenStream;
			// tokenStream.addAttribute(CharTermAttribute.class);
			// while (tokenStream.incrementToken()) {
			// CharTermAttribute charTermAttribute = tokenStream.getAttribute(CharTermAttribute.class);
			// words.add(new String(charTermAttribute.buffer()));
			// }
			res = words.toArray(new String[words.size()]);
			// tokenStream.
		} catch (IOException e) {
			logger.error(">>--->" + e.getMessage());
		}
		// TokenStream tokenStread=analyzer.
		return res;
	}

	@Override
	public WordContainer getKeywords(String content) {
		// 将内容进行分词
		// 将HTML元素全部去掉
		WordContainer container = new WordContainer();
//		content = HtmlUtil.getInstance().replace(content, RegexpConstants.HTML_ALL);
//		content = HtmlUtil.getInstance().replace(content, "&gt;");
//		content = HtmlUtil.getInstance().replace(content, "&lt;");
		String[] words = this.getAnalyzerResult(content);
		if (null != words && words.length > 0) {
			for (String word : words) {
				container.addWord(word);
			}
		}
		return container;
	}

}
