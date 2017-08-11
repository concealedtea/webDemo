package com.falcon.dsp.service;

import com.falcon.dsp.common.GdtAction;
import com.falcon.dsp.common.JsonUtil;
import com.falcon.dsp.enumration.GdtSyncStatus;
import com.falcon.dsp.enumration.target.*;
import com.falcon.dsp.handler.SqlSessionHandler;
import com.falcon.dsp.jdbc.entity.Target;
import com.falcon.dsp.jdbc.model.AreaModel;
import com.falcon.dsp.jdbc.model.TargetModel;
import com.falcon.dsp.param.TargetParam;
import com.falcon.dsp.rest.GdtRestHandler;
import com.falcon.dsp.rest.target.request.GdtTarget;
import com.falcon.dsp.rest.target.request.Keyword;
import com.falcon.dsp.rest.target.response.GdtTargetResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author dongbin.yu
 * @from 2016-04-12
 * @since V1.0
 */
@Service
public class TargetService {

    @Resource(name = "sqlHandler")
    private SqlSessionHandler sqlHandler;

    @Autowired
    private GdtRestHandler restHandler;

    public Target create(TargetParam param) {
        Target target = null;
        //同步到广点通
        GdtTarget gdtTarget = param.fetchGdtTarget();
        GdtTargetResult gdtTargetResult = restHandler.post("ad_target/create", gdtTarget, GdtTargetResult.class, null);
        target = new Target();
        target.setUid(param.getUid());
        target.setTid(gdtTargetResult.getData().getMid());
        target.setStatus(GdtSyncStatus.SYNC_SUCCESS.getStatus());
        target.setTargetValue(JsonUtil.toJson(generateTargetModel(param)));
        sqlHandler.insert("targetMapper.target:insert", target);

        return target;
    }

    public void edit(TargetParam param) {
        //同步到广点通
        GdtTarget gdtTarget = param.fetchGdtTarget();
        gdtTarget.setMid(param.getTid());

        restHandler.post("ad_target/update", gdtTarget, GdtTargetResult.class, null);
        Target target = new Target();
        target.setUid(param.getUid());
        target.setTid(param.getTid());
        target.setStatus(GdtSyncStatus.SYNC_SUCCESS.getStatus());

        target.setTargetValue(JsonUtil.toJson(generateTargetModel(param)));
        sqlHandler.insert("targetMapper.target:update", target);

    }

    private TargetModel generateTargetModel(TargetParam param) {
        TargetModel targetModel = new TargetModel();
        targetModel.setHours(param.getHours());
        targetModel.setAge(param.getAge());
        targetModel.setEducation(param.getEducation());
        targetModel.setGender(param.getGender());
        targetModel.setArea(param.getArea());
        targetModel.setConnection(param.getConnection());
        targetModel.setOs(param.getOs());
        targetModel.setTelcom(param.getTelcom());
        targetModel.setInterest(param.getInterest());
        targetModel.setKeyword(param.getKeyword());
        targetModel.setMarriage(param.getMarriage());
        return targetModel;
    }

    public void read(TargetParam param) {

        sqlHandler.insert("targetMapper.target:select", param);
    }

    public List<AreaModel> areaList(String searchKey) {
        Map map = new HashMap();
        map.put("searchKey", searchKey);
        return sqlHandler.selectList("targetMapper.target:area:list", map);
    }

    public List<AreaModel> industryList(String searchKey) {
        Map map = new HashMap();
        map.put("searchKey", searchKey);
        return sqlHandler.selectList("targetMapper.target:industry:list", map);
    }

    /**
     * 从广点通获取定向信息
     *
     * @param uid 广告主id
     * @param mid 定向id
     * @return GdtTarget 定向信息
     */
    public GdtTarget getGdtTargetInfo(Integer uid, Integer mid) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uid", uid);
        map.put("mid", mid);
        map.put("fields", "[\"uid\",\"mid\",\"mname\",\"description\",\"gender\",\"age\",\"area\",\"os\",\"connection\",\"telcom\",\"education\"]");
        GdtTargetResult gdtTargetResult = restHandler.get(GdtAction.AD_TARGET_READ, GdtTargetResult.class, map);
        return gdtTargetResult.getData();
    }

    /**
     * 查询商业兴趣列表
     * @param searchKey
     * @return
     */
    public List<AreaModel> interestList(String searchKey) {
        Map map = new HashMap();
        map.put("searchKey", searchKey);
        return sqlHandler.selectList("targetMapper.target:interest:list", map);
    }
}
