import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FirstSeleniumTest {

    private static final String
            HOME_PAGE_URL = "https://www.facebook.com/login/";

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
    public void findElementByAttributeTest() {


        WebElement elementEmailById = driver.findElement(By.id("email"));
        WebElement elementEmailByName = driver.findElement(By.name("email"));
        WebElement elementCreatePageByLinkText = driver.findElement(By.linkText("Create new account"));
        WebElement elementCreatePageByPartitialLinkText = driver.findElement(By.partialLinkText("new account"));
        WebElement elementEmailByCss = driver.findElement(By.cssSelector("#email"));
        assertNotNull(elementEmailById);


        List<WebElement> elementsByClassName = driver.findElements(By.className("inputtext"));
        System.out.println(elementsByClassName.size());
    }

    @Test
    public void findElementsByXpathTest() {
        WebElement emailElement = driver.findElement(By.xpath("//input[@name='email']"));
        assertNotNull(emailElement);
        WebElement passwordElement = driver.findElement(By.xpath("//input[@id='pass']"));
        assertNotNull(passwordElement);
        WebElement loginButtonElement = driver.findElement(By.xpath("//button[@type='submit']"));
        assertNotNull(loginButtonElement);
        WebElement forgotPassElement = driver.findElement(By.xpath("//a[text()='Forgot account?']"));
        assertNotNull(forgotPassElement);
        WebElement createNewAccButton = driver.findElement(By.xpath("//*[text() = 'Create new account']"));
        assertNotNull(createNewAccButton);
    }

    @Test
    public void loginScreenTest() {

        WebElement emailElement = driver.findElement(By.xpath("//input[@name='email']"));

        assertNotNull(emailElement);

        emailElement.sendKeys("My.email@gmail.com");

        String emailValue = emailElement.getAttribute("value");

        assertEquals("My.email@gmail.com", emailValue);

        WebElement passwordElement = driver.findElement(By.xpath("//input[@id='pass']"));
        assertNotNull(passwordElement);
        passwordElement.sendKeys("123456");
        String passValue = passwordElement.getAttribute("value");
        assertEquals("123456", passValue);


        WebElement loginButtonElement = driver.findElement(By.xpath("//button[@type='submit']"));
        assertNotNull(loginButtonElement);


        loginButtonElement.click();

    }

    @Test
    public void createNewAccTest() throws InterruptedException {
        driver.findElement(By.xpath("//*[text()='Create new account']")).click();
        Thread.sleep(1000);

    }

    @Test
    public void createNewAccountBtn() {
        WebElement createNewAccButton = driver.findElement(By.xpath("//*[text() = 'Create new account']"));
        assertNotNull(createNewAccButton);
        createNewAccButton.click();
    }

}