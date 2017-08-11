package com.falcon.dsp.service;

import com.falcon.dsp.enumration.CustomerRegistionType;
import com.falcon.dsp.param.AdvertiserParam;
import com.falcon.dsp.rest.GdtRestHandler;
import com.falcon.dsp.rest.advertiser.response.GdtAdvertiser;
import com.falcon.dsp.rest.advertiser.response.GdtAdvertiserResult;
import com.falcon.dsp.rest.finance.response.GdtFinanceBalanceTableResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Zhouchunhui
 * @from 2016-04-12
 * @since V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class AdvertiserServiceTest {
    @Autowired
    AdvertiserService advertiserService;
    @Autowired
    GdtRestHandler gdtRestHandler;
    @Autowired
    AgencyService agencyService;
    @Autowired
    private FinanceService financeService;
    @Test
    public void testGetGdtAdvertiserByUid(){
        int uid = 1021415;//51967;
        Map map = new HashMap<String,String>();
        map.put("uid",uid);
        GdtFinanceBalanceTableResult sd = financeService.listBalance(uid);
        Object obj = gdtRestHandler.get("account/get_day_cost",Object.class,map);
        //GdtAdvertiser ga = advertiserService.getGdtAdvertiserByUid(uid,null);
        System.out.println("sdsa");
    }

    @Test
    public void insertAdvertisers(){
        int[] array = new int[]{51967};
        for (int i=0;i<array.length;i++){
            GdtAdvertiserResult result = advertiserService.getGdtAdvertiserByUid(array[i],null);
            GdtAdvertiser ga = result.getData();
            ga.setUid(array[i]);
            int a = advertiserService.insertAdvertiser(ga);
            System.out.println(i+"-"+a);
        }
    }
    
   @Test
    public void testGetOneByUid(){
        int uid = 51967;//1021415;//51967;
       GdtAdvertiserResult result = advertiserService.getGdtAdvertiserByUid(uid,null);
        System.out.println(result);
    }
    
    @Test
    public void testCreateGdtLocal(){
        int uid = 51967;//1021415;//51967;
        AdvertiserParam advertiserParam = new AdvertiserParam();

//    	advertiserParam.setQualification("{\"qualificationurl1\":\"http://pgdt.gtimg.cn/gdt/0/CAAAMr2AABU0tR5BXUze2hK.png/0?ck=ab7c9f74451d83cb210d80579e8df8b3\"}");
    	HashMap<String, String> qualification = new HashMap<>();
    	qualification.put("qualificationurl1","/CAAAMr2AABU0tR5BXUze2hK.png/0?ck=ab7c9f74451d83cb210d80579e8df8b3");
    	advertiserParam.setQualification(qualification);

    	advertiserParam.setContactPersonTelephone("0755-86013399");
    	advertiserParam.setRzQqNumber("863524078");
    	advertiserParam.setAppId(null);
    	advertiserParam.setBusinessBrand("businessBrand");
    	advertiserParam.setContactPersonMobile("13900000000");

    	///必填
    	advertiserParam.setIcpImageUrl("http://pgdt.gtimg.cn/gdt/0/CCJFzuAAABT9AOBCifQM6cF.jpg/0?ck=b78d1e97619d2d5ee244522285c34164");
    	advertiserParam.setSiteUrl("http://www.baiddu.com"+"/gdt/0/CCJFzuAAABT9AOBCifQM6cF.jpg/0?ck=b78d1e97619d2d5ee244522285c34164");
    	advertiserParam.setIndustryId(21474836481l);
    	advertiserParam.setCertification("http://pgdt.gtimg.cn/gdt/0/CCJFzuAAABT9AOBCifQM6cF.jpg/0?ck=b78d1e97619d2d5ee244522285c34164");
    	advertiserParam.setCorporationLicence("1363636363636");
    	advertiserParam.setCorporation("corporation_name9718462");
    	//返回 79363
    		 
//		    	GdtAdvertiser gdtAdvertiser = new GdtAdvertiser();
//		    	gdtAdvertiser.setIcpImageUrl("http://www.baiddu.com"+"/gdt/0/CCJFzuAAABT9AOBCifQM6cF.jpg/0?ck=b78d1e97619d2d5ee244522285c34164");
//		    	gdtAdvertiser.setQualification("{\"qualificationurl1\":\"http://pgdt.gtimg.cn/gdt/0/CAAAMr2AABU0tR5BXUze2hK.png/0?ck=ab7c9f74451d83cb210d80579e8df8b3\"}");
//		    	gdtAdvertiser.setContactPersonTelephone("0755-86013399");
//		    	gdtAdvertiser.setRzQqNumber("863524078");
//		    	gdtAdvertiser.setAppId(null);
//		    	gdtAdvertiser.setBusinessBrand("businessBrand");
//		    	gdtAdvertiser.setContactPersonMobile("13900000000");
//		
//		    	//必填
//		    	gdtAdvertiser.setIcpImageUrl("http://pgdt.gtimg.cn/gdt/0/CCJFzuAAABT9AOBCifQM6cF.jpg/0?ck=b78d1e97619d2d5ee244522285c34164");
//		    	gdtAdvertiser.setSiteUrl("http://www.baiddu.com"+"/gdt/0/CCJFzuAAABT9AOBCifQM6cF.jpg/0?ck=b78d1e97619d2d5ee244522285c34164");
//		    	gdtAdvertiser.setIndustryId(21474836481l);
//		    	gdtAdvertiser.setCertification("http://pgdt.gtimg.cn/gdt/0/CCJFzuAAABT9AOBCifQM6cF.jpg/0?ck=b78d1e97619d2d5ee244522285c34164");
//		    	gdtAdvertiser.setCorporationLicence("1363636363636");
//		    	gdtAdvertiser.setCorporation("corporation_name9718462");
		    	
      	advertiserService.addAdvertiser(advertiserParam);
        System.out.println(0);
    }
    @Test
    public void testUpdateGdtLocal(){
    	int uid = 79373;//79369 ,79373
    	AdvertiserParam advertiserParam = new AdvertiserParam();
    	
    	HashMap<String, String> qualification = new HashMap<>();
    	qualification.put("qualificationurl1","/CAAAMr2AABU0tR5BXUze2hK.png/0?ck=ab7c9f74451d83cb210d80579e8df8b3");
    	//gdt 可选项
    	advertiserParam.setQualification(qualification);
    	advertiserParam.setContactPersonTelephone("0755-86013399");
    	advertiserParam.setRzQqNumber("863524078");
    	advertiserParam.setAppId(null);
    	advertiserParam.setBusinessBrand("businessBrand");
    	advertiserParam.setContactPersonMobile("13900000000");
    	
    	//必填 更新必填项
    	advertiserParam.setUid(uid);
    	//必填
    	advertiserParam.setIcpImageUrl("http://pgdt.gtimg.cn/gdt/0/CCJFzuAAABT9AOBCifQM6cF.jpg/0?ck=b78d1e97619d2d5ee244522285c34164");
    	advertiserParam.setSiteUrl("http://www.baiddu.com"+"/gdt/0/CCJFzuAAABT9AOBCifQM6cF.jpg/0?ck=b78d1e97619d2d5ee244522285c34164");
    	advertiserParam.setIndustryId(21474836481l);
    	advertiserParam.setCertification("http://pgdt.gtimg.cn/gdt/0/CCJFzuAAABT9AOBCifQM6cF.jpg/0?ck=b78d1e97619d2d5ee244522285c34164");
    	advertiserParam.setCorporationLicence("1363636363636");
    	advertiserParam.setCorporation("corporation_name9718462");
    	//返回 79363
    	
    	
//		    	GdtAdvertiser gdtAdvertiser = new GdtAdvertiser();
//		    	gdtAdvertiser.setIcpImageUrl("http://www.baiddu.com"+"/gdt/0/CCJFzuAAABT9AOBCifQM6cF.jpg/0?ck=b78d1e97619d2d5ee244522285c34164");
//		    	gdtAdvertiser.setQualification("{\"qualificationurl1\":\"http://pgdt.gtimg.cn/gdt/0/CAAAMr2AABU0tR5BXUze2hK.png/0?ck=ab7c9f74451d83cb210d80579e8df8b3\"}");
//		    	gdtAdvertiser.setContactPersonTelephone("0755-86013399");
//		    	gdtAdvertiser.setRzQqNumber("863524078");
//		    	gdtAdvertiser.setAppId(null);
//		    	gdtAdvertiser.setBusinessBrand("businessBrand");
//		    	gdtAdvertiser.setContactPersonMobile("13900000000");
//		
//		    	//必填
//		    	gdtAdvertiser.setIcpImageUrl("http://pgdt.gtimg.cn/gdt/0/CCJFzuAAABT9AOBCifQM6cF.jpg/0?ck=b78d1e97619d2d5ee244522285c34164");
//		    	gdtAdvertiser.setSiteUrl("http://www.baiddu.com"+"/gdt/0/CCJFzuAAABT9AOBCifQM6cF.jpg/0?ck=b78d1e97619d2d5ee244522285c34164");
//		    	gdtAdvertiser.setIndustryId(21474836481l);
//		    	gdtAdvertiser.setCertification("http://pgdt.gtimg.cn/gdt/0/CCJFzuAAABT9AOBCifQM6cF.jpg/0?ck=b78d1e97619d2d5ee244522285c34164");
//		    	gdtAdvertiser.setCorporationLicence("1363636363636");
//		    	gdtAdvertiser.setCorporation("corporation_name9718462");
    	
    	//GdtAdvertiserResult result = advertiserService.updateAdvertiser(advertiserParam);
    	//System.out.println(result);
    }
    
    @Test
    public void testInsert(){
    	int uid = 33519671;//1021415;//51967;
    	AdvertiserParam advertiserParam = new AdvertiserParam();
    	advertiserParam.setCorporation("companyName");
    	advertiserParam.setCorporationLicence("147852369");
    	advertiserParam.setCertification("/d/ddd/cer.jpg");
    	advertiserParam.setIndustryId(21474836484l);
//    	advertiserParam.setQualification("/d/ddd/qual.jpg");
    	advertiserParam.setSiteUrl("{a:a.jpg,b:b.jpg}");
    	advertiserParam.setIcpImageUrl("/bb/dd.icp.jpg");
    	advertiserParam.setRzQqNumber("863524078");
    	advertiserParam.setAppId(24880);
    	advertiserParam.setBusinessBrand("businessBrand");
    	advertiserParam.setContactPersonTelephone("0755-86013399");
    	advertiserParam.setContactPersonMobile("13911111111");
    	advertiserParam.setUid(uid);
    	
    	advertiserParam.setCustomerRegistrationType(CustomerRegistionType.CUSTOMERREGISTIONTYPE_CORPORATION);
    	advertiserParam.setUname(advertiserParam.getCorporation());
    	
    	AdvertiserParam result = advertiserService.insertLocal(advertiserParam);
    	System.out.println(result);
    }
    
    @Test
    public void testUpdate(){
    	int uid = 519671;//1021415;//51967;
    	AdvertiserParam advertiserParam = new AdvertiserParam();
    	advertiserParam.setCorporation("companyName");
    	advertiserParam.setCorporationLicence("147852369");
    	advertiserParam.setCertification("/upload/20160419/9ac738ef-0d97-4546-94ba-bc6439c40cfe.png");
    	advertiserParam.setIndustryId(21474836484l);
    	advertiserParam.setQualificationStr("/upload/20160419/9ac738ef-0d97-4546-94ba-bc6439c40cfe.png");
    	advertiserParam.setSiteUrl("{a:a.jpg,b:b.jpg}");
    	advertiserParam.setIcpImageUrl("/upload/20160419/9ac738ef-0d97-4546-94ba-bc6439c40cfe.png");
    	advertiserParam.setRzQqNumber("863524078");
    	advertiserParam.setAppId(1);
    	advertiserParam.setBusinessBrand("businessBrand");
    	advertiserParam.setContactPersonTelephone("0755-86013399");
    	advertiserParam.setContactPersonMobile("13911111111");
    	advertiserParam.setUid(uid);
    	AdvertiserParam result = advertiserService.updateLocal(advertiserParam);
    	System.out.println(result);
    }

}
