package com.faceye.test.component.clazz.repository.mongo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Assert;

import com.faceye.component.clazz.entity.Pkg;
import com.faceye.component.clazz.repository.mongo.PkgRepository;
import com.faceye.test.feature.repository.BaseRepositoryTestCase;
/**
 * Pkg Repository 测试
 * @author haipenge 
 * 联系:haipenge@gmail.com
*  创建日期:2016-7-1 11:23:24<br>
 */
public class PkgRepositoryTestCase extends BaseRepositoryTestCase {
	@Autowired
	private PkgRepository pkgRepository = null;

	@Before
	public void before() throws Exception {
		//this.pkgRepository.deleteAll();
	}

	@After
	public void after() throws Exception {

	}

	@Test
	public void testSave() throws Exception {
		Pkg pkg = new Pkg();
		this.pkgRepository.save(pkg);
		Iterable<Pkg> pkgs = this.pkgRepository.findAll();
		Assert.assertTrue(pkgs.iterator().hasNext());
	}

	@Test
	public void testDelete() throws Exception {
		Pkg pkg = new Pkg();
		this.pkgRepository.save(pkg);
        this.pkgRepository.deleteById(pkg.getId());
        Iterable<Pkg> pkgs = this.pkgRepository.findAll();
		Assert.assertTrue(!pkgs.iterator().hasNext());
	}

	@Test
	public void testFindOne() throws Exception {
		Pkg pkg = new Pkg();
		this.pkgRepository.save(pkg);
		pkg=this.pkgRepository.findById(pkg.getId());
		Assert.assertTrue(pkg!=null);
	}

	
}
