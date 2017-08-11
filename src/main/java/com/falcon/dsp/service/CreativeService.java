package com.falcon.dsp.service;

import com.falcon.dsp.common.GdtAction;
import com.falcon.dsp.common.StringUtil;
import com.falcon.dsp.enumration.GdtSyncStatus;
import com.falcon.dsp.exception.FalconDspSqlException;
import com.falcon.dsp.handler.SqlSessionHandler;
import com.falcon.dsp.interceptor.PageParameter;
import com.falcon.dsp.jdbc.entity.Creative;
import com.falcon.dsp.jdbc.entity.Material;
import com.falcon.dsp.jdbc.model.CreativeListModel;
import com.falcon.dsp.jdbc.model.CreativeModel;
import com.falcon.dsp.jdbc.model.TableData;
import com.falcon.dsp.param.CreativeParam;
import com.falcon.dsp.param.CreativeTableParam;
import com.falcon.dsp.param.MaterialParam;
import com.falcon.dsp.rest.GdtRestHandler;
import com.falcon.dsp.rest.common.Conf;
import com.falcon.dsp.rest.creative.request.GdtCreative;
import com.falcon.dsp.rest.creative.response.GdtCreativeResult;
import com.falcon.dsp.rest.creative.response.GdtCreativeTable;
import com.falcon.dsp.rest.creative.response.GdtCreativeTableResult;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author dongbin.yu
 * @from 2016-04-05
 * @since V1.0
 */
@Service
public class CreativeService {

    @Resource(name = "sqlHandler")
    private SqlSessionHandler sqlHandler;

    @Autowired
    private GdtRestHandler restHandler;

    private static Logger logger = LoggerFactory.getLogger(CreativeService.class);

    @Transactional
    public boolean create(CreativeParam creativeParam) {

        if(!StringUtil.isUrl(creativeParam.getDestUrl())){
            throw new FalconDspSqlException("目标地址不合法，请重新输入。例:http(s)://www.domain.com/path/to/picture");
        }
        //创意创建
        Creative creative = new Creative();
        creative.setCid(creativeParam.getCid());
        creative.setCreativeName(creativeParam.getName());
        creative.setAdSpacePositionId(creativeParam.getAdSpacePositionId());
        creative.setRemark(creativeParam.getRemark());
        creative.setDestUrl(creativeParam.getDestUrl());
        creative.setStatus(GdtSyncStatus.SYNC_INIT.getStatus());
        creative.setUid(creativeParam.getUid());

        int number = sqlHandler.selectOne("creativeMapper.creative:check:name", creative);

        if (number > 0) {
            throw new FalconDspSqlException("同一用户下创意名称不允许重复");
        }

        int result = 0;
        try {
            result = sqlHandler.insert("creativeMapper.creative:insert", creative);
        } catch (Exception e) {
            throw new FalconDspSqlException("创意创建失败，请联系管理员",e);
        }


        //素材创建
        materialAdd(creativeParam.getMaterials(), creative.getId());

        return result > 0;

    }

    public CreativeModel read(CreativeParam param) {

        return sqlHandler.selectOne("creativeMapper.creative:select:one", param);
    }

    @Transactional
    public Creative delete(Integer creativeId) {

        Creative localCreative = sqlHandler.selectOne("creativeMapper.creative:select", creativeId);
        localCreative.setStatus(GdtSyncStatus.SYNC_ABANDON.getStatus());

        try {
            sqlHandler.update("creativeMapper.creative:update", localCreative);
            sqlHandler.update("materialMapper.material:abandon:list", creativeId);
        } catch (Exception e) {
            logger.error("creative {} delete failed", creativeId);
            throw new FalconDspSqlException("创意删除失败，请联系管理员",e);
        }

        if (localCreative.getStrategyId() != null) {

            List<Material> materials = sqlHandler.selectList("materialMapper.material:select:list", creativeId);
            for (Material material : materials) {

                GdtCreative gdtCreative = new GdtCreative();
                gdtCreative.setUid(localCreative.getUid());
                gdtCreative.setTid(material.getGdtCreativeId());

                restHandler.post("/creative/update", gdtCreative, GdtCreative.class, null);
            }
        }

        return localCreative;

    }

    public void edit(CreativeParam creativeParam) {
        if(!StringUtil.isUrl(creativeParam.getDestUrl())){
            throw new FalconDspSqlException("目标地址不合法，请重新输入。例:http(s)://www.domain.com/path/to/picture");
        }
        Integer creativeId = creativeParam.getId();
        Creative localCreative = sqlHandler.selectOne("creativeMapper.creative:select", creativeId);
        creativeParam.setUid(localCreative.getUid());

        Creative creative = new Creative();
        creative.setId(creativeParam.getId());
        creative.setUid(localCreative.getUid());
        creative.setCreativeName(creativeParam.getName());
        creative.setAdSpacePositionId(creativeParam.getAdSpacePositionId());
        creative.setRemark(creativeParam.getRemark());
        creative.setDestUrl(creativeParam.getDestUrl());

        int number = sqlHandler.selectOne("creativeMapper.creative:check:name", creative);

        if (number > 0) {
            throw new FalconDspSqlException("同一用户下创意名称不允许重复");
        }

        List<MaterialParam> materialParams = creativeParam.getMaterials();

        if (!localCreative.getAdSpacePositionId().equals(creativeParam.getAdSpacePositionId())) {
            //投放平台变更|原有资源丢弃
            sqlHandler.update("materialMapper.material:abandon:list", creativeId);
            //素材创建
            materialAdd(materialParams, creativeId);

        } else {
            List<Material> materials = copyFromMaterialParam(materialParams, creativeId);
            List<Material> localMaterials = sqlHandler.selectList("materialMapper.material:select:list", creativeId);
            //比较后增加|删除|修改
            //增加
            List<Material> addMaterials = new LinkedList<>();
            //变化
            List<Material> changeMaterials = new LinkedList<>();
            for (Material material : materials) {
                if (material.getId() == null) {
                    addMaterials.add(material);
                } else {
                    changeMaterials.add(material);
                }
            }

            //删除
            List<Material> delMaterials = (List<Material>) CollectionUtils.subtract(localMaterials, changeMaterials);

            //修改
            List<Material> modifyMaterials = (List<Material>) CollectionUtils.intersection(changeMaterials, localMaterials);

            //保持rest操作在db操作之前
            if (localCreative.getStrategyId() != null && localCreative.getStrategyId() > 0) {

                for (Material material : delMaterials) {

                    GdtCreative gdtCreative = new GdtCreative();
                    gdtCreative.setUid(localCreative.getUid());
                    gdtCreative.setTid(material.getGdtCreativeId());

                    logger.info("creative id {} material id {} delete", creativeId, material.getGdtCreativeId());
                    GdtCreativeResult creativeResult = restHandler.post("/creative/delete", gdtCreative, GdtCreativeResult.class, null);
                    material.setStatus(GdtSyncStatus.SYNC_ABANDON.getStatus());
                    sqlHandler.update("materialMapper.material:status:change", material);

                }

                for (Material material : addMaterials) {

                    GdtCreative gdtCreative = new GdtCreative();
                    gdtCreative.setUid(localCreative.getUid());
                    gdtCreative.setCid(localCreative.getCid());
                    gdtCreative.setAid(localCreative.getStrategyId());
                    gdtCreative.setCrtSize(material.getCrtSize());
                    gdtCreative.setTname(creativeParam.getName() + UUID.randomUUID());
                    gdtCreative.setTitle(material.getTitle());
                    gdtCreative.setDesc(material.getDescription());
                    gdtCreative.setImageUrl(material.getImageUrl());
                    gdtCreative.setImage2Url(material.getImage2Url());
                    gdtCreative.setDestUrl(creativeParam.getDestUrl());

                    logger.info("creative id {} add", creativeId);
                    GdtCreativeResult creativeResult = restHandler.post("/creative/create", gdtCreative, GdtCreativeResult.class, null);
                    material.setGdtCreativeId(creativeResult.getData().getTid());
                    material.setStatus(GdtSyncStatus.SYNC_SUCCESS.getStatus());
                    try {
                        sqlHandler.insert("materialMapper.material:insert", material);
                    } catch (Exception e) {
                        throw new FalconDspSqlException("素材创建失败，请联系管理员",e);
                    }
                }

                for (Material material : modifyMaterials) {

                    GdtCreative gdtCreative = new GdtCreative();
                    gdtCreative.setUid(localCreative.getUid());
                    //添加广点通id
                    int id = material.getId();
                    Material materialLocal = sqlHandler.selectOne("material:select:one", id);
                    gdtCreative.setTid(materialLocal.getGdtCreativeId());
                    gdtCreative.setTname(creativeParam.getName() + UUID.randomUUID());
                    gdtCreative.setTitle(material.getTitle());
                    gdtCreative.setDesc(material.getDescription());
                    gdtCreative.setImageUrl(material.getImageUrl());
                    gdtCreative.setImage2Url(material.getImage2Url());
                    gdtCreative.setDestUrl(creativeParam.getDestUrl());

                    logger.info("creative id {} -> material id {} update", creativeId, materialLocal.getGdtCreativeId());
                    GdtCreativeResult creativeResult = restHandler.post("/creative/update", gdtCreative, GdtCreativeResult.class, null);
                    material.setStatus(GdtSyncStatus.SYNC_SUCCESS.getStatus());
                    sqlHandler.update("material:update", material);

                }

            } else {
                for (Material material : delMaterials) {
                    material.setStatus(GdtSyncStatus.SYNC_ABANDON.getStatus());
                    sqlHandler.update("material:status:change", material);
                }

                for (Material material : addMaterials) {
                    try {
                        material.setStatus(GdtSyncStatus.SYNC_INIT.getStatus());
                        sqlHandler.insert("material:insert", material);
                    } catch (Exception e) {
                        throw new FalconDspSqlException("素材创建失败，请联系管理员",e);
                    }
                }

                for (Material material : modifyMaterials) {
                    sqlHandler.update("material:update", material);
                }
            }
        }

        //更新创意
        try {
            sqlHandler.update("creativeMapper.creative:update", creative);
        } catch (Exception e) {
            logger.error("creative {} modify failed", creativeId);
            throw new FalconDspSqlException("创意修改失败，请联系管理员",e);
        }

    }

    /**
     * 获取创意列表
     *
     * @return
     */
    public TableData<CreativeListModel> list(CreativeTableParam tableParam) {

        PageParameter pageParameter = new PageParameter();
        pageParameter.setPageSize(tableParam.getLength());
        pageParameter.setCurrentPage(tableParam.getStart() / tableParam.getLength() + 1);

        Map map = new HashMap();
        map.put("searchKeyword", tableParam.getSearchKeyword());
        map.put("cid", tableParam.getCid());
        map.put("filter", tableParam.isFilter());
        map.put("strategyId", tableParam.getStrategyId());

        List<CreativeListModel> creativeListModels = sqlHandler.selectList("creativeMapper.creative:select:list:page", map, pageParameter);

        TableData<CreativeListModel> listModelTableData = new TableData<>();
        listModelTableData.setRecordsTotal(pageParameter.getTotalCount());
        listModelTableData.setRecordsFiltered(pageParameter.getTotalCount());
        listModelTableData.setData(creativeListModels);

        return listModelTableData;
    }

    private List<Material> copyFromMaterialParam(List<MaterialParam> materialParams, Integer creativeId) {

        List<Material> materials = new LinkedList<>();
        Material material = null;
        for (MaterialParam materialParam : materialParams) {
            material = new Material();
            material.setId(materialParam.getId());
            material.setCreativeId(creativeId);
            material.setCrtSize(materialParam.getCrtSize());
            material.setTitle(materialParam.getTitle());
            material.setDescription(materialParam.getDescription());
            material.setImageUrl(materialParam.getImageUrl());
            material.setImage2Url(materialParam.getImage2Url());
            materials.add(material);
        }

        return materials;
    }

    private void materialAdd(List<MaterialParam> materialParams, Integer creativeId) {
        Material material = null;
        for (MaterialParam materialParam : materialParams) {

            material = new Material();
            material.setCreativeId(creativeId);
            material.setCrtSize(materialParam.getCrtSize());
            material.setTitle(materialParam.getTitle());
            material.setDescription(materialParam.getDescription());
            material.setImageUrl(materialParam.getImageUrl());
            material.setImage2Url(materialParam.getImage2Url());
            material.setStatus(GdtSyncStatus.SYNC_INIT.getStatus());

            try {
                sqlHandler.insert("material:insert", material);
            } catch (Exception e) {
                logger.error("创意id为{} 对应素材尺寸{} 创建失败", creativeId, material.getCrtSize());
                throw new FalconDspSqlException("素材创建失败，请联系管理员",e);
            }
        }
    }

    public String getPlatformValue(int creativeId) {
        Map<String, Object> params = new HashMap<>();
        params.put("creative_id", creativeId);
        return sqlHandler.selectOne("creativeMapper.creative:platform:select:one", params);
    }

    /**
     * 获取广点通创意列表
     *
     * @param aid 广点通广告组id(对应Falcon策略id)
     * @return GdtCreativeTable 广点通创意列表
     */
    public List<GdtCreative> getGdtCreativeList(int uid,Integer aid) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uid",uid);
        map.put("where", "{\"aid\":"+aid+"}");
        map.put("page_size",100);
        GdtCreativeTableResult result = restHandler.get(GdtAction.CREATIVE_SELECT, GdtCreativeTableResult.class, map);
        GdtCreativeTable gt = result.getData();
        Conf conf = gt.getConf();
        List<GdtCreative> list = gt.getList();
        for(int i= 2;i<=conf.getTotalPage();i++){
            map.put("page",i);
            result = restHandler.get(GdtAction.AD_GROUP_SELECT,GdtCreativeTableResult.class,map);
            list.addAll(result.getData().getList());
        }
        return list;
    }

}
