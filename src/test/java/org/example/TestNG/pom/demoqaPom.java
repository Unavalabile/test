package org.example.TestNG.pom;

import Utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class demoqaPom {
    private static final By RESULT_TITLE = By.id("example-modal-sizes-title-lg");
    private static final By BIRTHDAY = By.id("dateOfBirthInput");

    private final WebDriver driver;
    private final JavascriptExecutor js;

    @FindBy(css = "#firstName")
    WebElement firstName;

    @FindBy(css = "#lastName")
    WebElement lastName;

    @FindBy(css = "#userEmail")
    WebElement userEmail;

    @FindBy(css = "#userNumber")
    WebElement userNumber;

    @FindBy(xpath = "//*[@id='subjectsInput']")
    WebElement subjects;

    @FindBy(xpath = "//*[@id='state']")
    WebElement state;

    @FindBy(xpath = "//*[@id='city']")
    WebElement city;

    @FindBy(xpath = "//*[@id='submit']")
    WebElement submit;

    public demoqaPom(WebDriver driver) {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    public void closeAdvert() {
        removeByXpath("//*[@id='Ad.Plus-970x250-2']");
        removeByXpath("//footer");
        removeByXpath("//*[@id='fixedban']");
    }

    private void removeByXpath(String xpath) {
        try {
            js.executeScript("var e = document.evaluate(arguments[0], document, null, "
                    + "XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;"
                    + "if (e) e.parentNode.removeChild(e);", xpath);
        } catch (Exception ignored) {
        }
    }

    public void scrollToElement(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    // Click normal; dacă e acoperit de un banner, click prin JavaScript
    private void safeClick(WebElement element) {
        scrollToElement(element);
        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            js.executeScript("arguments[0].click();", element);
        }
    }

    public void setFirstName(String value) {
        firstName.clear();
        firstName.sendKeys(value);
    }

    public void setLastName(String value) {
        lastName.clear();
        lastName.sendKeys(value);
    }

    public void setUserEmail(String value) {
        userEmail.clear();
        userEmail.sendKeys(value);
    }

    public void setGender(String value) {
        WebElement gender = driver.findElement(
                By.xpath("//*[@id='genterWrapper']//*[text()='" + value + "']"));
        safeClick(gender);
    }

    public void setUserNumber(String value) {
        userNumber.clear();
        userNumber.sendKeys(value);
    }

    // Câmpul de dată este re-randat de React, așa că îl căutăm din nou la fiecare pas
    // și repetăm dacă devine "stale". Nu depinde de sistemul de operare.
    public void setBirthday(String value) {
        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                WaitUtils.explicitWait(driver, ExpectedConditions.elementToBeClickable(BIRTHDAY), 10).click();
                driver.findElement(BIRTHDAY).sendKeys(Keys.END, Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
                driver.findElement(BIRTHDAY).sendKeys(value);
                driver.findElement(BIRTHDAY).sendKeys(Keys.ENTER);
                return;
            } catch (StaleElementReferenceException e) {
                if (attempt == 3) {
                    throw e;
                }
            }
        }
    }

    public void addSubject(String value) {
        subjects.sendKeys(value);
        subjects.sendKeys(Keys.ENTER);
    }

    public void addHobby(String value) {
        WebElement hobby = driver.findElement(By.xpath("//*[@id='hobbiesWrapper']//*[text()='" + value + "']"));
        safeClick(hobby);
    }

    public void setState(String value) {
        scrollToElement(state);
        state.click();
        selectOption(value);
    }

    public void setCity(String value) {
        scrollToElement(city);
        city.click();
        selectOption(value);
    }

    private void selectOption(String value) {
        By option = By.xpath("//div[contains(@id,'-option-') and text()='" + value + "']");
        WaitUtils.explicitWait(driver, ExpectedConditions.elementToBeClickable(option), 10).click();
    }

    public void clickSubmit() {
        safeClick(submit);
    }

    public String getResultTitle() {
        return WaitUtils.explicitWait(driver, ExpectedConditions.visibilityOfElementLocated(RESULT_TITLE), 10).getText();
    }
}
