package com.udacity.jwdnd.course1.cloudstorage.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePageObject {

    WebDriver webDriver;

    public HomePageObject(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(tagName = "title")
    public WebElement title;

    @FindBy(css = ".btn.btn-secondary.float-right")
    public WebElement logoutButton;

    //Note View

    @FindBy(id = "nav-notes-tab")
    public WebElement noteTab;

    @FindBy(xpath = "//button[contains(text(), 'Add a New Note')]")
    public WebElement createNewNoteButton;

    @FindBy(xpath = "//button[contains(text(), 'Edit')]")
    public WebElement editFirstNoteButton;

    @FindBy(xpath = "//a[contains(text(), 'Delete')]")
    public WebElement deleteFirstNoteButton;

    @FindBy(id = "note-title")
    public WebElement noteTitleInput;

    @FindBy(id = "note-description")
    public WebElement noteDescriptionInput;

    @FindBy(xpath = "//button[contains(text(), 'Save changes')]")
    public WebElement noteSubmitButton;

    @FindBy(xpath = "//button[contains(text(), 'Close')]")
    public WebElement noteCancelEditButton;

    @FindBy(id = "noteTable")
    public WebElement noteTable;


    //Credential

    @FindBy(id = "nav-credentials-tab")
    public WebElement credentialTab;

    @FindBy(xpath = "//button[contains(text(), 'Add a New Credential')]")
    public WebElement createNewCredentialButton;

    @FindBy(xpath = "//button[contains(text(), 'Edit')]")
    public WebElement editFirstCredentialButton;

    @FindBy(xpath = "//a[contains(text(), 'Delete')]")
    public WebElement deleteFirstCredentialButton;

    @FindBy(id = "credential-url")
    public WebElement credentialUrlInput;

    @FindBy(id = "credential-username")
    public WebElement credentialUsernameInput;

    @FindBy(id = "credential-password")
    public WebElement credentialPasswordInput;

    @FindBy(xpath = "//button[contains(text(), 'Save changes')]")
    public WebElement credentialSubmitButton;

    @FindBy(xpath = "//button[contains(text(), 'Close')]")
    public WebElement credentialCancelEditButton;

    @FindBy(id = "credentialTable")
    public WebElement credentialTable;






    public void clickLogoutButton() {
        logoutButton.click();
    }
    public String getHomeTitle() {
        try {
            return this.title.getAttribute("innerText");
        } catch (Exception e) {
            System.out.println("Debug get Title Exception: " +e.getMessage() );
            return null;
        }
    }

    //Notes
    public void clickNoteTab(){
        this.noteTab.click();
    }

    public void clickNewNoteButton(){
        this.createNewNoteButton.click();
    }

    public void clickFirstNoteEditButton(){
        this.editFirstNoteButton.click();
    }

    public void clickFirstNoteDeleteButton(){
        this.deleteFirstNoteButton.click();
    }

    public void inputNoteTitle(String title){
        noteTitleInput.clear();
        noteTitleInput.sendKeys(title);
    }

    public void inputNoteDescription(String description){
        noteDescriptionInput.clear();
        noteDescriptionInput.sendKeys(description);
    }

    public String getNoteTitle(){
        return this.noteTitleInput.getAttribute("value");
    }

    public String getNoteDescription(){
        return this.noteDescriptionInput.getAttribute("value");
    }

    public void clickNoteSubmitButton(){
        noteSubmitButton.click();
    }

    public void clickNoteEditCancelButton(){
        noteCancelEditButton.click();
    }

    public boolean checkNoteTableContainsTitle(String noteTitle){
        List<WebElement> matchingTitles = this.noteTable.findElements(By.xpath(".//th[contains(text(),'" + noteTitle + "')]"));

        // Prüfung, ob die Liste der Elemente, die den Kriterien entsprechen, leer ist oder nicht
        return !matchingTitles.isEmpty();
    }
    public boolean checkNoteTableContainsDescription(String noteDescription){
        List<WebElement> matchingTitles = this.noteTable.findElements(By.xpath(".//td[contains(text(),'" + noteDescription + "')]"));

        // Prüfung, ob die Liste der Elemente, die den Kriterien entsprechen, leer ist oder nicht
        return !matchingTitles.isEmpty();
    }

    //Credntials

    public void clickCredentialTab(){
        this.credentialTab.click();
    }

    public void clickNewCredentialButton(){
        this.createNewCredentialButton.click();
    }

    public void clickEditFirstCredentialButton(){
        this.editFirstCredentialButton.click();
    }

    public void clickDeleteFirstCredentialButton(){
        this.deleteFirstCredentialButton.click();
    }

    public void inputCredentialUrl(String url){
        this.credentialUrlInput.clear();
        this.credentialUrlInput.sendKeys(url);
    }

    public void inputCredentialUsername(String username){
        this.credentialUsernameInput.clear();
        this.credentialUsernameInput.sendKeys(username);
    }

    public void inputCredentialPassword(String passwort){
        this.credentialPasswordInput.clear();
        this.credentialPasswordInput.sendKeys(passwort);
    }

    public String getCredentialUrl(){
        return this.credentialUrlInput.getAttribute("value");
    }

    public String getCredntialUsername(){
        return this.credentialUsernameInput.getAttribute("value");
    }

    public String getCredentialPassword(){
        return this.credentialPasswordInput.getAttribute("value");
    }

    public void clickCredentialSubmitButton(){
        this.credentialSubmitButton.click();
    }

    public void clockCredentialCancelButton(){
        this.credentialCancelEditButton.click();
    }

    public boolean checkCredentialTableContainsUrl(String Url){
        List<WebElement> matchingTitles = this.credentialTable.findElements(By.xpath(".//th[contains(text(),'" + Url + "')]"));

        // Prüfung, ob die Liste der Elemente, die den Kriterien entsprechen, leer ist oder nicht
        return !matchingTitles.isEmpty();
    }
    public boolean checkCredentialTableContainsUsername(String username){
        List<WebElement> matchingTitles = this.noteTable.findElements(By.xpath(".//td[contains(text(),'" + username + "')]"));
        // Prüfung, ob die Liste der Elemente, die den Kriterien entsprechen, leer ist oder nicht
        return !matchingTitles.isEmpty();
    }

    public boolean checkCredentialTableContainsNotClearPassword(String password){
        List<WebElement> matchingTitles = this.noteTable.findElements(By.xpath(".//td[contains(text(),'" + password + "')]"));
        // Prüfung, ob die Liste der Elemente, die den Kriterien entsprechen, leer ist oder nicht
        return matchingTitles.isEmpty();
    }










}
