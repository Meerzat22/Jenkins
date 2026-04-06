package junit.warmUp;

import org.example.warmUp.WarmUpV1;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
@Tag("UNIT")
public class WarmUpV1Test {
    WarmUpV1 obj = new WarmUpV1();

    @BeforeAll
    static void beforAll(){
        System.out.println("Начало WarmUp 1");
    }

    @BeforeEach
    void beforEach(){
        System.out.println("First");
    }
    @Test
    void sleepInT() {
        Assertions.assertTrue(obj.sleepIn(false,false));
        Assertions.assertFalse(obj.sleepIn(true,false));
        Assertions.assertTrue(obj.sleepIn(false,true));
    }

    @BeforeEach
    void beforeEach(){
        System.out.println("Second");
    }

    @Test
    void monkeyTroubleT(){
        Assertions.assertTrue(obj.monkeyTrouble(true,true));
        Assertions.assertTrue(obj.monkeyTrouble(false,false));
        Assertions.assertFalse(obj.monkeyTrouble(false,true));
    }

    @BeforeEach
    void beforeEach1(){
        System.out.println("Third");
    }
    @Test
    void sumDoubleT(){
        Assertions.assertEquals(3,obj.sumDouble(1,2));
        Assertions.assertEquals(8,obj.sumDouble(2,2));
        Assertions.assertEquals(12,obj.sumDouble(3,3));
    }

    @Test
    void diff21T(){
        Assertions.assertEquals(2,obj.diff21(19));
        Assertions.assertEquals(10,obj.diff21(11));
        Assertions.assertEquals(21,obj.diff21(0));
    }

    @Test
    void parrotTroubleT(){
        Assertions.assertTrue(obj.parrotTrouble(true,6));
        Assertions.assertFalse(obj.parrotTrouble(true,7));
        Assertions.assertTrue(obj.parrotTrouble(true,21));
    }

    @Test
    void makes10T(){
        Assertions.assertTrue(obj.makes10(9,10));
        Assertions.assertFalse(obj.makes10(9,9));
        Assertions.assertTrue(obj.makes10(1,9));
    }

    @Test
    void nearHundredT(){
        Assertions.assertTrue(obj.nearHundred(93));
        Assertions.assertTrue(obj.nearHundred(90));
        Assertions.assertFalse(obj.nearHundred(89));
    }

    @Test
    void posNegT(){
        Assertions.assertTrue(obj.posNeg(1,-1,false));
        Assertions.assertFalse(obj.posNeg(-4,-5,false));
        Assertions.assertTrue(obj.posNeg(5,-5,false));
    }
    @Test
    void notStringT(){
        Assertions.assertEquals("not candy",obj.notString("not candy"));
        Assertions.assertEquals("not bad",obj.notString("not bad"));
        Assertions.assertEquals("not x",obj.notString("x"));
    }
    @Test
    void missingCharT(){
        Assertions.assertEquals("ktten",obj.missingChar("kitten",1));
        Assertions.assertEquals("itten",obj.missingChar("kitten",0));
        Assertions.assertEquals("i",obj.missingChar("Hi",0));
    }
    @Test
    void frontBackT(){
        Assertions.assertEquals("eodc",obj.frontBack("code"));
        Assertions.assertEquals("ehocolatC",obj.frontBack("Chocolate"));
        Assertions.assertEquals("aavJ",obj.frontBack("Java"));
    }
    @Test
    void front3T(){
        Assertions.assertEquals("JavJavJav",obj.front3("Java"));
        Assertions.assertEquals("ChoChoCho",obj.front3("Chocolate"));
        Assertions.assertEquals("aaa",obj.front3("a"));
    }
    @ParameterizedTest
    @CsvSource({
            "cat, tcatt",
            "Hello, oHelloo",
            "a,aaa"
    })
    void backgroundT(String str1, String str2){
        Assertions.assertEquals(str2,obj.backAround(str1));
    }
    @ParameterizedTest
    @CsvSource({
            "Hi, 2 ,HiHi",
            "x,4,xxxx",
            "code,2,codecode"
    })
    void strTimT(String str,int n,String res){
        Assertions.assertEquals(res,obj.stringTimes(str,n));
    }
}
