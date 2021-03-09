package com.guolong.handler;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice//這個註解會攔截帶有@Controller註解的類
public class ControllerExceptionHandler {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@ExceptionHandler(Exception.class)//這樣就代表所有例外都會到這個function來處理
	public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
		logger.error("Request URL : {}, Exception : {}",request.getRequestURI(),e);
		
		if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class ) != null) {
			throw e;
		}
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("url", request.getRequestURI());
		mv.addObject("exception", e);
		mv.setViewName("error/error");
		return mv;
	}
}
