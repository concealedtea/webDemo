package com.falcon.dsp.service;

import com.falcon.dsp.enumration.GdtSyncStatus;
import com.falcon.dsp.param.TargetParam;
import com.falcon.dsp.rest.target.request.GdtTarget;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author dongbin.yu
 * @from 2016-04-12
 * @since V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TargetServiceTest {

    @Autowired
    private TargetService service;

    @Test
    public void testCreate() throws Exception {

        TargetParam param = new TargetParam();
        param.setUid(51967);
//        param.setGender(new Gender[]{Gender.FEMALE});

//        param.setConnection(new Connection[]{Connection.NET2G, Connection.NET3G, Connection.NET4G, Connection.WIFI});
//        param.setEducation(new Education[]{Education.DOCTOR, Education.JUNIOR});
//        param.setOs(new OperationSystem[]{OperationSystem.ANDROID, OperationSystem.IOS});
//        param.setTelcom(new Operator[]{Operator.CMC, Operator.CTC, Operator.CUC});
//        param.setArea(new Integer[]{110000, 120000, 130000});
//        param.setAge("10~19");

//        param.setConnection(new Connection[]{Connection.NET2G, Connection.NET3G, Connection.NET4G, Connection.WIFI});
        /*param.setEducation(new Education[]{Education.DOCTOR, Education.JUNIOR});
        param.setOs(new OperationSystem[]{OperationSystem.ANDROID, OperationSystem.IOS});
        param.setTelcom(new Operator[]{Operator.CMC, Operator.CTC, Operator.CUC});
        param.setArea(new Integer[]{110000, 120000, 130000});
        param.setAge("10~19");*/

        param.setStatus(GdtSyncStatus.SYNC_INIT.getStatus());
        param.setStrategyId(111111222);

        service.create(param);
    }

    @Test
    public void testEdit() throws Exception {

        TargetParam param = new TargetParam();
        param.setUid(51967);
        param.setTid(86670);
//        param.setGender(new Gender[]{Gender.MALE});
//        param.setConnection(new Connection[]{Connection.NET2G, Connection.NET4G, Connection.WIFI});
//        param.setEducation(new Education[]{Education.DOCTOR, Education.JUNIOR});
//        param.setOs(new OperationSystem[]{OperationSystem.ANDROID, OperationSystem.IOS});
//        param.setTelcom(new Operator[]{Operator.CMC, Operator.CTC, Operator.CUC});
//        param.setArea(new Integer[]{110000, 120000, 140000});
        param.setAge("20~25");
        param.setStatus(GdtSyncStatus.SYNC_INIT.getStatus());
        param.setStrategyId(1111111);

        service.edit(param);
    }

    // 从广点通获取定向信息
    @Test
    public void testGetGdtTargetInfo() {
        GdtTarget gdtTarget = service.getGdtTargetInfo(59881, 87086);
    }
}