package com.faceye.test.component.clazz.repository.mongo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Assert;

import com.faceye.component.clazz.entity.SourceFile;
import com.faceye.component.clazz.repository.mongo.SourceFileRepository;
import com.faceye.test.feature.repository.BaseRepositoryTestCase;
/**
 * SourceFile Repository 测试
 * @author haipenge 
 * 联系:haipenge@gmail.com
*  创建日期:2016-7-4 10:33:01<br>
 */
public class SourceFileRepositoryTestCase extends BaseRepositoryTestCase {
	@Autowired
	private SourceFileRepository sourceFileRepository = null;

	@Before
	public void before() throws Exception {
		//this.sourceFileRepository.deleteAll();
	}

	@After
	public void after() throws Exception {

	}

	@Test
	public void testSave() throws Exception {
		SourceFile sourceFile = new SourceFile();
		this.sourceFileRepository.save(sourceFile);
		Iterable<SourceFile> sourceFiles = this.sourceFileRepository.findAll();
		Assert.assertTrue(sourceFiles.iterator().hasNext());
	}

	@Test
	public void testDelete() throws Exception {
		SourceFile sourceFile = new SourceFile();
		this.sourceFileRepository.save(sourceFile);
        this.sourceFileRepository.deleteById(sourceFile.getId());
        Iterable<SourceFile> sourceFiles = this.sourceFileRepository.findAll();
		Assert.assertTrue(!sourceFiles.iterator().hasNext());
	}

	@Test
	public void testFindOne() throws Exception {
		SourceFile sourceFile = new SourceFile();
		this.sourceFileRepository.save(sourceFile);
		sourceFile=this.sourceFileRepository.findById(sourceFile.getId());
		Assert.assertTrue(sourceFile!=null);
	}

	
}
