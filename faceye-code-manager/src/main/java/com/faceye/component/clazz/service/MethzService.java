package com.faceye.component.clazz.service;

import com.faceye.component.clazz.entity.Clazz;
import com.faceye.component.clazz.entity.Methz;
import com.faceye.feature.service.BaseService;

import java.util.List;

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
 * 实体:方法管理->com.faceye.component.clazz.entity.entity.Methz 服务层接口<br>
 * @author haipenge <br>
 * 联系:haipenge@gmail.com<br>
 * 创建日期:2016-7-1 11:23:23<br>
 */
public interface MethzService extends BaseService<Methz,Long>{

	public List<Methz> getMethzsByClazz(Clazz clazz);
	
	public List<Methz> getMethzsByName(String name);
}/**@generate-service-source@**/
