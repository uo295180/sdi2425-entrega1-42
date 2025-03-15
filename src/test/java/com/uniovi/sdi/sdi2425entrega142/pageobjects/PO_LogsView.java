package com.uniovi.sdi.sdi2425entrega142.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PO_LogsView extends PO_NavView{
    public static void filter(WebDriver driver, String tipoText) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));

        WebElement tipoDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("filtrarDropdown")));
        // Crear un objeto Select para manejar el dropdown
        Select selectTipo = new Select(tipoDropdown);
        selectTipo.selectByVisibleText(tipoText);

        // Enviar el formulario
        WebElement boton = driver.findElement(By.id("filtrarButton"));
        boton.click();
    }

    public static void delete(WebDriver driver, String tipoText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));

        WebElement tipoDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("borrarDropdown")));
        // Crear un objeto Select para manejar el dropdown
        Select selectTipo = new Select(tipoDropdown);
        selectTipo.selectByVisibleText(tipoText);

        // Enviar el formulario
        WebElement boton = driver.findElement(By.id("borrarButton"));
        boton.click();
    }
}
