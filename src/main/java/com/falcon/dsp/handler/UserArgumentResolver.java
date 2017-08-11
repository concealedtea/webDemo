package com.falcon.dsp.handler;

import com.falcon.dsp.annotation.UserAttribute;
import com.falcon.dsp.common.Constant;
import com.falcon.dsp.jdbc.entity.UserInfo;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author dongbin.yu
 * @from 2016-04-19
 * @since V1.0
 */
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        UserAttribute userAttribute = parameter.getParameterAnnotation(UserAttribute.class);
        return userAttribute != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpSession session = request.getSession();

        UserInfo user = (UserInfo)session.getAttribute(Constant.USER_INFO_SESSION);

        return user;
    }
}
