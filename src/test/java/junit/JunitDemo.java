package junit;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
@Tag("UNIT")
public class JunitDemo {
    @Test
    @DisplayName("Проверка регистрации нового пользователя")
    void registrationTest1(){
        System.out.println("Регистрация нового пользователя 1");
    }

    @Test
    @DisplayName("Проверка регистрации нового пользователя")
    @Disabled("Ведется исправление фичи")
    void registrationTest2(){
        System.out.println("Регистрация нового пользователя 2");
    }

    @Test
    @DisplayName("Проверка регистрации нового пользователя")
    void registrationTest3(){
        System.out.println("Регистрация нового пользователя 3");
    }
}
