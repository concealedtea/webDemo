package com.falcon.dsp.service;

import com.falcon.dsp.jdbc.entity.AdSpacePosition;
import com.falcon.dsp.jdbc.model.AdSpacePositionModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author dongbin.yu
 * @from 2016-04-07
 * @since V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class AdSpacePositionServiceTest {

    @Autowired
    private AdSpacePositionService positionService;

    @Test
    public void testGetAdSpacePositionList() throws Exception {

        AdSpacePosition adSpacePosition = new AdSpacePosition();
        adSpacePosition.setPlatformId(1);

        List<AdSpacePositionModel> adSpacePositionList = positionService.getAdSpacePositionList(adSpacePosition);

    }

    @Test
    public void testGetStrategyPlatformList() {

    }
}