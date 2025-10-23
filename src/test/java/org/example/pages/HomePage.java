package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private final WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void goToRegistration() {
        // The demo site has a "Skip Sign In" link that opens the registration page
        driver.findElement(By.linkText("Skip Sign In")).click();
    }
}

