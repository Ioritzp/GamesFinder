package Utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ActionsUtility extends Utility{

    private static Actions act (){
        //actions can perform things like action movement or keyboard events

        //an object from the Actions class that contains methods for said actions. Actions can also be concatenated into one complex action.
        return new Actions(driver);
    }

    public static void dragAndDropBy(WebElement source, int x, int y){
        //drags an element - the slider in this case - and drops on a specific point, indicated by the x and y offsets.
        act().dragAndDropBy(source, x, y)
        .perform();
    }

    //sends a series of keys to a specific target, like a fillable text box.
    public static void sendKeys (WebElement target, CharSequence keys){
        act().sendKeys(target, keys)  //chord sends more than one key at a time.
        .perform();
    }

    public static void pressTab(){
        act().sendKeys(Keys.TAB).perform();
    }

    public static void scrollToViewElement(By locator){
        act().scrollToElement(driver.findElement(locator)).perform();
    }
}
