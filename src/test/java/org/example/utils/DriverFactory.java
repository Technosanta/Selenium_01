package org.example.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {
    private static final ThreadLocal<WebDriver> THREAD_DRIVER = new ThreadLocal<>();

    public static void initDriver() {
        if (THREAD_DRIVER.get() == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
            THREAD_DRIVER.set(new ChromeDriver(options));
        }
    }

    public static WebDriver getDriver() {
        return THREAD_DRIVER.get();
    }

    public static void quitDriver() {
        WebDriver drv = THREAD_DRIVER.get();
        if (drv != null) {
            try {
                drv.quit();
            } catch (Exception ignored) {
            }
            THREAD_DRIVER.remove();
        }
    }
}

