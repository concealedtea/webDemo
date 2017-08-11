package com.falcon.dsp.service;

import com.falcon.dsp.common.JsonUtil;
import com.falcon.dsp.enumration.FalCreativeStatus;
import com.falcon.dsp.enumration.GdtAdStatus;
import com.falcon.dsp.enumration.GdtSyncStatus;
import com.falcon.dsp.exception.FalDspException;
import com.falcon.dsp.exception.FalDspParamException;
import com.falcon.dsp.handler.SqlSessionHandler;
import com.falcon.dsp.jdbc.entity.Creative;
import com.falcon.dsp.jdbc.entity.Material;
import com.falcon.dsp.jdbc.model.MaterialPreviewModel;
import com.falcon.dsp.jdbc.model.MaterialRuleModel;
import com.falcon.dsp.param.MaterialParam;
import com.falcon.dsp.rest.GdtRestHandler;
import com.falcon.dsp.rest.creative.request.GdtCreative;
import com.falcon.dsp.rest.creative.response.GdtCreativeResult;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author dongbin.yu
 * @from 2016-04-08
 * @since V1.0
 */
@Service
public class MaterialService {

    @Resource(name = "sqlHandler")
    private SqlSessionHandler sqlHandler;

    @Autowired
    private GdtRestHandler restHandler;

    private static Logger logger = LoggerFactory.getLogger(MaterialService.class);

    public void create(Integer StrategyId, Integer creativeId) {

        //获取本地创意
        Creative creative = sqlHandler.selectOne("creative:select", creativeId);

        //获取本地素材
        List<Material> materials = sqlHandler.selectList("material:select:list", creativeId);

        //rest请求获取创意id
        GdtCreative gdtCreative = null;
        for (Material material : materials) {
            gdtCreative = new GdtCreative();
            gdtCreative.setUid(creative.getUid());
            gdtCreative.setCid(creative.getCid());
            gdtCreative.setAid(StrategyId);
            gdtCreative.setTname(creative.getCreativeName() + UUID.randomUUID());
            gdtCreative.setCrtSize(material.getCrtSize());
            gdtCreative.setTitle(material.getTitle());
            gdtCreative.setDesc(material.getDescription());
            gdtCreative.setImageUrl(material.getImageUrl());
            gdtCreative.setImage2Url(material.getImage2Url());
            gdtCreative.setDestUrl(creative.getDestUrl());

            GdtCreativeResult creativeResult = null;
            try {
                creativeResult = restHandler.post("/creative/create", gdtCreative, GdtCreativeResult.class, null);
            } catch (FalDspException ex) {
                //返回失败处理消息
                material.setStatus(GdtSyncStatus.SYNC_FAILED.getStatus());
                sqlHandler.update("materialMapper.material:update", material);

                logger.error("material create failed ,cause by {}", ex.getMessage());


                //FIXME
                //一个策略对应一个创意
                //一个素材失败后， 创意关联的所有素材都将失效
                //调用广点通接口将该策略下的创意都删除
                //策略那边也需要提示策略新建失败|如果是修改策略变更创意的时候，如果失败，策略修改失败
                List<Integer> gdtCreativeIds = sqlHandler.selectList("materialMapper.material:creative:exists", creativeId);
                if(CollectionUtils.isNotEmpty(gdtCreativeIds)){
                    deleteGdtCreative(gdtCreativeIds, creative.getUid());
                }
                throw ex;
            }

            //处理成功后记载创意id
            material.setGdtCreativeId(creativeResult.getData().getTid());
            material.setStatus(GdtSyncStatus.SYNC_SUCCESS.getStatus());
            sqlHandler.update("material:update", material);
        }

    }

    /**
     * 广点通创意删除
     * @param gdtCreativeIds
     * @param uid
     */
    private void deleteGdtCreative(List<Integer> gdtCreativeIds,Integer uid) {

        GdtCreative gdtCreative = null;
        for (Integer gdtCreativeId : gdtCreativeIds) {
            //调用广点通删除接口删除这些创意
            gdtCreative = new GdtCreative();
            gdtCreative.setUid(uid);
            gdtCreative.setTid(gdtCreativeId);
            restHandler.post("creative/delete", gdtCreative, GdtCreativeResult.class, null);
        }
    }


    /**
     * 状态改变
     *
     */
    public FalCreativeStatus statusChange(MaterialParam param) {

        int status = param.getStatus();
        int id = param.getId();
        Material material = sqlHandler.selectOne("materialMapper.material:select:one",id);
        int creativeId = material.getCreativeId();
        //获取本地创意
        Creative creative = sqlHandler.selectOne("creative:select", creativeId);

        GdtCreative gdtCreative = new GdtCreative();
        gdtCreative.setUid(creative.getUid());
        if (material.getGdtCreativeId() == null) {
            throw new FalDspParamException("素材暂不支持修改状态，请关联策略后保存。");
        } else {
            gdtCreative.setTid(material.getGdtCreativeId());
        }

        //获取本地创意
        FalCreativeStatus falCreativeStatus = FalCreativeStatus.forValue(status);
        GdtAdStatus gdtAdStatus = falCreativeStatus.getGdtAdStatus();

        if (gdtAdStatus.equals(GdtAdStatus.ADSTATUS_SUSPEND)) {
            gdtCreative.setStatus(FalCreativeStatus.ADSTATUS_NORMAL);
        } else if (gdtAdStatus.equals(GdtAdStatus.ADSTATUS_NORMAL)) {
            gdtCreative.setStatus(FalCreativeStatus.ADSTATUS_SUSPEND);
        } else {
            throw new FalDspParamException("素材的状态不支持变更。");
        }

        GdtCreativeResult gdtCreativeResult = restHandler.post("creative/update", gdtCreative, GdtCreativeResult.class, null);
        gdtCreativeResult.getData().setStatus(gdtCreative.getStatus());

        return gdtCreative.getStatus();

    }

    /**
     * 素材预览列表
     */
    public List<MaterialPreviewModel> previewList(int creativeId) {

        //获取当前创意
        Creative creative = sqlHandler.selectOne("creativeMapper.creative:select", creativeId);

        //获取本地素材
        List<MaterialRuleModel> materials = sqlHandler.selectList("materialMapper.material:preview", creativeId);

        //获取创意的广告形式
        String adFormat = sqlHandler.selectOne("creativeMapper.creative:ad:format", creativeId);

        List<MaterialPreviewModel> materialPreviewModels = new LinkedList<>();
        MaterialPreviewModel materialPreviewModel = null;
        for (MaterialRuleModel materialRuleModel : materials) {
            materialPreviewModel = new MaterialPreviewModel();
            materialPreviewModel.setId(materialRuleModel.getId());
            if (materialRuleModel.getWidth() != null) {
                materialPreviewModel.setImg(adFormat + materialRuleModel.getWidth() + "*" + materialRuleModel.getHeight());
            } else {
                materialPreviewModel.setImg(adFormat + materialRuleModel.getrWidth() + "*" + materialRuleModel.getrHeight());
            }

            if (materialRuleModel.getGdtCreativeId() != null && materialRuleModel.getGdtCreativeId() != 0) {
                Map map = new HashMap();
                map.put("tid", materialRuleModel.getGdtCreativeId());
                map.put("uid", creative.getUid());
                map.put("fields", JsonUtil.toJson(new String[]{"status"}));

                GdtCreativeResult creativeResult = restHandler.get("creative/read", GdtCreativeResult.class, map);
                if (creativeResult.getRet() == 0) {
                    materialPreviewModel.setStatus(creativeResult.getData().getStatus().getValue());
                    materialPreviewModel.setStatusName(creativeResult.getData().getStatus().getDescription());
                }

            } else {
                materialPreviewModel.setStatus(FalCreativeStatus.ADSTATUS_PENDING.getValue());
                materialPreviewModel.setStatusName(FalCreativeStatus.ADSTATUS_PENDING.getDescription());
            }

            materialPreviewModels.add(materialPreviewModel);
        }

        return materialPreviewModels;

    }

    public List<Material> list(int creativeId) {
        return sqlHandler.selectList("materialMapper.material:select:list", creativeId);
    }
}
