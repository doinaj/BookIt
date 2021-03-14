package com.BookIt.step_definitions;

import com.BookIt.utilities.DB_Utility;
import com.BookIt.utilities.Driver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import io.cucumber.java.Scenario;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Hooks {
    @Before
    public void setUp() {
        Driver.getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        //  Driver.getDriver().manage().window().maximize();
    }
    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot,"image/png", scenario.getName());
        }
        Driver.closeDriver();
    }
    @Before("@db")
    public void setUpDb() {
        System.out.println("Creating database connection");
        DB_Utility.createConnection();
    }
    @After("@db")
    public void tearDownDb() {
        System.out.println("Closing database connection");
        DB_Utility.destroy();
    }
}
