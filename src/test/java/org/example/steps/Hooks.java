package org.example.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.example.utils.DriverFactory;

import java.time.Duration;

public class Hooks {

    @Before
    public void beforeScenario() {
        DriverFactory.initDriver();
        DriverFactory.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        DriverFactory.getDriver().manage().window().maximize();
        DriverFactory.getDriver().get("https://demo.automationtesting.in/");
    }

    @After
    public void afterScenario() {
        DriverFactory.quitDriver();
    }
}

