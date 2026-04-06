package selenium.locators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
@Tag("UI")
@Tag("REGRESSION")
public class BasicLocators extends BaseUiTest {

    @Test
    void byIdTest(){
    driver.get("https://demoqa.com/text-box");
        WebElement userName = driver.findElement(
                By.id("userName"));
        userName.sendKeys("Sanzhar Amanturov");
       WebElement email = driver.findElement(
               By.id("userEmail"));
       email.sendKeys("@koko12");
       WebElement text_center= driver.findElement(
               By.className("text-center"));
        System.out.println(text_center.getText());
    WebElement cerrentAddres = driver.findElement(
            By.id("currentAddress"));
    cerrentAddres.sendKeys("woll-strit 123");
    WebElement permanentAddress = driver.findElement(
            By.id("permanentAddress"));
    permanentAddress.sendKeys("Mira 123");
    WebElement submit = driver.findElement(
            By.id("submit"));
    submit.click();

    }

    @Test
    void TagName(){
        driver.get("https://demoqa.com/text-box");
        WebElement textBox = driver.findElement(By.tagName("h1"));
        System.out.println(textBox.getText());
    }

    @Test
    void byLinkTest(){
        driver.get("https://demoqa.com/links");
        WebElement homeLink = driver.findElement(By.linkText("Home"));
        homeLink.click();
    }

    @Test
    void byPartialLinkTest(){
        driver.get("https://demoqa.com/links");
        WebElement homeLink = driver.findElement(By.partialLinkText("Bad"));
        homeLink.click();
        WebElement linkRes = driver.findElement(By.id("linkResponse"));
        String str1 =linkRes.getText();
        final String cos = "Link has responded with staus 400 and status text Bad Request";
        Assertions.assertEquals(cos,str1);

    }

    @Test
    void byName(){
        driver.get("https://www.google.com/");
        WebElement searchInput = driver.findElement(By.name("q"));
        searchInput.sendKeys("iphone");
        searchInput.sendKeys(Keys.ENTER);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
