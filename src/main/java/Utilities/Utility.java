package Utilities;


import com.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class Utility {

    public static WebDriver driver;

    public static void setUtilityDriver(){
        driver = BasePage.driver;
    }

    public static void scrollToElementActions(By locator){
        WebElement element = driver.findElement(locator);

        new Actions(driver).scrollToElement(element).perform();
    }

    public static void waitForClickable(By locator){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    public static void waitForVisible(By locator){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static boolean elementExists(By locator){
        System.out.println("size: " + driver.findElements(locator).size());
        if(driver.findElements(locator).size() > 0){
            for(WebElement element : driver.findElements(locator)){
                System.out.println("element " + element);
            }
        }
        return !driver.findElements(locator).isEmpty();
    }

    public static String[] fromTxtToArray(String filePath){

        try {
            Path path = Paths.get(filePath);
            String content = Files.readString(path);

            return content.split(",");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public static List<String> fromCsvToArray(String filePath){
        try(var lines = Files.lines(Paths.get(filePath))) {
            return lines
                    .skip(1) //skips the first line (headers)
                    .filter(line -> !line.isBlank()) //ignores empty lines
                    .map(line -> line.split(",")[0].trim())
                    .map(cell -> cell.replace("\"",""))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            System.err.println("Error:" + e.getMessage());
            return List.of();
        }
    }



    //generate random number

    //return strings to UpperCase
}
