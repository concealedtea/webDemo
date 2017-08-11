package com.falcon.dsp.service;

import com.falcon.dsp.enumration.UserRoleType;
import com.falcon.dsp.handler.SqlSessionHandler;
import com.falcon.dsp.jdbc.entity.UserInfo;
import com.falcon.dsp.jdbc.model.*;
import com.falcon.dsp.param.CreativeParam;
import com.falcon.dsp.param.CreativeTableParam;
import com.falcon.dsp.param.MaterialParam;
import com.falcon.dsp.param.UserParam;
import com.falcon.dsp.rest.GdtRestHandler;
import com.falcon.dsp.rest.creative.request.GdtCreative;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * @author yujuan.zhang
 * @from 2016-07-08
 * @since V1.2
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class UserInfoServiceTest {

    @Resource(name = "sqlHandler")
    private SqlSessionHandler sqlSessionHandler;

    @Autowired
    private UserInfoService service;

    @Test
    public void testList() {
        UserInfo user = new UserInfo();
        user.setId(1);
        user.setUserRole(UserRoleType.AGENCY);

        TableParam param = new TableParam();
        param.setStart(1);
        param.setLength(40);
        TableData<UserListModel> tableData = service.getUserList(param, user);
    }
}