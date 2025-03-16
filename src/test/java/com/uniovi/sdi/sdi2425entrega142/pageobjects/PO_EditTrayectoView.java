package com.uniovi.sdi.sdi2425entrega142.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PO_EditTrayectoView extends PO_NavView {

    public static void fillEditForm(WebDriver driver, String fechaInicio, String fechaFinal, String odometroI,
                                    String odometroF, String obs) {
        // Esperar a que el campo fechaInicioTrayecto esté presente
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        WebElement fechaInicioTrayecto = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("fechaInicioTrayecto")));
        fechaInicioTrayecto.click();
        fechaInicioTrayecto.clear();
        fechaInicioTrayecto.sendKeys(fechaInicio);

        // Esperar a que el campo fechaFinTrayecto esté presente
        WebElement fechaFinTrayecto = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("fechaFinTrayecto")));
        fechaFinTrayecto.click();
        fechaFinTrayecto.clear();
        fechaFinTrayecto.sendKeys(fechaFinal);

        // Esperar a que el campo odometroInicio esté presente
        WebElement odometroInicio = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("odometroInicio")));
        odometroInicio.click();
        odometroInicio.clear();
        odometroInicio.sendKeys(odometroI);

        // Esperar a que el campo odometroFin esté presente
        WebElement odometroFin = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("odometroFin")));
        odometroFin.click();
        odometroFin.clear();
        odometroFin.sendKeys(odometroF);

        // Esperar a que el campo observaciones esté presente
        WebElement observaciones = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("observaciones")));
        observaciones.click();
        observaciones.clear();
        observaciones.sendKeys(obs);

        // Enviar el formulario
        WebElement boton = driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/button"));
        boton.click();
    }
}
