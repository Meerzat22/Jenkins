package demoQa.elements;

import demoQa.BaseTest;
import org.example.demoQa.models.Employee;
import org.example.demoQa.models.UserWebTables;
import org.example.demoQa.utils.RandomUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
@Tag("UI")
@Tag("REGRESSION")
public class WebTableTest extends BaseTest {

    @Test
    public void test(){
        driver.get("https://demoqa.com/webtables");
        UserWebTables user = RandomUtils.generateWebTablesForm();
        webTables.changeElement("first name" , "koko");
//        webTables.clickAdd();
//        webTables.firstName(user.getFirstName()).
//                lastName(user.getLastName()).
//                email(user.getEmail()).
//                age(user.getAge()).
//                salary(user.getSalary()).
//                department(user.getDepartment()).
//                submit();

//        List<Employee> employeeList = webTables.getEmployeeFromTable();
//        for (Employee employee:employeeList){
//            System.out.println(employee);
//        }
//        webTables.addNewEmployee(new Employee("Cierra","Vega",22,
//                "amama@gmail.com",1000,"law"));
    }


}
