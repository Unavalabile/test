package org.example.TestNG.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class demoqaPom {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(id = "firstName")
    private WebElement firstName;

    @FindBy(id = "lastName")
    private WebElement lastName;

    @FindBy(id = "userEmail")
    private WebElement userEmail;

    public demoqaPom(WebDriver driverParam) {
        this.driver = driverParam;
        this.wait = new WebDriverWait(driverParam, Duration.ofSeconds(10));
        PageFactory.initElements(driverParam, this);
    }

    public void setFirstName(String firstNameParam) {
        wait.until(ExpectedConditions.visibilityOf(firstName));
        firstName.clear();
        firstName.sendKeys(firstNameParam);
    }

    public String getFirstName() {
        return firstName.getAttribute("value");
    }

    public void setLastName(String lastNameParam) {
        wait.until(ExpectedConditions.visibilityOf(lastName));
        lastName.clear();
        lastName.sendKeys(lastNameParam);
    }

    public String getLastName() {
        return lastName.getAttribute("value");
    }

    public void setUserEmail(String userEmailParam) {
        wait.until(ExpectedConditions.visibilityOf(userEmail));
        userEmail.clear();
        userEmail.sendKeys(userEmailParam);
    }

    public String getUserEmail() {
        return userEmail.getAttribute("value");
    }
}