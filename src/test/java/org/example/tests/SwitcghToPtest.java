package org.example.tests;

import org.example.base.TestBase;
import org.example.pages.HomePage;
import org.example.pages.RegistrationPage;
import org.example.pages.SwitchToPage;
import org.example.utils.CSVUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class SwitcghToPtest extends TestBase {

    @DataProvider(name = "users")
    public Object[][] users() {
        try {
            Object[][] raw = CSVUtils.readCSVFromResources("testdata/users.csv");
            java.util.List<Object[]> filtered = new java.util.ArrayList<>();
            for (Object[] row : raw) {
                if (row == null) continue;
                if (row.length == 6) {
                    filtered.add(row);
                } else if (row.length > 6) {
                    // trim to expected number of columns
                    Object[] firstSix = new Object[6];
                    System.arraycopy(row, 0, firstSix, 0, 6);
                    filtered.add(firstSix);
                } else {
                    // skip invalid rows and log for debugging
                    System.out.println("Skipping CSV row with unexpected column count: " + java.util.Arrays.toString(row));
                }
            }
            return filtered.toArray(new Object[0][]);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read CSV data", e);
        }
    }
    @Test(dataProvider = "users")
    public void switchToAlert(String firstName, String lastName, String address, String email, String phone, String password) {

        HomePage home = new HomePage(getDriver());
        home.goToRegistration();
        SwitchToPage sp = new SwitchToPage(getDriver());

        RegistrationPage reg = new RegistrationPage(getDriver());
        reg.fillBasicDetails(firstName, lastName, address, email, phone, password);

        sp.switchtoheading();
        sp.switchtoalert();
        sp.switchtoOktab();

    }
}
