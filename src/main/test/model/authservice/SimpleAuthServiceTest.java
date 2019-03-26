package model.authservice;

import model.accountservice.AuthDataSet;
import mytest.WordGenerator;
import org.junit.Test;

import static org.junit.Assert.*;


public class SimpleAuthServiceTest {

    String loginExp = WordGenerator.genWord(5);
    String passwordExp = WordGenerator.genWord(7);

    SimpleAuthService authService = new SimpleAuthService();
    AuthDataSet expected = new AuthDataSet(loginExp, passwordExp);

    @Test
    public void testCheck () throws Exception {


        String loginCur = loginExp;
        String passwordCur = passwordExp;

        AuthDataSet current = new AuthDataSet(loginCur, passwordCur);
        assertTrue(authService.check(expected,current));

        String loginCur1 = WordGenerator.genWord(4);
        String passwordCur1 = WordGenerator.genWord(8);

        AuthDataSet current1 = new AuthDataSet(loginCur1, passwordCur1);
        assertFalse(authService.check(expected, current1));


        String loginCur2 = "";
        String passwordCur2 = "";
        AuthDataSet current2 = new AuthDataSet(loginCur2, passwordCur2);
        assertFalse(authService.check(expected, current2));

        String loginCur3 = null;
        String passwordCur3 = null;
        AuthDataSet current3 = new AuthDataSet(loginCur3, passwordCur3);
        assertFalse(authService.check(expected, current3));

        String loginCur4 = loginExp;
        String passwordCur4 = "";
        AuthDataSet current4 = new AuthDataSet(loginCur4, passwordCur4);
        assertFalse(authService.check(expected, current4));

        String loginCur5 = "";
        String passwordCur5 = passwordExp;
        AuthDataSet current5 = new AuthDataSet(loginCur5, passwordCur5);
        assertFalse(authService.check(expected, current5));



    }

}