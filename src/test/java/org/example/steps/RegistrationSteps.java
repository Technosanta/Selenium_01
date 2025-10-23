package org.example.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.example.pages.HomePage;
import org.example.pages.RegistrationPage;
import org.example.utils.DriverFactory;
import org.testng.Assert;

public class RegistrationSteps {
    private HomePage home;
    private RegistrationPage reg;

    @Given("I am on the demo automation home page")
    public void i_am_on_home_page() {
        // Driver is initialized in Hooks.beforeScenario
        home = new HomePage(DriverFactory.getDriver());
    }

    @When("I go to registration page")
    public void i_go_to_registration_page() {
        home.goToRegistration();
        reg = new RegistrationPage(DriverFactory.getDriver());
    }

    @When("I fill registration with {string} {string} {string} {string} {string} {string}")
    public void i_fill_registration_with(String firstName, String lastName, String address, String email, String phone, String password) {
        reg.fillBasicDetails(firstName, lastName, address, email, phone, password);
    }

    @Then("the first name field should contain {string}")
    public void the_first_name_field_should_contain(String firstName) {
        Assert.assertEquals(reg.getFirstName(), firstName);
    }
}
