package demoQa;

import org.example.demoQa.helpers.ElementActions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
@Tag("UI")
@Tag("REGRESSION")
public class HWNavigateBrowserTest extends BaseTest {

    @Test
    public void navigate() {
        broserHelper.open("https://demoqa.com/elements");

        for (WebElement el : hwNavigateBrowser.listOfElements()) {
            hwNavigateBrowser.click(el);
            broserHelper.goBack();
        }
    }

    @Test
    public void list() {
        broserHelper.open("https://demoqa.com/elements");
        List<WebElement> koko = driver.findElements(By.xpath("//span[@class = 'text']"));
        for (WebElement l : koko) {
            String text = l.getText().trim();
            if (!text.isEmpty()) {
                WebElement lolo = driver.findElement(By.xpath("//span[text()= '" + l.getText() + "']"));
                hwNavigateBrowser.click(lolo);
                broserHelper.goBack();
            }
        }
        hwNavigateBrowser.clickForms();
        hwNavigateBrowser.clickPractiseForms();
        hwNavigateBrowser.alerts();
        for (WebElement l : koko) {
            String text = l.getText().trim();
            if (!text.isEmpty()) {
                WebElement lo = driver.findElement(By.xpath("//span[text()='" + text + "']"));
                hwNavigateBrowser.click(lo);
                broserHelper.goBack();
            }
        }
        hwNavigateBrowser.clickWidgets();
        for (WebElement l : koko) {
            String text = l.getText().trim();
            if (!text.isEmpty()) {
                WebElement lo = driver.findElement(By.xpath("//span[text()='" + text + "']"));
                hwNavigateBrowser.click(lo);
                broserHelper.goBack();
            }
        }
        hwNavigateBrowser.clickInteractions();
        for (WebElement l : koko) {
            String text = l.getText().trim();
            if (!text.isEmpty()) {
                WebElement lo = driver.findElement(By.xpath("//span[text()='" + text + "']"));
                hwNavigateBrowser.click(lo);
                broserHelper.goBack();
            }
        }
        hwNavigateBrowser.clickBook();
        for (WebElement l : koko) {
            String text = l.getText().trim();
            if (!text.isEmpty()) {
                WebElement lo = driver.findElement(By.xpath("//span[text()='" + text + "']"));
                hwNavigateBrowser.click(lo);
                broserHelper.goBack();
            }
        }
    }

    @Test
    void findElement(){
        driver.get("https://demoqa.com");
        findElement("elements","links");
    }


    public void findElement(String menu,String element) {
        ElementActions elementActions = new ElementActions();
        for (WebElement el : hwNavigateBrowser.listOfMenu()) {
            String menuElement = el.getText();
            if (menuElement.equalsIgnoreCase(menu)) {
                elementActions.waitElementToBeClickable(el);
                elementActions.clickBtn(el);
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.urlContains(menu.toLowerCase()));
                List<WebElement> koko = driver.findElements(By.xpath("//span[@class = 'text']"));
                for (WebElement l : koko) {
                    elementActions.scrollToElement(l);
                    String text = l.getText().trim();
                    if (!text.isEmpty()) {
                        WebElement lolo = l.findElement(By.xpath("//span[text()= '" + l.getText()
                                + "']"));
                        if(lolo.getText().equalsIgnoreCase(element)){
                            lolo.click();
                            //System.out.println("Success");
                        }
                    }
                }
            }
            break;
        }
    }
}
