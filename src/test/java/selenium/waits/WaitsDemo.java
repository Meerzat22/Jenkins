package selenium.waits;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.locators.BaseUiTest;

import java.time.Duration;
import java.util.Base64;
@Tag("UI")
@Tag("REGRESSION")
public class WaitsDemo extends BaseUiTest {
    WebDriver driver = new FirefoxDriver();
    //implicitlyWait - не явное ожидание

    @Test
    public void explicitlyWaitTest(){
        driver.get("https://demoqa.com/dynamic-properties");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement enableAfterBtn =
                wait.until(ExpectedConditions.elementToBeClickable(By.id(
                "enableAfter")));
        enableAfterBtn.click();
        Boolean coloChange =
                wait.until(ExpectedConditions.attributeToBe(By.id(
                        "colorChange"),"class","mt-4 text-danger"));

//        if(coloChange){
//            WebElement colorChangeBtn = driver.findElement(By.id("colorChange"));
//            colorChangeBtn.click();
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }

        wait.until(ExpectedConditions.textToBe(By.tagName("h1"),"Dynamic Properties"));

    }
}
//implicitlyWait - не явное ожидание