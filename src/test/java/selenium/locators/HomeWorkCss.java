package selenium.locators;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
@Tag("UI")
@Tag("REGRESSION")
public class HomeWorkCss extends BaseUiTest {

    void cssTest() {
        driver.get("https://dostavka312.kg/eda");
        List<WebElement> allRestaurants = driver.findElements(By.cssSelector(
                " a.popular__item h3"));
    }

    @Test
    void europeanRestaurants() {
        driver.get("https://dostavka312.kg/eda");
        System.out.println("-------Европейская кухня---------");
        List<WebElement> europeanRestaurants = driver.findElements(By.cssSelector(
                "a.popular__item[href*='cata-evropeyskaya-kukhnya/']"));
        for (WebElement elementForEach : europeanRestaurants) {
            WebElement restaurant = elementForEach.findElement(By.cssSelector(".popular__item-name"));
            System.out.println(restaurant.getText());
        }
    }
    void nationalRestaurants() {
        driver.get("https://dostavka312.kg/eda");
        System.out.println("-------Национальная кухная--------");
        List<WebElement> nationalRestaurants = driver.findElements(By.cssSelector(
                "a.popular__item[href*='cata-nacionalnaya-kukhnya/']"));
        for (WebElement elementForEach : nationalRestaurants) {
            WebElement restaurant = elementForEach.findElement(By.cssSelector(".popular__item-name"));
            System.out.println(restaurant.getText());
        }
    }
    void japaneseRestaurants() {
        driver.get("https://dostavka312.kg/eda");
        System.out.println("--------Японская кухня-------------");
        List<WebElement> japaneseRestaurants = driver.findElements(By.cssSelector(
                "a.popular__item[href*='cata-yaponskaya-kukhnya/']"));
        for (WebElement elementForEach : japaneseRestaurants) {
            WebElement restaurant = elementForEach.findElement(By.cssSelector(".popular__item-name"));
            System.out.println(restaurant.getText());
        }
    }
    void chinesRestaurants() {
        driver.get("https://dostavka312.kg/eda");
        System.out.println("-------Китайская кухня---------");
        List<WebElement> chinesRestaurants = driver.findElements(By.cssSelector(
                "a.popular__item[href*='cata-kitayskaya-kukhnya/']"));
        for (WebElement elementForEach : chinesRestaurants) {
            WebElement restaurant = elementForEach.findElement(By.cssSelector(".popular__item-name"));
            System.out.println(restaurant.getText());
        }
    }
    void kukhniMeraRestaurants() {
        driver.get("https://dostavka312.kg/eda");
        System.out.println("-------Кухни мира---------");
        List<WebElement> kukhniMeraRestaurants = driver.findElements(By.cssSelector(
                "a.popular__item[href*='cata-kukhni-mira/']"));
        for (WebElement elementForEach : kukhniMeraRestaurants) {
            WebElement restaurant = elementForEach.findElement(By.cssSelector(".popular__item-name"));
            System.out.println(restaurant.getText());
        }
    }
    void turkeyRestaurants() {
        driver.get("https://dostavka312.kg/eda");
        System.out.println("-------Турецкая кухня---------");
        List<WebElement> turkeyRestaurants = driver.findElements(By.cssSelector(
                "a.popular__item[href*='cata-tureckaya-kukhnya/']"));
        for (WebElement elementForEach : turkeyRestaurants) {
            WebElement restaurant = elementForEach.findElement(By.cssSelector(".popular__item-name"));
            System.out.println(restaurant.getText());
        }
    }
    void coffees() {
        driver.get("https://dostavka312.kg/eda");
        System.out.println("-------Кофейни---------");
        List<WebElement> europeanRestaurants = driver.findElements(By.cssSelector(
                "a.popular__item[href*='cata-kofeyni/']"));
        for (WebElement elementForEach : europeanRestaurants) {
            WebElement restaurant = elementForEach.findElement(By.cssSelector(".popular__item-name"));
            System.out.println(restaurant.getText());
        }
    }
    void georgianRestaurants() {
        driver.get("https://dostavka312.kg/eda");
        System.out.println("-------Грузинская кухня---------");
        List<WebElement> georgianRestaurants = driver.findElements(By.cssSelector(
                "a.popular__item[href*='cata-gruzinskaya-kukhnya/']"));
        for (WebElement elementForEach : georgianRestaurants) {
            WebElement restaurant = elementForEach.findElement(By.cssSelector(".popular__item-name"));
            System.out.println(restaurant.getText());
        }
    }
}
