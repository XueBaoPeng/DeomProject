package com.demo.project.api;

import com.demo.project.entity.Entity;
import com.demo.project.entity.Page;
import com.demo.project.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class BaseController {
	
	   public <T extends Entity<?>> Result  result(Page<T> page) {
	        return Result.success(page);
	    }

	    public Result  result(Object object) {
	    	return  Result.success(object);
	    }

	    @ExceptionHandler
	    public Result  controllerException(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
	        log.error("【web接口执行异常】", e);
	        if (e instanceof Exception) {
	            return Result.error(e);
	        }
	        return Result.error(e);
	    }

	    /**
	     * AJAX输出HTML，返回null
	     *
	     * @param html     内容
	     * @param response 用户响应
	     * @return 固定null
	     */
	    protected String ajaxHtml(String html, HttpServletResponse response) {
	        return ajax(html, "text/html", response);
	    }

	    /**
	     * AJAX输出json，返回null
	     *
	     * @param json     内容
	     * @param response 用户响应
	     * @return 固定null
	     */
	    public String ajaxJson(String json, HttpServletResponse response) {
	        return ajax(json, "application/json", response);
	    }

	    /**
	     * AJAX输出，返回null
	     *
	     * @param content  内容
	     * @param type     结果格式
	     * @param response 用户响应
	     * @return 固定null
	     */
	    private String ajax(String content, String type, HttpServletResponse response) {
	        try {
	            response.setContentType(type + ";charset=UTF-8");
	            response.setHeader("Pragma", "No-cache");
	            response.setHeader("Cache-Control", "no-cache");
	            response.setDateHeader("Expires", 0);
	            response.getWriter().write(content);
	            response.getWriter().flush();
	        } catch (IOException e) {
	            log.error("ajax请求异常", e);
	        }
	        return null;
	    }

}
