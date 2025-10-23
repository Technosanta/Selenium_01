package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SwitchToPage {

    private WebDriver driver;

    // Use By locators so elements are found only after driver is initialized
    private By puntoswitcherLocator = By.xpath("(//a[@href=\"SwitchTo.html\"])[1]");
    private By switchtoalertLocator = By.xpath("//a[@href=\"Alerts.html\"]");
    private By switchtoOkLocator = By.cssSelector("[href=\"#OKTab\"]");

    public SwitchToPage(WebDriver driver) {
        this.driver = driver;
    }

    public void switchtoheading(){
        driver.findElement(puntoswitcherLocator).click();
    }

    public void switchtoalert(){
        driver.findElement(switchtoalertLocator).click();
    }

    public void switchtoOktab(){
        driver.findElement(switchtoOkLocator).click();
    }
}
