package demoQa.forms;

import org.example.demoQa.models.UserPractiseForm;
import org.example.demoQa.utils.RandomUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
@Tag("UI")
@Tag("REGRESSION")
public class PractiseFormTest extends BasePractiseForn {

    @Test
    void practiseTest() throws InterruptedException {
      // driver.get("https://demoqa.com/automation-practice-form");
        //practiseForm.fillDateOfBirth("06 Feb 2014");
        // practiseForm.fillSubject("English");

        //driver.get("https://demoqa.com/automation-practice-form");
        practiseForm.fillFirstName("Andrei").fillLastName("Ramisov").fillEmail("koko@gmail.com")
                .selectDOB("11 September 2002")
                .chooseGender("Female").fillMobileNumber("1234567890").chooseHobbies("Music")
                .fillCurrentAddress("Mira 123")
                .submit();
    }

    @Test
    @Tag("SMOKE")
    void practiseTest2 (){
//         System.setProperty("webdriver.firefox.logfile", "/dev/null");
        driver.get("https://demoqa.com/automation-practice-form");
        UserPractiseForm user = RandomUtils.generatePracticeFormUser();
        practiseForm.fillPractiseForm(user);

    }
}
