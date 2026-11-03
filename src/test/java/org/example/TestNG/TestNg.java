package org.example.TestNG;

import Utils.SeleniumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.TestNG.pom.demoqaPom;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestNg {
    private static final Logger logger = LogManager.getLogger(TestNg.class);

    private static final String URL = System.getProperty("url", "https://demoqa.com/automation-practice-form");
    private static final String FIRST_NAME = "Veniamin";
    private static final String LAST_NAME = "Costandoi";
    private static final String EMAIL = "test.user@example.com";
    private static final String GENDER = "Male";
    private static final String NUMBER = "0600000000";
    private static final String DATE_OF_BIRTH = "17 Jul 2007";
    private static final String SUBJECT = "Maths";
    private static final String HOBBY = "Sports";
    private static final String STATE = "Rajasthan";
    private static final String CITY = "Jaipur";

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = SeleniumDriver.getDriver();
        logger.info("Driver pornit");
    }

    @Test
    public void formTest() {
        driver.get(URL);
        driver.manage().window().maximize();
        demoqaPom form = new demoqaPom(driver);
        form.closeAdvert();

        logger.info("Completez formularul");
        form.setFirstName(FIRST_NAME);
        form.setLastName(LAST_NAME);
        form.setUserEmail(EMAIL);
        form.setGender(GENDER);
        form.setUserNumber(NUMBER);
        form.setBirthday(DATE_OF_BIRTH);
        form.addSubject(SUBJECT);
        form.addHobby(HOBBY);
        form.setState(STATE);
        form.setCity(CITY);
        form.clickSubmit();

        String title = form.getResultTitle();
        logger.info("Titlul ferestrei de rezultat: {}", title);
        Assert.assertEquals(title, "Thanks for submitting the form");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null && !Boolean.getBoolean("keep.open")) {
            driver.quit();
        }
    }
}
