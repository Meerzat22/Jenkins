package demoQa.elements;

import demoQa.BaseTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
@Tag("UI")
@Tag("REGRESSION")
public class LinksPageTest extends BaseTest {

    @Test
    public void checkAll(){
        driver.get("https://demoqa.com/links");
        List<WebElement> active = new ArrayList<>();
        List<WebElement> broken = new ArrayList<>();
        for(WebElement el : linksPage.list()){
            int before = driver.getWindowHandles().size();
            el.click();
            int after = driver.getWindowHandles().size();
            if (before < after){
                active.add(el);
            }else {
                broken.add(el);
            }
        }
        System.out.println(active.size());
        System.out.println(broken.size());
    }
}
