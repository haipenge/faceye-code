package com.faceye.component.clazz.repository.mongo.customer.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import com.faceye.component.clazz.repository.mongo.customer.SourceFileCustomerRepository;

/**
 * 模块:类管理->com.faceye.compoent.clazz.repository.mongo.customer<br>
 * 说明:<br>
 * 实体:源码->com.faceye.component.clazz.entity.entity.SourceFile  的数据操作对像<br>
 * @author haipenge <br>
 * 联系:haipenge@gmail.com<br>
*  创建日期:2016-7-4 10:33:01<br>
*  本类只会在第一次生成源码时创建，后绪生成将不会被覆盖。
*  用户自定义的一些方法，可以安全的在这里编写
 */
public class SourceFileCustomerRepositoryImpl implements  SourceFileCustomerRepository{
	private Logger logger=LoggerFactory.getLogger(getClass());
	@Autowired
	private MongoOperations mongoOperations = null;
	
}/**@generate-repository-source@**/
