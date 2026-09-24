package com.base;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BasePage {

    public static WebDriver driver; //global variable to keep browser session active.

    public void setDriver(WebDriver driver){//receives a webdriver instance and assigns it to the static driver from basepage, so it injects the browsers active session on the pages.
        BasePage.driver = driver;
    }

    protected WebElement find(By locator){//it receives a localizer (locator) like By.id, By.name, etc. and returns the found object. a way to centralize finding any element on the web.
        return driver.findElement(locator);
    }

    protected void set(By locator, String text){ //using the previous find method, we provide the locator for the element we want to find and we input a string (like for example, looking for the username text box element on the web and inputing an username)
        find(locator).clear(); //good practice to clear the element before inputing something.
        find(locator).sendKeys(text);
    }

    protected void click(By locator){ //clicks any element on the page, a button, a link...

        find(locator).click();
    }

    public static void delay (int miliseconds){
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }
}
