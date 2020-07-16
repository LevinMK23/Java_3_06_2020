package homework.customTest;

import homework.customTest.module.After;
import homework.customTest.module.Before;
import homework.customTest.module.Test;
import homework.utils.Logger;

import java.util.Arrays;
import java.util.Objects;


public class TestWorker {
    private Logger log = new Logger(homework.jUnit.Test.class);
    private final int[] data = { 41, 15 };
    private TestUtil util;

    public TestWorker() {
        this.util = new TestUtil(data);;
    }

    @Before
    public void start() {
        log.appInfo("start", "Тест начинается");
        log.appInfo("start", "Входные параметры: " + Arrays.toString(data));
    }

    @Test()
    public void sumTest() {
        log.appInfo("sumTest", "Тест операции сложение");
        int sum = util.sum();
        isSuccessful(Objects.equals(sum, 56));
    }

    @Test(level = 2)
    public void subTest() {
        log.appInfo("subTest", "Тест операции вычитание");
        int sub = util.sub();
        isSuccessful(Objects.equals(sub, 13));
    }

    @Test(level = 7)
    public void mulTest() {
        log.appInfo("mulTest", "Тест операции умножение");
        int mul = util.mul();
        isSuccessful(Objects.equals(mul, 45));
    }

    @Test(level = 2)
    public void divTest(){
        log.appInfo("divTest", "Тест операции деление");
        int div = util.div();
        isSuccessful(Objects.equals(div, 4));
    }

    @Test(level = 5)
    public void modTest() {
        log.appInfo("modTest", "Тест операции вычисление остатка от деления");
        int mod = util.mod();
        isSuccessful(Objects.equals(mod, 11));
    }

    @After
    public void end() {
        log.appInfo("end", "Тесты завершены");
    }

    private void isSuccessful(boolean isSuccessful) {
        if (isSuccessful) {
            log.appInfo("isSuccessful", "Тестирование успешно");
        } else {
            log.appError("isSuccessful", "Тестирование неудачно");
        }
    }
}
