package com.udacity.jwdnd.course1.cloudstorage;

import java.time.Duration;

import com.udacity.jwdnd.course1.cloudstorage.PageObjects.HomePageObject;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignupLoginUnauthorizedTests {
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


    //Write a Selenium test that signs up a new user,
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
    void userRegistrationFailedTest(){
        LoginPageObject loginPage = new LoginPageObject(this.webDriver);
        loginPage.clickRegistrationLink();
        SignupPageObject signupPage = new SignupPageObject(this.webDriver);
        signupPage.inputFirstname("Alex");
        signupPage.inputLastName("Lindele");
        signupPage.inputUsername("Alex");
        signupPage.inputPassword("password");
        signupPage.clickRegister();
        assertEquals(null, signupPage.getSuccessMsg());
        assertNotNull(signupPage.getErrorMsg());
    }


    //logs that user in, verifies that they can access the home page
    @Test
    void userLoginTest() throws InterruptedException {
        LoginPageObject loginPage = new LoginPageObject(this.webDriver);
        loginPage.inputUserName("Alex");
        loginPage.inputPassword("password");
        loginPage.clickLoginButton();
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, Duration.ofMillis(2000));
        webDriverWait.until(ExpectedConditions.titleIs("Home"));
        HomePageObject homePageObject = new HomePageObject(webDriver);
        Thread.sleep(1000);
        assertEquals("Home", homePageObject.getHomeTitle());
    }

    //, then logs out

    @Test
    void userLoginAndLogoutTest(){
        LoginPageObject loginPageObject = new LoginPageObject(webDriver);
        loginPageObject.inputUserName("Alex");
        loginPageObject.inputPassword("password");
        loginPageObject.clickLoginButton();
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, Duration.ofMillis(2000));
        webDriverWait.until(ExpectedConditions.titleIs("Home"));
        HomePageObject homePageObject = new HomePageObject(webDriver);

        homePageObject.clickLogoutButton();

        webDriverWait.until(ExpectedConditions.titleIs("Login"));

        assertNotNull(loginPageObject.getLogOutMsg());

    }

    //and verifies that the home page is no longer accessible.
    //Write a Selenium test that verifies that the home page is not accessible without logging in.
    @Test
    void homePageWithoutAuthenticationTest(){
        LoginPageObject loginPageObject = new LoginPageObject(webDriver);
        webDriver.get("http://localhost:8080/home");
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, Duration.ofMillis(2000));
        webDriverWait.until(ExpectedConditions.titleIs("Login"));
       assertEquals("Login", loginPageObject.getTitle());

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