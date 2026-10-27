package Utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SeleniumDriver {

    private static final String CHROME_BINARY =
            "/Users/veniamin/Downloads/chrome-mac-arm64/Google Chrome for Testing.app/Contents/MacOS/Google Chrome for Testing";

    public static WebDriver getAutoDriver() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }

    public static WebDriver getLocalDriver() {
        ChromeOptions options = new ChromeOptions();
        options.setBinary(CHROME_BINARY);
        options.addArguments("--remote-allow-origins=*");
        return new ChromeDriver(options);
    }

    public static RemoteWebDriver getRemoteDriver() {
        ChromeOptions options = new ChromeOptions();
        options.setCapability("browserVersion", "128.0");

        Map<String, Object> selenoidOptions = new HashMap<>();
        selenoidOptions.put("name", "Test badge...");
        selenoidOptions.put("sessionTimeout", "15m");
        selenoidOptions.put("env", new ArrayList<String>() {{
            add("TZ=UTC");
        }});
        selenoidOptions.put("enableVideo", true);
        selenoidOptions.put("enableVNC", true);
        selenoidOptions.put("enableLog", true);
        selenoidOptions.put("noSandbox", true);
        selenoidOptions.put("headless", true);
        options.setCapability("selenoid:options", selenoidOptions);

        RemoteWebDriver remoteDriver = null;
        try {
            remoteDriver = new RemoteWebDriver(URI.create("http://127.0.0.1:4444/wd/hub").toURL(), options);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return remoteDriver;
    }
}