package Utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeleniumDriver {

    // Chrome for Testing de pe Mac; se poate suprascrie cu -Dchrome.binary="..."
    private static final String CHROME_BINARY =
            "/Users/veniamin/Downloads/chrome-mac-arm64/Google Chrome for Testing.app/Contents/MacOS/Google Chrome for Testing";

    /**
     * Implicit rulează pe un browser remote (Selenium Grid sau Selenoid).
     *   -Dremote=false         -> Chrome local
     *   -Dheadless=true        -> Chrome local fără fereastră
     *   -Dselenoid.url=...     -> adresa hub-ului (implicit http://127.0.0.1:4444/wd/hub)
     *   -Dbrowser.version=...  -> versiunea browserului (implicit gol = cea implicită a hub-ului;
     *                             pentru Selenoid se poate da de ex. 128.0)
     */
    public static WebDriver getDriver() {
        return Boolean.parseBoolean(System.getProperty("remote", "true"))
                ? getRemoteDriver() : getLocalDriver();
    }

    public static WebDriver getAutoDriver() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }

    public static WebDriver getLocalDriver() {
        ChromeOptions options = new ChromeOptions();
        String binary = System.getProperty("chrome.binary", CHROME_BINARY);
        if (new File(binary).exists()) {
            options.setBinary(binary);
        }
        options.addArguments("--remote-allow-origins=*");
        if (Boolean.getBoolean("headless")) {
            options.addArguments(
                    "--headless=new",
                    "--no-sandbox",
                    "--disable-dev-shm-usage",
                    "--disable-gpu",
                    "--window-size=1920,1080");
        }
        return new ChromeDriver(options);
    }

    public static RemoteWebDriver getRemoteDriver() {
        ChromeOptions options = new ChromeOptions();

        String version = System.getProperty("browser.version", "");
        if (!version.isBlank()) {
            options.setCapability("browserVersion", version);
        }

        // Opțiuni specifice Selenoid (Selenium Grid le ignoră)
        Map<String, Object> selenoidOptions = new HashMap<>();
        selenoidOptions.put("name", "demoqa form test");
        selenoidOptions.put("sessionTimeout", "15m");
        selenoidOptions.put("env", List.of("TZ=UTC"));
        selenoidOptions.put("enableVideo", false);
        selenoidOptions.put("enableVNC", true);
        options.setCapability("selenoid:options", selenoidOptions);

        String hub = System.getProperty("selenoid.url", "http://127.0.0.1:4444/wd/hub");
        try {
            return new RemoteWebDriver(URI.create(hub).toURL(), options);
        } catch (MalformedURLException e) {
            throw new IllegalStateException("URL hub invalid: " + hub, e);
        }
    }
}
