package com.uniovi.sdi.sdi2425entrega142.pageobjects;

import com.uniovi.sdi.sdi2425entrega142.util.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PO_AddRepostajeView extends PO_NavView{

    public static void fillRegisterForm(WebDriver driver, String estacion, String price, String cantidad, String odometro) {
        // Esperar a que el campo nombre estación esté presente
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));

        WebElement nombreEstacion = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("nombreEstacion")));
        nombreEstacion.click();
        nombreEstacion.clear();
        nombreEstacion.sendKeys(estacion);

        // Esperar a que el campo precio esté presente
        WebElement precio = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("precio")));
        precio.click();
        precio.clear();
        precio.sendKeys(price);

        // Esperar a que el campo cantidad repostada esté presente
        WebElement cantidadRepostada = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("cantidadRepostada")));
        cantidadRepostada.click();
        cantidadRepostada.clear();
        cantidadRepostada.sendKeys(cantidad);


        // Esperar a que el campo cantidad repostada esté presente
        WebElement apellidos = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("cantidadRepostada")));
        apellidos.click();
        apellidos.clear();
        apellidos.sendKeys(cantidad);

        // Esperar a que el campo odometro esté presente
        WebElement odometroField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("odometro")));
        odometroField.click();
        odometroField.clear();
        odometroField.sendKeys(odometro);

        // Enviar el formulario
        WebElement boton = driver.findElement(By.className("btn"));
        boton.click();
    }
}
