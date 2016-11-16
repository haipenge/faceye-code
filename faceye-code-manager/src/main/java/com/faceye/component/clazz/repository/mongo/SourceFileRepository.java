package com.faceye.component.clazz.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.faceye.component.clazz.entity.SourceFile;
import com.faceye.feature.repository.mongo.BaseMongoRepository;
import com.faceye.component.clazz.repository.mongo.customer.SourceFileCustomerRepository;
import com.faceye.component.clazz.repository.mongo.gen.SourceFileGenRepository;
/**
 * 模块:类管理->com.faceye.compoent.clazz.repository.mongo<br>
 * 说明:<br>
 * 实体:源码->com.faceye.component.clazz.entity.entity.SourceFile 实体DAO<br>
 * @author haipenge <br>
 * 联系:haipenge@gmail.com<br>
*  创建日期:2016-7-4 10:33:00<br>
 */
public interface SourceFileRepository extends SourceFileCustomerRepository,SourceFileGenRepository {
	
	public SourceFile getSourceFileByPath(String path);
}/**@generate-repository-source@**/
