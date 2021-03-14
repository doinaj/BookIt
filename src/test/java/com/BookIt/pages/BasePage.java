package com.BookIt.pages;

import com.BookIt.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public abstract class BasePage {
    public BasePage(){
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy (linkText = "my")
    public WebElement myLink;

    @FindBy (linkText = "self")
    public WebElement selfLink;



}
