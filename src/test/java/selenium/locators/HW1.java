package selenium.locators;


import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
@Tag("UI")
public class HW1 extends  BaseUiTest{

    @Test
    @Tag("SMOKE")
    void registration(){
        driver.get("https://demoqa.com/text-box");
        WebElement fullName = driver.findElement(By.id("userName"));
        fullName.sendKeys("Nurmagamedov Khabib");
        WebElement email = driver.findElement(By.id("userEmail"));
        email.sendKeys("khabib@mail.ru");
        WebElement currentAddress = driver.findElement(By.id("currentAddress"));
        currentAddress.sendKeys("mira 123");
        WebElement permanentAddress = driver.findElement(By.id("permanentAddress"));
        permanentAddress.sendKeys("koko 123");
        WebElement submit = driver.findElement(By.id("submit"));
        submit.click();
        WebElement fullName1 = driver.findElement(By.id("name"));
        final String cos1 = "Name:Nurmagamedov Khabib";
        assertEquals(cos1,fullName1.getText());
        WebElement email1 = driver.findElement(By.id("email"));
        final String cos2 = "Email:khabib@mail.ru";
        assertEquals(cos2,email1.getText());
        WebElement current1 = driver.findElement(By.xpath("//p[@id='currentAddress' and @class='mb-1']"));
        String text = current1.getText();
        System.out.println(text+"2222");
    }
}
