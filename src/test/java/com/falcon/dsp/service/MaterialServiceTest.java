package com.falcon.dsp.service;

import com.falcon.dsp.rest.GdtRestHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author dongbin.yu
 * @from 2016-04-08
 * @since V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MaterialServiceTest {

    @Autowired
    private GdtRestHandler restHandler;

    @Autowired
    private MaterialService service;

    @Test
    public void testCreate() throws Exception {


        service.create(101210,72);
    }

    @Test
    public void testPreviewList() {

        service.previewList(1);
    }
}