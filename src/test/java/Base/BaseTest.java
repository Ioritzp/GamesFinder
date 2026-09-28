package Base;

import com.base.BasePage;
import com.steamPages.SteamHomePage;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;

import static com.base.BasePage.delay;

public class BaseTest {


    protected WebDriver driver;
    protected BasePage basePage;
    protected SteamHomePage homePage;

    private String STEAM_URL = "https://store.steampowered.com/?l=spanish";

    @BeforeClass
    public void setUp(){
        //generic setup to configure Brave as browser - as it uses chromium, it can be done as a chromedriver.
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe");

        //below are options to configure the program to act without opening a visual browser.
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("user-agent=Chrome/120.0.0.0");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        //implicit wait - never use it with explicit or flexible timeouts, as they can mix and cause errors. This sets a general timeout for the driver.
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        //page load
        //driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

    }

    @BeforeMethod
    public void loadApplication(){
        driver.get(STEAM_URL);
        basePage = new BasePage();
        basePage.setDriver(driver);
        homePage = new SteamHomePage(driver);
    }

    @AfterMethod
    public void takeFailedResultScreenshot(ITestResult testResult){
        if(ITestResult.FAILURE == testResult.getStatus()){
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File source = screenshot.getScreenshotAs(OutputType.FILE);
            File destination = new File(System.getProperty("user.dir") + "/resources/screenshots/ " + java.time.LocalDate.now() + testResult.getName() +".png");
            try {
                FileHandler.copy(source, destination);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Screenshot located at " + destination);
        }
    }

    @AfterClass
    public void tearDown(){
        delay(4000);
        driver.quit();
    }
}
