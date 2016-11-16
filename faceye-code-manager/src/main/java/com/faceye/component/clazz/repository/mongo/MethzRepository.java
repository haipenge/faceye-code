package com.faceye.component.clazz.repository.mongo;

import java.util.List;

import com.faceye.component.clazz.entity.Clazz;
import com.faceye.component.clazz.entity.Methz;
import com.faceye.component.clazz.repository.mongo.customer.MethzCustomerRepository;
import com.faceye.component.clazz.repository.mongo.gen.MethzGenRepository;
/**
 * 模块:类管理->com.faceye.compoent.clazz.repository.mongo<br>
 * 说明:<br>
 * 实体:方法管理->com.faceye.component.clazz.entity.entity.Methz 实体DAO<br>
 * @author haipenge <br>
 * 联系:haipenge@gmail.com<br>
*  创建日期:2016-7-1 11:23:23<br>
 */
public interface MethzRepository extends MethzCustomerRepository,MethzGenRepository {
	
	public List<Methz> getMethzsByClazz(Clazz clazz);
	
	public List<Methz> getMethzsByName(String name);
}/**@generate-repository-source@**/
