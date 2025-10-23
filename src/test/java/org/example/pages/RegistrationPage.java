package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RegistrationPage {
    private final WebDriver driver;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillBasicDetails(String firstName, String lastName, String address, String email, String phone, String password) {
        driver.findElement(By.cssSelector("input[ng-model='FirstName']")).clear();
        driver.findElement(By.cssSelector("input[ng-model='FirstName']")).sendKeys(firstName);

        driver.findElement(By.cssSelector("input[ng-model='LastName']")).clear();
        driver.findElement(By.cssSelector("input[ng-model='LastName']")).sendKeys(lastName);

        driver.findElement(By.cssSelector("textarea[ng-model='Adress']")).clear();
        driver.findElement(By.cssSelector("textarea[ng-model='Adress']")).sendKeys(address);

        driver.findElement(By.cssSelector("input[ng-model='EmailAdress']")).clear();
        driver.findElement(By.cssSelector("input[ng-model='EmailAdress']")).sendKeys(email);

        driver.findElement(By.cssSelector("input[ng-model='Phone']")).clear();
        driver.findElement(By.cssSelector("input[ng-model='Phone']")).sendKeys(phone);

        // set password and confirm
        WebElement pwd = driver.findElement(By.id("firstpassword"));
        pwd.clear();
        pwd.sendKeys(password);

        WebElement confirm = driver.findElement(By.id("secondpassword"));
        confirm.clear();
        confirm.sendKeys(password);
    }

    public String getFirstName() {
        return driver.findElement(By.cssSelector("input[ng-model='FirstName']")).getAttribute("value");
    }

    public static class Switchpage {


    }
}

