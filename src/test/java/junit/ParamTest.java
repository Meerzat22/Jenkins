package junit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
@Tag("UNIT")
public class ParamTest {
    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5})
    void testWithNumbers(int num){
        System.out.println("Numbers: " + num);
    }

    @ParameterizedTest
    @CsvSource({
            "2,3,5",
            "5,5,10",
            "2,5,7",
            "5,1,6"
    })
    void testWithNumbers2(int a,int b,int res){
        Assertions.assertEquals(res,a+b);
    }

    @RepeatedTest(3)
    void repeatTest(){
        System.out.println("Koko");
    }
}
