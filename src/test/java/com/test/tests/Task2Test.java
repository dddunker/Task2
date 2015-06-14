package com.test.tests;

import com.test.utils.ConfigProperties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class Task2Test extends TestCase {

    @Test
    public void createFolder() {
        mainPage.newFolder();

        Assert.assertTrue(mainPage.findNewFolder().isDisplayed(), "New folder was not created!");
    }

    @Test(dependsOnMethods = "createFolder")
    public void deleteFolder() {
        mainPage.deleteFolder();

        List<WebElement> foldersList = driver.findElements(By.xpath("//div[@id='folderView']//a"));

        for (WebElement folder : foldersList) {
            Assert.assertFalse(folder.getText().contains("No Title"), "'No Title' folder is present!");
        }
    }

    @Test
    public void createThingByTopButton() {
        // count things before creation
        int before = mainPage.countThings();
        // create a new thing
        mainPage.newThingByTop();
        // count things after creation
        int after = mainPage.countThings();

        Assert.assertTrue(mainPage.isNewThingCreated(before, after), "Thing was not created!");
    }

    @Test(dependsOnMethods = {"createThingByTopButton"})
    public void createThingByNextButton() {
        // count things before creation
        int before = mainPage.countThings();
        // create a new thing
        mainPage.newThingByNext();
        // count things after creation
        int after = mainPage.countThings();

        Assert.assertTrue(mainPage.isNewThingCreated(before, after), "Thing was not created!");
    }

    @Test(priority = 2)
    public void cantCreateFirstThingByNextButton() {
        mainPage.projectsFolder.click();

        Assert.assertFalse(mainPage.nextButton.isDisplayed(), "Next button is displayed!");
    }

    @Test(dependsOnMethods = {"createThingByTopButton"})
    public void createThingChild() {
        mainPage.newThingChild();

        Assert.assertTrue(mainPage.findThingChild().isDisplayed());
    }

    @Test(dependsOnMethods = {"createThingChild"})
    public void deleteThingChildByDeleteButton() {
        // create one more child
        mainPage.newThingChild();

        // count childs before delete
        int before = mainPage.countThingChilds();

        // delete child
        mainPage.delThingChildByDelete();

        // count childs after delete
        int after = mainPage.countThingChilds();

        Assert.assertTrue(mainPage.isChildDeleted(before, after));
    }

    @Test(dependsOnMethods = {"createThingChild"})
    public void deleteThingChildByEditButton() {
        // create one more child
        mainPage.newThingChild();

        // count childs before delete
        int before = mainPage.countThingChilds();

        // delete child
        mainPage.delThingChildByEdit();

        // count childs after delete
        int after = mainPage.countThingChilds();

        Assert.assertTrue(mainPage.isChildDeleted(before, after));
    }

    @Test(priority = 1, dependsOnMethods = "createThingChild")
    public void thingChildIsNotDeletedAfterCancelConfirmation(){
        // count childs before delete
        int before = mainPage.countThingChilds();

        mainPage.cancelThingChildDelete();

        // count childs after delete
        int after = mainPage.countThingChilds();

        Assert.assertFalse(mainPage.isChildDeleted(before, after));
    }

    @Test(priority = 2)
    public void canNotDeleteChildIfNotSelected() {
        mainPage.actionsFolder.click();
        Assert.assertFalse(mainPage.deleteThingButton.isDisplayed());
        Assert.assertFalse(mainPage.editThingButton.isDisplayed());
    }

    @Test
    public void selectedModeByDefault() {
        Assert.assertTrue(mainPage.selectedModeType.getText().contains(ConfigProperties.getProperty("mode.default")));
    }

    @Test(priority = 2, enabled = false)
    public void addThingToTop() {

    }

    @Test(priority = 2, enabled = false)
    public void addThingToNext() {

    }

}