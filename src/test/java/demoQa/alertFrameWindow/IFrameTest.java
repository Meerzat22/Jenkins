package demoQa.alertFrameWindow;

import demoQa.BaseTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
@Tag("UI")
@Tag("REGRESSION")
public class IFrameTest extends BaseTest {

    @Test
    void frameTest(){
        driver.get("https://demoqa.com/frames");
        iFrameHelper.switchToFrame("frame1");
        String txt = frame.getSampleHeading();
        System.out.println(txt);
        iFrameHelper.switchToParentFrame();
        iFrameHelper.switchToFrame("frame2");
        String txt1 = frame.getSampleHeading();
        System.out.println(txt1);
    }
}
