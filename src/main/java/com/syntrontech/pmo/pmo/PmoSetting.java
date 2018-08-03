package com.syntrontech.pmo.pmo;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="pmo")
public class PmoSetting {

    @Value("${pmo.url}")
    private String url;

    @Value("${pmo.account}")
    private String pmoAccount;

    @Value("${pmo.credentials}")
    private String pmoCredentials;

    @Value("${pmo.areaCode}")
    private String areaCode;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPmoAccount() {
        return pmoAccount;
    }

    public void setPmoAccount(String pmoAccount) {
        this.pmoAccount = pmoAccount;
    }

    public String getPmoCredentials() {
        return pmoCredentials;
    }

    public void setPmoCredentials(String pmoCredentials) {
        this.pmoCredentials = pmoCredentials;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
