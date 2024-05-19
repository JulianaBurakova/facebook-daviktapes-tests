import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class NewAccountTest {
    private static final String NEW_ACCOUNT_URL = "https://www.facebook.com/r.php?locale=en_US&display=page";
    private static WebDriver driver;

    @BeforeAll
    public static void classSetup() {
        driver = SharedDriver.getWebDriver();
        driver.get(NEW_ACCOUNT_URL);
    }

    @AfterAll
    public static void classTearDown() {
        SharedDriver.closeDriver();

    }

    @AfterEach
    public void testTeardown() {
        driver.get(NEW_ACCOUNT_URL);
    }

    @Test
    public void newAccountPageURLTest() {
        String actualURL = driver.getCurrentUrl();
        assertEquals(NEW_ACCOUNT_URL, actualURL, "URLs do not match");
    }

        @Test
    public void FindElementsNewAccountTest() {
        WebElement firstName = driver.findElement(By.xpath("//input[@name='firstname']"));
        assertNotNull(firstName);
        WebElement lastName = driver.findElement(By.xpath("//input[@name='lastname']"));
        assertNotNull(lastName);
        WebElement mobEmail = driver.findElement(By.xpath("//input[@name='reg_email__']"));
        assertNotNull(mobEmail);
        WebElement newPassword = driver.findElement(By.xpath("//input[@id='password_step_input']"));
        assertNotNull(newPassword);
        WebElement signUpBtn = driver.findElement(By.xpath("//button[@type='submit']"));
        assertNotNull(signUpBtn);
        WebElement genderOptional = driver.findElement(By.xpath("//input[@name='custom_gender']"));
        assertNotNull(genderOptional);

            firstName.sendKeys("Test");
            lastName.sendKeys("User");
            mobEmail.sendKeys("test.user@gmail.com");
            newPassword.sendKeys("123456");

            String firstNameValue = firstName.getAttribute("value");
            String lastNameValue = lastName.getAttribute("value");
            String emailValue = mobEmail.getAttribute("value");
            String passwordValue = newPassword.getAttribute("value");

            assertEquals("Test", firstNameValue);
            assertEquals("User", lastNameValue);
            assertEquals("test.user@gmail.com", emailValue);
            assertEquals("123456", passwordValue);

            signUpBtn.click();


    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "aaaaaaaaaaaaaaa", "!@#$%^&*()", "invalidemail@gmail"})
    public void testInvalidInputs(String input) {
        WebElement firstName = driver.findElement(By.xpath("//input[@name='firstname']"));
        firstName.sendKeys(input);
        WebElement lastName = driver.findElement(By.xpath("//input[@name='lastname']"));
        lastName.sendKeys(input);
        WebElement mobEmail = driver.findElement(By.xpath("//input[@name='reg_email__']"));
        mobEmail.sendKeys(input);
        WebElement signUpBtn = driver.findElement(By.xpath("//button[@type='submit']"));
        signUpBtn.click();
        String actualURL = driver.getCurrentUrl();
        assertEquals(NEW_ACCOUNT_URL, actualURL, "URLs do not match, new screen was opened");
    }

    @Test
    public void customGenderFieldTest() {
        WebElement customGenderRadioButton = driver.findElement(By.xpath("//input[@value='-1']"));
        assertNotNull(customGenderRadioButton);
        customGenderRadioButton.click();
        WebElement genderOptional = driver.findElement(By.xpath("//input[@name='custom_gender']"));
        assertNotNull(genderOptional);
    }

    @Test
    public void longTextTest() {
        WebElement firstNameElement = driver.findElement(By.xpath("//input[@name='firstname']"));
        assertNotNull(firstNameElement);
        String longText = String.join("", Collections.nCopies(1000, "a"));
        firstNameElement.sendKeys(longText);
        String value = firstNameElement.getAttribute("value");
        assertEquals(longText, value);
    }
    @Test
    public void specialCharacterTest() {
        WebElement firstNameElement = driver.findElement(By.xpath("//input[@name='firstname']"));
        assertNotNull(firstNameElement);
        String specialCharacters = "!@#$%^&*()";
        firstNameElement.sendKeys(specialCharacters);
        String value = firstNameElement.getAttribute("value");
        assertEquals(specialCharacters, value);
    }

    @Test
    public void yearTest() {
        driver.findElement(By.xpath("//*[@id='year']")).click();
        driver.findElement(By.xpath("//*[text()='1990']")).click();
        String yearValue = driver.findElement(By.xpath("//*[@id='year']")).getAttribute("value");
        assertEquals("1990", yearValue);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1999", "1905", "2024", "1950"})
    public void yearTestParameterized(String yearInput){

        driver.findElement(By.xpath("//*[@id='year']")).click();
        driver.findElement(By.xpath("//*[text()='"+yearInput+"']")).click();
        String yearValue = driver.findElement(By.xpath("//*[@id='year']")).getAttribute("value");
        assertEquals(yearInput, yearValue);
    }
    @ParameterizedTest
    @ValueSource(strings = {"5", "1", "15", "31"})
    public void dayTestParameterized(String dayInput){
        driver.findElement(By.xpath("//*[@id='day']")).click();
        driver.findElement(By.xpath("//*[text()='" + dayInput + "']")).click();
        String dayValue = driver.findElement(By.xpath("//*[@id='day']")).getAttribute("value");
        assertEquals(dayInput, dayValue);
    }
    @ParameterizedTest
    @ValueSource(strings = {"Jan", "Feb","Jul" ,"Dec"})
    public void monthTest(String monthInput) {
        Select monthDropdown = new Select(driver.findElement(By.xpath("//select[@aria-label='Month']")));
        monthDropdown.selectByVisibleText(monthInput);

        String selectedMonth = monthDropdown.getFirstSelectedOption().getText();
        assertEquals(monthInput, selectedMonth, "The incorrect month has been selected.");
    }
    @Test
    public void emailErrorMessageTest(){
        driver.findElement(By.xpath("//*[@name='websubmit']")).click();
        driver.findElement(By.xpath("//*[@name='reg_email__']")).click();

        WebElement error = driver.findElement(By.xpath("//*[contains(text(),'to reset')]"));
        assertNotNull(error);
    }
    @Test
    public void firstNameErrorMessageTest() {
        driver.findElement(By.xpath("//*[@name='websubmit']")).click();
        driver.findElement(By.xpath("//*[@name='firstname']")).click();
        WebElement error = driver.findElement(By.xpath("//*[contains(text(),'your name?')]"));
        assertNotNull(error);
    }

    @Test
    public void lastNameErrorMessageTest() {
        driver.findElement(By.xpath("//*[@name='websubmit']")).click();
        driver.findElement(By.xpath("//*[@name='lastname']")).click();
        WebElement error = driver.findElement(By.xpath("//*[contains(text(),'your name?')]"));
        assertNotNull(error);
    }

    @Test
    public void passwordErrorMessageTest() {
        driver.findElement(By.xpath("//*[@name='websubmit']")).click();
        driver.findElement(By.xpath("//*[@name='reg_passwd__']")).click();
        WebElement error = driver.findElement(By.xpath("//*[contains(text(),'combination')]"));
        assertNotNull(error);
    }
    @Test
    public void birthdayErrorMessageTest() {
        driver.findElement(By.xpath("//*[@name='websubmit']")).click();
        driver.findElement(By.xpath("//*[@data-name='birthday_wrapper']")).click();
        WebElement error = driver.findElement(By.xpath("//*[contains(text(),'real birthday')]"));
        assertNotNull(error);
    }
    @Test
    public void genderErrorMessageTest() {
        driver.findElement(By.xpath("//*[@name='websubmit']")).click();
        driver.findElement(By.xpath("//*[@data-name='gender_wrapper']")).click();
        WebElement error = driver.findElement(By.xpath("//*[contains(text(),'choose a gender')]"));
        assertNotNull(error);
    }
    @Test
    public void genderTest() {
        String femaleXpath = "//*[text()='Female']//following-sibling::*[@type='radio']";
        String maleXpath = "//*[text()='Male']//following-sibling::*[@type='radio']";
        String customXpath = "//*[text()='Custom']//following-sibling::*[@type='radio']";

        WebElement femaleBtn = driver.findElement(By.xpath(femaleXpath));
        femaleBtn.click();
        String isFemaleChecked = driver.findElement(By.xpath(femaleXpath)).getAttribute("checked");
        assertNotNull(isFemaleChecked);
        assertEquals("true", isFemaleChecked);

        WebElement maleBtn = driver.findElement(By.xpath(maleXpath));
        maleBtn.click();
        String isMaleChecked = driver.findElement(By.xpath(maleXpath)).getAttribute("checked");
        assertNotNull(isMaleChecked);
        assertEquals("true", isMaleChecked);

        WebElement customBtn = driver.findElement(By.xpath(customXpath));
        customBtn.click();
        String isCustomChecked = driver.findElement(By.xpath(customXpath)).getAttribute("checked");
        assertNotNull(isCustomChecked);
        assertEquals("true", isCustomChecked);
    }
    @Test
    public void termsLinkTest() {
        String mainWindowHandle = driver.getWindowHandle();
        driver.findElement(By.xpath("//a[@id='terms-link']")).click();

        for (String windowHandle : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandle);
        }

        assertNotEquals(driver.getCurrentUrl(), NEW_ACCOUNT_URL);
        driver.close();
        driver.switchTo().window(mainWindowHandle);
    }

    @Test
    public void privacyPolicyLinkTest() {
        String mainWindowHandle = driver.getWindowHandle();
        driver.findElement(By.xpath("//a[@id='privacy-link']")).click();

        for (String windowHandle : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandle);
        }

        assertNotEquals(driver.getCurrentUrl(), NEW_ACCOUNT_URL);
        driver.close();
        driver.switchTo().window(mainWindowHandle);
    }
}
