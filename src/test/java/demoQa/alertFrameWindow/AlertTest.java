package demoQa.alertFrameWindow;

import demoQa.BaseTest;
import org.example.demoQa.drivers.DriverManager;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
@Tag("UI")
@Tag("REGRESSION")
public class AlertTest extends BaseTest {

    @Test
    @Tag("SMOKE")
    void alertTest(){
        driver.get("https://demoqa.com/alerts");
        alertPage.clickAlertBtn();
        alertHelper.accept();
        alertPage.clickAlertBtn();
        alertHelper.dismis();
        alertPage.promtButton();
        alertHelper.sendKeys("koko shnene").accept();
    }
}
