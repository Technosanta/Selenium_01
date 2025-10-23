package org.example.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "org.example.steps",
        plugin = {"pretty", "html:target/cucumber-html-report"},
        monochrome = true
)
public class CucumberTestRunner extends AbstractTestNGCucumberTests {

    // Enable parallel execution of scenarios
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}

