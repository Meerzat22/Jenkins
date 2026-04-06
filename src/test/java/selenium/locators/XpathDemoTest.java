package selenium.locators;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Tag("UI")
@Tag("REGRESSION")
public class XpathDemoTest extends BaseUiTest {
    String nameOfRestaurant = "Кщкщ";
    List<String> listOfMeals = Arrays.asList("Винегрет", "Борщ", "Кола 1л.");


    void byRelativeXpathTest() {
        driver.get("https://dostavka312.kg/#");
        List<WebElement> restourentNames = driver.findElements(By.xpath("//div[@class ='popular__list']" +
                "/a/div[@class='popular__item-info']/div[@class='popular__item-info-head']/h3"));
        for (WebElement res : restourentNames) {
            System.out.println("Brand Name: " + res.getText());
        }
        driver.quit();
    }

    WebElement findRes(String name) {
        List<WebElement> restourentNames = driver.findElements(By.xpath(
                "//div[@class='popular__item-info-head']/h3"));
        WebElement timeOfRestaurant = driver.findElement(By.xpath("//section[@class='popular']/" +
                "div[@class='container-big']/div[@class='popular__list']/a[@href='/cata-nacionalnaya-kukhnya/faiza']/" +
                "div[@class='popular__item-info']/ul/li[1]"));
        WebElement nameRes = null;
        for (WebElement res : restourentNames) {
            if (res.getText().equals(name)) {
                nameRes = res;
            }
        }
        return nameRes;
    }

    @Test
    void checkOrder() throws Exception {

        driver.get("https://dostavka312.kg/#");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));
        Thread.sleep(5000);
        WebElement timeOfRestaurant = driver.findElement(By.xpath("//section[@class='popular']/" +
                "div[@class='container-big']/div[@class='popular__list']/a[@href='/cata-nacionalnaya-kukhnya/faiza']/" +
                "div[@class='popular__item-info']/ul/li[1]"));
        String nameOfRestaurant = "Фаиза";
        String str1 = timeOfRestaurant.getText().substring(0, 5);
        String str2 = timeOfRestaurant.getText().substring(8);
        LocalTime time = LocalTime.of(12, 10);
        LocalTime time1 = LocalTime.of(8, 8);
        List<WebElement> meals = new ArrayList<>();
        WebElement m = findRes(nameOfRestaurant);
        if (time.isAfter(LocalTime.parse(str1)) && time.isBefore(LocalTime.parse(str2))) {
            m.click();
            meals = driver.findElements(By.xpath(
                    "//h3[@class='shop__item-title']"));
        } else {
            throw new RuntimeException("Not available");
        }
        List<String> list = Arrays.asList("Винегрет", "Борщ", "Кола 1л.");
        int i = list.size();
        for (WebElement web : meals) {
            for (String web1 : list) {
                if (web.getText().equals(web1)) {
                    WebElement ll = web.findElement(By.xpath(
                            "//h3[contains(text(),'" +web1+"')]//..//.."));
                    WebElement kp = ll.findElement(By.className("product_btn"));
                    kp.click();
                    i--;
                }
            }
        }
        WebElement button = driver.findElement(By.xpath(
                "//a[@class='btn btn-orange w-100 oform_zakaz']"));
        button.click();
    }

    String name() {
        return nameOfRestaurant;
    }

    List<String> namesOfMeals() {
        return listOfMeals;
    }


public boolean isOpen(WebElement restaurant) {
    String from = restaurant.getAttribute("data-time-from");
    String to = restaurant.getAttribute("data-time-to");
    LocalTime openTime = LocalTime.parse(from);
    LocalTime closeTime = LocalTime.parse(to);
    LocalTime now = LocalTime.now();
    return now.isAfter(openTime) && now.isBefore(closeTime);
}

//if (timeOfRestaurant.equals(time)){
//        res.click();
//WebElement f =driver.findElement(By.xpath("//li[@class='shop-banner__about-rate']"));
//                    System.out.println(f.getText());
//        }else {
//        throw new RuntimeException("Not available");
//                }
    //StaleElementReferenceException

@Test
void newOne() throws InterruptedException {
    driver.get("https://dostavka312.kg/#");
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));
    Thread.sleep(5000);
    List<WebElement> meals = new ArrayList<>();
    List<String> notFound = new ArrayList<>();
    boolean inTheEnd = false;
    List<WebElement> all = driver.findElements(By.cssSelector(".popular__item"));
    for (WebElement elementForEach : all) {
        if (isOpen(elementForEach)) {
            WebElement namesOfRestaurants = elementForEach.findElement(By.className("popular__item-name"));
            if (namesOfRestaurants.getText().equals(name())) {
                elementForEach.click();
                Thread.sleep(3000);
                meals = driver.findElements(By.xpath(
                        "//h3[@class='shop__item-title']"));
                break;
            }

        }else {
            System.out.println("Not available");
            break;
        }
    }
    for (WebElement web : meals) {
        for (String names : namesOfMeals()) {
            if (web.getText().equals(names)) {
                WebElement ll = web.findElement(By.xpath(
                        "//h3[contains(text(),'" + names + "')]//..//.."));
                WebElement kp = ll.findElement(By.className("product_btn"));
                kp.click();
                Thread.sleep(1000);
                inTheEnd = true;
            }
        }
    }
    if (inTheEnd) {
        WebElement button = driver.findElement(By.xpath(
                "//a[@class='btn btn-orange w-100 oform_zakaz']"));
        button.click();
    }
}
}