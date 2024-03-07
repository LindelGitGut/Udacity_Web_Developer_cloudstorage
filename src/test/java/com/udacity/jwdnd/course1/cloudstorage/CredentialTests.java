package com.udacity.jwdnd.course1.cloudstorage;


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

import java.time.Duration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CredentialTests {

    @LocalServerPort
    int port;

    static WebDriver webDriver;

    @BeforeAll
    static void beforeAll(){
        /// Init Webdriver for Chrome
        WebDriverManager.chromedriver().clearResolutionCache();
        WebDriverManager.chromedriver().clearDriverCache();
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
    }


    //Write a Selenium test that logs in an existing user, creates a credential and verifies that the credential details
    // are visible in the credential list.
    @Test
    @Order(1)
    void createCredential() throws InterruptedException {
        createUser();
        loginUser();


    }



    @AfterEach
    public void afterEach(){
        webDriver.get("http://localhost:"+port+"/login");
    }

    @AfterAll
    static void afterAll(){
        webDriver.close();
    }

    public void createUser(){
        //create User before Tests
        LoginPageObject loginPage = new LoginPageObject(webDriver);
        loginPage.clickRegistrationLink();
        SignupPageObject signupPage = new SignupPageObject(webDriver);
        signupPage.inputFirstname("Alex");
        signupPage.inputLastName("Lindele");
        signupPage.inputUsername("Alex");
        signupPage.inputPassword("password");
        signupPage.clickRegister();
    }
    public void loginUser() throws InterruptedException {
        webDriver.get("http://localhost:"+port+"/login");

        LoginPageObject loginPage = new LoginPageObject(webDriver);
        loginPage.inputUserName("Alex");
        loginPage.inputPassword("password");
        loginPage.clickLoginButton();
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, Duration.ofMillis(2000));
        webDriverWait.until(ExpectedConditions.titleIs("Home"));
    }
}
