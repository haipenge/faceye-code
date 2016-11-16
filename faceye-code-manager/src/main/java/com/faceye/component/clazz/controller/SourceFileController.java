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


import com.faceye.component.clazz.entity.SourceFile;
import com.faceye.component.clazz.service.SourceFileService;
import com.faceye.feature.controller.BaseController;
import com.faceye.feature.util.http.HttpUtil;
import com.faceye.feature.util.AjaxResult;
import com.faceye.feature.util.view.MessageBuilder;
import com.faceye.feature.util.GlobalEntity;

/**
 * 模块:类管理->com.faceye.compoent.clazz.controller<br>
 * 说明:<br>
 * 实体:源码:com.faceye.component.clazz.entity.entity.SourceFile<br>
 * @author haipenge <br>
 * haipenge@gmail.com<br>
*  创建日期:2016-7-4 10:33:00<br>
 */
@Controller
@Scope("prototype")
@RequestMapping("/clazz/sourceFile")
public class SourceFileController extends BaseController<SourceFile, Long, SourceFileService> {
	@Autowired
	public SourceFileController(SourceFileService service) {
		super(service);
	}
	
	
	/**
	 * 首页<br>
	 * 
	 * @todo<br>
	 * @return<br>
	 * @author:haipenge<br> 
	 * haipenge@gmail.com <br>
	 * 创建日期2016-7-4 10:33:00<br>
	 */
	@RequestMapping("/home")
	public String home(HttpServletRequest request, Model model) {
		Map searchParams=HttpUtil.getRequestParams(request);
		beforeInput(model,request);
		Page<SourceFile> page = this.service.getPage(searchParams, getPage(searchParams), getSize(searchParams));
		model.addAttribute("page", page);
		resetSearchParams(searchParams);
		model.addAttribute("searchParams", searchParams);
		GlobalEntity global=new GlobalEntity();
		global.setTitle(this.getI18N("clazz.sourceFile"));
		model.addAttribute("global",global);
		return "clazz.sourceFile.manager";
	}

	/**
	 * 转向编辑或新增页面<br>
	 * 
	 * @todo<br>
	 * @return<br>
	 * @author:haipenge<br>
	 * haipenge @gmail.com <br>
	 * 创建日期:2016-7-4 10:33:00<br>
	 */
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id,Model model,HttpServletRequest request) {
		if(id!=null){
			beforeInput(model,request);
			SourceFile sourceFile=this.service.get(id);
			model.addAttribute("sourceFile", sourceFile);
		}
		GlobalEntity global=new GlobalEntity();
		global.setTitle(this.getI18N("clazz.sourceFile.edit"));
		model.addAttribute("global",global);
		return "clazz.sourceFile.update";
	}
	
	/**
	 * 转向新增页面<br>
	 * @todo<br>
	 * @param model<br>
	 * @return<br>
	 * @author:haipenge<br>
	 * haipenge @gmail.com <br>
	 * 创建日期:2016-7-4 10:33:00<br>
	 */
	@RequestMapping(value="/input")
	public String input(SourceFile sourceFile, Model model,HttpServletRequest request){
		beforeInput(model,request);
		GlobalEntity global=new GlobalEntity();
		global.setTitle(this.getI18N("clazz.sourceFile.add"));
		model.addAttribute("global",global);
		return "clazz.sourceFile.update";
	}
	

	/**
	 * 数据保存<br>
	 * @author:haipenge<br>
	 * haipenge @gmail.com <br>
	 * 创建日期:2016-7-4 10:33:00<br>
	 */
	@RequestMapping("/save")
	public String save(@Valid SourceFile sourceFile,BindingResult bindingResult,Model model,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()){
		  beforeInput(model,request);
		  return "clazz.sourceFile.update";
		}else{
		  this.beforeSave(sourceFile,request);
	      this.service.save(sourceFile);
	      this.afterSave(sourceFile,request);
		  return "redirect:/clazz/sourceFile/home";
		}
	}

	/**
	 * 数据删除<br>
	 * 
	 * @todo<br>
	 * @return<br>
	  * @author:haipenge<br>
	 * haipenge @gmail.com <br>
	 * 创建日期:2016-7-4 10:33:00<br>
	 */
	@RequestMapping("/remove/{id}")
	public String remove(@PathVariable("id") Long id, RedirectAttributes redirectAttributes,RedirectAttributesModelMap model) {
		if(id!=null){
			SourceFile sourceFile=this.service.get(id);
				if(sourceFile!=null){
					if(beforeRemove(sourceFile,model)){
						this.service.remove(sourceFile);		
						//MessageBuilder.getInstance().setMessage(model,sourceFile.getName()+" "+ this.getI18N("global.remove.success"));
					}
				}
		}
		return "redirect:/clazz/sourceFile/home";
	}
	
	/**
	 * 数据批量删除<br>
	 * 
	 * @todo<br>
	 * @return<br>
	  * @author:haipenge<br>
	 * haipenge @gmail.com <br>
	 * 创建日期:2016-7-4 10:33:00<br>
	 */
	@RequestMapping("/multiRemove")
	@ResponseBody
	public String remove(@RequestParam(required=true) String  ids, RedirectAttributes redirectAttributes,Model model) {
		if(StringUtils.isNotEmpty(ids)){
			String [] idArray=ids.split(",");
			for(String id:idArray){
				SourceFile sourceFile=this.service.get(Long.parseLong(id));
				if(sourceFile!=null){
					if(beforeRemove(sourceFile,model)){
						this.service.remove(sourceFile);
						//MessageBuilder.getInstance().setMessage(model,sourceFile.getName()+" "+ this.getI18N("global.remove.success"));		
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
	 * 创建日期:2016-7-4 10:33:00<br>
	 */
	@RequestMapping("/detail/{id}")
	public String detail(@PathVariable Long id,Model model){
		if(id!=null){
			SourceFile sourceFile=this.service.get(id);
			model.addAttribute("sourceFile", sourceFile);
		}
		return "clazz.sourceFile.detail";
	}
	///////////////////////////////////////////////以下为回调函数///////////////////////////////////////////////

	/**
	 * 新增、编辑页面的前置逻辑处理<br>
	 * @todo<br>
	 * @param model<br>
	 * @param request<br>
	 * @author:haipenge<br>
	 * haipenge @gmail.com <br>
	 * 创建日期:2016-7-4 10:33:00<br>
	 */
	protected void beforeInput(Model model,HttpServletRequest request){
	}
	
	/**
	 * 保存前的数据回调
	 * @todo
	 * @param sourceFile
	 * @param request
	 * @author:haipenge
	 * 联系:haipenge@gmail.com
	 * 创建日期:2016-7-4 10:33:00
	 */
	protected void beforeSave(SourceFile sourceFile,HttpServletRequest request){
	}
	
	/**
	 * 删除前 数据回调
	 */
	protected boolean beforeRemove(SourceFile sourceFile,Model model){
		boolean res=true;
		
		return res;
	}
	
	/**
	 * 保存后的数据回调
	 * @todo
	 * @param sourceFile
	 * @param request
	 * @author:haipenge
	 * 联系:haipenge@gmail.com
	 * 创建日期:2016-7-4 10:33:00
	 */
	protected void afterSave(SourceFile sourceFile,HttpServletRequest request){
	   
	}
	

}
