package com.pages;

import com.microsoft.playwright.Page;

import static com.config.ConfigurationManager.config;

public abstract class BasePage {

    protected Page page;

    public void setAndConfigurePage(final Page page){
        this.page = page;
        page.setDefaultTimeout(config().timeout());
    }

    public void initComponents(){}

}
