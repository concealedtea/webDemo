package com.falcon.dsp.rest.advertiser.response;
import com.falcon.dsp.common.JsonUtil;
import com.falcon.dsp.enumration.CustomerRegistionType;
import com.falcon.dsp.enumration.CustomerStatus;
import com.falcon.dsp.enumration.FinanceStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Zhouchunhui
 * @from 2016-04-12
 * @since V1.0
 */

public class GdtAdvertiser {

    private int uid;

    @JsonProperty(value = "customer_registration_type")
    private CustomerRegistionType customerRegistrationType;

    @JsonProperty(value = "uname")
    private String uname;

    @JsonProperty(value = "category")
    private Long category;

    @JsonProperty(value = "industry_id")
    private Long industryId;

    @JsonProperty(value = "corporation")
    private String corporation;

    @JsonProperty(value = "address")
    private String address;

    @JsonProperty(value = "corporation_licence")
    private String corporationLicence;

    @JsonProperty(value = "contact_person")
    private String contactPerson;

    @JsonProperty(value = "contact_person_email")
    private String contactPersonEmail;

    @JsonProperty(value = "contact_person_telephone")
    private String contactPersonTelephone;

    @JsonProperty(value = "contact_person_mobile")
    private String contactPersonMobile;

    @JsonProperty(value = "individual_qualification")
    private String individualQualification;

    @JsonProperty(value = "certification")
    private String certification;

    @JsonProperty(value = "site_url")
    private String siteUrl;

    @JsonProperty(value = "site_name")
    private String siteName;

    @JsonProperty(value = "zip_code")
    private String zipCode;

    @JsonProperty(value = "company_area_code")
    private String companyAreaCode;

    @JsonProperty(value = "invoice_area_code")
    private String invoiceAreaCode;

    @JsonProperty(value = "rz_qq_number")
    private String rzQqNumber;

    @JsonProperty(value = "customer_status")
    private CustomerStatus customerStatus;

    @JsonProperty(value = "fund_status")
    private FinanceStatus fundStatus;

    @JsonProperty(value = "virfund_status")
    private FinanceStatus virfundStatus;

    @JsonProperty(value = "qualification")
    private Object qualification;

    private String qualificationStr;

  /*  @JsonProperty(value = "ad_qualification")
    private String adQualification;*/

    @JsonProperty(value = "day_budget")
    private long dayBudget;

    @JsonProperty(value = "business_brand")
    private String businessBrand;

    @JsonProperty(value = "audit_msg")
    private String auditMsg;

    @JsonProperty(value = "industry_tree")
    private Object industryTree;

    private String industryTreeStr;

    @JsonProperty(value = "agency_id")
    private Integer agencyId;
    
    /**
     * 
     * 推广站点 ICP备案截图文件 URL
     */
    @JsonProperty(value = "icp_image_url")
    private String icpImageUrl;
    
    
    /**
     * 
     * 推广站点 ICP备案截图文件 URL
     */
    @JsonProperty(value = "app_id")
    private Integer appId;
    

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public String getIcpImageUrl() {
		return StringUtils.isEmpty(icpImageUrl)?"":this.icpImageUrl;
	}

	public void setIcpImageUrl(String icpImageUrl) {
		this.icpImageUrl = icpImageUrl;
	}

	public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public CustomerRegistionType getCustomerRegistrationType() {
        return customerRegistrationType;
    }

    public void setCustomerRegistrationType(CustomerRegistionType customerRegistrationType) {
        this.customerRegistrationType = customerRegistrationType;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public Long getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Long industryId) {
        this.industryId = industryId;
    }

    public String getCorporation() {
        return corporation;
    }

    public void setCorporation(String corporation) {
        this.corporation = corporation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCorporationLicence() {
        return corporationLicence;
    }

    public void setCorporationLicence(String corporationLicence) {
        this.corporationLicence = corporationLicence;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPersonEmail() {
        return contactPersonEmail;
    }

    public void setContactPersonEmail(String contactPersonEmail) {
        this.contactPersonEmail = contactPersonEmail;
    }

    public String getContactPersonTelephone() {
        return contactPersonTelephone;
    }

    public void setContactPersonTelephone(String contactPersonTelephone) {
        this.contactPersonTelephone = contactPersonTelephone;
    }

    public String getContactPersonMobile() {
        return contactPersonMobile;
    }

    public void setContactPersonMobile(String contactPersonMobile) {
        this.contactPersonMobile = contactPersonMobile;
    }

    public String getIndividualQualification() {
        return individualQualification;
    }

    public void setIndividualQualification(String individualQualification) {
        this.individualQualification = individualQualification;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCompanyAreaCode() {
        return companyAreaCode;
    }

    public void setCompanyAreaCode(String companyAreaCode) {
        this.companyAreaCode = companyAreaCode;
    }

    public String getInvoiceAreaCode() {
        return invoiceAreaCode;
    }

    public void setInvoiceAreaCode(String invoiceAreaCode) {
        this.invoiceAreaCode = invoiceAreaCode;
    }

    public String getRzQqNumber() {
        return rzQqNumber;
    }

    public void setRzQqNumber(String rzQqNumber) {
        this.rzQqNumber = rzQqNumber;
    }

    public CustomerStatus getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(CustomerStatus customerStatus) {
        this.customerStatus = customerStatus;
    }

    public FinanceStatus getFundStatus() {
        return fundStatus;
    }

    public void setFundStatus(FinanceStatus fundStatus) {
        this.fundStatus = fundStatus;
    }

    public FinanceStatus getVirfundStatus() {
        return virfundStatus;
    }

    public void setVirfundStatus(FinanceStatus virfundStatus) {
        this.virfundStatus = virfundStatus;
    }

   /* public String getAdQualification() {
        return adQualification;
    }

    public void setAdQualification(String adQualification) {
        this.adQualification = adQualification;
    }
*/
    public long getDayBudget() {
        return dayBudget;
    }

    public void setDayBudget(long dayBudget) {
        this.dayBudget = dayBudget;
    }

    public String getBusinessBrand() {
        return businessBrand;
    }

    public void setBusinessBrand(String businessBrand) {
        this.businessBrand = businessBrand;
    }

    public String getAuditMsg() {
        return auditMsg;
    }

    public void setAuditMsg(String auditMsg) {
        this.auditMsg = auditMsg;
    }

    
    public String getQualificationStr() {
        Map mapResult = new LinkedHashMap<>();
        if(qualification==null||qualification.equals("")){
            return "";
        }
        if(qualification instanceof ArrayList){
            ArrayList<LinkedHashMap> list = (ArrayList<LinkedHashMap>) qualification;
            if(list!=null&&list.size()>0){
                for(LinkedHashMap map:list){
                    mapResult.put(map.get("name"),map.get("url"));
                }
            }
            return JsonUtil.toJson(mapResult);
        }else{
            return JsonUtil.toJson(qualification);
        }

    }

    public String getIndustryTreeStr() {
        return JsonUtil.toJson(industryTree);
    }

    public void setQualificationStr(String qualificationStr) {
        this.qualificationStr = qualificationStr;
    }

    public Object getIndustryTree() {
        return industryTree;
    }

    public void setIndustryTree(Object industryTree) {
        this.industryTree = industryTree;
    }

    public void setIndustryTreeStr(String industryTreeStr) {
        this.industryTreeStr = industryTreeStr;
    }

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    public Object getQualification() {
        return qualification;
    }

    public void setQualification(Object qualification) {
        this.qualification = qualification;
    }
}
