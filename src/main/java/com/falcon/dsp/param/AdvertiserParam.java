package com.falcon.dsp.param;

import com.falcon.dsp.enumration.CustomerRegistionType;
import com.falcon.dsp.enumration.CustomerStatus;
import com.falcon.dsp.enumration.FinanceStatus;
import com.falcon.dsp.jdbc.entity.Advertiser;
import com.falcon.dsp.rest.advertiser.response.GdtAdvertiser;
import com.falcon.dsp.rest.common.BasicParam;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

/**
 * @author Zhouchunhui
 * @from 2016-04-13
 * @since V1.0
 */
public class AdvertiserParam extends BasicParam {

	private Integer uid;

	private Integer agencyId;

	private String searchKey;

	private String corporation;

	private String corporationLicence;

	private String certification;

	private Long industryId;
	
	private HashMap<String,String> qualification;
	
	private String siteUrl;
	
	private String icpImageUrl;
	
	private String rzQqNumber;
	
	private Integer appId;
	
	private String businessBrand;
	
	private String contactPersonTelephone;
	
	private String contactPersonMobile;

    @JsonProperty(value = "customer_registration_type")
    private CustomerRegistionType customerRegistrationType;
	
    @JsonProperty(value = "uname")
    private String uname;

    @JsonProperty(value = "category")
    private long category;


    @JsonProperty(value = "address")
    private String address;


    @JsonProperty(value = "contact_person")
    private String contactPerson;

    @JsonProperty(value = "contact_person_email")
    private String contactPersonEmail;


    @JsonProperty(value = "individual_qualification")
    private String individualQualification;

    @JsonProperty(value = "site_name")
    private String siteName;

    @JsonProperty(value = "zip_code")
    private String zipCode;

    @JsonProperty(value = "company_area_code")
    private String companyAreaCode;

    @JsonProperty(value = "invoice_area_code")
    private String invoiceAreaCode;

    @JsonProperty(value = "customer_status")
    private CustomerStatus customerStatus;

    private String customerStatusName;

    @JsonProperty(value = "fund_status")
    private FinanceStatus fundStatus;

    @JsonProperty(value = "virfund_status")
    private FinanceStatus virfundStatus;

    private String qualificationStr;

    @JsonProperty(value = "ad_qualification")
    private String adQualification;

    @JsonProperty(value = "day_budget")
    private long dayBudget;

    @JsonProperty(value = "audit_msg")
    private String auditMsg;

    @JsonProperty(value = "industry_tree")
    private Object industryTree;

    private String industryTreeStr;

	private double rate;

	private int commission;

    public String getCustomerStatusName() {
		return customerStatusName;
	}

	public void setCustomerStatusName(String customerStatusName) {
		this.customerStatusName = customerStatusName;
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

	public long getCategory() {
		return category;
	}

	public void setCategory(long category) {
		this.category = category;
	}
	

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getIndividualQualification() {
		return individualQualification;
	}

	public void setIndividualQualification(String individualQualification) {
		this.individualQualification = individualQualification;
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

	public String getQualificationStr() {
		return qualificationStr;
	}

	public void setQualificationStr(String qualificationStr) {
		this.qualificationStr = qualificationStr;
	}

	public String getAdQualification() {
		return adQualification;
	}

	public void setAdQualification(String adQualification) {
		this.adQualification = adQualification;
	}

	public long getDayBudget() {
		return dayBudget;
	}

	public void setDayBudget(long dayBudget) {
		this.dayBudget = dayBudget;
	}

	public String getAuditMsg() {
		return auditMsg;
	}

	public void setAuditMsg(String auditMsg) {
		this.auditMsg = auditMsg;
	}

	public Object getIndustryTree() {
		return industryTree;
	}

	public void setIndustryTree(Object industryTree) {
		this.industryTree = industryTree;
	}

	public String getIndustryTreeStr() {
		return industryTreeStr;
	}

	public void setIndustryTreeStr(String industryTreeStr) {
		this.industryTreeStr = industryTreeStr;
	}
	
    public String getCorporation() {
		return corporation;
	}

	public void setCorporation(String corporation) {
		this.corporation = corporation;
	}

	public String getCorporationLicence() {
		return corporationLicence;
	}

	public void setCorporationLicence(String corporationLicence) {
		this.corporationLicence = corporationLicence;
	}

	public String getCertification() {
		return certification;
	}

	public void setCertification(String certification) {
		this.certification = certification;
	}

	public Long getIndustryId() {
		return industryId;
	}

	public void setIndustryId(Long industryId) {
		this.industryId = industryId;
	}

	public HashMap<String,String> getQualification() {
		return qualification;
	}

	public void setQualification(HashMap<String,String> qualification) {
		this.qualification = qualification;
	}

	public String getSiteUrl() {
		return siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}

	public String getIcpImageUrl() {
		return icpImageUrl;
	}

	public void setIcpImageUrl(String icpImageUrl) {
		this.icpImageUrl = icpImageUrl;
	}

	public String getRzQqNumber() {
		return rzQqNumber;
	}

	public void setRzQqNumber(String rzQqNumber) {
		this.rzQqNumber = rzQqNumber;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public String getBusinessBrand() {
		return businessBrand;
	}

	public void setBusinessBrand(String businessBrand) {
		this.businessBrand = businessBrand;
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

	public Integer getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public int getCommission() {
		return commission;
	}

	public void setCommission(int commission) {
		this.commission = commission;
	}

	public GdtAdvertiser getGdtAdvertiser(){
		/*
		页面上:
		corporation:corporation, *
		corporationLicence:corporationLicence, *
		industryId:industryId,*
		siteUrl:siteUrl,*
		certification:certification,*
		qualification:qualification,
		icpImageUrl:icpImageUrl,*
		contactPerson:contactPerson,
		contactPersonTelephone:contactPersonTelephone,
		ontactPersonEmail:contactPersonEmail
		
		customer_status
		*/
		
		
		
		
		//gdt可选项
		GdtAdvertiser gdtAdvertiser = new GdtAdvertiser();
    	if(!StringUtils.isEmpty(rzQqNumber))gdtAdvertiser.setRzQqNumber(rzQqNumber);
    	if(appId!=null)gdtAdvertiser.setAppId(appId);
    	if(!StringUtils.isEmpty(businessBrand))gdtAdvertiser.setBusinessBrand(businessBrand);
    	if(!StringUtils.isEmpty(contactPersonTelephone))gdtAdvertiser.setContactPersonTelephone(contactPersonTelephone);
    	//gdt可选项 且 页面上可选
    	if(qualification!=null)gdtAdvertiser.setQualification(qualification);
    	if(!StringUtils.isEmpty(contactPersonMobile))gdtAdvertiser.setContactPersonMobile(contactPersonMobile);
        
    	//必填项
    	gdtAdvertiser.setIcpImageUrl(icpImageUrl);
    	gdtAdvertiser.setSiteUrl(siteUrl);
    	gdtAdvertiser.setIndustryId(industryId);
    	gdtAdvertiser.setCertification(certification);
    	gdtAdvertiser.setCorporationLicence(corporationLicence);
    	gdtAdvertiser.setCorporation(corporation);
			
		return gdtAdvertiser;
	}
	
	/**
	 *  广告主使用 更新。
	 * @return
	 */
	public Advertiser getAdvertiserForAdvertiserUpdate(){
		
		Advertiser advertiser = new Advertiser();

		//页面上 可选项
		if(qualification!=null)advertiser.setQualification(qualification);
		if(!StringUtils.isEmpty(contactPerson))advertiser.setContactPerson(contactPerson);
		if(!StringUtils.isEmpty(contactPersonMobile))advertiser.setContactPersonMobile(contactPersonMobile);
		if(!StringUtils.isEmpty(contactPersonEmail))advertiser.setContactPersonEmail(contactPersonEmail);
		/*
		页面上:
		corporation:corporation, *
		corporationLicence:corporationLicence, *
		industryId:industryId,*
		siteUrl:siteUrl,*
		certification:certification,*
		qualification:qualification,
		icpImageUrl:icpImageUrl,*
		contactPerson:contactPerson,
		contactPersonMobile:contactPersonMobile
		ontactPersonEmail:contactPersonEmail
		
		customer_status  //只有在 本地有
		 */
		
		//主键必填项
		advertiser.setUid(uid);
		//每次数据库更新 必须
		advertiser.setCustomerStatus(customerStatus);
		//必填项
		advertiser.setUname(uname);
		advertiser.setIcpImageUrl(icpImageUrl);
		advertiser.setSiteUrl(siteUrl);
		advertiser.setIndustryId(industryId);
		advertiser.setCertification(certification);
		advertiser.setCorporationLicence(corporationLicence);
		advertiser.setCorporation(corporation);
		
		advertiser.qualificationBean2Str();
		
		return advertiser;
	}
	
	/**
	 *  代理商使用 新建。
	 * @return
	 */
	public Advertiser getAdvertiserForAgencyInsert(){
		
		Advertiser advertiser = new Advertiser();
		//没有的话 默认
		if(fundStatus!=null){
			advertiser.setFundStatus(fundStatus.name());
		}else{
			advertiser.setFundStatus(FinanceStatus.FUNDSTATUS_NOT_ACTIVED.name());
		}
		if(virfundStatus!=null){
			advertiser.setVirfundStatus(virfundStatus.name());
		}else{
			advertiser.setVirfundStatus(FinanceStatus.FUNDSTATUS_NOT_ACTIVED.name());
		}
		
		//代理id  必填项
		advertiser.setAgencyId(agencyId);
		//页面上 可选项
		if(qualification!=null)advertiser.setQualification(qualification);
		if(!StringUtils.isEmpty(contactPerson))advertiser.setContactPerson(contactPerson);
		if(!StringUtils.isEmpty(contactPersonMobile))advertiser.setContactPersonMobile(contactPersonMobile);
		if(!StringUtils.isEmpty(contactPersonEmail))advertiser.setContactPersonEmail(contactPersonEmail);
		/*
		页面上:
		corporation:corporation, *
		corporationLicence:corporationLicence, *
		industryId:industryId,*
		siteUrl:siteUrl,*
		certification:certification,*
		qualification:qualification,
		icpImageUrl:icpImageUrl,*
		contactPerson:contactPerson,
		contactPersonMobile:contactPersonMobile
		ontactPersonEmail:contactPersonEmail
		
		customer_status  //只有在 本地有
		 */
		
		//主键必填项
		advertiser.setUid(uid);
		//每次数据库更新 必须
		advertiser.setCustomerStatus(customerStatus);
		//必填项
		advertiser.setUname(uname);
		advertiser.setIcpImageUrl(icpImageUrl);
		advertiser.setSiteUrl(siteUrl);
		advertiser.setIndustryId(industryId);
		advertiser.setCertification(certification);
		advertiser.setCorporationLicence(corporationLicence);
		advertiser.setCorporation(corporation);
		
		advertiser.qualificationBean2Str();
		advertiser.setCommission(commission);
		return advertiser;
	}
	/**
	 *  代理商 使用 更新。
	 * @return
	 */
	public Advertiser getAdvertiserForAgencyUpdate(){
		
		Advertiser advertiser = new Advertiser();
		//页面上 可选项
		if(qualification!=null)advertiser.setQualification(qualification);
		if(!StringUtils.isEmpty(contactPerson))advertiser.setContactPerson(contactPerson);
		if(!StringUtils.isEmpty(contactPersonMobile))advertiser.setContactPersonMobile(contactPersonMobile);
		if(!StringUtils.isEmpty(contactPersonEmail))advertiser.setContactPersonEmail(contactPersonEmail);
		/*
		页面上:
		uname:uname
		corporation:corporation, *
		corporationLicence:corporationLicence, *
		industryId:industryId,*
		siteUrl:siteUrl,*
		certification:certification,*
		qualification:qualification,
		icpImageUrl:icpImageUrl,*
		contactPerson:contactPerson,
		contactPersonMobile:contactPersonMobile
		ontactPersonEmail:contactPersonEmail
		
		customer_status  //只有在 本地有
		 */
		
		//主键必填项
		advertiser.setUid(uid);
		//必填项
		advertiser.setUname(uname);
		advertiser.setIcpImageUrl(icpImageUrl);
		advertiser.setSiteUrl(siteUrl);
		advertiser.setIndustryId(industryId);
		advertiser.setCertification(certification);
		advertiser.setCorporationLicence(corporationLicence);
		advertiser.setCorporation(corporation);
		advertiser.qualificationBean2Str();
		
		return advertiser;
	}
	

}
