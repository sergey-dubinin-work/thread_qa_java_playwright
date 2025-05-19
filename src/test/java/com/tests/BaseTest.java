package com.tests;

import com.google.common.collect.ImmutableMap;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.pages.BasePage;
import com.pages.LoginPage;
import com.utils.BasePageFactory;
import com.utils.BrowserManager;
import io.qameta.allure.Attachment;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import static com.config.ConfigurationManager.config;
import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext browserContext;
    protected Page page;
    protected LoginPage loginPage;
    protected boolean needVideo;

    @RegisterExtension
    AfterTestExecutionCallback callback =
            context -> {
                Optional<Throwable> exception = context.getExecutionException();
                if (exception.isPresent()){
                    needVideo = true;
                    captureScreenshotOnFailure();
                }
            };

    @BeforeAll
    public void initBrowser(){
        playwright = Playwright.create();
        browser = BrowserManager.getBrowser(playwright);

        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("Platform", System.getProperty("os.name"))
                        .put("Version", System.getProperty("os.version"))
                        .put("Browser", config().browser().toUpperCase())
                        .put("Context URL", config().baseUrl())
                        .build(),
                config().allureResultsDir() + "/"
        );

    }

    @BeforeEach
    public void createContext(){
        if (config().video()){
            browserContext = browser.newContext(
                    new Browser.NewContextOptions()
                            .setRecordVideoDir(Paths.get(config().baseTestVideoPath()))
            );
        } else {
            browserContext = browser.newContext();
        }
        page = browserContext.newPage();
        loginPage = createInstance(LoginPage.class);

    }


    @AfterEach
    public void attach(){
        browserContext.close();
        if (config().video() && needVideo){
            captureVideo();
        }
        needVideo = false;
    }

    @Attachment(value = "Test video", type = "video/webm")
    @SneakyThrows
    private byte[] captureVideo(){
        return Files.readAllBytes(page.video().path());
    }

    @Attachment(value = "Screenshot on fail", type = "image/png")
    private byte[] captureScreenshotOnFailure() {
        return page.screenshot();
    }

    protected <T extends BasePage> T createInstance(final Class<T> clazz){
        return BasePageFactory.createInstance(page, clazz);
    }

}
