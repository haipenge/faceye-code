package com.faceye.component.clazz.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.faceye.component.clazz.entity.Clazz;
import com.faceye.feature.repository.mongo.BaseMongoRepository;
import com.faceye.component.clazz.repository.mongo.customer.ClazzCustomerRepository;
import com.faceye.component.clazz.repository.mongo.gen.ClazzGenRepository;
/**
 * 模块:类管理->com.faceye.compoent.clazz.repository.mongo<br>
 * 说明:<br>
 * 实体:类管理->com.faceye.component.clazz.entity.entity.Clazz 实体DAO<br>
 * @author haipenge <br>
 * 联系:haipenge@gmail.com<br>
*  创建日期:2016-7-1 11:23:24<br>
 */
public interface ClazzRepository extends ClazzCustomerRepository,ClazzGenRepository {
	
	
}/**@generate-repository-source@**/
