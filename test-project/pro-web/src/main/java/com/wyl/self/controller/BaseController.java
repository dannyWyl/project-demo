package com.wyl.self.controller;

import com.wyl.self.model.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wyl
 * @create 2017-07-29 14:26
 * @desc ${DESCRIPTION}
 **/

@Controller
@RequestMapping(value = "/base")
public class BaseController<T> {

    private static Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private com.wyl.self.service.Test testser;

    public static final String BUSINESS_PATH = "views/active";
    /**
     * 首页
     */
    public static final String PAGE_INDEX = "index";

    /**
     * 新建页面
     */
    public static final String PAGE_EDIT_NEW = "edit_new";

    /**
     * 修改页
     */
    public static final String PAGE_EDIT= "edit";

    /**
     * 详情页
     */
    public static final String PAGE_SHOW = "show";

    @RequestMapping(value="/test")
    public ModelAndView index(HttpServletRequest request, Test test) {
        System.out.println("*************************************************");
        ModelAndView mv = new ModelAndView();
        try {
            testser.test("1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String url = getMappingUrl(BUSINESS_PATH, PAGE_INDEX);
        System.out.println(url+"*********************************************");
        mv.setViewName(url);
        return mv;
    }

    @RequestMapping(value="/test1")
    public ModelAndView index1(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        String url = getMappingUrl(BUSINESS_PATH, PAGE_SHOW);
        mv.setViewName(url);
        return mv;
    }

    /**
     * 获取映射路径
     *
     * @param businessPath 业务路径
     * @param page 页面
     * @return
     */
    protected String getMappingUrl(String businessPath, String page) {
        return new StringBuilder().append(businessPath).append("/").append(page).toString();
    }


}
