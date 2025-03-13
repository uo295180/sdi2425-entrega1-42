package com.uniovi.sdi.sdi2425entrega142.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PO_EditView extends PO_NavView{
    public static void fillEditForm(WebDriver driver, String dniNew, String nombreNew, String apellidosNew, boolean asAdmin) {
        // Esperar a que el campo DNI esté presente
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        WebElement dni = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("dni")));
        dni.click();
        dni.clear();
        dni.sendKeys(dniNew);

        // Esperar a que el campo Nombre esté presente
        WebElement nombre = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("nombre")));
        nombre.click();
        nombre.clear();
        nombre.sendKeys(nombreNew);

        // Esperar a que el campo Apellidos esté presente
        WebElement apellidos = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("apellidos")));
        apellidos.click();
        apellidos.clear();
        apellidos.sendKeys(apellidosNew);

        // Esperar a que el select de roles esté presente
        WebElement roleDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("role")));

        // Crear un objeto Select para manejar el dropdown
        Select selectRole = new Select(roleDropdown);

        // Seleccionar la opción "admin" si isAdmin es true, de lo contrario, seleccionar "estandar"
        if (asAdmin) {
            selectRole.selectByVisibleText("ROLE_ADMIN");  // Selecciona el rol "admin"
        } else {
            selectRole.selectByVisibleText("ROLE_ESTANDAR");  // Cambia esto por el rol que necesites
        }

        // Enviar el formulario
        WebElement boton = driver.findElement(By.className("btn"));
        boton.click();
    }
}
