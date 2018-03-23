package com.faceye.test.component.clazz.repository.mongo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Assert;

import com.faceye.component.clazz.entity.Clazz;
import com.faceye.component.clazz.repository.mongo.ClazzRepository;
import com.faceye.test.feature.repository.BaseRepositoryTestCase;
/**
 * Clazz Repository 测试
 * @author haipenge 
 * 联系:haipenge@gmail.com
*  创建日期:2016-7-1 11:23:24<br>
 */
public class ClazzRepositoryTestCase extends BaseRepositoryTestCase {
	@Autowired
	private ClazzRepository clazzRepository = null;

	@Before
	public void before() throws Exception {
		//this.clazzRepository.deleteAll();
	}

	@After
	public void after() throws Exception {

	}

	@Test
	public void testSave() throws Exception {
		Clazz clazz = new Clazz();
		this.clazzRepository.save(clazz);
		Iterable<Clazz> clazzs = this.clazzRepository.findAll();
		Assert.assertTrue(clazzs.iterator().hasNext());
	}

	@Test
	public void testDelete() throws Exception {
		Clazz clazz = new Clazz();
		this.clazzRepository.save(clazz);
        this.clazzRepository.deleteById(clazz.getId());
        Iterable<Clazz> clazzs = this.clazzRepository.findAll();
		Assert.assertTrue(!clazzs.iterator().hasNext());
	}

	@Test
	public void testFindOne() throws Exception {
		Clazz clazz = new Clazz();
		this.clazzRepository.save(clazz);
		clazz=this.clazzRepository.findById(clazz.getId());
		Assert.assertTrue(clazz!=null);
	}

	
}
