package Utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SwitchToUtility extends Utility{

    //allows us to switch to another element of the DOM in cases we can't inspect and get the paths, such as in alerts.
    private static WebDriver.TargetLocator switchTo(){
        return driver.switchTo();
    }

    public static String getAlertText(){
        return switchTo().alert().getText();
    }

    //lets check first if this is right.
    public static void acceptAlert(){
        switchTo().alert().accept();
    }

    public static void dismissAlert(){
        switchTo().alert().dismiss();
    }

    public static void setAlertText(String text){
        switchTo().alert().sendKeys(text);
    }

    //switch to a frame via string
    public static void switchToFrameString(String value){
        switchTo().frame(value);
    }

    //switch out from a frame
    public static void switchToDefaultContent(){
        switchTo().defaultContent();
    }

    //switch to frame via index
    public static void switchToFrameIndex(int index){
        switchTo().frame(index);
    }

    //switch via webelement
    public static void switchToFrameElement(WebElement element){
        switchTo().frame(element);
    }

    public static void switchToWindow(String handle){
        switchTo().window(handle);
    }

}
