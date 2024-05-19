import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DaviktapesTest {
    private static final String
            HOME_PAGE_URL = "https://daviktapes.com/";


    private static WebDriver driver;

    @BeforeAll
    public static void classSetup() {
        driver = SharedDriver.getWebDriver();
        driver.get(HOME_PAGE_URL);
    }

    @AfterAll
    public static void classTearDown() {
        SharedDriver.closeDriver();

    }

    @AfterEach
    public void testTeardown() {
        driver.get(HOME_PAGE_URL);
    }

    @Test
    public void homePageURLTest() {
        String actualURL = driver.getCurrentUrl();
        assertEquals(HOME_PAGE_URL, actualURL, "URLs do not match");
    }
    @Test

    public void actionTest() {
//        pause();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='Company']")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//a[text()='Company']")));

        WebElement element = driver.findElement(By.xpath("//a[text()='Company']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).build().perform();
    }

    @Test
    public void waitAndClickTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        //wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Company']"))).click();
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Company']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='Company']"))).click();
        pause();
    }

    public void pause() {
        try {
            Thread.sleep(5000);
        } catch (Exception err) {
            System.out.println("Something went wrong");
        }

    }
    public static void smallPause(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void largePause() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testTopMenuOptions() {
        String[] menuOptions = {"Company", "Products", "Industries", "Knowledge Center", "CONTACT"};

        for (String option : menuOptions) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            By locator = By.xpath("//a[text()='" + option + "']");

            wait.until(ExpectedConditions.presenceOfElementLocated(locator));

            WebElement element = driver.findElement(locator);
            Actions actions = new Actions(driver);

            actions.moveToElement(element).build().perform();

            assertEquals(option, element.getText(), "Menu option is not opened");

            smallPause();
        }
    }


}
