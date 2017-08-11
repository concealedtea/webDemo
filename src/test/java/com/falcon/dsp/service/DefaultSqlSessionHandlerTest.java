package com.falcon.dsp.service;

import com.falcon.dsp.handler.DefaultSqlSessionHandler;
import com.falcon.dsp.jdbc.model.AdSpacePositionModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author dongbin.yu
 * @from 2016-04-05
 * @since V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class DefaultSqlSessionHandlerTest {

    @Autowired
    @Resource(name = "sqlHandler")
    private DefaultSqlSessionHandler handler;

    @Test
    public void testSelectList() throws Exception {

        List<AdSpacePositionModel> adSpacePositionModels = handler.selectList("adSpacePosition:select:list", null);

        System.out.println();
    }
}