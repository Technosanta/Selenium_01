package org.example.tests;

import org.example.pages.HomePage;
import org.example.pages.RegistrationPage;
import org.example.utils.CSVUtils;
import org.example.base.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DemoSiteTest extends TestBase {

    // Use the static DataProvider from CSVUtils to avoid duplicate providers and enable parallel execution
    @Test(dataProvider = "users", dataProviderClass = CSVUtils.class)
    public void testRegistration(String firstName, String lastName, String address, String email, String phone, String password) {
        HomePage home = new HomePage(getDriver());
        home.goToRegistration();

        RegistrationPage reg = new RegistrationPage(getDriver());
        reg.fillBasicDetails(firstName, lastName, address, email, phone, password);

        // quick verification that first name was entered correctly
        Assert.assertEquals(reg.getFirstName(), firstName, "First name input should match provided value");
    }
}
