package com.falcon.dsp.interceptor;

import com.falcon.dsp.common.Constant;
import com.falcon.dsp.common.UserViewConstant;
import com.falcon.dsp.enumration.UserRoleType;
import com.falcon.dsp.jdbc.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Zhouchunhui on 2016/5/26 0026.
 */
public class UserVisitInterceptor implements HandlerInterceptor {

    @Autowired
    private UserViewConstant userViewConstant;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
       /* UserModel userInfo = (UserModel) (request.getSession()).getAttribute(Constant.USER_INFO_SESSION);
        String path = request.getServletPath();

        if (userInfo == null) {
            response.sendRedirect("/login.shtml");
        }
        Map<String,String[]> map = request.getParameterMap();
        if(userInfo.getUserRole()== UserRoleType.ADMIN){
            if(userViewConstant.getAdminViews().contains(path)){
                return userInfo.getAccessParam().ifHasPermissions(map);
            }
        }else if(userInfo.getUserRole()== UserRoleType.AGENCY){
            if(userViewConstant.getAgencyViews().contains(path)){
                return userInfo.getAccessParam().ifHasPermissions(map);
            }
        }else if(userInfo.getUserRole()== UserRoleType.ADVERTISER){
            if(userViewConstant.getAdvertiserViews().contains(path)){
                return userInfo.getAccessParam().ifHasPermissions(map);
            }
        }

        response.sendRedirect("/logout.shtml");*/
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
