package com.uniovi.sdi.sdi2425entrega142.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PO_EndTrayectoView extends PO_NavView{

    public static void fillRegisterForm(WebDriver driver, String odometro, String observaciones) {
        // Esperar a que el campo nombre estación esté presente
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));

        // Esperar a que el campo odometro esté presente
        WebElement odometroField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("odometroFin")));
        odometroField.click();
        odometroField.clear();
        odometroField.sendKeys(odometro);

        // Esperar a que el campo observaciones este presente
        WebElement observacionesField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("observaciones")));
        observacionesField.click();
        observacionesField.clear();
        observacionesField.sendKeys(observaciones);

        // Enviar el formulario
        WebElement boton = driver.findElement(By.className("btn"));
        boton.click();
    }
}
