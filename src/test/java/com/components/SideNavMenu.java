package com.components;

import com.microsoft.playwright.Page;

public final class SideNavMenu extends BaseComponent{

    public SideNavMenu(Page page) {
        super(page);
    }

    public void clickOnLogout(){
        page.click("#logout_sidebar_link");
    }
}
