package Utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import static com.base.BasePage.driver;


public class JavaScriptUtility extends Utility{


    //JS = Javascript - Consider using Actions class, native to selenium, to avoid js.
    public static void scrollToElementJS(By locator){
        int yOffset = 2000;
        WebElement element = driver.findElement(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            //js.executeScript("window.scrollTo(0, document.body.scrollHeight);", element);
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            js.executeScript("window.scrollBy(0, arguments[0]);", yOffset);
        } catch (Exception e){
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        }

    }

    public static void hideAds() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // Remove the fixed banner and the footer.
        js.executeScript(
                "var fixedban = document.getElementById('fixedban');" +
                        "if(fixedban) { fixedban.remove(); }" +
                        "var footer = document.getElementsByTagName('footer')[0];" +
                        "if(footer) { footer.remove(); }"
        );
    }

}
