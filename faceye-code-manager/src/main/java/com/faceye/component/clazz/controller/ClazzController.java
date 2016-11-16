package com.faceye.component.clazz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
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

import com.faceye.component.clazz.entity.Clazz;
import com.faceye.component.clazz.entity.Methz;
import com.faceye.component.clazz.entity.Pkg;
import com.faceye.component.clazz.service.ClazzService;
import com.faceye.component.clazz.service.CodeSearchService;
import com.faceye.component.clazz.service.MethzService;
import com.faceye.component.clazz.service.PkgService;
import com.faceye.component.clazz.service.impl.SearchResult;
import com.faceye.feature.controller.BaseController;
import com.faceye.feature.util.AjaxResult;
import com.faceye.feature.util.GlobalEntity;
import com.faceye.feature.util.http.HttpUtil;
import com.faceye.feature.util.view.MessageBuilder;

/**
 * 模块:类管理->com.faceye.compoent.clazz.controller<br>
 * 说明:<br>
 * 实体:类管理:com.faceye.component.clazz.entity.entity.Clazz<br>
 * 
 * @author haipenge <br>
 *         haipenge@gmail.com<br>
 *         创建日期:2016-7-1 11:23:24<br>
 */
@Controller
@Scope("prototype")
@RequestMapping("/clazz/clazz")
public class ClazzController extends BaseController<Clazz, Long, ClazzService> {
	@Autowired
	protected PkgService pkgService = null;
	@Autowired
	private CodeSearchService searchService = null;
	@Autowired
	private MethzService methzService=null;

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
	 * 						haipenge@gmail.com <br>
	 *                      创建日期2016-7-1 11:23:24<br>
	 */
	@RequestMapping("/home")
	public String home(HttpServletRequest request, Model model) {
		Map searchParams = HttpUtil.getRequestParams(request);
		beforeInput(model, request);
		Page<Clazz> page = this.service.getPage(searchParams, getPage(searchParams), getSize(searchParams));
		model.addAttribute("page", page);
		resetSearchParams(searchParams);
		model.addAttribute("searchParams", searchParams);
		GlobalEntity global = new GlobalEntity();
		global.setTitle(this.getI18N("clazz.clazz"));
		model.addAttribute("global", global);
		return "clazz.clazz.manager";
	}

	/**
	 * 转向编辑或新增页面<br>
	 * 
	 * @todo<br>
	 * @return<br>
	 * @author:haipenge<br>
	 * 						haipenge @gmail.com <br>
	 *                      创建日期:2016-7-1 11:23:24<br>
	 */
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
		if (id != null) {
			beforeInput(model, request);
			Clazz clazz = this.service.get(id);
			model.addAttribute("clazz", clazz);
		}
		GlobalEntity global = new GlobalEntity();
		global.setTitle(this.getI18N("clazz.clazz.edit"));
		model.addAttribute("global", global);
		return "clazz.clazz.update";
	}

	/**
	 * 转向新增页面<br>
	 * @todo<br>
	 * 
	 * @param model<br>
	 * @return<br>
	 * @author:haipenge<br>
	 * 						haipenge @gmail.com <br>
	 *                      创建日期:2016-7-1 11:23:24<br>
	 */
	@RequestMapping(value = "/input")
	public String input(Clazz clazz, Model model, HttpServletRequest request) {
		beforeInput(model, request);
		GlobalEntity global = new GlobalEntity();
		global.setTitle(this.getI18N("clazz.clazz.add"));
		model.addAttribute("global", global);
		return "clazz.clazz.update";
	}

	/**
	 * 数据保存<br>
	 * @author:haipenge<br>
	 * 						haipenge @gmail.com <br>
	 *                      创建日期:2016-7-1 11:23:24<br>
	 */
	@RequestMapping("/save")
	public String save(@Valid Clazz clazz, BindingResult bindingResult, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			beforeInput(model, request);
			return "clazz.clazz.update";
		} else {
			this.beforeSave(clazz, request);
			this.service.save(clazz);
			this.afterSave(clazz, request);
			return "redirect:/clazz/clazz/home";
		}
	}

	/**
	 * 数据删除<br>
	 * 
	 * @todo<br>
	 * @return<br>
	 * @author:haipenge<br>
	 * 						haipenge @gmail.com <br>
	 *                      创建日期:2016-7-1 11:23:24<br>
	 */
	@RequestMapping("/remove/{id}")
	public String remove(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, RedirectAttributesModelMap model) {
		if (id != null) {
			Clazz clazz = this.service.get(id);
			if (clazz != null) {
				if (beforeRemove(clazz, model)) {
					this.service.remove(clazz);
					// MessageBuilder.getInstance().setMessage(model,clazz.getName()+" "+ this.getI18N("global.remove.success"));
				}
			}
		}
		return "redirect:/clazz/clazz/home";
	}

	/**
	 * 数据批量删除<br>
	 * 
	 * @todo<br>
	 * @return<br>
	 * @author:haipenge<br>
	 * 						haipenge @gmail.com <br>
	 *                      创建日期:2016-7-1 11:23:24<br>
	 */
	@RequestMapping("/multiRemove")
	@ResponseBody
	public String remove(@RequestParam(required = true) String ids, RedirectAttributes redirectAttributes, Model model) {
		if (StringUtils.isNotEmpty(ids)) {
			String[] idArray = ids.split(",");
			for (String id : idArray) {
				Clazz clazz = this.service.get(Long.parseLong(id));
				if (clazz != null) {
					if (beforeRemove(clazz, model)) {
						this.service.remove(clazz);
						// MessageBuilder.getInstance().setMessage(model,clazz.getName()+" "+ this.getI18N("global.remove.success"));
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
	 * 
	 * @param id<br>
	 * @param model<br>
	 * @return<br>
	 * @author:haipenge<br>
	 * 						haipenge @gmail.com <br>
	 *                      创建日期:2016-7-1 11:23:24<br>
	 */
	@RequestMapping("/detail/{id}")
	public String detail(@PathVariable Long id, Model model) {
		if (id != null) {
			Clazz clazz = this.service.get(id);
			model.addAttribute("clazz", clazz);
			Page<SearchResult> searchResults = this.searchService.search(clazz.getName(), 1, 10);
			// logger.debug(">>FaceYe--> get Serarch Result of:"+clazz.getName()+",size is:"+searchResults.getSize());
			model.addAttribute("searchResults", searchResults);
			if (searchResults == null || CollectionUtils.isEmpty(searchResults.getContent())) {
				List<Methz> methzs = this.methzService.getMethzsByClazz(clazz);
				model.addAttribute("methzs", methzs);
				// 取得当前包下的其它类
				Map params = new HashMap();
				params.put("EQ|pk.$id", clazz.getPkg().getId());
				Page<Clazz> clazzs = this.service.getPage(params, 1, 10);
				model.addAttribute("clazzs", clazzs);
			}
		}
		return "clazz.clazz.detail";
	}
	/////////////////////////////////////////////// 以下为回调函数///////////////////////////////////////////////

	/**
	 * 新增、编辑页面的前置逻辑处理<br>
	 * @todo<br>
	 * 
	 * @param model<br>
	 * @param request<br>
	 * @author:haipenge<br>
	 * 						haipenge @gmail.com <br>
	 *                      创建日期:2016-7-1 11:23:24<br>
	 */
	protected void beforeInput(Model model, HttpServletRequest request) {
		List<Pkg> pkgs = this.pkgService.getAll();
		model.addAttribute("pkgs", pkgs);
	}

	/**
	 * 保存前的数据回调
	 * 
	 * @todo
	 * @param clazz
	 * @param request
	 * @author:haipenge 联系:haipenge@gmail.com 创建日期:2016-7-1 11:23:24
	 */
	protected void beforeSave(Clazz clazz, HttpServletRequest request) {
	}

	/**
	 * 删除前 数据回调
	 */
	protected boolean beforeRemove(Clazz clazz, Model model) {
		boolean res = true;

		return res;
	}

	/**
	 * 保存后的数据回调
	 * 
	 * @todo
	 * @param clazz
	 * @param request
	 * @author:haipenge 联系:haipenge@gmail.com 创建日期:2016-7-1 11:23:24
	 */
	protected void afterSave(Clazz clazz, HttpServletRequest request) {

	}

}
