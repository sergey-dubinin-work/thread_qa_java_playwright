package com.pages;

import com.components.Header;
import com.microsoft.playwright.Locator;
import com.models.ShipInfo;
import io.qameta.allure.Step;

public final class CartPage extends BasePage{

    private Header header;

    @Override
    public void initComponents() {
        header = new Header(page);
    }

    @Step("Get item names")
    public Locator getItems(){
        return page.locator(".cart_list .inventory_item_name");
    }

    @Step("Click on checkout button")
    public CartPage clickOnCheckout(){
        page.click("button[data-test=\"checkout\"]");
        return this;
    }

    @Step("Fill ship information <shipInfo>")
    public CartPage fillInfo(ShipInfo shipInfo){
        page.fill("[data-test=\"firstName\"]", shipInfo.getFirstName());
        page.fill("[data-test=\"lastName\"]", shipInfo.getLastName());
        page.fill("[data-test=\"postalCode\"]", shipInfo.getZip());
        return this;
    }

    @Step("Click continue button")
    public CartPage clickOnContinue(){
        page.click("[data-test=\"continue\"]");
        return this;
    }

    @Step("Click finish button")
    public CartPage clickOnFinish(){
        page.click("[data-test=\"continue\"]");
        return this;
    }

    @Step("Get complete header")
    public Locator getCompleteHeader(){
        return page.locator("[data-test=\"complete-header\"]");
    }

}
