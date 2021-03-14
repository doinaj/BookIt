package com.BookIt.step_definitions;

import com.BookIt.pages.MapPage;
import com.BookIt.pages.SelfPage;
import com.BookIt.utilities.BrowserUtils;
import com.BookIt.utilities.DB_Utility;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class User_StepDefinitions {

    MapPage mapPage = new MapPage();

    SelfPage selfPage = new SelfPage();


    @When("User goes to self page")
    public void user_goes_to_self_page() {
        BrowserUtils.hoverOver(mapPage.myLink);
        BrowserUtils.waitUntilClickable(mapPage.selfLink).click();

    }


    @Then("User info on UI should match with DB user info for user {string}")
    public void user_info_on_UI_should_match_with_DB_user_info_for_user(String string) throws SQLException {

        String query = "SELECT u.firstname, u.lastname, u.role, c.location,t.name, t.batch_number\n" +
                "FROM users u\n" +
                "JOIN team t ON  u.campus_id = t.campus_id\n" +
                "JOIN campus c ON t.campus_id = c.id\n" +
                "WHERE email = 'bbursnoll8d@acquirethisname.com'\n" +
                "AND t.name = 'HighTech';";


        ResultSet rs = DB_Utility.runQuery(query);
        rs.next();



        Map<String, String> userInfo = new LinkedHashMap<>();


        try {
            ResultSetMetaData rsmd = rs.getMetaData();

            rs.beforeFirst();
            while (rs.next()) {
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    userInfo.put(rsmd.getColumnName(i), rs.getString(i));
                }
            }


        } catch (SQLException e) {
            System.err.println("ERROR WHILE GETTING METADATA " + e.getMessage());
        }


        String expectedFullName = userInfo.get("firstname") + " " + userInfo.get("lastname");
        String expectedRole = userInfo.get("role");
        String expectedLocation = userInfo.get("location");
        String expectedTeamName = userInfo.get("name");
        String expectedBatch = "#" + userInfo.get("batch_number");


        BrowserUtils.waitUntilTextToBe(selfPage.fullName, "John Dillinger");
        String actualFullName = selfPage.fullName.getText().trim();

        BrowserUtils.waitUntilTextToBe(selfPage.role, expectedRole);
        String actualRole = selfPage.role.getText().trim();
        BrowserUtils.waitUntilTextToBe(selfPage.campus, expectedLocation);
        String actualLocation = selfPage.campus.getText().trim();
        BrowserUtils.waitUntilTextToBe(selfPage.teamName, expectedTeamName);
        String actualTeamName = selfPage.teamName.getText().trim();
        BrowserUtils.waitUntilTextToBe(selfPage.batch, expectedBatch);
        String actualBatch = selfPage.batch.getText().trim();

        Assert.assertTrue(expectedFullName.equals(actualFullName));
        Assert.assertTrue(expectedRole.equals(actualRole));
        Assert.assertTrue(expectedLocation.equals(actualLocation));
        Assert.assertTrue(expectedTeamName.equals(actualTeamName));
        Assert.assertTrue(expectedBatch.equals(actualBatch));


    }
}
