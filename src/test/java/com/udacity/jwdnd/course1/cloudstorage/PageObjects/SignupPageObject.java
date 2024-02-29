package com.udacity.jwdnd.course1.cloudstorage.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPageObject {

    WebDriver webDriver;

    public SignupPageObject(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }


    @FindBy(id = "success-msg")
    WebElement successMsg;

    @FindBy(className = "alert alert-danger")
    WebElement errormsg;

    @FindBy(id = "inputFirstName")
    WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    WebElement inputLastName;

    @FindBy(id = "inputUsername")
    WebElement inputUsername;

    @FindBy(id = "inputPassword")
    WebElement inputPassword;

    @FindBy(id = "buttonSignUp")
    WebElement signupButton;

}
