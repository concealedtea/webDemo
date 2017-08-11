package com.falcon.dsp.service;

import com.falcon.dsp.common.GdtAction;
import com.falcon.dsp.exception.FalconDspSqlException;
import com.falcon.dsp.handler.SqlSessionHandler;
import com.falcon.dsp.jdbc.entity.Advertiser;
import com.falcon.dsp.jdbc.model.Tree;
import com.falcon.dsp.jdbc.model.UserModel;
import com.falcon.dsp.param.AdvertiserParam;
import com.falcon.dsp.rest.GdtRestHandler;
import com.falcon.dsp.rest.advertiser.response.GdtAdvertiser;
import com.falcon.dsp.rest.advertiser.response.GdtAdvertiserResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Zhouchunhui
 * @from 2016-04-12
 * @since V1.0
 */
@Service
public class AdvertiserService {
    @Autowired
    private GdtRestHandler gdtRestHandler;

    @Resource(name = "sqlHandler")
    private SqlSessionHandler sqlSessionTemplate;

    public GdtAdvertiserResult getGdtAdvertiserByUid(int uid, String fields){
        Map<String,Object> map = new HashMap<>();
        map.put("uid",uid);
        if(!StringUtils.isEmpty(fields)){
            map.put("fields",fields);
        }
        GdtAdvertiserResult gdtAdvertiserResult = gdtRestHandler.get(GdtAction.ADVERTISER_READ,GdtAdvertiserResult.class,map);

        return gdtAdvertiserResult;
    }
    
    public int insertAdvertiser(GdtAdvertiser advertiser){
    	 
        return sqlSessionTemplate.insert("advertiser:insert",advertiser);
    }
    

    /**
     * 创建广告主，广电通 和 本地db 同时存放。
     * @param advertiserParam
     * @return
     */
    public Advertiser addAdvertiser(AdvertiserParam advertiserParam){
    	Advertiser advertiser = advertiserParam.getAdvertiserForAgencyInsert();
    	advertiser.setUid(null);
    	/**
    	 * Corporation 和 Uname check
    	 */
    	checkByCorporation(advertiser);
    	checkByUname(advertiser);
    	
    	GdtAdvertiser gdtAdvertiser = advertiserParam.getGdtAdvertiser();
    	GdtAdvertiserResult gdtAdvertiserResult = gdtRestHandler.post("/agency/add_advertiser", gdtAdvertiser, GdtAdvertiserResult.class, null);

		advertiserParam.setUid(gdtAdvertiserResult.getData().getUid());
		advertiser = advertiserParam.getAdvertiserForAgencyInsert();
		sqlSessionTemplate.insert("advertiser:insertone",advertiser);

		return advertiser;

    }
    
    /**
     * 更新广告主，广电通 和 本地 同时更新。
     * @param advertiserParam
     * @param user
     * @return
     */
    public void updateAdvertiser(AdvertiserParam advertiserParam,UserModel user){

    	if(advertiserParam.getUid()==null){
    		 throw new FalconDspSqlException("系统错误!");
    	}
    	Advertiser advertiser =null;
    	
    	/**
    	 * Corporation 和 Uname check
    	 */
    	advertiser = advertiserParam.getAdvertiserForAgencyUpdate();
    	checkByCorporation(advertiser);
    	checkByUname(advertiser);
    	
    	GdtAdvertiser gdtAdvertiser = advertiserParam.getGdtAdvertiser();
    	gdtAdvertiser.setUid(advertiserParam.getUid());
 
    	GdtAdvertiserResult gdtAdvertiserResult = gdtRestHandler.post("/agency/edit_advertiser", gdtAdvertiser, GdtAdvertiserResult.class, null);
    	

		gdtAdvertiser.setUid(gdtAdvertiserResult.getData().getUid());
		switch (user.getUserRole()) {
		case AGENCY://代理商更新子客户
			advertiser = advertiserParam.getAdvertiserForAgencyUpdate();
			sqlSessionTemplate.update("advertiser:update",advertiser);
			break;
		case ADVERTISER://广告主更新自己的信息
			advertiser = advertiserParam.getAdvertiserForAdvertiserUpdate();
			sqlSessionTemplate.update("advertiser:update",advertiser);
			break;
		default:
			advertiser = advertiserParam.getAdvertiserForAdvertiserUpdate();
			sqlSessionTemplate.update("advertiser:update",advertiser);
			break;
		}

    }
    
    /**
     * 在本地插入。
     * @param advertiserParam
     * @return
     */
    public AdvertiserParam insertLocal(AdvertiserParam advertiserParam){

    	Advertiser advertiser = advertiserParam.getAdvertiserForAgencyInsert();
		sqlSessionTemplate.insert("advertiser:insertone",advertiser);
		return advertiserParam;
    }
    
    /**
     * 在本地更新。
     * @param advertiserParam
     * @return
     */
    public AdvertiserParam updateLocal(AdvertiserParam advertiserParam){

    	Advertiser advertiser = advertiserParam.getAdvertiserForAdvertiserUpdate();
		sqlSessionTemplate.update("advertiser:update",advertiser);
		return advertiserParam;
    
    }
     
    /**
     * 根据id获取本地广告主信息。
     * @param uid  广告主id
     * @return
     */
    public Advertiser selectOne(int uid){
    	return sqlSessionTemplate.selectOne("advertiserMapper.advertiser:selectone",uid);
    }

    /**
     * 广告主选择树
     */
    public List<Tree> tree(int agencyId, String searchKeyword) {

        Map map = new HashMap(2);
        map.put("agencyId", agencyId);
        map.put("searchKeyword", searchKeyword);

        return sqlSessionTemplate.selectList("advertiserMapper.advertiser:select:tree", map);

    }

	public List<Map> industryListByPid(String pid) {
		Map map = new HashMap(1);
		map.put("pid", pid);
		return sqlSessionTemplate.selectList("targetMapper.target:industry:list:pid", map);
	}
		
    /**
     * 是否有Corporation 重名
     * @param advertiser
     * @return
     */
    public void checkByCorporation(Advertiser advertiser){
    	
        int number = sqlSessionTemplate.selectOne("advertiser:check:corporation", advertiser);

        if (number > 0) {
            throw new FalconDspSqlException("公司名已经存在");
        }
    }
    
    /**
     *是否有uname 重名
     * @param advertiser
     * @return
     */
    public void checkByUname(Advertiser advertiser){
    	
    	int number = sqlSessionTemplate.selectOne("advertiser:check:uname", advertiser);
    	
    	if (number > 0) {
    		throw new FalconDspSqlException("广告主名已经存在");
    	}
    	
    }

	/**
	 * 广告主uidList 放入到sesson中用于判断权限
	 */
	public List<Integer> uidList(int uid) {

		return sqlSessionTemplate.selectList("advertiserMapper.advertiser:select:uid:list", uid);

	}
}
