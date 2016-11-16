package com.faceye.component.clazz.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.faceye.component.clazz.entity.Methz;
import com.faceye.feature.repository.mongo.BaseMongoRepository;
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
	
	
}/**@generate-repository-source@**/
