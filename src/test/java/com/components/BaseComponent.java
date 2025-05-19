package com.components;

import com.microsoft.playwright.Page;

public class BaseComponent {

    protected Page page;

    protected BaseComponent(final Page page){
        this.page = page;
    }

}
