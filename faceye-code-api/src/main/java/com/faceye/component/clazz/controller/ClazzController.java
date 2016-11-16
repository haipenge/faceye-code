package com.faceye.component.clazz.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.faceye.component.clazz.entity.Clazz;
import com.faceye.component.clazz.service.ClazzService;
import com.faceye.component.clazz.entity.Pkg;
import com.faceye.component.clazz.service.PkgService;
import com.faceye.feature.controller.BaseController;
import com.faceye.feature.util.http.HttpUtil;
import com.faceye.feature.util.AjaxResult;
import com.faceye.feature.util.view.MessageBuilder;
import com.faceye.feature.util.GlobalEntity;

/**
 * 模块:类管理->com.faceye.compoent.clazz.controller<br>
 * 说明:<br>
 * 实体:类管理:com.faceye.component.clazz.entity.entity.Clazz<br>
 * @author haipenge <br>
 * haipenge@gmail.com<br>
*  创建日期:2016-7-1 11:23:24<br>
 */
@Controller
@Scope("prototype")
@RequestMapping("/clazz/clazz")
public class ClazzController extends BaseController<Clazz, Long, ClazzService> {
	@Autowired
	private PkgService pkgService=null; 
	
	@Autowired
	public ClazzController(ClazzService service) {
		super(service);
	}

	/**
	 * 首页<br>
	 * 
	 * @todo<br>
	 * @return<br>
	 * @author:haipenge<br> 
	 * haipenge@gmail.com <br>
	 * 创建日期2016-7-1 11:23:24<br>
	 */
	@RequestMapping("/home")
	@ResponseBody
	public Page<Clazz> home(HttpServletRequest request, Model model) {
		Map searchParams=HttpUtil.getRequestParams(request);
		beforeInput(model,request);
		Page<Clazz> page = this.service.getPage(searchParams, getPage(searchParams), getSize(searchParams));
		model.addAttribute("page", page);
		resetSearchParams(searchParams);
		model.addAttribute("searchParams", searchParams);
		return page;
	}

	/**
	 * 转向编辑或新增页面<br>
	 * 
	 * @todo<br>
	 * @return<br>
	 * @author:haipenge<br>
	 * haipenge @gmail.com <br>
	 * 创建日期:2016-7-1 11:23:24<br>
	 */
	@RequestMapping("/edit/{id}")
	@ResponseBody
	public Clazz edit(@PathVariable("id") Long id,Model model,HttpServletRequest request) {
		beforeInput(model,request);
		Clazz clazz=this.service.get(id);
		return clazz;
	}
	
	/**
	 * 转向新增页面<br>
	 * @todo<br>
	 * @param model<br>
	 * @return<br>
	 * @author:haipenge<br>
	 * haipenge @gmail.com <br>
	 * 创建日期:2016-7-1 11:23:24<br>
	 */
	@RequestMapping(value="/input")
	public String input(Clazz clazz, Model model,HttpServletRequest request){
		beforeInput(model,request);
		return "clazz.clazz.update";
	}
	

	/**
	 * 数据保存<br>
	 * @author:haipenge<br>
	 * haipenge @gmail.com <br>
	 * 创建日期:2016-7-1 11:23:24<br>
	 */
	@RequestMapping("/save")
	@ResponseBody
	public String save(@Valid Clazz clazz,BindingResult bindingResult,Model model,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()){
		  beforeInput(model,request);
		  return AjaxResult.getInstance().buildDefaultResult(false);
		}else{
		  this.beforeSave(clazz,request);
	      this.service.save(clazz);
	      this.afterSave(clazz,request);
	      return  AjaxResult.getInstance().buildDefaultResult(true);
		}
	}

	/**
	 * 数据删除<br>
	 * 
	 * @todo<br>
	 * @return<br>
	  * @author:haipenge<br>
	 * haipenge @gmail.com <br>
	 * 创建日期:2016-7-1 11:23:24<br>
	 */
	@RequestMapping("/remove/{id}")
	@ResponseBody
	public String remove(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		if(id!=null){
			this.service.remove(id);
		}
		return AjaxResult.getInstance().buildDefaultResult(true);
	}
	
	/**
	 * 数据批量删除<br>
	 * 
	 * @todo<br>
	 * @return<br>
	  * @author:haipenge<br>
	 * haipenge @gmail.com <br>
	 * 创建日期:2016-7-1 11:23:24<br>
	 */
	@RequestMapping("/multiRemove")
	@ResponseBody
	public String remove(@RequestParam(required=true) String  ids, RedirectAttributes redirectAttributes) {
		if(StringUtils.isNotEmpty(ids)){
			String [] idArray=ids.split(",");
			for(String id:idArray){
				this.service.remove(Long.parseLong(id));
			}
		}
		return AjaxResult.getInstance().buildDefaultResult(true);
	}
	
	/**
	 * 取得数据明细<br>
	 * @todo<br>
	 * @param id<br>
	 * @param model<br>
	 * @return<br>
	 * @author:haipenge<br>
	 * haipenge @gmail.com <br>
	 * 创建日期:2016-7-1 11:23:24<br>
	 */
	@RequestMapping("/detail/{id}")
	@ResponseBody
	public Clazz detail(@PathVariable Long id,Model model){
		Clazz clazz=this.service.get(id);
		return clazz;
	}
	
	///////////////////////////////////////////////以下为回调函数////////////////////////////////////////////
	/**
	 * 新增、编辑页面的前置逻辑处理<br>
	 * @todo<br>
	 * @param model<br>
	 * @param request<br>
	 * @author:haipenge<br>
	 * haipenge @gmail.com <br>
	 * 创建日期:2016-7-1 11:23:24<br>
	 */
	protected void beforeInput(Model model,HttpServletRequest request){
		    List<Pkg> pkgs=this.pkgService.getAll();
		    model.addAttribute("pkgs", pkgs);
	}
	
	/**
	 * 保存前的数据回调
	 * @todo
	 * @param clazz
	 * @param request
	 * @author:haipenge
	 * 联系:haipenge@gmail.com
	 * 创建日期:2016-7-1 11:23:24
	 */
	protected void beforeSave(Clazz clazz,HttpServletRequest request){
	}
	
	
	
	/**
	 * 保存后的数据回调
	 * @todo
	 * @param clazz
	 * @param request
	 * @author:haipenge
	 * 联系:haipenge@gmail.com
	 * 创建日期:2016-7-1 11:23:24
	 */
	protected void afterSave(Clazz clazz,HttpServletRequest request){
	   
	}
	

}
