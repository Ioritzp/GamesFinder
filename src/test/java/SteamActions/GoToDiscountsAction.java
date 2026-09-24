package SteamActions;

import Base.BaseTest;
import org.testng.annotations.Test;

public class GoToDiscountsAction extends BaseTest {

    //TODO: create the system to check the discounts list, expanding the list, and getting the names, links and prices of the first 20 games.
    @Test
    public void goToDiscountsPage(){
        var steamDiscountsPageInstance = homePage.clickDiscountsButton();



    }


}
