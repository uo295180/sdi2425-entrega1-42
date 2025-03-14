package com.uniovi.sdi.sdi2425entrega142.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_LoginView extends PO_NavView{

    static public void fillLoginForm(WebDriver driver, String dnip, String password) {
        WebElement dni = driver.findElement(By.name("username"));
        dni.click();
        dni.clear();
        dni.sendKeys(dnip);
        WebElement pwd = driver.findElement(By.name("password"));
        pwd.click();
        pwd.clear();
        pwd.sendKeys(password);
        By boton = By.className("btn");
        driver.findElement(boton).click();
    }

    static public boolean login(WebDriver driver, String dnip, String password, String textToVerify) {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        fillLoginForm(driver, dnip, password);
        return PO_PrivateView.findText(driver, textToVerify);
    }

    static public void logout(WebDriver driver) {
        String loginText = PO_HomeView.getP().getString("signup.message", PO_Properties.getSPANISH());
        PO_PrivateView.clickOption(driver, "logout", "text", loginText);
    }
}
