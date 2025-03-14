package com.uniovi.sdi.sdi2425entrega142.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PO_PasswordChangeView extends PO_NavView{
    public static void fillPasswordForm(WebDriver driver, String actualPasswordTxt, String newPasswordTxt, String repeatPasswordTxt) {
        // Esperar a que el campo DNI esté presente
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        WebElement actualPassword = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("actualPassword")));
        actualPassword.click();
        actualPassword.clear();
        actualPassword.sendKeys(actualPasswordTxt);

        // Esperar a que el campo Nombre esté presente
        WebElement newPassword = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("newPassword")));
        newPassword.click();
        newPassword.clear();
        newPassword.sendKeys(newPasswordTxt);

        // Esperar a que el campo Apellidos esté presente
        WebElement repeatPassword = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("repeatPassword")));
        repeatPassword.click();
        repeatPassword.clear();
        repeatPassword.sendKeys(repeatPasswordTxt);

        // Enviar el formulario
        WebElement boton = driver.findElement(By.className("btn"));
        boton.click();
    }
}
