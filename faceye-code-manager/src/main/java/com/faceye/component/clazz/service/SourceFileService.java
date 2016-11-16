package com.faceye.component.clazz.service;

import com.faceye.component.clazz.entity.SourceFile;
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
import com.faceye.feature.util.ServiceException;
/**
 * 模块:类管理->com.faceye.compoent.clazz.service<br>
 * 说明:<br>
 * 实体:源码->com.faceye.component.clazz.entity.entity.SourceFile 服务层接口<br>
 * @author haipenge <br>
 * 联系:haipenge@gmail.com<br>
 * 创建日期:2016-7-4 10:33:00<br>
 */
public interface SourceFileService extends BaseService<SourceFile,Long>{

	public SourceFile getSourceFileByPath(String path);
}/**@generate-service-source@**/
