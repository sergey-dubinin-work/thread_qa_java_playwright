package com.components;

import com.microsoft.playwright.Page;

public final class Header extends BaseComponent{

    public Header(Page page) {
        super(page);
    }

    public void clickOnHamburgerIcon(){
        page.click("#react-burger-menu-btn");
    }

    public void clickOnCart(){
        page.click("[data-test=\"shopping-cart-link\"]");
    }
}
