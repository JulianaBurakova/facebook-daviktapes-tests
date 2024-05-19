import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class SharedDriver {

 private static WebDriver webDriver;

 public static WebDriver getWebDriver(){
     if (webDriver==null) {
         WebDriverManager.chromedriver().setup();
         webDriver = new ChromeDriver();
         webDriver.manage().window().maximize();
 //        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
     }
     return webDriver;
 }
 public static void closeDriver(){
     if (webDriver!=null) {
         webDriver.close();
     }
 }
}
