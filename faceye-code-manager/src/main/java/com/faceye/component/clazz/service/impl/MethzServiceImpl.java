package com.faceye.component.clazz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import com.faceye.feature.repository.mongo.DynamicSpecifications;
 
import com.faceye.component.clazz.entity.Clazz;
import com.faceye.component.clazz.entity.Methz;
import com.faceye.component.clazz.repository.mongo.MethzRepository;
import com.faceye.component.clazz.service.MethzService;
import com.faceye.feature.service.impl.BaseMongoServiceImpl;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

/**
 * 模块:类管理->com.faceye.compoent.clazz.service.impl<br>
 * 说明:<br>
 * 实体:方法管理->com.faceye.component.clazz.entity.entity.Methz 服务实现类<br>
 * @author haipenge <br>
 * 联系:haipenge@gmail.com<br>
 * 创建日期:2016-7-1 11:23:23<br>
 */
@Service
public class MethzServiceImpl extends BaseMongoServiceImpl<Methz, Long, MethzRepository> implements MethzService {

	@Autowired
	public MethzServiceImpl(MethzRepository dao) {
		super(dao);
	}
	
	/**
	 *数据分页查询
	 * @author haipenge <br>
     * 联系:haipenge@gmail.com<br>
     * 创建日期:2016-7-1 11:23:23<br>
	*/
	@Override
	public Page<Methz> getPage(Map<String, Object> searchParams, int page, int size)   {
		if (page != 0) {
			page = page - 1;
		}
		// SimpleEntityPathResolver resolver = SimpleEntityPathResolver.INSTANCE;
		// EntityPath<Methz> entityPath = resolver.createPath(entityClass);
		// PathBuilder<Methz> builder = new PathBuilder<Methz>(entityPath.getType(), entityPath.getMetadata());
		// Path path = entityPath.getRoot();
		// List<Predicate> predicates=DynamicSpecifications.buildPredicates(searchParams, entityClass);
		// Predicate predicate=DynamicSpecifications.builder(predicates);
		// NumberPath numberPath = new NumberPath(Number.class, path, "age");
		// predicates.add(numberPath.eq(15));
		Predicate predicate = DynamicSpecifications.builder(searchParams, entityClass);
		if (predicate != null) {
			logger.debug(">>FaceYe -->Query predicate is:" + predicate.toString());
		}
		Sort sort = new Sort(Direction.DESC, "id");
		Page<Methz> res = null;
		if (size != 0) {
			Pageable pageable = new PageRequest(page, size, sort);
			res = this.dao.findAll(predicate, pageable);
		} else {
			// OrderSpecifier<Comparable> orderPOrderSpecifier=new OrderSpecifier<Comparable>(new Order(), new NumberExpression<Methz>("id") {
			// })
			List<Methz> items = (List) this.dao.findAll(predicate);
			res = new PageImpl<Methz>(items);

		}
		return res;
	}

	@Override
	public List<Methz> getMethzsByClazz(Clazz clazz) {
		return this.dao.getMethzsByClazz(clazz);
	}

	@Override
	public List<Methz> getMethzsByName(String name) {
		return this.dao.getMethzsByName(name);
	}
	
	
}/**@generate-service-source@**/
