package com.falcon.dsp.param;

import com.falcon.dsp.enumration.target.*;
import com.falcon.dsp.rest.target.request.GdtTarget;
import com.falcon.dsp.rest.target.request.Keyword;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.UUID;

/**
 * @author dongbin.yu
 * @from 2016-04-12
 * @since V1.0
 */
public class TargetParam {

    private String tName;

    private Integer tid;

    private Integer uid;

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

    private int status;

    private Integer strategyId;

    public GdtTarget fetchGdtTarget() {

        GdtTarget gdtTarget = new GdtTarget();
        gdtTarget.setUid(this.getUid());
        if (this.getAge() != null) {
            gdtTarget.setAge(new String[]{this.getAge()});
        }else{
            gdtTarget.setAge(new String[]{});
        }
        if (this.getGender() != null) {
            gdtTarget.setGender(this.getGender().keySet().toArray(new Gender[]{}));
        }else{
            gdtTarget.setGender(new Gender[]{});
        }
        if (this.getConnection() != null) {
            gdtTarget.setConnection(this.getConnection().keySet().toArray(new Connection[]{}));
        }else{
            gdtTarget.setConnection(new Connection[]{});
        }
        if (this.getArea() != null) {
            gdtTarget.setArea(this.getArea().keySet().toArray(new Integer[]{}));
        }else{
            gdtTarget.setArea(new Integer[]{});
        }
        if (this.getTelcom() != null) {
            gdtTarget.setTelcom(this.getTelcom().keySet().toArray(new Operator[]{}));
        }
        if (this.getOs() != null) {
            gdtTarget.setOs(this.getOs().keySet().toArray(new OperationSystem[]{}));
        }else{
            gdtTarget.setOs(new OperationSystem[]{});
        }
        gdtTarget.setMname(UUID.randomUUID().toString());
        if (this.getEducation() != null) {
            gdtTarget.setEducation(this.getEducation().keySet().toArray(new Education[]{}));
        }else{
            gdtTarget.setEducation(new Education[]{});
        }
        if (this.getInterest() != null) {
            gdtTarget.setBusinessinterest(this.getInterest().keySet().toArray(new Integer[]{}));
        }else{
            gdtTarget.setBusinessinterest(new Integer[]{});
        }
        if (this.getMarriage() != null) {
            gdtTarget.setMarriageStatus(this.getMarriage().keySet().toArray(new MarriageStatus[]{}));
        }else{
            gdtTarget.setMarriageStatus(new MarriageStatus[]{});
        }
        if(!StringUtils.isEmpty(this.getKeyword())){
            String keyword = StringUtils.replaceChars(this.getKeyword(),'，',',');
            gdtTarget.setKeyword(new Keyword(keyword.split(",")));
        }else{
            gdtTarget.setKeyword(new Keyword(new String[]{}));
        }
        return gdtTarget;

    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Integer strategyId) {
        this.strategyId = strategyId;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Integer[][] getHours() {
        return hours;
    }

    public void setHours(Integer[][] hours) {
        this.hours = hours;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
