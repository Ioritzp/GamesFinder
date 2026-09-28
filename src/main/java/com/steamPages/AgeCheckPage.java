package com.steamPages;

import com.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class AgeCheckPage extends BasePage {

    private WebElement dayOfBirthDropDown = driver.findElement(By.id("ageDay"));
    private WebElement monthOfBirthDropDown =  driver.findElement(By.id("ageMonth"));
    private WebElement yearOfBirthDropDown = driver.findElement(By.id("ageYear"));

    Select ageDayDropdown = new Select(dayOfBirthDropDown);
    Select ageMonthDropdown = new Select(monthOfBirthDropDown);
    Select ageYearDropdown = new Select(yearOfBirthDropDown);

    private By selectPageBttn = By.id("view_product_page_btn");

    public AgeCheckPage(WebDriver driver){
        setDriver(driver);
    }


    public SteamGamePage verifyAge(){
        String day = "15";
        String month = "December";
        String year = "1991";

        ageDayDropdown.selectByValue(day);
        ageMonthDropdown.selectByValue(month);
        ageYearDropdown.selectByValue(year);



        click(selectPageBttn);
        return new SteamGamePage(driver);
    }




}
