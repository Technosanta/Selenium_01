package org.example.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.time.Duration;

public class TestBase {
    // Use ThreadLocal to support parallel test execution without driver collisions
    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    protected WebDriver getDriver() {
        WebDriver drv = driverThread.get();
        if (drv == null) {
            // Provide a clearer error to help diagnose NullPointerException causes in parallel runs
            throw new IllegalStateException("WebDriver has not been initialized for this thread. Make sure @BeforeMethod setUp() ran and did not fail. Thread: " + Thread.currentThread().getName());
        }
        return drv;
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"baseUrl", "browser"})
    public void setUp(@Optional("") String baseUrl, @Optional("chrome") String browser) {
        // Simple browser selection (only chrome implemented)
        if (browser == null || browser.isEmpty() || browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            // run in headless mode to allow execution in environments without display
            //options.addArguments("--headless=new");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
            // options.addArguments("--headless=new"); // uncomment to run headless
            driverThread.set(new ChromeDriver(options));
        } else {
            // Fallback to Chrome if unsupported browser is provided
            WebDriverManager.chromedriver().setup();
            driverThread.set(new ChromeDriver());
        }

        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        getDriver().manage().window().maximize();

        if (baseUrl == null || baseUrl.isEmpty()) {
            baseUrl = "https://demo.automationtesting.in/";
        }
        getDriver().get(baseUrl);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        // Use direct ThreadLocal access to avoid throwing IllegalStateException from getDriver()
        WebDriver drv = driverThread.get();
        if (drv != null) {
            try {
                drv.quit();
            } catch (Exception ignored) {
                // ignore quit failures
            }
        }
        driverThread.remove();
    }
}
