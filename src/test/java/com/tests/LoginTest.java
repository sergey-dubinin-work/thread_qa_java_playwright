package com.tests;

import com.pages.ProductsPage;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public final class LoginTest extends BaseTest{

    @Test
    public void testCorrectLoginCredentials(){
        ProductsPage productsPage = loginPage.loginAs("standard_user", "secret_sauce");
        assertThat(productsPage.getTitle()).hasText("Products");
    }

    @Test
    public void testLockedOutUser(){
        loginPage.loginAs("wrong_username", "fake_password");
        assertThat(loginPage.getErrorMessage()).hasText("Epic sadface: Username and password do not match any user in this service");
    }
}
