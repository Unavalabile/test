package org.example.TestNG.pom;

import Utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class demoqaPom {
    private static final By RESULT_TITLE = By.id("example-modal-sizes-title-lg");

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
        js.executeScript("arguments[0].scrollIntoView(true);", element);
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
        driver.findElement(By.xpath("//*[@id='genterWrapper']//*[text()='" + value + "']")).click();
    }

    public void setUserNumber(String value) {
        userNumber.clear();
        userNumber.sendKeys(value);
    }

    // Nu depinde de sistemul de operare (macOS / Windows / Linux din Selenoid)
    public void setBirthday(String value) {
        WebElement field = WaitUtils.explicitWait(driver,
                ExpectedConditions.visibilityOfElementLocated(By.id("dateOfBirthInput")), 10);
        field.sendKeys(Keys.END, Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        field.sendKeys(value);
        field.sendKeys(Keys.ENTER);
    }

    public void addSubject(String value) {
        subjects.sendKeys(value);
        subjects.sendKeys(Keys.ENTER);
    }

    public void addHobby(String value) {
        WebElement hobby = driver.findElement(By.xpath("//*[@id='hobbiesWrapper']//*[text()='" + value + "']"));
        scrollToElement(hobby);
        hobby.click();
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
        scrollToElement(submit);
        submit.click();
    }

    public String getResultTitle() {
        return WaitUtils.explicitWait(driver, ExpectedConditions.visibilityOfElementLocated(RESULT_TITLE), 10).getText();
    }
}
