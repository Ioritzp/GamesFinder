package Utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class waitUtility extends Utility{

    //wait methods have been stablished on Utility class, but will be replicated and expanded here consistently
    public static void explicitWaitUntilVisible(By locator, int seconds){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)); //an element object is not needed, in contrast to the ones in Utility. It's better to deliver all the parameters to the method.
    }

    public static void waitUntilUrlContains(int seconds, String condition){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains(condition),
                ExpectedConditions.urlContains(condition.toUpperCase())
        ));
    }

    public static void fluentWaitUntilVisible(By locator, int seconds){
        FluentWait fluentWait = new FluentWait(driver)
                .withTimeout(Duration.ofSeconds(seconds))
                .pollingEvery(Duration.ofMillis(500)) //poll evaluates the condition for every X seconds, instead of waiting a maximum of X seconds or if the condition complies, as the explicit version. Duration.ofseconds sets the maximum amount of seconds to pass.
                .ignoring(NoSuchSessionException.class, StaleElementReferenceException.class); //decides to ignore specific exceptions.

        fluentWait.until(ExpectedConditions.visibilityOfElementLocated(locator));


    }
}
