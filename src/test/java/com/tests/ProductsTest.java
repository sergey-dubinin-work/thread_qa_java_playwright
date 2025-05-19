package com.tests;

import com.models.ShipInfo;
import com.pages.CartPage;
import com.pages.ProductsPage;
import org.junit.jupiter.api.Test;

import static com.config.ConfigurationManager.config;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ProductsTest extends BaseTest{

    @Test
    public void testSuccessfulLogout(){
        loginPage.loginAs("standard_user", "secret_sauce")
                .clockOnLogout();

        assertThat(page).hasURL(config().baseUrl());
    }

    @Test
    public void testSortItems(){
        ProductsPage productsPage = loginPage.loginAs("standard_user", "secret_sauce");

        assertThat(productsPage.getProductsNames().first()).hasText("Sauce Labs Backpack");

        productsPage.setSortFilter("Name (Z to A)");

        assertThat(productsPage.getProductsNames().first()).hasText("Test.allTheThings() T-Shirt (Red)");
    }

    @Test
    public void testAddItemToCartAndBuy(){
        ProductsPage productsPage = loginPage.loginAs("standard_user", "secret_sauce");

        String firstItemName = productsPage.getProductsNames().first().textContent();

        productsPage.addItemToCart(firstItemName);

        CartPage cartPage = productsPage.clickOnCart();

        assertThat(cartPage.getItems()).hasText(firstItemName);

        ShipInfo shipInfo = ShipInfo.builder()
                .firstName("Sergey")
                .lastName("Dubinin")
                .zip("123456")
                .build();

        cartPage
                .clickOnCheckout()
                .fillInfo(shipInfo)
                .clickOnContinue()
                .clickOnFinish();

        assertThat(cartPage.getCompleteHeader()).hasText("Thank you for your order!");
    }

}
