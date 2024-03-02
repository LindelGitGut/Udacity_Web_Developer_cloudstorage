package com.udacity.jwdnd.course1.cloudstorage.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePageObject {

    WebDriver webDriver;

    public HomePageObject(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(tagName = "title")
    WebElement title;

    @FindBy(css = ".btn.btn-secondary.float-right")
    WebElement logoutButton;


    public void clickLogoutButton() {
        logoutButton.click();
    }

    public String getHomeTitle() {
        try {
            return this.title.getText();
        } catch (Exception e) {
            System.out.println("Debug get Title Exception: " +e.getMessage() );
            return null;
        }
    }


    //TODO Complete Selenium Page


}
