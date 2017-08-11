package com.falcon.dsp.service;

import com.falcon.dsp.handler.SqlSessionHandler;
import com.falcon.dsp.jdbc.entity.AdSpacePosition;
import com.falcon.dsp.jdbc.entity.StrategyPlatform;
import com.falcon.dsp.jdbc.model.AdSpacePositionModel;
import com.falcon.dsp.jdbc.model.ImageRuleModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author dongbin.yu
 * @from 2016-04-07
 * @since V1.0
 */
@Service
public class AdSpacePositionService {

    @Resource(name = "sqlHandler")
    private SqlSessionHandler sqlHandler;

    /**
     * 投放位置
     * @param adSpacePosition
     * @return
     */
    public List<AdSpacePositionModel> getAdSpacePositionList(AdSpacePosition adSpacePosition) {

        return sqlHandler.selectList("adSpacePosition:select:list", adSpacePosition);

    }

    /**
     * 投放平台
     * @return
     */
    public List<StrategyPlatform> getStrategyPlatformList() {

        return sqlHandler.selectList("platform:select:list", null);
    }

    /**
     * 获取创意尺寸规格
     * @return
     */
    public List<ImageRuleModel> getImageRuleList(int id) {

        return sqlHandler.selectList("imageRule:select:list", id);
    }

}
