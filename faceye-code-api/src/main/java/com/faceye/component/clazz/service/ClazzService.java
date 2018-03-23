package com.faceye.component.clazz.service;

import com.faceye.component.clazz.entity.Clazz;
import com.faceye.feature.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import com.faceye.feature.repository.mongo.DynamicSpecifications;
 
/**
 * 模块:类管理->com.faceye.compoent.clazz.service<br>
 * 说明:<br>
 * 实体:类管理->com.faceye.component.clazz.entity.entity.Clazz 服务层接口<br>
 * @author haipenge <br>
 * 联系:haipenge@gmail.com<br>
 * 创建日期:2016-7-1 11:23:24<br>
 */
public interface ClazzService extends BaseService<Clazz,Long>{

	
}/**@generate-service-source@**/
