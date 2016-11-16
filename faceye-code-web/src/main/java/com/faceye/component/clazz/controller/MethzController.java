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
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;


import com.faceye.component.clazz.entity.Methz;
import com.faceye.component.clazz.service.MethzService;
import com.faceye.component.clazz.entity.Clazz;
import com.faceye.component.clazz.service.ClazzService;
import com.faceye.feature.controller.BaseController;
import com.faceye.feature.util.http.HttpUtil;
import com.faceye.feature.util.AjaxResult;
import com.faceye.feature.util.view.MessageBuilder;
import com.faceye.feature.util.GlobalEntity;

/**
 * 模块:类管理->com.faceye.compoent.clazz.controller<br>
 * 说明:<br>
 * 实体:方法管理:com.faceye.component.clazz.entity.entity.Methz<br>
 * @author haipenge <br>
 * haipenge@gmail.com<br>
*  创建日期:2016-7-1 11:23:23<br>
 */
@Controller
@Scope("prototype")
@RequestMapping("/clazz/methz")
public class MethzController extends BaseController<Methz, Long, MethzService> {
	@Autowired
	protected ClazzService clazzService=null; 
	@Autowired
	public MethzController(MethzService service) {
		super(service);
	}
	
	
	/**
	 * 首页<br>
	 * 
	 * @todo<br>
	 * @return<br>
	 * @author:haipenge<br> 
	 * haipenge@gmail.com <br>
	 * 创建日期2016-7-1 11:23:23<br>
	 */
	@RequestMapping("/home")
	public String home(HttpServletRequest request, Model model) {
		Map searchParams=HttpUtil.getRequestParams(request);
		beforeInput(model,request);
		Page<Methz> page = this.service.getPage(searchParams, getPage(searchParams), getSize(searchParams));
		model.addAttribute("page", page);
		resetSearchParams(searchParams);
		model.addAttribute("searchParams", searchParams);
		GlobalEntity global=new GlobalEntity();
		global.setTitle(this.getI18N("clazz.methz"));
		model.addAttribute("global",global);
		return "clazz.methz.manager";
	}

	/**
	 * 转向编辑或新增页面<br>
	 * 
	 * @todo<br>
	 * @return<br>
	 * @author:haipenge<br>
	 * haipenge @gmail.com <br>
	 * 创建日期:2016-7-1 11:23:23<br>
	 */
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id,Model model,HttpServletRequest request) {
		if(id!=null){
			beforeInput(model,request);
			Methz methz=this.service.get(id);
			model.addAttribute("methz", methz);
		}
		GlobalEntity global=new GlobalEntity();
		global.setTitle(this.getI18N("clazz.methz.edit"));
		model.addAttribute("global",global);
		return "clazz.methz.update";
	}
	
	/**
	 * 转向新增页面<br>
	 * @todo<br>
	 * @param model<br>
	 * @return<br>
	 * @author:haipenge<br>
	 * haipenge @gmail.com <br>
	 * 创建日期:2016-7-1 11:23:23<br>
	 */
	@RequestMapping(value="/input")
	public String input(Methz methz, Model model,HttpServletRequest request){
		beforeInput(model,request);
		GlobalEntity global=new GlobalEntity();
		global.setTitle(this.getI18N("clazz.methz.add"));
		model.addAttribute("global",global);
		return "clazz.methz.update";
	}
	

	/**
	 * 数据保存<br>
	 * @author:haipenge<br>
	 * haipenge @gmail.com <br>
	 * 创建日期:2016-7-1 11:23:23<br>
	 */
	@RequestMapping("/save")
	public String save(@Valid Methz methz,BindingResult bindingResult,Model model,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()){
		  beforeInput(model,request);
		  return "clazz.methz.update";
		}else{
		  this.beforeSave(methz,request);
	      this.service.save(methz);
	      this.afterSave(methz,request);
		  return "redirect:/clazz/methz/home";
		}
	}

	/**
	 * 数据删除<br>
	 * 
	 * @todo<br>
	 * @return<br>
	  * @author:haipenge<br>
	 * haipenge @gmail.com <br>
	 * 创建日期:2016-7-1 11:23:23<br>
	 */
	@RequestMapping("/remove/{id}")
	public String remove(@PathVariable("id") Long id, RedirectAttributes redirectAttributes,RedirectAttributesModelMap model) {
		if(id!=null){
			Methz methz=this.service.get(id);
				if(methz!=null){
					if(beforeRemove(methz,model)){
						this.service.remove(methz);		
						//MessageBuilder.getInstance().setMessage(model,methz.getName()+" "+ this.getI18N("global.remove.success"));
					}
				}
		}
		return "redirect:/clazz/methz/home";
	}
	
	/**
	 * 数据批量删除<br>
	 * 
	 * @todo<br>
	 * @return<br>
	  * @author:haipenge<br>
	 * haipenge @gmail.com <br>
	 * 创建日期:2016-7-1 11:23:23<br>
	 */
	@RequestMapping("/multiRemove")
	@ResponseBody
	public String remove(@RequestParam(required=true) String  ids, RedirectAttributes redirectAttributes,Model model) {
		if(StringUtils.isNotEmpty(ids)){
			String [] idArray=ids.split(",");
			for(String id:idArray){
				Methz methz=this.service.get(Long.parseLong(id));
				if(methz!=null){
					if(beforeRemove(methz,model)){
						this.service.remove(methz);
						//MessageBuilder.getInstance().setMessage(model,methz.getName()+" "+ this.getI18N("global.remove.success"));		
					}
				}
			}
		}
		String messages = MessageBuilder.getInstance().getMessages(model);
		return AjaxResult.getInstance().buildDefaultResult(StringUtils.isEmpty(messages), messages);
	}
	
	/**
	 * 取得数据明细<br>
	 * @todo<br>
	 * @param id<br>
	 * @param model<br>
	 * @return<br>
	 * @author:haipenge<br>
	 * haipenge @gmail.com <br>
	 * 创建日期:2016-7-1 11:23:23<br>
	 */
	@RequestMapping("/detail/{id}")
	public String detail(@PathVariable Long id,Model model){
		if(id!=null){
			Methz methz=this.service.get(id);
			model.addAttribute("methz", methz);
		}
		return "clazz.methz.detail";
	}
	///////////////////////////////////////////////以下为回调函数///////////////////////////////////////////////

	/**
	 * 新增、编辑页面的前置逻辑处理<br>
	 * @todo<br>
	 * @param model<br>
	 * @param request<br>
	 * @author:haipenge<br>
	 * haipenge @gmail.com <br>
	 * 创建日期:2016-7-1 11:23:23<br>
	 */
	protected void beforeInput(Model model,HttpServletRequest request){
		    List<Clazz> clazzs=this.clazzService.getAll();
		    model.addAttribute("clazzs", clazzs);
	}
	
	/**
	 * 保存前的数据回调
	 * @todo
	 * @param methz
	 * @param request
	 * @author:haipenge
	 * 联系:haipenge@gmail.com
	 * 创建日期:2016-7-1 11:23:23
	 */
	protected void beforeSave(Methz methz,HttpServletRequest request){
	}
	
	/**
	 * 删除前 数据回调
	 */
	protected boolean beforeRemove(Methz methz,Model model){
		boolean res=true;
		
		return res;
	}
	
	/**
	 * 保存后的数据回调
	 * @todo
	 * @param methz
	 * @param request
	 * @author:haipenge
	 * 联系:haipenge@gmail.com
	 * 创建日期:2016-7-1 11:23:23
	 */
	protected void afterSave(Methz methz,HttpServletRequest request){
	   
	}
	

}
