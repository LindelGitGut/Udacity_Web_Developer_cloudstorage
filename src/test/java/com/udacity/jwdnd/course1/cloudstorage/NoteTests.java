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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NoteTests {


    @LocalServerPort
    Integer port;

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


    //Write a Selenium test that logs in an existing user, creates a note and verifies that the note details are visible in the note list.

    @Test
    @Order(1)
    public void createNoteTest() throws InterruptedException {
        createUser();
        loginUser();
        HomePageObject homePageObject = new HomePageObject(webDriver);
        homePageObject.clickNoteTab();
        WebDriverWait webDriverWait= new WebDriverWait(webDriver, Duration.ofMillis(2000));

        webDriverWait.until(ExpectedConditions.visibilityOf(homePageObject.createNewNoteButton));
        homePageObject.clickNewNoteButton();
        webDriverWait.until(ExpectedConditions.visibilityOf(homePageObject.noteTitleInput));

        homePageObject.inputNoteTitle("This is a Test Note");
        homePageObject.inputNoteDescription("Test Note for automatic Testing purposes");
        homePageObject.clickNoteSubmitButton();
        webDriverWait.until(ExpectedConditions.titleIs("Result"));
        ResultPageObject resultPageObject = new ResultPageObject(webDriver);
        assertTrue(resultPageObject.getSuccessMsg().contains("Success"));
        resultPageObject.clickRedirectLink();
        webDriverWait.until(ExpectedConditions.titleIs("Home"));
        homePageObject.clickNoteTab();
        webDriverWait.until(ExpectedConditions.visibilityOf(homePageObject.createNewNoteButton));

        //Check if Note is appears in List

        assertTrue(homePageObject.checkNoteTableContainsTitle("This is a Test Note"));
        assertTrue(homePageObject.checkNoteTableContainsDescription("Test Note for automatic Testing purposes"));

        //Check if Edit View Contains Right Values

        homePageObject.clickFirstNoteEditButton();
        webDriverWait.until(ExpectedConditions.visibilityOf(homePageObject.noteTitleInput));
        assertTrue(homePageObject.getNoteTitle().contains("This is a Test Note"));
        assertTrue(homePageObject.getNoteDescription().contains("Test Note for automatic Testing purposes"));


    }

    //Write a Selenium test that logs in an existing user with existing notes, clicks the edit note button on an
    // existing note, changes the note data, saves the changes, and verifies that the changes appear in the note list.

    @Test
    @Order(2)
    void loginUserandEditNote() throws InterruptedException {
        //Trigger Function to create User with TestFile
        loginUser();
        HomePageObject homePageObject = new HomePageObject(webDriver);
        homePageObject.clickNoteTab();
        WebDriverWait webDriverWait= new WebDriverWait(webDriver, Duration.ofMillis(2000));
        webDriverWait.until(ExpectedConditions.visibilityOf(homePageObject.createNewNoteButton));
        homePageObject.clickFirstNoteEditButton();
        webDriverWait.until(ExpectedConditions.visibilityOf(homePageObject.noteTitleInput));

        //Edit Note

        homePageObject.inputNoteTitle("Changed Note");
        homePageObject.inputNoteDescription("Hopefully that works this time!");
        homePageObject.clickNoteSubmitButton();
        //checking Success from Resultpage
        ResultPageObject resultPageObject = new ResultPageObject(webDriver);
        webDriverWait.until(ExpectedConditions.titleIs("Result"));
        assertEquals("Success", resultPageObject.getSuccessMsg());
        resultPageObject.clickRedirectLink();
        //switch to Note Tab
        webDriverWait.until(ExpectedConditions.titleIs("Home"));
        homePageObject.clickNoteTab();
        webDriverWait.until(ExpectedConditions.visibilityOf(homePageObject.createNewNoteButton));
        //check Notes overview for new Values
        assertTrue( homePageObject.checkNoteTableContainsTitle("Changed Note"));
        assertTrue(homePageObject.checkNoteTableContainsDescription("Hopefully that works this time!"));
        //check Values again in edeting view
        homePageObject.clickFirstNoteEditButton();
        webDriverWait.until(ExpectedConditions.visibilityOf(homePageObject.noteTitleInput));
        assertEquals("Changed Note",homePageObject.getNoteTitle());
        assertEquals("Hopefully that works this time!",homePageObject.getNoteDescription());
        homePageObject.clickNoteEditCancelButton();
    }

    @Test
    @Order(3)
    public void deleteNote() throws InterruptedException {
        loginUser();
        HomePageObject homePageObject = new HomePageObject(webDriver);
        //navigate to Notes Tab
        homePageObject.clickNoteTab();
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, Duration.ofMillis(2000));
        webDriverWait.until(ExpectedConditions.visibilityOf(homePageObject.createNewNoteButton));

        //Locate existing Note Element and delete
        Thread.sleep(4000);
        homePageObject.clickFirstNoteDeleteButton();
        webDriverWait.until(ExpectedConditions.titleIs("Result"));
        ResultPageObject resultPageObject = new ResultPageObject(webDriver);
        assertEquals("Success", resultPageObject.getSuccessMsg());
        resultPageObject.clickRedirectLink();

        homePageObject.clickNoteTab();

        //ceheck if old Note disappeared
        assertTrue(!homePageObject.checkNoteTableContainsTitle("Changed Note"));
        assertTrue(!homePageObject.checkNoteTableContainsDescription("Hopefully that works this time!"));

    }


    @AfterEach
    void logoutUser(){
        HomePageObject homePageObject = new HomePageObject(webDriver);
        webDriver.get("http://localhost:"+port+"/home");
        homePageObject.clickLogoutButton();


    }

    @AfterAll
    static void closeWebBrowser(){
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
