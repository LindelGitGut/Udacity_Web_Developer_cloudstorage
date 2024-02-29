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


    @FindBy(css = ".alert.alert-danger")
    WebElement invalidCredentialsMessage;

    @FindBy(css = ".alert.alert-dark")
    WebElement logoutMessage;

    @FindBy(id = "inputUsername")
    WebElement usernameInput;

    @FindBy(id = "inputPassword")
    WebElement passwordInput;

    @FindBy(id = "login-button")
    WebElement loginButton;

    @FindBy(id = "registrationlink")
    WebElement registrationLink;

    public void clickRegistrationLink() {
        registrationLink.click();
    }

    public void inputUserName(String username) {
        usernameInput.clear();
        usernameInput.sendKeys(username);
    }

    public void inputPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void clickLoginButton() {
        loginButton.click();
    }

    public String getErrorMsg() {
        try{
        return this.invalidCredentialsMessage.getText();}
        catch (Exception e){
            System.out.println("Debug LoginPageObject getErrorMSG: " +e.getMessage());return null;}
    }

    public String getLogOutMsg() {
        try{return this.logoutMessage.getText();}
        catch (Exception e){return null;}

    }

}