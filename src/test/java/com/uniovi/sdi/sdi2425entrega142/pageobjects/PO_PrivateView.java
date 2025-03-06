package com.uniovi.sdi.sdi2425entrega142.pageobjects;

import com.uniovi.sdi.sdi2425entrega142.util.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class PO_PrivateView extends PO_NavView{

    static public void fillFormAddMark(WebDriver driver, int userOrder, String descriptionp, String scorep) {
        SeleniumUtils.waitSeconds(driver, 5);
        new Select(driver.findElement(By.id("user"))).selectByIndex(userOrder);
//Rellenemos el campo de descripción
        WebElement description = driver.findElement(By.name("description"));
        description.clear();
        description.sendKeys(descriptionp);
        WebElement score = driver.findElement(By.name("score"));
        score.click();
        score.clear();
        score.sendKeys(scorep);
        By boton = By.className("btn");
        driver.findElement(boton).click();
    }

    static public boolean findText(WebDriver driver, String text) {
        List<WebElement> result = PO_View.checkElementBy(driver, "text", text);
        return text.equals(result.get(0).getText());
    }
}
