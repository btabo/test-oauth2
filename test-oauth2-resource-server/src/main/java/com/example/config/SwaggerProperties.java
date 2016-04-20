package com.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by btaboule010714 on 19/04/2016.
 */
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {
    private String includePatterns;
    private String title;
    private String description;
    private String termOfServiceUrl;
    private String contact;
    private String license;
    private String licenseUrl;
    private String version;

    public String getIncludePatterns() {
        return includePatterns;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTermOfServiceUrl() {
        return termOfServiceUrl;
    }

    public String getContact() {
        return contact;
    }

    public String getLicense() {
        return license;
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }

    public void setIncludePatterns(String includePatterns) {
        this.includePatterns = includePatterns;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTermOfServiceUrl(String termOfServiceUrl) {
        this.termOfServiceUrl = termOfServiceUrl;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
