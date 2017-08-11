package com.falcon.dsp.controller;

import com.falcon.dsp.annotation.UserAttribute;
import com.falcon.dsp.common.JsonUtil;
import com.falcon.dsp.enumration.UserRoleType;
import com.falcon.dsp.exception.FalconDspServerException;
import com.falcon.dsp.handler.RoleAndView;
import com.falcon.dsp.interceptor.PageParameter;
import com.falcon.dsp.jdbc.entity.Response;
import com.falcon.dsp.jdbc.model.TableData;
import com.falcon.dsp.jdbc.model.UserModel;
import com.falcon.dsp.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wanxm
 * @from 2016-04-01
 * @since V1.0
 */
@Controller
@RequestMapping("/finance")
public class FinanceController {
    @Autowired
    private FinanceService financeService;

    @RequestMapping(value = "view", method = RequestMethod.GET)
    public RoleAndView view(Model model, @UserAttribute UserModel user) {

        try {
            Map map = financeService.getCrruteUserFinance(user);
            model.addAllAttributes(map);
        } catch(Exception e){
            throw new FalconDspServerException(e.getMessage());
        }

        return new RoleAndView("Finance", user);
    }

    /**
     * 设置广告主的日限额
     * @param map
     * @return
     */
    @RequestMapping(value = "setDayBudget", method = RequestMethod.POST)
    @ResponseBody
    public Response setDayBudget(@RequestBody Map<String, Object> map,@UserAttribute UserModel user) {
        //从request中获取查询参数，并初始化到FinanceParam中
       financeService.setDayBudget(map,user);
       return new Response().success("ok!");
    }

    /**
     * 交易流水信息查询
     * @param map
     * @param user
     * @return
     */
    @RequestMapping(value = "financeFlow", method = RequestMethod.POST)
    @ResponseBody
    public TableData<Map> financeFlow(@RequestBody Map<String, Object> map, @UserAttribute UserModel user) {
        PageParameter page = new PageParameter();
        page.setCurrentPage((Integer) map.get("start")/(Integer) map.get("length")+1);
        page.setPageSize((Integer) map.get("length"));
        if (user.getUserRole() == UserRoleType.ADVERTISER) {
            //如果为广告主的话。调用接口查询广告主的资金流水
            Map map_1 = new HashMap(5);
            Map<String,Object> mp = new HashMap<>(2);
            mp.put("start_date",map.get("startDate"));
            mp.put("end_date",map.get("endDate"));
            map_1.put("uid", user.getUid());
            map_1.put("time_range", JsonUtil.toJson(mp));
            map_1.put("page", page.getCurrentPage());
            map_1.put("page_size", page.getPageSize());
            map_1.put("account_type", map.get("accountType"));
            TableData<Map> data = financeService.getAccountFinstament(map_1,user);
            if (data == null) {
                data = new TableData<>();
                data.setData(new ArrayList<Map>());
                data.setRecordsFiltered(0);
                data.setRecordsTotal(0);
                return data;
            } else {
                return data;
            }
        } else {

            //如果为代理商的话，查询代理商下所有广告主的资金流水
            map.put("agencyId", user.getUid());
            map.put("rate",user.getRate());
            TableData<Map> data = financeService.getAccountInvoiceList(map, page);
            return data;
        }
    }

    /**
     * 获取当前登录用户的资金信息
     * @param user
     * @return
     */
    @RequestMapping(value = "getUserFinanceInfo", method = RequestMethod.POST)
    @ResponseBody
    public Response<Map> getUserFinanceInfo(@UserAttribute UserModel user) {
        Map map = financeService.getCrruteUserFinance(user);
        if(map == null){
            return new Response<>().failure("未查询到资金信息！");
        }else{
            return new Response<Map>().success(map);
        }
    }

}
