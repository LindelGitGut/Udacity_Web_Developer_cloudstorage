package com.udacity.jwdnd.course1.cloudstorage;


import com.udacity.jwdnd.course1.cloudstorage.PageObjects.HomePageObject;
import com.udacity.jwdnd.course1.cloudstorage.PageObjects.LoginPageObject;
import com.udacity.jwdnd.course1.cloudstorage.PageObjects.ResultPageObject;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @BeforeEach
    public void beforeEach(){
        webDriver.get("http://localhost:"+port+"/login");
    }


    //Write a Selenium test that logs in an existing user, creates a credential and verifies that the credential details
    // are visible in the credential list.
    @Test
    @Order(1)
    void createCredential() throws InterruptedException {
        createUser();
        loginUser();
        HomePageObject homePageObject = new HomePageObject(webDriver);
        homePageObject.clickCredentialTab();
        WebDriverWait webDriverWait = new WebDriverWait(webDriver,Duration.ofMillis(2000));
        webDriverWait.until(ExpectedConditions.visibilityOf(homePageObject.createNewCredentialButton));
        homePageObject.clickNewCredentialButton();
        webDriverWait.until(ExpectedConditions.visibilityOf(homePageObject.credentialUsernameInput));
        homePageObject.inputCredentialUrl("http://sometestsite.com");
        homePageObject.inputCredentialUsername("Testuser");
        homePageObject.inputCredentialPassword("password");

        homePageObject.clickCredentialSubmitButton();
        webDriverWait.until(ExpectedConditions.titleIs("Result"));
        ResultPageObject resultPageObject = new ResultPageObject(webDriver);
        assertEquals("Success", resultPageObject.getSuccessMsg());
        resultPageObject.clickRedirectLink();

        homePageObject.clickCredentialTab();

        assertTrue(homePageObject.checkCredentialTableContainsUrl("http://sometestsite.com"));
        Thread.sleep(2000);
        assertTrue(homePageObject.checkCredentialTableContainsUsername("Testuser"));
        assertTrue(homePageObject.checkCredentialTableContainsNotClearPassword("password"));

        homePageObject.clickEditFirstCredentialButton();

        webDriverWait.until(ExpectedConditions.visibilityOf(homePageObject.credentialUsernameInput));


        Thread.sleep(10000);
        assertEquals("http://sometestsite.com", homePageObject.getCredentialUrl());
        assertEquals("Testuser", homePageObject.getCredentialUsername());
        assertEquals("password", homePageObject.getCredentialPassword());

        homePageObject.clickCredentialCancelButton();

    }

    //Write a Selenium test that logs in an existing user with existing credentials, clicks the edit credential button
    // on an existing credential, changes the credential data, saves the changes, and verifies that the changes appear
    // in the credential list.

    @Test
    @Order(2)
    public void changeCredntial() throws InterruptedException {
        loginUser();
        HomePageObject homePageObject = new HomePageObject(webDriver);
        homePageObject.clickCredentialTab();
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, Duration.ofMillis(2000));
        webDriverWait.until(ExpectedConditions.visibilityOf(homePageObject.createNewCredentialButton));

        homePageObject.clickEditFirstCredentialButton();
        webDriverWait.until(ExpectedConditions.visibilityOf(homePageObject.credentialPasswordInput));

        homePageObject.inputCredentialUrl("http://changedTestSite.com");
        homePageObject.inputCredentialUsername("SuperDuperTestUser");
        homePageObject.inputCredentialPassword("newpassword");

        homePageObject.clickCredentialSubmitButton();
        ResultPageObject resultPageObject = new ResultPageObject(webDriver);
        webDriverWait.until(ExpectedConditions.titleIs("Result"));
        resultPageObject.clickRedirectLink();

        webDriverWait.until(ExpectedConditions.titleIs("Home"));
        homePageObject.clickCredentialTab();
        webDriverWait.until(ExpectedConditions.visibilityOf(homePageObject.createNewCredentialButton));

        //check if changed Entry is there
        assertTrue(homePageObject.checkCredentialTableContainsUrl("http://changedTestSite.com"));
        assertTrue(homePageObject.checkCredentialTableContainsUsername("SuperDuperTestUser"));
        assertTrue(homePageObject.checkCredentialTableContainsNotClearPassword("newpassword"));

        homePageObject.clickEditFirstCredentialButton();

        webDriverWait.until(ExpectedConditions.visibilityOf(homePageObject.credentialUsernameInput));

        assertEquals("http://changedTestSite.com", homePageObject.getCredentialUrl());
        assertEquals("SuperDuperTestUser", homePageObject.getCredentialUsername());
        assertEquals("newpassword", homePageObject.getCredentialPassword());

        homePageObject.clickCredentialCancelButton();
    }



//Write a Selenium test that logs in an existing user with existing credentials, clicks the delete credential button
// on an existing credential, and verifies that the credential no longer appears in the credential list.

    @Test
    @Order(3)
    public void deleteCredential() throws InterruptedException {
        loginUser();
        HomePageObject homePageObject = new HomePageObject(webDriver);
        homePageObject.clickCredentialTab();
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, Duration.ofMillis(2000));
        webDriverWait.until(ExpectedConditions.visibilityOf(homePageObject.createNewCredentialButton));

        homePageObject.clickDeleteFirstCredentialButton();

        ResultPageObject resultPageObject = new ResultPageObject(webDriver);
        webDriverWait.until(ExpectedConditions.titleIs("Result"));
        resultPageObject.clickRedirectLink();

        webDriverWait.until(ExpectedConditions.titleIs("Home"));
        homePageObject.clickCredentialTab();
        webDriverWait.until(ExpectedConditions.visibilityOf(homePageObject.createNewCredentialButton));

        //check if Credential has disappeared

        assertTrue(!homePageObject.checkCredentialTableContainsUrl("http://changedTestSite.com"));
        assertTrue(!homePageObject.checkCredentialTableContainsUsername("SuperDuperTestUser"));



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
