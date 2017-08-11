package com.falcon.dsp.enumration;

/**
 * @author dongbin.yu
 * @from 2016-04-18
 * @since V1.0
 */
public enum UserRoleType {

    AGENCY("agency","代理商"),
    ADVERTISER("advertiser","广告主"),
	ADMIN("admin","超级用户");

    private String name;

    private String description;

    private UserRoleType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
