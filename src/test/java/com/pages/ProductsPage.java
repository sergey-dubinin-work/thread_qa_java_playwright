package com.pages;

import com.components.Header;
import com.components.SideNavMenu;
import com.microsoft.playwright.Locator;
import com.utils.BasePageFactory;
import io.qameta.allure.Step;

public final class ProductsPage extends BasePage {

    private Header header;
    private SideNavMenu sideNavMenu;

    @Override
    public void initComponents() {
        header = new Header(page);
        sideNavMenu = new SideNavMenu(page);
    }

    @Step("Get title of the 'Products' page")
    public Locator getTitle() {
        return page.locator(".title");
    }

    @Step("Get all products names")
    public Locator getProductsNames() {
        return page.locator("div[data-test=inventory-item-name]");
    }

    @Step("Set <sortName> into 'Sorting filter'")
    public ProductsPage setSortFilter(String sortName) {
        page.locator("[data-test=\"product-sort-container\"]").selectOption(sortName);
        return this;
    }

    @Step("Add to cart item with name <itemName>")
    public ProductsPage addItemToCart(String itemName) {
        page.locator(String.format("//div[text()='%s']//following::button[1]", itemName)).click();
        return this;
    }

    @Step("Click on Cart button")
    public CartPage clickOnCart(){
        header.clickOnCart();
        return BasePageFactory.createInstance(page, CartPage.class);
    }

    @Step("Click on 'Logout' botton from side navigation menu")
    public LoginPage clockOnLogout(){
        header.clickOnHamburgerIcon();
        sideNavMenu.clickOnLogout();
        return BasePageFactory.createInstance(page, LoginPage.class);
    }

}
