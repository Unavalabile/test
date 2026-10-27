package org.example.TestNG;

import Utils.SeleniumDriver;
import org.example.TestNG.pom.demoqaPom;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class testNg {
    public static WebDriver driver;
    public static String url = "https://demo-qa-app.azurewebsites.net/automation-practice-form";
    public static String firstName = "Dorin";
    public static String lastName = "Ceban";
    public static String userEmail = "dorin.ceban@example.com";

    @BeforeTest
    public void beforeTest() {
        driver = SeleniumDriver.getLocalDriver();
    }

    @Test
    public void formTest() {
        driver.get(url);
        driver.manage().window().maximize();
        demoqaPom form = new demoqaPom(driver);

        form.setFirstName(firstName);
        Assert.assertEquals(form.getFirstName(), firstName);

        form.setLastName(lastName);
        Assert.assertEquals(form.getLastName(), lastName);

        form.setUserEmail(userEmail);
        Assert.assertEquals(form.getUserEmail(), userEmail);
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}