package com.falcon.dsp.controller;

import com.falcon.dsp.annotation.UserAttribute;
import com.falcon.dsp.common.JsonUtil;
import com.falcon.dsp.enumration.FalCreativeStatus;
import com.falcon.dsp.exception.FalconDspServerException;
import com.falcon.dsp.handler.RoleAndView;
import com.falcon.dsp.jdbc.entity.Campaign;
import com.falcon.dsp.jdbc.entity.Creative;
import com.falcon.dsp.jdbc.entity.Response;
import com.falcon.dsp.jdbc.model.*;
import com.falcon.dsp.param.CreativeParam;
import com.falcon.dsp.param.CreativeTableParam;
import com.falcon.dsp.param.MaterialParam;
import com.falcon.dsp.rest.campaign.params.CampaignParam;
import com.falcon.dsp.service.CampaignService;
import com.falcon.dsp.service.CreativeService;
import com.falcon.dsp.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 创意
 * @author dongbin.yu
 * @from 2016-04-01
 * @since V1.0
 */
@Controller
@RequestMapping("/creative")
public class CreativeController extends BaseController{

    @Autowired
    private CreativeService creativeService;

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private String resourceAddress;

    @Autowired
    private String serverAddress;

    private static Pattern pattern = Pattern.compile("^(data:.*;base64,)");

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public RoleAndView Detail(CampaignParam param, @RequestParam(value = "creative_id",required = false) Integer creativeId, Model model, @UserAttribute UserModel user) {

        String role = user.getUserRole().getName();
        if (param.getCid() == null) {
            CreativeParam creativeParam = new CreativeParam();
            creativeParam.setId(creativeId);

            CreativeModel creativeModel = null;
            try {
                creativeModel = creativeService.read(creativeParam);
            } catch (Exception e) {
                throw new FalconDspServerException("创意错误", e);
            }

            param.setCid(creativeModel.getCampaignId());

            model.addAttribute("creative", creativeModel);
            model.addAttribute("creativeData", JsonUtil.toJson(creativeModel));
        }

        try {
            Campaign campaign = campaignService.read(param,user.getRate());
            model.addAttribute("campaign", campaign);
        } catch (Exception e) {
            throw new FalconDspServerException("服务器异常", e);
        }
        return new RoleAndView("CreativeDetail", user);
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public Response create(@RequestBody CreativeParam param,@UserAttribute UserModel user) {

        //广告主的id从订单表上获取
        CampaignParam campaignParam = new CampaignParam();
        campaignParam.setCid(param.getCid());
        Campaign campaign = campaignService.read(campaignParam,user.getRate());
        param.setUid(campaign.getUid());

        if(creativeService.create(param)){
            return new Response().success("创意创建成功");
        } else {
            return new Response().failure("创意创建失败");
        }
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    public Response edit(@RequestBody CreativeParam param) {

        creativeService.edit(param);

        return new Response().success("创意编辑成功");
    }

    @RequestMapping(value = "read", method = RequestMethod.GET)
    @ResponseBody
    public Response read(@RequestParam(name = "creative_id") Integer creativeId) {

        CreativeParam param = new CreativeParam();
        param.setId(creativeId);
        CreativeModel read = creativeService.read(param);
        return new Response().success(read);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public Response delete(@RequestParam(name = "creative_id") Integer creativeId) {

        Creative creative = creativeService.delete(creativeId);
        return new Response().success("创意删除成功！");
    }

    /**
     * 创意列表
     * @param param
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public TableData<CreativeListModel> list(@RequestBody CreativeTableParam param) {

        return creativeService.list(param);
    }

    /**
     * 素材预览
     */
    @RequestMapping(value = "materialPreview", method = RequestMethod.GET)
    @ResponseBody
    public List<MaterialPreviewModel> previewMaterial(@RequestParam(value = "creative_id",required = false) int creativeId){

        return materialService.previewList(creativeId);
    }

    /**
     * 素材状态修改
     * NORMAL  -> SUSPEND
     * SUSPEND -> NORMAL
     * @param param
     * @return
     */
    @RequestMapping(value = "/material/statusChange", method = RequestMethod.POST)
    @ResponseBody
    public Response statusChange(@RequestBody MaterialParam param) {

        FalCreativeStatus falStrategyStatus = materialService.statusChange(param);
        Map map = new HashMap(2);
        map.put("status", falStrategyStatus.getValue());
        map.put("status_name", falStrategyStatus.getDescription());

        return new Response().success(map);

    }

    /**
     * 解析图片
     */
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @ResponseBody
    public Response parseImage(String imageStr, int width, int height, HttpServletRequest request) throws IOException {

        Matcher matcher = pattern.matcher(imageStr);
        while (matcher.find()) {
            //处理字符开头的64位
            imageStr = imageStr.replace(matcher.group(1), "");
        }


        BASE64Decoder decoder = new BASE64Decoder();
        String currentDate = DATE_FORMAT.format(new Date());
        String path = resourceAddress + "/upload/" + currentDate;

        /*String path = request.getSession().getServletContext().getRealPath("/upload");
        String currentDate = DATE_FORMAT.format(new Date());
        path += File.separator + currentDate;*/
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = UUID.randomUUID() + ".png";
        File file = new File(path, fileName);

        try (FileOutputStream outputStream = new FileOutputStream(file)) {

            byte[] bytes = decoder.decodeBuffer(imageStr);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {
                    bytes[i] += 256;
                }
            }

            outputStream.write(bytes);
            outputStream.flush();
        }

        // 添加尺寸的判断
        BufferedImage image = ImageIO.read(file);
        if (image.getWidth() != width || image.getHeight() != height) {
            return new Response().failure("图片尺寸不正确");
        }


        String relativePath = serverAddress + "/upload/" + currentDate + "/" + fileName;

        return new Response().success(relativePath);

    }

}
