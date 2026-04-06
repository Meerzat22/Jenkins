package junit.calculator;

import org.example.calculator.Calculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
@Tag("UNIT")
public class CalculatorTest {

    Calculator calculator = new Calculator();

    @Test
    void divideTest() {
        Assertions.assertEquals(1, calculator.divide(2, 2));
    }
}
