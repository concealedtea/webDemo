package com.falcon.dsp.handler;

import com.falcon.dsp.exception.FalDspException;
import com.falcon.dsp.exception.FalDspParamException;
import com.falcon.dsp.exception.FalconDspServerException;
import com.falcon.dsp.exception.FalconDspSqlException;
import com.falcon.dsp.jdbc.entity.Response;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author dongbin.yu
 * @from 2016-04-11
 * @since V1.0
 */
@ControllerAdvice
public class FalconExceptionHandler {

    @ExceptionHandler(value = {FalconDspSqlException.class,
            FalDspException.class,
            FalDspParamException.class})
    @ResponseBody
    public Response falconSqlExceptionHandle(Exception ex) {

        if (ex instanceof FalDspException) {
            FalDspException dspException = (FalDspException) ex;
            if (dspException.getRet() == 4000) {
                return new Response().failure("当前账户异常，请联系运营人员。");
            }
        }

        String message = ex.getMessage().replace("aname", "策略名称");
        message = message.replace("tname", "创意名称");
        message = message.replace("cname", "订单名称");
        return new Response().failure(message);
    }

    @ExceptionHandler(value = {FalconDspServerException.class})
    public String falconServerExceptionHandler(Exception ex, Model model) {

        ex.printStackTrace();
        model.addAttribute("message",ex.getMessage());
        return "default/500";
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public Response defaultExceptionHandle(Exception ex) {
        ex.printStackTrace();
        return new Response().failure("系统错误");
    }

}
