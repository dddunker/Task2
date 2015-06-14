package com.test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MainPage extends Page {

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//nav[@id='folderBottomNav']/button[@class='addButton btn btn-default navbar-btn']")
    public WebElement addFolderButton;

    @FindBy(xpath = "//nav[@id='folderBottomNav']//button[@class='editButton btn btn-default navbar-btn']")
    public WebElement editFolderButton;

    @FindBy(xpath = "//div[@id='folderView']//ul//a[contains(@href, 'ACTIONS')]")
    public WebElement actionsFolder;

    @FindBy(xpath = "//div[@id='folderView']//ul//a[contains(@href, 'PROJECTS')]")
    public WebElement projectsFolder;

    @FindBy(xpath = "//div[@id='folderView']//ul//a[contains(@href, 'INBOX')]")
    public WebElement inboxFolder;

    @FindBy(id = "addTopLevelButton")
    public WebElement topButton;

    @FindBy(id = "addButtonInMainBottom")
    public WebElement nextButton;

    @FindBy(xpath = "//nav[@id='mainBottomNav']//button[@class='addChildButton btn btn-default navbar-btn']")
    public WebElement addThingChildButton;

    @FindBy(xpath = "//nav[@id='mainBottomNav']/button[@class='editButton btn btn-default navbar-btn']")
    public WebElement editThingButton;

    @FindBy(xpath = "//span[@class='pull-right']/button[@class='deleteButton btn btn-default navbar-btn']")
    public WebElement deleteThingButton;

    @FindBy(xpath = "//div[@id='folderView']/div/div")
    public WebElement selectedModeType;

    public void newFolder() {
        addFolderButton.click();
    }

    public void deleteFolder() {
        // deselect No Title folder
        inboxFolder.click();
        // select No Title folder
        findNewFolder().click();
        
        editFolderButton.click();
        WebElement deleteButton = driver.findElement(By.xpath("//div[@class='modal-dialog']" +
                "//button[@id='deleteButton']"));
        deleteButton.click();

        WebElement confirmDeleteButton = driver.findElement(By.xpath("//div[@class='modal-content']" +
                "/div[@class='modal-footer']/div/div/button[@class='btn btn-danger']"));
        confirmDeleteButton.click();
    }

    public WebElement findNewFolder() {
        return driver.findElement(By.xpath("//div[@id='folderView']/ul/li/div/div" +
                "/a[text()[contains(.,'No Title')]]"));
    }

    public void newThingByTop() {
        actionsFolder.click();
        topButton.click();
    }

    public int countThings() {
        actionsFolder.click();
        List<WebElement> things = driver.findElements(By.xpath("//div[@id='thingView']" +
                "/ul[@class='containerForSortable']/li"));
        return things.size();
    }

    public boolean isNewThingCreated(int before, int after) {
        return (before == after - 1);
    }

    public void newThingByNext() {
        actionsFolder.click();
        findFirstThing().click();
        nextButton.click();
    }

    public void newThingChild() {
        actionsFolder.click();

        // select first thing
        findFirstThing().click();

        addThingChildButton.click();
    }

    private WebElement findFirstThing() {
        return driver.findElement(By.xpath("//div[@id='thingView']/ul[@class='containerForSortable']/li[1]/div/div"));
    }

    public WebElement findThingChild() {
        return driver.findElement(By.xpath("//div[@id='thingView']/ul[@class='containerForSortable']/li[1]/ul/li[1]" +
                "/div/div"));
    }

    public void delThingChildByDelete() {
        actionsFolder.click();
        // select child
        findThingChild().click();

        // delete a child
        deleteThingButton.click();
        WebElement confirmDeleteButton = driver.findElement(By.xpath("//div[@class='modal-content']" +
                "//button[@class='btn btn-danger']"));
        confirmDeleteButton.click();
    }

    public int countThingChilds() {
        actionsFolder.click();
        // select first thing with child
        findFirstThing().click();
        List<WebElement> thingChilds = driver.findElements(By.xpath("//div[@id='thingView']/ul[@class=" +
                "'containerForSortable']/li[1]/ul/li"));

    return thingChilds.size();
    }

    public boolean isChildDeleted(int before, int after) {
        return (before == after + 1);
    }

    public void delThingChildByEdit () {
        actionsFolder.click();
        // select child
        findThingChild().click();

        // delete child
        editThingButton.click();
        WebElement deleteButton = driver.findElement(By.xpath("//div[@class='modal-content']" +
                "//button[@id='deleteButton']"));
        deleteButton.click();

        WebElement confirmDeleteButton = driver.findElement(By.xpath("//div[@class='modal-content']" +
                "//div[@class='bootstrap-dialog-footer-buttons']//button[@class='btn btn-danger']"));
        confirmDeleteButton.click();
    }

    public void deleteAllThingsFromActions() {
        actionsFolder.click();

        List<WebElement> things = driver.findElements(By.xpath("//div[@id='thingView']" +
                "/ul[@class='containerForSortable']/li"));

        for (int i = 0; i < things.size(); i++) {
            findFirstThing().click();
            deleteThingButton.click();
            WebElement confirmDeleteButton = driver.findElement(By.xpath("//div[@class='modal-content']" +
                    "//button[@class='btn btn-danger']"));
            confirmDeleteButton.click();
        }
    }

    public void cancelThingChildDelete() {
        actionsFolder.click();
        findThingChild().click();

        deleteThingButton.click();

        WebElement cancelDeleteButton = driver.findElement(By.xpath("//div[@class='modal-content']" +
                "//button[@class='btn btn-default']"));
        cancelDeleteButton.click();
    }

    @Override
    public void open() {

    }

    public void waitUntilSync() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                "//span[@id='syncMessage' and contains(text(), 'Synchronized')]")));
    }
}
