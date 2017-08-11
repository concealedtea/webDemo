package com.falcon.dsp.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by falcon-zhangxg on 2016/4/20.
 */
public class MathUtil {

    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");


    public static double doubleFormatRoundUp(double d){
        BigDecimal b1 = new BigDecimal(Double.valueOf(d));
        return b1.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double doubleFormat(double d) {
        try {
            return Double.parseDouble(decimalFormat.format(d));
        } catch (Exception ex) {
            return 0;
        }
    }

    public static double ctrFormat(double d) {
        try {
            d = d * 100;
            return Double.parseDouble(decimalFormat.format(d));
        } catch (Exception ex) {
            return 0;
        }
    }

    public static double ecpmFormat(double d) {
        try {
            d = d * 1000;
            return Double.parseDouble(decimalFormat.format(d));
        } catch (Exception ex) {
            return 0;
        }

    }

    /**
     *
     * @param current 要计算的金额
     * @param rate 服务费率
     * @param p  10，保留到10 ，100保留到百位，向上取整
     * @return
     */
    public static int getCeilNumber(double current,double rate,int p){
        double currentChange = current/rate;
        return (int) (Math.ceil(currentChange/p)*p);
    }

    /**
     * 数值向下取整,如4892 返回 4000
     * @param current
     * @param rate
     * @return
     */
    public static int getFloorMaxNumber(double current,double rate){
        double currentChange = current/rate;
        int val = (int) Math.floor(currentChange);
        return val/10000*10000;
    }


    /**
     * 四舍五入取整
     * @param current
     * @param rate
     * @return
     */
    public static int getRoundNumberMultiply(double current,double rate){
        BigDecimal b = new BigDecimal(current*rate).setScale(0, BigDecimal.ROUND_HALF_UP);
        return b.intValue();
    }

    /**
     * 四舍五入取整,除法
     * @param current
     * @param
     * @return
     */
    public static int getRoundNumberDivision(double current,double rate){
        BigDecimal b = new BigDecimal(current/rate).setScale(0, BigDecimal.ROUND_HALF_UP);
        return b.intValue();
    }

    /**
     * @param min  最小值  (单位：元)
     * @param max  最大值
     * @param current  当前消耗金额，通过接口获取
     * @param changeValue  更改后的金额
     * @param beforeValue  改变前的数值
     * @param rate  服务费率
     * @return
     */
    public static String  booleanChangeBudget(Integer min,Integer max,double current,double changeValue,double beforeValue,double rate){
        //获取除以服务费之后向上取整的最小值
        min = getCeilNumber(min,rate,10);
        //获取除以服务费之后向下取整的最大值
        max = getFloorMaxNumber(max,rate);
        //当前消耗金额转成除以服务费之后的金额
        current = current/rate;
        //当前数据库中存的日限额除以服务费
        beforeValue = doubleFormat(beforeValue/rate);
        //获取金额变动的最小值
        int minChange = (int) (Math.ceil(Constant.MIN_CHANGE_VALUE/rate/10)*10);

        String msg;
        if(changeValue<min||changeValue>max){
            msg = "设置金额，应该在"+min+"-"+max+"范围内";
        }else if(Math.abs(changeValue-beforeValue)<minChange){
            msg = "修改金额幅度不能低于"+minChange;
        }else if(changeValue<current||changeValue-current<minChange){
            msg = "修改后的金额不能低于今日消耗加上"+minChange;
        }else{
            msg = "SUCCESS";
        }
        return msg;
    }

    public static String booleanTransferFinance(int min, int max, long amount, double rate) {
        //获取除以服务费之后向上取整的最小值
        min = getCeilNumber(min,rate,10);
        //获取除以服务费之后向下取整的最大值
        max = getFloorMaxNumber(max,rate);
        String  msg = "SUCCESS";
        if(amount<min||amount>max){
            msg = "溢价后金额，应该在"+min+"-"+max+"范围之间";
        }
        return msg;
    }

    /**
     * 提供精确加法计算的add方法
     * @param value1 被加数
     * @param value2 加数
     * @return 两个参数的和
     */
    public static double add(double value1,double value2){
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.add(b2).doubleValue();
    }
    /**
     * 提供精确减法运算的sub方法
     * @param value1 被减数
     * @param value2 减数
     * @return 两个参数的差
     */
    public static double sub(double value1,double value2){
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.subtract(b2).doubleValue();
    }
    /**
     * 提供精确乘法运算的mul方法
     * @param value1 被乘数
     * @param value2 乘数
     * @return 两个参数的积
     */
    public static double mul(double value1,double value2){
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return (b1.multiply(b2)).setScale(4,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    /**
     * 提供精确的除法运算方法div
     * @param value1 被除数
     * @param value2 除数
     * @return 两个参数的商
     * @throws IllegalAccessException
     */
    public static double div(double value1,double value2) throws IllegalAccessException{
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.divide(b2,4, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    public static void main(String[] args){
        System.out.print(String.format(" %.2f", 2.0));
    }
}
