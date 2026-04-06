package demoQa.elements;

import demoQa.BaseTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
@Tag("UI")
@Tag("REGRESSION")
public class BrokenLinksTest extends BaseTest {

    @FindBy(css = "a[href = 'http://demoqa.com']")
    private WebElement validLink;
    @FindBy(css = "a[href = 'http://the-internet.herokuapp.com/status_codes/500']")
    private WebElement invalidLink;

    @Test
    public void checkLink() throws IOException {
        PageFactory.initElements(driver, this);
        driver.get("https://demoqa.com/broken");
        List<WebElement> allLinks = List.of(invalidLink, validLink);
        for (WebElement link : allLinks) {
            String urlLink = link.getAttribute("href");
            URL url = new URL(urlLink);
            HttpURLConnection httpURLConnect = (HttpURLConnection) url.openConnection();
            httpURLConnect.setConnectTimeout(5000);
            httpURLConnect.connect();
            if (httpURLConnect.getResponseCode() >= 400) {
                System.out.println(urlLink + " - " + httpURLConnect.getResponseMessage() + " is a broken link");
            } else {
                System.out.println(urlLink + " - " + httpURLConnect.getResponseMessage() + " OK");
            }
        }
    }
}
