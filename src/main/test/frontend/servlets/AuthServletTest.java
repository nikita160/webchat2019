package frontend.servlets;

import model.accountservice.AccountService;
import model.accountservice.AuthDataSet;
import model.authservice.AuthService;
import model.authservice.SimpleAuthService;
import mytest.WordGenerator;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class AuthServletTest {

    @Mock
    HttpServletRequest reqMock;
    @Mock
    HttpServletResponse respMock;
    @Mock
    AccountService accountServiceMock;
    @Mock
    AuthService authServiceMock;
    @Mock
    HttpSession httpSession;

    @InjectMocks
    AuthServlet servlet;// = new AuthServlet(accountServiceMock, authServiceMock);


    @Test
    public void testValidAuthData() throws Exception {

        servlet = new AuthServlet(accountServiceMock, authServiceMock);

        String testLogin = WordGenerator.genWord(5);
        String testPassword = WordGenerator.genWord(7);


        when(reqMock.getParameter("username")).thenReturn(testLogin);
        when(reqMock.getParameter("password")).thenReturn(testPassword);
        when(reqMock.getSession()).thenReturn(httpSession);


        when(accountServiceMock.verify(any(AuthService.class), any(AuthDataSet.class))).thenReturn(1);
        servlet.doPost(reqMock, respMock);
        verify(respMock,times(1)).sendRedirect("/webchat2019");
    }


    @Test
    public void testEmptyAuthData() throws Exception {

        servlet = new AuthServlet(accountServiceMock, authServiceMock);

        String testLogin = WordGenerator.genWord(4);
        String testPassword = WordGenerator.genWord(10);

        StringWriter testWriter = new StringWriter();

        when(reqMock.getParameter("username")).thenReturn(testLogin);
        when(reqMock.getParameter("password")).thenReturn(testPassword);
        when(reqMock.getSession()).thenReturn(httpSession);
        when(respMock.getWriter()).thenReturn(new PrintWriter(testWriter));


        when(accountServiceMock.verify(any(AuthService.class), any(AuthDataSet.class))).thenReturn(0);
        servlet.doPost(reqMock, respMock);
        verify(respMock,times(1)).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        System.out.println(testWriter.toString());
    }
}