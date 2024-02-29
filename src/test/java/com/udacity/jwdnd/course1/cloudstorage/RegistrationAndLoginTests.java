package com.udacity.jwdnd.course1.cloudstorage;

import java.time.Duration;
import com.udacity.jwdnd.course1.cloudstorage.PageObjects.LoginPageObject;
import com.udacity.jwdnd.course1.cloudstorage.PageObjects.SignupPageObject;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegistrationAndLoginTests {
    @LocalServerPort
    Integer port;


    static WebDriver webDriver;

    @BeforeAll
    static void beforeAll(){
        WebDriverManager.chromedriver().clearResolutionCache();
        WebDriverManager.chromedriver().clearDriverCache();
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
    }

    @AfterAll
    static void closeBrowserPage(){
        webDriver.close();
    }

    @BeforeEach
    void openStartPage(){
        webDriver.get("http://localhost:"+port+"/login");
    }

    @Test
    void userRegistrationTest(){
        LoginPageObject loginPage = new LoginPageObject(this.webDriver);
        loginPage.clickRegistrationLink();
        SignupPageObject signupPage = new SignupPageObject(this.webDriver);
        signupPage.inputFirstname("Alex");
        signupPage.inputLastName("Lindele");
        signupPage.inputUsername("Alex");
        signupPage.inputPassword("password");
        signupPage.clickRegister();
        assertNotNull(signupPage.getSuccessMsg());
        assertEquals(null,signupPage.getErrorMsg());
    }

    @Test
    void userLoginTest(){
        LoginPageObject loginPage = new LoginPageObject(this.webDriver);
        loginPage.inputUserName("Alex");
        loginPage.inputPassword("password");
        loginPage.clickLoginButton();
        //TODO Complete test with checking HomepageElements
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, Duration.ofMillis(2000));
        webDriverWait.until(ExpectedConditions.titleIs("Home"));
    }

    @Test
    void wrongUserTest() throws InterruptedException {
        LoginPageObject loginPage = new LoginPageObject(this.webDriver);
        loginPage.inputUserName("WrongUserName");
        loginPage.inputPassword("meh");
        loginPage.clickLoginButton();
        Thread.sleep(100);
        assertNotNull(loginPage.getErrorMsg());
    }

}
