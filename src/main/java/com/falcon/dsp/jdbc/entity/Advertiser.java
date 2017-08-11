package com.falcon.dsp.jdbc.entity;

import org.apache.commons.lang3.StringUtils;
import com.falcon.dsp.common.JsonUtil;
import com.falcon.dsp.enumration.CustomerStatus;
import com.falcon.dsp.rest.common.BasicEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.type.Alias;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 广点通-广告主模块
 */
@Alias("advertiser")
public class Advertiser extends BasicEntity{

    /**
     * 客户 id
     */
    private Integer uid;

    /**
     * 客户注册类型 customer_registration_type
     */
    @JsonProperty(value = "customer_registration_type")
    private String customerRegistrationType;

    /**
     * 客户名称
     */
    private String uname;

    /**
     * 类目
     */
    private Integer category;

    /**
     * 行业分类 id industry_id
     */
    @JsonProperty(value = "industry_id")
    private Long industryId;
    
    /**
     * 行业分类 id industry_id
     */
    @JsonProperty(value = "agency_id")
    private Integer agencyId;

    /**
     * 公司名称
     */
    private String corporation;

	/**
     * 公司地址
     */
    private String address;

    /**
     * 公司营业执照 corporation_licence
     * 营业执照号码 /ICP证备号
     */
    @JsonProperty(value = "corporation_licence")
    private String corporationLicence;
    
    /**
     * 
     * 推广站点 ICP备案截图文件 URL
     */
    @JsonProperty(value = "icp_image_url")
    private String icpImageUrl;

    /**
     * 联系人 contact_person
     */
    @JsonProperty(value = "contact_person")
    private String contactPerson;

    /**
     * 联系人邮箱 contact_person_email
     */
    @JsonProperty(value = "contact_person_email")
    private String contactPersonEmail;

    /**
     * 联系人电话 contact_person_telephone
     */
    @JsonProperty(value = "contact_person_telephone")
    private String contactPersonTelephone;

    /**
     * 联系人手机 contact_person_mobile
     */
    @JsonProperty(value = "contact_person_mobile")
    private String contactPersonMobile;

    /**
     * 身份证明，当广告主组织类型为个人时必填 individual_qualification
     */
    @JsonProperty(value = "individual_qualification")
    private String individualQualification;

    /**
     * 营业执照url
     */
    private String certification;

    /**
     * 站点地址 site_url
    */
    @JsonProperty(value = "site_url")
    private String siteUrl;

    /**
     * 站点名称 site_name
     */
    @JsonProperty(value = "site_name")
    private String siteName;

    /**
     * 邮政编码 zip_code
     */
    @JsonProperty(value = "zip_code")
    private String zipCode;

    /**
     * 公司所在地区编码 company_area_code
     */
    @JsonProperty(value = "company_area_code")
    private String companyAreaCode;

    /**
     * 联系人所在地区编码 invoice_area_code
     */
    @JsonProperty(value = "invoice_area_code")
    private String invoiceAreaCode;

    /**
     * 客户状态 customer_status
     */
    @JsonProperty(value = "customer_status")
    private CustomerStatus customerStatus;

    /**
     * 资金状态 fund_status
     */
    @JsonProperty(value = "fund_status")
    private String fundStatus;

    /**
     * 虚拟资金状态 virfund_status
     */
    @JsonProperty(value = "virfund_status")
    private String virfundStatus;

    /**
     * 行业资质证明图片 URL 地址
     */
    private Object qualification;

    /**
     * 广告特殊资质证明图片 URL 地址 ad_qualification
     */
    @JsonProperty(value = "ad_qualification")
    private String adQualification;

    /**
     * 日消耗限额，单位为分 day_budget
     */
    @JsonProperty(value = "day_budget")
    private double dayBudget;

    /**
     * 品牌名称 business_brand
     */
    @JsonProperty(value = "business_brand")
    private String businessBrand;

    /**
     * 审核信息，只有审核拒绝的账户该字段才有值 audit_msg
     */
    @JsonProperty(value = "audit_msg")
    private String auditMsg;
    
    /**
     * 认证空间 QQ
     */
    @JsonProperty(value = "rz_qq_number")
    private String rzQqNumber;
    
    /** 
     * 推广站点 ICP备案截图文件 URL
     */
    @JsonProperty(value = "app_id")
    private Integer appId;
    
    @JsonProperty(value = "qualification")
    private String qualificationStr;
    
	@JsonProperty(value = "industry_tree")
    private Object industryTree;

    private String industryTreeStr;

    private int commission;

    public Object getIndustryTree() {
		return industryTree;
	}

	public void setIndustryTree(Object industryTree) {
		this.industryTree = industryTree;
	}

	public String getQualificationStr() {
		if(qualification!=null&&!qualification.equals("")){
			qualificationStr = JsonUtil.toJson(qualification);
		}else{
			qualificationStr=null;
		}
        return  qualificationStr;
    }
    
    public void setQualificationStr(String qualificationStr) {
        this.qualificationStr = qualificationStr;
    }

    public String getIndustryTreeStr() {
        return JsonUtil.toJson(industryTree);
    }
    
    public void setIndustryTreeStr(String industryTreeStr) {
        this.industryTreeStr = industryTreeStr;
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

	public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Long getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Long industryId) {
        this.industryId = industryId;
    }

    public double getDayBudget() {
        return dayBudget;
    }

    public void setDayBudget(double dayBudget) {
        this.dayBudget = dayBudget;
    }

    public String getCustomerRegistrationType() {
        return customerRegistrationType;
    }

    public void setCustomerRegistrationType(String customerRegistrationType) {
        this.customerRegistrationType = customerRegistrationType;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
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

    public CustomerStatus getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(CustomerStatus customerStatus) {
        this.customerStatus = customerStatus;
    }

    public String getFundStatus() {
        return fundStatus;
    }

    public void setFundStatus(String fundStatus) {
        this.fundStatus = fundStatus;
    }

    public String getVirfundStatus() {
        return virfundStatus;
    }

    public void setVirfundStatus(String virfundStatus) {
        this.virfundStatus = virfundStatus;
    }


    public Object getQualification() {
		return qualification;
	}

	public void setQualification(Object qualification) {
		
		if(qualification instanceof String){
			qualificationStr = qualification.toString();
		}else{			
			this.qualification = qualification;
		}
	} 
	public static void main(String[] ar){
		
		ArrayList<HashMap<String, String>> listm = new ArrayList<>();
		ArrayList<String[]> listar = new ArrayList<>();
		ArrayList<String> list = new ArrayList<>();
		
	
		HashMap<String, String> map = new HashMap<>();
		int a=3;
		while(a>0){
			HashMap<String, String> m = new HashMap<>();
			m.put("aa","aav");
			map.put("aa","aav");
			a--;
			list.add("aa");
			listar.add(new String[]{"aa","bb"});
//			list.add("{"+mat.group()+"}");
			 
			listm.add(m);
		}
		/*
		System.out.println(list.toString());
		System.out.println(listar.toString()); 
		//ArrayList<String[]> fromJson = JsonUtil.fromJson("[[name=qualificationurl1, url=/upl]]", ArrayList.class);
		ArrayList fromJsona = JsonUtil.fromJson("[\"aa\", \"bb\"]", ArrayList.class);
		
		
		String JsonStr = JsonUtil.toJson(listar);
		fromJsona = JsonUtil.fromJson(JsonStr, ArrayList.class); 
		
		System.out.println( JsonStr);
		System.out.println(fromJsona);

		
		Advertiser advertiser = new Advertiser();
		String advertiserStr = JsonUtil.toJson(advertiser);
		System.out.println(advertiserStr);
		*/
		
		String listMStr = JsonUtil.toJson(listm);
		System.out.println(listMStr);
		
		Object oMap = map;
		String mapStr = JsonUtil.toJson(oMap);
		System.out.println(mapStr);
		
		/*
		ArrayList<HashMap<String, String>> listMStrJson = JsonUtil.fromJson(listMStr, ArrayList.class); 
		System.out.println(listMStrJson);
		
		String lsM="[{aa=aav}, {aa=aav}, {aa=aav}]";
		lsM = lsM.replaceAll("}", "\"}").replaceAll("aa=", "\"aa\":\"");
		System.out.println(lsM);
		ArrayList<HashMap<String, String>> listMStrtes = JsonUtil.fromJson(lsM, ArrayList.class); 
		System.out.println(listMStrtes);
		
		
		
//		String lsM2="[{aa=aav2, url=urlll}, {aa=aav2, url=urlll}, {aa=aav, url=urlll}]";
//		lsM2 = lsM2.replaceAll("}", "\"}").replaceAll("aa=", "\"aa\":\"").replaceAll(", url", "\", \"url\":\"");
//		System.out.println(lsM2);
//		ArrayList<HashMap<String, String>> listMStrtes2 = JsonUtil.fromJson(lsM2, ArrayList.class); 
//		System.out.println(listMStrtes2);
		
		String quaStr ="[{name=qualificationurl1, url=/upload/20160415/473cdbfa-4609-4127-a1cd-235d30dcaed7.png},{name=qualificationurl2, url=/upload/20160415/473cdbfa-4609-4127-a1cd-235d30dcaed7.png}]";
		
		String qualificationStrTest = quaStr.toString().replaceAll("}", "\"}").replaceAll("name=", "\"name\":\"").replaceAll(", url=", "\", \"url\":\"");
		System.out.println(qualificationStrTest);
		ArrayList<HashMap<String, String>> lists = JsonUtil.fromJson(qualificationStrTest.toString(), ArrayList.class); 
		
		System.out.println(lists);
		*/
		
		
		
	}
	public String getAdQualification() {
        return adQualification;
    }

    public void setAdQualification(String adQualification) {
        this.adQualification = adQualification;
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

    public Integer getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}

	public String getIcpImageUrl() {
		return icpImageUrl;
	}

	public void setIcpImageUrl(String icpImageUrl) {
		this.icpImageUrl = icpImageUrl;
	}
    
	public void qualificationStr2BeanJson(){
		if(StringUtils.isEmpty(qualificationStr)){
			qualification=null;
			return;
		}
		//qualificationJson = qualificationStr.toString().replaceAll("}", "\"}").replaceAll("name=", "\"name\":\"").replaceAll(", url=", "\", \"url\":\"");
		HashMap<String, String> map = JsonUtil.fromJson(qualificationStr, HashMap.class); 
		this.qualification = map;
	}
	
	public void qualificationBean2Str(){
		if(qualification!=null){
			qualificationStr = JsonUtil.toJson(qualification);
		}else{
			qualificationStr=null;
		}
			
	}
	//industryTree
	public void industryTreeStr2BeanJson(){
		if(industryTreeStr==null){
			qualification=null;
			return;
		}
		//qualificationJson = qualificationStr.toString().replaceAll("}", "\"}").replaceAll("name=", "\"name\":\"").replaceAll(", url=", "\", \"url\":\"");
		ArrayList<HashMap<String, String>> list = JsonUtil.fromJson(industryTreeStr, ArrayList.class); 
		this.qualification = list;
	}
	
	public void industryTreeBean2Str(){
		if(qualification!=null){
			industryTreeStr = JsonUtil.toJson(qualification);
		}else{
			industryTreeStr=null;
		}
		
	}
    public int getUserStatusToInt() {
        switch (customerStatus){
            case CUSTOMERSTATUS_NORMAL:
                return 0;
            case CUSTOMERSTATUS_PENDING:
                return 1;
            case CUSTOMERSTATUS_DENIED:
                return 2;
            case CUSTOMERSTATUS_FROZEN:
                return 3;
            case CUSTOMERSTATUS_TOBE_ACCEPTED:
                return 4;
            case CUSTOMERSTATUS_TOBE_ACTIVATED:
                return 5;
            case CUSTOMERSTATUS_SUSPEND:
                return 6;
            case CUSTOMERSTATUS_MATERIAL_PREPARED:
                return 7;
            case CUSTOMERSTATUS_DELETED:
                return 8;
            case CUSTOMERSTATUS_UNREGISTERED:
                return 9;
            case UNKNOWN0:
                return 10;
            default:
                return 0;
        }
    }
    public int getCommission() {
        return commission;
    }

    public void setCommission(int commission) {
        this.commission = commission;
    }
}
