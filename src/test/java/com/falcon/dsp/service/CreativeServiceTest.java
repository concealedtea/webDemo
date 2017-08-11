package com.falcon.dsp.service;

import com.falcon.dsp.jdbc.model.CreativeListModel;
import com.falcon.dsp.jdbc.model.CreativeModel;
import com.falcon.dsp.jdbc.model.TableData;
import com.falcon.dsp.param.CreativeParam;
import com.falcon.dsp.param.CreativeTableParam;
import com.falcon.dsp.param.MaterialParam;
import com.falcon.dsp.rest.GdtRestHandler;
import com.falcon.dsp.rest.creative.request.GdtCreative;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.LinkedList;
import java.util.List;

/**
 * @author dongbin.yu
 * @from 2016-04-05
 * @since V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class CreativeServiceTest {

    @Autowired
    private GdtRestHandler restHandler;

    @Autowired
    private CreativeService service;

    @Test
    public void testCreate() throws Exception {

    }

    @Test
    public void testEdit() {

        CreativeParam creativeParam = new CreativeParam();
        creativeParam.setId(1);
        creativeParam.setCid(136651);
        creativeParam.setStrategyId(83018);
        creativeParam.setName("test creative modify");
        creativeParam.setRemark("test creative remark");
        creativeParam.setDestUrl("http://www.youku.com");
        creativeParam.setAdSpacePositionId(1);

        List<MaterialParam> materialParams = new LinkedList<>();
        MaterialParam materialParam = new MaterialParam();
        materialParam.setId(1);
        materialParam.setCrtSize(29);
        materialParam.setTitle("test material1");
        materialParams.add(materialParam);

        materialParam = new MaterialParam();
        materialParam.setId(2);
        materialParam.setCrtSize(29);
        materialParam.setTitle("test material2");
        materialParams.add(materialParam);

        materialParam = new MaterialParam();
        materialParam.setId(3);
        materialParam.setCrtSize(29);
        materialParam.setTitle("test material3");
        materialParams.add(materialParam);

        materialParam = new MaterialParam();
        materialParam.setCrtSize(29);
        materialParam.setTitle("test material4");
        materialParams.add(materialParam);

        creativeParam.setMaterials(materialParams);
        service.edit(creativeParam);
    }

    @Test
    public void testList() {

        CreativeTableParam param = new CreativeTableParam();
        param.setSearchKeyword("test");
        TableData<CreativeListModel> tableData = service.list(param);
    }

    @Test
    public void testRead() {
        CreativeParam param = new CreativeParam();
        param.setId(1);
        CreativeModel creativeModel = service.read(param);
    }

    @Test
    public void testGetGdtCreativeList() {
        List<GdtCreative> gdtCreativeTable = service.getGdtCreativeList(51967,117296);
    }
}