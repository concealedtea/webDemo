package com.falcon.dsp.handler;

import com.falcon.dsp.common.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author dongbin.yu
 * @from 2016-04-21
 * @since V1.0
 */
public class RoleReturnValueHandler implements HandlerMethodReturnValueHandler {

    private static Logger logger = LoggerFactory.getLogger(RoleReturnValueHandler.class);

    private static Properties properties = new Properties();

    static{
        InputStream inputStream = RoleReturnValueHandler.class.getResourceAsStream("/title.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("title properties load failed");
        }
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return RoleAndView.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {

        RoleAndView roleAndView = (RoleAndView) returnValue;

        String viewName = roleAndView.getUserInfo().getUserRole().getName() + "." + roleAndView.getView();

        String title = properties.getProperty(roleAndView.getView());

        if (StringUtil.hasText(title)) {
            mavContainer.addAttribute("title", title);
        }
        mavContainer.setViewName(viewName);

    }
}
