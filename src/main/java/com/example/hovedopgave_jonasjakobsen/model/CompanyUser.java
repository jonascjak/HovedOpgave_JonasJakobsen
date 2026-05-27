package com.example.hovedopgave_jonasjakobsen.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "company_users")
public class CompanyUser extends User {
    private String companyAddress;

    private String companyWebsiteURL;

    public CompanyUser() {

    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyWebsiteURL() {
        return companyWebsiteURL;
    }

    public void setCompanyWebsiteURL(String companyWebsiteURL) {
        this.companyWebsiteURL = companyWebsiteURL;
    }
}
