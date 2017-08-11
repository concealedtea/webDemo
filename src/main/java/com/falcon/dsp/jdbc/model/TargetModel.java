package com.falcon.dsp.jdbc.model;

import com.falcon.dsp.enumration.target.*;

import java.util.Map;

/**
 * @author dongbin.yu
 * @from 2016-04-13
 * @since V1.0
 */
public class TargetModel {

    private String age;

    private Integer[][] hours;

    private Map<Integer, String> area;

    private Map<Connection, String> connection;

    private Map<Education, String> education;

    private Map<Gender, String> gender;

    private Map<OperationSystem, String> os;

    private Map<Operator, String> telcom;

    /**
     * 商业兴趣
     */
    private Map<Integer,String> interest;

    /**
     * 婚恋状况
     */
    private Map<MarriageStatus, String> marriage;

    /**
     * 关键词
     */
    private String keyword;

    public Integer[][] getHours() {
        return hours;
    }

    public void setHours(Integer[][] hours) {
        this.hours = hours;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Map<Integer, String> getArea() {
        return area;
    }

    public void setArea(Map<Integer, String> area) {
        this.area = area;
    }

    public Map<Connection, String> getConnection() {
        return connection;
    }

    public void setConnection(Map<Connection, String> connection) {
        this.connection = connection;
    }

    public Map<Education, String> getEducation() {
        return education;
    }

    public void setEducation(Map<Education, String> education) {
        this.education = education;
    }

    public Map<Gender, String> getGender() {
        return gender;
    }

    public void setGender(Map<Gender, String> gender) {
        this.gender = gender;
    }

    public Map<OperationSystem, String> getOs() {
        return os;
    }

    public void setOs(Map<OperationSystem, String> os) {
        this.os = os;
    }

    public Map<Operator, String> getTelcom() {
        return telcom;
    }

    public void setTelcom(Map<Operator, String> telcom) {
        this.telcom = telcom;
    }

    public Map<Integer, String> getInterest() {
        return interest;
    }

    public void setInterest(Map<Integer, String> interest) {
        this.interest = interest;
    }

    public Map<MarriageStatus, String> getMarriage() {
        return marriage;
    }

    public void setMarriage(Map<MarriageStatus, String> marriage) {
        this.marriage = marriage;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
