package com.BookIt.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SelfPage extends BasePage{

    @FindBy (xpath = "//p[.='name']/preceding-sibling::p")
    public WebElement fullName;

    @FindBy (xpath = "//p[.='role']/preceding-sibling::p")
    public WebElement role;

    @FindBy (xpath = "//p[.='team']/preceding-sibling::p")
    public WebElement teamName;

    @FindBy (xpath = "//p[.='batch']/preceding-sibling::p")
    public WebElement batch;

    @FindBy (xpath = "//p[.='campus']/preceding-sibling::p")
    public WebElement campus;

}
