package com.th.guard.component;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@ConfigurationProperties(prefix = "")
public class AppEnvLogger {

    private final Environment env;

    // app.*
    private String appVersion;

    // backend_server.*
    private String backendServerWebUrl;

    // setting.*
    private boolean settingEnableSaveLog;
    private boolean settingEnableRegister;

    public AppEnvLogger(Environment env) {
        this.env = env;
    }

    @PostConstruct
    public void logEnv() {
        System.out.println("======================================");
        System.out.println(" 🚀 Guard Service Started");
        System.out.println(" App Version       : " + appVersion);
        System.out.println(" Frontend URL      : " + backendServerWebUrl);
        System.out.println(" Enable Save Log   : " + settingEnableSaveLog);
        System.out.println(" Enable Register   : " + settingEnableRegister);
        String[] profiles = env.getActiveProfiles();
        System.out.println(" Active Profiles   : " + (profiles.length > 0 ? String.join(", ", profiles) : "default"));
        System.out.println("======================================");
    }

    // getters + setters (needed for @ConfigurationProperties)
    public String getAppVersion() {
        return appVersion;
    }
    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getBackendServerWebUrl() {
        return backendServerWebUrl;
    }
    public void setBackendServerWebUrl(String backendServerWebUrl) {
        this.backendServerWebUrl = backendServerWebUrl;
    }

    public boolean isSettingEnableSaveLog() {
        return settingEnableSaveLog;
    }
    public void setSettingEnableSaveLog(boolean settingEnableSaveLog) {
        this.settingEnableSaveLog = settingEnableSaveLog;
    }

    public boolean isSettingEnableRegister() {
        return settingEnableRegister;
    }
    public void setSettingEnableRegister(boolean settingEnableRegister) {
        this.settingEnableRegister = settingEnableRegister;
    }
}
