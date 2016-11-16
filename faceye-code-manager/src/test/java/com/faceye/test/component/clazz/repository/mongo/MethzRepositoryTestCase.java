package com.faceye.test.component.clazz.repository.mongo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.faceye.component.clazz.entity.Methz;
import com.faceye.component.clazz.repository.mongo.MethzRepository;
import com.faceye.test.feature.repository.BaseRepositoryTestCase;
/**
 * Methz Repository 测试
 * @author haipenge 
 * 联系:haipenge@gmail.com
*  创建日期:2016-7-1 11:23:23<br>
 */
public class MethzRepositoryTestCase extends BaseRepositoryTestCase {
	@Autowired
	private MethzRepository methzRepository = null;

	@Before
	public void before() throws Exception {
		//this.methzRepository.deleteAll();
	}

	@After
	public void after() throws Exception {

	}

	@Test
	public void testSave() throws Exception {
		Methz methz = new Methz();
		this.methzRepository.save(methz);
		Iterable<Methz> methzs = this.methzRepository.findAll();
		Assert.isTrue(methzs.iterator().hasNext());
	}

	@Test
	public void testDelete() throws Exception {
		Methz methz = new Methz();
		this.methzRepository.save(methz);
        this.methzRepository.delete(methz.getId());
        Iterable<Methz> methzs = this.methzRepository.findAll();
		Assert.isTrue(!methzs.iterator().hasNext());
	}

	@Test
	public void testFindOne() throws Exception {
		Methz methz = new Methz();
		this.methzRepository.save(methz);
		methz=this.methzRepository.findOne(methz.getId());
		Assert.isTrue(methz!=null);
	}

	
}
