package com.udacity.jwdnd.course1.cloudstorage.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPageObject {

    WebDriver webDriver;

    public LoginPageObject(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }


    @FindBy(className = "alert alert-danger")
    WebElement invalidCredentialsMessage;

    @FindBy(className = "alert alert-dark")
    WebElement logoutMessage;

    @FindBy(id = "inputUsername")
    WebElement usernameInput;

    @FindBy(id = "inputPassword")
    WebElement passwordInput;

    @FindBy(id = "login-button")
    WebElement loginButton;

    @FindBy(id = "registrationlink")
    WebElement registrationLink;




}