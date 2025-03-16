package com.uniovi.sdi.sdi2425entrega142.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PO_IncidenciaView extends PO_NavView{

    public static void fillIncidenciaNoEsperadaForm(WebDriver driver, String titulop, String descripcionp) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement titulo = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("titulo")));
        titulo.click();
        titulo.clear();
        titulo.sendKeys(titulop);

        WebElement descripcion = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("descripcion")));
        descripcion.click();
        descripcion.clear();
        descripcion.sendKeys(descripcionp);

        Select tipo = new Select(driver.findElement(By.name("tipo")));
        tipo.selectByVisibleText("Respuesta No Esperada");

        // Enviar el formulario
        WebElement boton = driver.findElement(By.className("btn"));
        boton.click();
    }
    public static void fillIncidenciaEsperadaForm(WebDriver driver, String titulop, String descripcionp, String respuestap) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement titulo = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("titulo")));
        titulo.click();
        titulo.clear();
        titulo.sendKeys(titulop);

        WebElement descripcion = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("descripcion")));
        descripcion.click();
        descripcion.clear();
        descripcion.sendKeys(descripcionp);

        Select tipo = new Select(driver.findElement(By.name("tipo")));
        tipo.selectByVisibleText("Respuesta Esperada");

        WebElement respuesta = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("respuesta")));
        respuesta.click();
        respuesta.clear();
        respuesta.sendKeys(respuestap);

        // Enviar el formulario
        WebElement boton = driver.findElement(By.className("btn"));
        boton.click();
    }
    public static boolean existeIncidenciaNoEsperada(WebDriver driver, String titulop) {
        List<WebElement> filas = driver.findElements(By.cssSelector("tbody tr"));

        return filas.stream().anyMatch(fila ->
                fila.getText().contains(titulop)
        );
    }
    public static boolean existeIncidenciaEsperada(WebDriver driver, String titulop) {
        WebElement segundoTbody = driver.findElement(By.xpath("(//tbody)[2]"));
        List<WebElement> filas = segundoTbody.findElements(By.tagName("tr"));

        return filas.stream().anyMatch(fila ->
                fila.getText().contains(titulop)
        );
    }
}
