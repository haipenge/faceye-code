package com.faceye.component.clazz.repository.mongo.gen;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.faceye.component.clazz.entity.Clazz;
import com.faceye.feature.repository.mongo.BaseMongoRepository;
/**
 * 模块:类管理->com.faceye.compoent.clazz.repository.mongo<br>
 * 说明:<br>
 * 实体:类管理->com.faceye.component.clazz.entity.entity.Clazz 实体DAO<br>
 * @author haipenge <br>
 * 联系:haipenge@gmail.com<br>
*  创建日期:2016-7-4 10:33:01<br>
 */
public interface ClazzGenRepository extends BaseMongoRepository<Clazz,Long> {
	
	
}/**@generate-repository-source@**/
