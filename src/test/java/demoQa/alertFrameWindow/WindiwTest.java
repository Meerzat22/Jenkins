package demoQa.alertFrameWindow;

import demoQa.BaseTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
@Tag("UI")
@Tag("REGRESSION")
public class WindiwTest extends BaseTest {

    @Test
    void windowTest(){
        driver.get("https://demoqa.com/browser-windows");
        windowPage.tabButton();
        windowPage.tabButton();
        broserHelper.switchToWindow(1);
    }
}
