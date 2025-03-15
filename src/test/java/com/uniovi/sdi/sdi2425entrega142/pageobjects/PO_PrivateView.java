package com.uniovi.sdi.sdi2425entrega142.pageobjects;

import com.uniovi.sdi.sdi2425entrega142.util.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class PO_PrivateView extends PO_NavView{

    static public void fillFormAddVehiculo(WebDriver driver, String matriculap, String numeroBastidorp,
                                           String marcap, String modelop, String tipoCombustiblep) {
        //SeleniumUtils.waitSeconds(driver, 2);
        //Rellenemos los campos
        WebElement matricula = driver.findElement(By.name("matricula"));
        matricula.click();
        matricula.clear();
        matricula.sendKeys(matriculap);
        WebElement numeroBastidor = driver.findElement(By.name("numeroBastidor"));
        numeroBastidor.click();
        numeroBastidor.clear();
        numeroBastidor.sendKeys(numeroBastidorp);
        WebElement marca = driver.findElement(By.name("marca"));
        marca.click();
        marca.clear();
        marca.sendKeys(marcap);
        WebElement modelo = driver.findElement(By.name("modelo"));
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
