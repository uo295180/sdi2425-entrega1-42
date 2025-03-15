package com.uniovi.sdi.sdi2425entrega142.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PO_AddVehiculo extends PO_NavView{

    static public void fillFormAddVehiculo(WebDriver driver, String matriculap, String numeroBastidorp,
                                           String marcap, String modelop, String tipoCombustiblep) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        //Rellenemos los campos
        WebElement matricula = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("matricula")));
        matricula.click();
        matricula.clear();
        matricula.sendKeys(matriculap);
        WebElement numeroBastidor = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("numeroBastidor")));
        numeroBastidor.click();
        numeroBastidor.clear();
        numeroBastidor.sendKeys(numeroBastidorp);
        WebElement marca = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("marca")));
        marca.click();
        marca.clear();
        marca.sendKeys(marcap);
        WebElement modelo = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("modelo")));
        modelo.click();
        modelo.clear();
        modelo.sendKeys(modelop);
        // Seleccionar el tipo de combustible
        Select tipoCombustible = new Select(driver.findElement(By.name("tipoCombustible")));
        tipoCombustible.selectByVisibleText(tipoCombustiblep); // Selecciona el valor del tipo de combustible
        By boton = By.className("btn");
        driver.findElement(boton).click();
    }

    static public boolean findText(WebDriver driver, String text) {
        List<WebElement> result = PO_View.checkElementBy(driver, "text", text);
        return text.equals(result.get(0).getText());
    }
}
