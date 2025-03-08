package com.uniovi.sdi.sdi2425entrega142.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PO_AddView extends PO_NavView{

    public static void fillRegisterForm(WebDriver driver, String dniValue, String nombreValue, String apellidosValue) {
        // Esperar a que el campo DNI esté presente
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        WebElement dni = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("dni")));
        dni.click();
        dni.clear();
        dni.sendKeys(dniValue);

        // Esperar a que el campo Nombre esté presente
        WebElement nombre = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("nombre")));
        nombre.click();
        nombre.clear();
        nombre.sendKeys(nombreValue);

        // Esperar a que el campo Apellidos esté presente
        WebElement apellidos = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("apellidos")));
        apellidos.click();
        apellidos.clear();
        apellidos.sendKeys(apellidosValue);

        // Enviar el formulario
        WebElement boton = driver.findElement(By.className("btn"));
        boton.click();
    }

}
