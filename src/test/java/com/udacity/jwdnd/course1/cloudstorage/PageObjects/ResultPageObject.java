package com.udacity.jwdnd.course1.cloudstorage.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPageObject {


    WebDriver webDriver;

    public ResultPageObject(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }


    @FindBy(tagName = "title")
    WebElement title;

    @FindBy(id = "success")
    WebElement successMsg;

    @FindBy(css = ".alert.alert-danger.fill-parent")
    WebElement errorMsg;

    @FindBy(linkText = "here")
    WebElement redirectHomeLink;

    public String getTitle(){
        return this.title.getAttribute("innerText");
    }

    public String getSuccessMsg(){
        return this.successMsg.getText();
    }

    public String getErrorMsg(){
        return this.errorMsg.getText();
    }

    public void clickRedirectLink(){
        this.redirectHomeLink.click();
    }

}
