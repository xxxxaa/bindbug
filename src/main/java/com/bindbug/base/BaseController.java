package com.bindbug.base;

import com.bindbug.util.ViewResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;


public class BaseController {

    /** Logger instance. **/
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String REDIRECT = "redirect:";
    private static final String URL_ROOT = "/";
    private static final String URL_HOMEPAGE = "/homepage.htm";
    private static final String DEFAULT_JSON_FAIL_RESULT = ViewResult.newInstance().fail().json();
    private static final String DEFAULT_JSON_SUCCESS_RESULT = ViewResult.newInstance().fail().json();
    
    private static final String ERROR_PAGE = "/error/errorPage.htm";
    private static final String NOT_FOUND_PAGE = "/error/not_found.htm";
    private static final String INTERNAL_ERROR_PAGE = "/error/internal_error.htm";
    private static final String UNAUTHORIZED_PAGE = "/error/unauthorized.htm";

    /**
     * 重定向到
     * @param toUrl
     * @return
     */
    protected String redirectTo(String toUrl) {
        if (toUrl == null) {
            toUrl = URL_ROOT;
        }
        StringBuffer sb = new StringBuffer();
        return sb.append(REDIRECT).append(toUrl).toString();
    }
    
    /**
     * 通用错误页面
     * @return
     */
    protected String redirectToErrorPage() {
        return redirectToErrorPage(null);
    }

    /**
     * 通用错误页面
     * @return
     */
    protected String redirectToErrorPage(String reason) {
        StringBuffer sb = new StringBuffer();
        sb.append(ERROR_PAGE).append("?reason=").append(encodeParam(reason)).toString();
        return redirectTo(sb.toString());
    }
    
    /**
     * 页面未找到
     * @return
     */
    protected String redirectTo404Page() {
        return redirectTo404Page(null);
    }
    
    /**
     * 页面未找到
     * @return
     */
    protected String redirectTo404Page(String reason) {
    	StringBuffer sb = new StringBuffer();
        sb.append(NOT_FOUND_PAGE).append("?reason=").append(encodeParam(reason)).toString();
        return redirectTo(sb.toString());
    }

	/**
	 * 跳转到首页
	 * @return
	 */
	protected String redirectToIndexPage() {
		return redirectTo(URL_ROOT);
	}
	
	/**
	 * 跳转到首页
	 * @return
	 */
	protected String redirectToHomePage() {
		return redirectTo(URL_HOMEPAGE);
	}
    
    /**
     * 服务器内部错误（服务异常）
     * @return
     */
    protected String redirectTo500Page() {
    	return redirectTo500Page(null);
    }
    
    /**
     * 服务器内部错误（服务异常）
     * @return
     */
    protected String redirectTo500Page(String reason) {
    	StringBuffer sb = new StringBuffer();
        sb.append(INTERNAL_ERROR_PAGE).append("?reason=").append(encodeParam(reason)).toString();
        return redirectTo(sb.toString());
    }
    
    /**
     * 无权限页面
     * @return
     */
    protected String redirectToNoPermissionPage() {
        return redirectToNoPermissionPage(null);
    }
    
    /**
     * 无权限页面
     * @return
     */
    protected String redirectToNoPermissionPage(String reason) {
    	StringBuffer sb = new StringBuffer();
        sb.append(UNAUTHORIZED_PAGE).append("?reason=").append(encodeParam(reason)).toString();
        return redirectTo(sb.toString());
    }
    
    /**
     * 返回页面结果
     * @param viewPath
     * @return
     */
    protected String pageView(String viewPath) {
    	return StringUtils.isBlank(viewPath) ? redirectToErrorPage() : viewPath;
    }
    
    /**
     * 返回json结果
     * @param jsonStr
     * @return
     */
    protected String jsonView(String jsonStr) {
    	return StringUtils.isNotBlank(jsonStr) ? jsonStr : DEFAULT_JSON_FAIL_RESULT;
    }
    
    /**
     * 返回json结果
     * @param jsonObject\
     * @return
     */
    protected String jsonView(ViewResult jsonObject) {
    	return jsonObject != null ? jsonObject.json() : DEFAULT_JSON_FAIL_RESULT;
    }
    
    /**
     * 返回json结果
     * @return
     */
    protected String jsonViewFail() {
    	return DEFAULT_JSON_FAIL_RESULT;
    }
    
    /**
     * 返回json结果
     * @return
     */
    protected String jsonViewSuccess() {
    	return DEFAULT_JSON_SUCCESS_RESULT;
    }
    

    
    /**
     * 编码参数 
     * @param param
     * @return
     */
	protected String encodeParam(String param) {
		if(StringUtils.isNotBlank(param)){
    		try {
    			param = URLEncoder.encode(param, "utf-8");
			} catch (Exception e) {
				param = null;
			}
    	}
    	
    	if(StringUtils.isBlank(param)){
    		param = StringUtils.EMPTY;
    	}
		return param;
	}
	
	/**
     * 解码参数 
     * @param param
     * @return
     */
	protected String decodeParam(String param, String defaultValue){
    	if(StringUtils.isNotBlank(param) && param.trim().length() <= 1){
    		param = null;
    	}
    	
    	if(StringUtils.isBlank(param)){
    		param = defaultValue;
    	}else{
    		try {
    			param = URLDecoder.decode(param, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
    	}
    	
    	if(StringUtils.isBlank(param)){
    		param = defaultValue;
    	}
    	return param;
    }
}