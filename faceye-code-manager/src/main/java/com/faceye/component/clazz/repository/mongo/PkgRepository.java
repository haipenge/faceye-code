package com.faceye.component.clazz.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.faceye.component.clazz.entity.Pkg;
import com.faceye.feature.repository.mongo.BaseMongoRepository;
import com.faceye.component.clazz.repository.mongo.customer.PkgCustomerRepository;
import com.faceye.component.clazz.repository.mongo.gen.PkgGenRepository;
/**
 * 模块:类管理->com.faceye.compoent.clazz.repository.mongo<br>
 * 说明:<br>
 * 实体:包名管理->com.faceye.component.clazz.entity.entity.Pkg 实体DAO<br>
 * @author haipenge <br>
 * 联系:haipenge@gmail.com<br>
*  创建日期:2016-7-1 11:23:24<br>
 */
public interface PkgRepository extends PkgCustomerRepository,PkgGenRepository {
	
	public Pkg getPkgByName(String name);
}/**@generate-repository-source@**/
