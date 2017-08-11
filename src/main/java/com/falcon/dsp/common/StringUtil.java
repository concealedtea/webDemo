package com.falcon.dsp.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author dongbin.yu
 * @from 2016-03-22
 * @since V1.0
 */
public class StringUtil {

    private static Logger logger = LoggerFactory.getLogger(StringUtil.class);

    private static Pattern camelToUnderline = Pattern.compile("([a-z]{1})([A-Z]{1})");
    private static Pattern underlineToCamel = Pattern.compile("([a-zA-Z]{1})_([a-zA-Z]{1})");

    public static boolean hasText(String s) {
        return s != null && s.length() > 0;
    }

    public static boolean hasTextWithoutWhiteSpace(String s) {
        return s != null && s.trim().length() > 0;
    }

    public static int parseInt(String s) {
        return parseInt(s, 0);
    }

    public static int parseInt(String s, int defaultValue) {
        return parseInt(s, defaultValue, "");
    }

    public static int parseInt(String s, int defaultValue, String description) {
        int result = 0;
        try {
            result = Integer.parseInt(s);
        } catch (Exception e) {
            logger.info("param {} is not int,maybe null or String", description);
        }

        return result;
    }

    public static double parseDouble(String s) {
        return parseDouble(s, 0.0);
    }

    public static double parseDouble(String s, double defaultValue) {
        return parseDouble(s, defaultValue, "");
    }

    public static double parseDouble(String s, double defaultValue, String description) {
        double result = 0;
        try {
            result = Double.parseDouble(s);
        } catch (Exception e) {
            logger.info("param {} is not double,maybe null or String", description);
        }

        return result;
    }

    public static long parseLong(String s) {
        return parseLong(s, 0);
    }

    public static long parseLong(String s, long defaultValue) {
        return parseLong(s, defaultValue, "");
    }

    public static long parseLong(String s, long defaultValue, String description) {
        long result = 0;
        try {
            result = Long.parseLong(s);
        } catch (Exception e) {
            logger.info("param {} is not double,maybe null or String", description);
        }

        return result;
    }

    public static String combinePath(String parent, String path) {

        String combinePath = path;

        if (hasText(parent)) {
            if (parent.endsWith(File.separator)) {
                combinePath = String.format("%s%s", parent, path);
            } else {
                combinePath = String.format("%s%s%s", parent, File.separator, path);
            }

        } else {
            logger.info("parent path is null");
        }

        return combinePath;
    }

    public static String camelToUnderline(String s) {

        StringBuffer sb = new StringBuffer("");
        Matcher matcher = camelToUnderline.matcher(s);
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1) + "_" + matcher.group(2).toLowerCase());
        }

        matcher.appendTail(sb);

        return sb.toString();
    }

    public static String underlineToCamel(String s) {

        StringBuffer sb = new StringBuffer("");
        Matcher matcher = underlineToCamel.matcher(s);
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1) + matcher.group(2).toUpperCase());
        }

        matcher.appendTail(sb);

        return sb.toString();
    }

    public static String convertTargetDate(Integer[][] targetTime) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer[] day : targetTime) {
            for (Integer hour : day) {
                stringBuilder.append(String.format("%d%d", hour, hour));
            }
        }
        return stringBuilder.toString();
    }

    /**
     * URL检查<br> http|https|ftp 需要小写，后面的可以大写
     * <br>
     * @param pInput     要检查的字符串<br>
     * @return boolean   返回检查结果<br>
     */
    public static boolean isUrl (String pInput) {
        if(pInput == null){
            return false;
        }
        Pattern p = Pattern.compile("^(http|https|ftp)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)" +
                "(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*" +
                "(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*" +
                "(\\w*-)*(\\w*=)*)*(\\w*)*)$");

        Matcher matcher = p.matcher(pInput);
        return matcher.matches();
    }
}
