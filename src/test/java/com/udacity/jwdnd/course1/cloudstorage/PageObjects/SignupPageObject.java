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
    @FindBy(css = ".alert.alert-danger")
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
    public void inputFirstname(String firstname) {
        this.inputFirstName.clear();
        this.inputFirstName.sendKeys(firstname);
    }
    public void inputLastName(String lastname) {
        this.inputLastName.clear();
        this.inputLastName.sendKeys(lastname);
    }
    public void inputUsername(String username) {
        this.inputUsername.clear();
        this.inputUsername.sendKeys(username);
    }
    public void inputPassword(String password) {
        this.inputPassword.clear();
        this.inputPassword.sendKeys(password);
    }
    public void clickRegister() {
        this.signupButton.click();
    }
    public String getErrorMsg() {
        try{
        return this.errormsg.getText();}
        catch (Exception e){
            return null;
        }
    }
    public String getSuccessMsg() {
        try {
            return this.successMsg.getText();
        }
        catch (Exception e){return null;}
    }
}
