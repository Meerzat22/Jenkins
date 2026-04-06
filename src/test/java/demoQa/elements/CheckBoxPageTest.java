package demoQa.elements;

import demoQa.BaseTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
@Tag("UI")
@Tag("REGRESSION")
public class CheckBoxPageTest extends BaseTest {

    @Test
    @Tag("SMOKE")
    public void checkBox(){
        driver.get("https://demoqa.com/checkbox");
        checkBoxPage.clickHome();
        checkBoxPage.documents().office();
        checkBoxPage.downloads().wordFile();
    }
}
