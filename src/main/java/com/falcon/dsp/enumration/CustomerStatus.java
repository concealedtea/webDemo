package com.falcon.dsp.enumration;

/**
 * @author Zhouchunhui
 * @from 2016-04-13
 * @since V1.0
 */
public enum CustomerStatus {
    CUSTOMERSTATUS_NORMAL("CUSTOMERSTATUS_NORMAL","有效"),
    CUSTOMERSTATUS_PENDING("CUSTOMERSTATUS_PENDING","待审核"),
    CUSTOMERSTATUS_DENIED("CUSTOMERSTATUS_DENIED","审核不通过"),
    CUSTOMERSTATUS_FROZEN("CUSTOMERSTATUS_FROZEN","冻结"),
    CUSTOMERSTATUS_TOBE_ACCEPTED("CUSTOMERSTATUS_TOBE_ACCEPTED","待接受"),
    CUSTOMERSTATUS_TOBE_ACTIVATED("CUSTOMERSTATUS_TOBE_ACTIVATED","待激活"),
    CUSTOMERSTATUS_SUSPEND("CUSTOMERSTATUS_SUSPEND","挂起"),
    CUSTOMERSTATUS_MATERIAL_PREPARED("CUSTOMERSTATUS_MATERIAL_PREPARED","准备"),
    CUSTOMERSTATUS_DELETED("CUSTOMERSTATUS_DELETED","删除"),
    CUSTOMERSTATUS_UNREGISTERED("CUSTOMERSTATUS_UNREGISTERED","未注册"),
    UNKNOWN0("UNKNOWN(0)","未知，如 UNKNOWN(0) 表示一个未知的/ 未激活的状态");
    private String name;
    private String description;

    private CustomerStatus(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
