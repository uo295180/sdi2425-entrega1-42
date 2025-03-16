package com.uniovi.sdi.sdi2425entrega142;

import com.uniovi.sdi.sdi2425entrega142.pageobjects.*;

import com.uniovi.sdi.sdi2425entrega142.util.SeleniumUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Sdi2425Entrega142ApplicationTests {
    static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
    static String Geckodriver = "C:\\Users\\PC\\Downloads\\PL-SDI-Sesión6-material\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";
    static WebDriver driver = getDriver(PathFirefox, Geckodriver);
    static String URL = "http://localhost:8090";

    public static WebDriver getDriver(String PathFirefox, String Geckodriver) {
        System.setProperty("webdriver.firefox.bin", PathFirefox);
        System.setProperty("webdriver.gecko.driver", Geckodriver);
        driver = new FirefoxDriver();
        return driver;
    }

    @BeforeEach
    public void setUp(){
        driver.navigate().to(URL);
    }
    @AfterEach
    public void tearDown(){
        driver.manage().deleteAllCookies();
    }
    //Antes de la primera prueba
    @BeforeAll
    static public void begin() {}
    //Al finalizar la última prueba
    @AfterAll
    static public void end() {
        //Cerramos el navegador al finalizar las pruebas
        driver.quit();
    }


    @Test
    @Order(1)
    public void Prueba1() {

        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678Z", "@Dm1n1str@D0r");
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/empleado/list", currentUrl);
    }

    @Test
    @Order(2)
    public void Prueba2() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678C", "123456");
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/trayecto/list", currentUrl);
    }

    @Test
    @Order(3)
    public void Prueba3() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "", "");
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/login", currentUrl);
    }

    @Test
    @Order(4)
    public void Prueba4() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678A", "incorrecto");
        // Comprobar que se muestra el mensaje de error
        String checkText = "Usuario o contraseña inválidos. Por favor, inténtelo de nuevo";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    @Test
    @Order(5)
    public void Prueba5() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678Z", "@Dm1n1str@D0r");
        driver.navigate().to("http://localhost:8090/home");
        PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
        // When the user logs out, the app redirects him to /login?logout
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/login?logout", currentUrl);
        // When the user is not authenticated, trying to reach /home will redirect him to /login
        driver.navigate().to("http://localhost:8090/home");
        currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/login", currentUrl);
    }

    @Test
    @Order(6)
    public void Prueba6() {

        boolean isLogoutButtonRendered = driver.findElements(By.xpath("//a[@href='/logout']")).size() > 0;
        Assertions.assertFalse(isLogoutButtonRendered);

        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678Z", "@Dm1n1str@D0r");
        driver.navigate().to("http://localhost:8090/home");
        PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");

        isLogoutButtonRendered = driver.findElements(By.xpath("//a[@href='/logout']")).size() > 0;
        Assertions.assertFalse(isLogoutButtonRendered);
    }

    @Test
    @Order(7)
    public void Prueba7() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678B", "123456");

        driver.navigate().to("http://localhost:8090/empleado/add");
        PO_AddView.fillRegisterForm(driver, "33345678T", "testNombre", "testApellidos");

        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/empleado/add", currentUrl);
    }

    @Test
    @Order(8)
    public void Prueba8() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678B", "123456");

        driver.navigate().to("http://localhost:8090/empleado/add");
        PO_AddView.fillRegisterForm(driver, " ", " ", " ");

        //Errores en todos los campos
        String checkText = "El campo nombre no puede estar vacio ni con espacios.";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

        checkText = "El campo apellidos no puede estar vacio ni con espacios.";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

        checkText = "El campo dni no puede estar vacio ni con espacios.";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertTrue(result.get(0).getText().contains(checkText));

        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/empleado/add", currentUrl);
    }

    @Test
    @Order(9)
    public void Prueba9() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678B", "123456");

        driver.navigate().to("http://localhost:8090/empleado/add");
        PO_AddView.fillRegisterForm(driver, "2345", "testNombre", "testApellidos");

        //Error en el campo dni
        String checkText = "El dni no tiene el formato correcto.";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/empleado/add", currentUrl);
    }

    @Test
    @Order(10)
    public void Prueba10() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678A", "123456");

        driver.navigate().to("http://localhost:8090/empleado/add");
        PO_AddView.fillRegisterForm(driver, "12345678B", "testNombre", "testApellidos");

        //Error en el campo dni (existente)
        String checkText = "El dni ya esta registrado en el sistema.";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/empleado/add", currentUrl);
    }

    @Test
    @Order(11)
    public void Prueba11() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678Z", "@Dm1n1str@D0r");
        driver.navigate().to("http://localhost:8090/vehiculo/add");
        PO_PrivateView.fillFormAddVehiculo(driver, "1234AAB", "ppppppppppppppppp",
                "Mercedes", "A4", "Diesel");
        // Navegamos a la lista de vehículos
        driver.navigate().to("http://localhost:8090/vehiculo/list");
        boolean encontrado = false; // Creamos una flag para buscar el vehículo por todas las páginas de la vista
        while (!encontrado) {
            List<WebElement> vehiculos = driver.findElements(By.xpath("//*[contains(text(), '1234AAB')]"));
            if (!vehiculos.isEmpty()) {
                encontrado = true;
                break;
            }
            // Buscamos el enlace de las siguientes páginas si existen
            List<WebElement> nextPage = driver.findElements(By.xpath("//a[contains(@href, '?page=')]"));
            if (!nextPage.isEmpty()) {
                nextPage.get(nextPage.size() - 1).click(); // Última opción disponible
            } else {
                break; // No hay más páginas, terminamos la búsqueda
            }
        }
        Assertions.assertTrue(encontrado, "El vehículo no se encontró en la lista de vehículos.");
    }

    @Test
    @Order(12)
    public void Prueba12() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678Z", "@Dm1n1str@D0r");
        driver.navigate().to("http://localhost:8090/vehiculo/add");
        PO_PrivateView.fillFormAddVehiculo(driver, "", "",
                "", "", "Diesel");

        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/vehiculo/add", currentUrl);
    }
    @Test
    @Order(13)
    public void Prueba13() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678Z", "@Dm1n1str@D0r");
        driver.navigate().to("http://localhost:8090/vehiculo/add");
        PO_PrivateView.fillFormAddVehiculo(driver, "1234AABA", "ppppppppppppppppp",
                "Mercedes", "A4", "Diesel");
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/vehiculo/add", currentUrl);

        String checkText = PO_HomeView.getP().getString("Error.vehiculo.matricula.format",
                PO_Properties.getSPANISH());
        PO_AddVehiculo.findText(driver, checkText);
    }
    @Test
    @Order(14)
    public void Prueba14() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678Z", "@Dm1n1str@D0r");
        driver.navigate().to("http://localhost:8090/vehiculo/add");
        PO_PrivateView.fillFormAddVehiculo(driver, "1234AAB", "ppppppppp",
                "Mercedes", "A4", "Diesel");

        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/vehiculo/add", currentUrl);
    }
    @Test
    @Order(15)
    public void Prueba15() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678Z", "@Dm1n1str@D0r");
        driver.navigate().to("http://localhost:8090/vehiculo/add");
        PO_PrivateView.fillFormAddVehiculo(driver, "1234AAA", "ppppppppppppppppp",
                "Mercedes", "A4", "Diesel");

        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/vehiculo/add", currentUrl);
    }
    @Test
    @Order(16)
    public void Prueba16() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678Z", "@Dm1n1str@D0r");
        driver.navigate().to("http://localhost:8090/vehiculo/add");
        PO_PrivateView.fillFormAddVehiculo(driver, "1234AAB", "pppppppppppppppaa",
                "Mercedes", "A4", "Diesel");

        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/vehiculo/add", currentUrl);
    }

    @Test
    @Order(20)
    public void Prueba20() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678Z", "@Dm1n1str@D0r");

        driver.navigate().to("http://localhost:8090/vehiculo/list");

        // Obtener la lista de vehículos mostrados en la tabla
        List<WebElement> vehicles = driver.findElements(By.cssSelector("tbody tr"));

        // Verificar que hay al menos un vehículo en la tabla
        Assertions.assertFalse(vehicles.isEmpty());

        // Verificar que cada vehículo tiene los datos esperados (Matrícula, Bastidor, etc.)
        for (WebElement vehicleRow : vehicles) {
            List<WebElement> columns = vehicleRow.findElements(By.tagName("td"));
            Assertions.assertTrue(columns.size() >= 7); // 7 columnas
        }

        // Comprobar que el sistema de paginación existe
        List<WebElement> pagination = driver.findElements(By.cssSelector("footer"));
        Assertions.assertFalse(pagination.isEmpty());
    }

    @Test
    @Order(21)
    public void Prueba21() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678Z", "@Dm1n1str@D0r");
        driver.navigate().to("http://localhost:8090/vehiculo/list");

        List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));
        // Eliminar el primer vehículo
        WebElement checkbox = rows.get(0).findElement(By.xpath(".//input[@type='checkbox']"));
        String vehicleId = checkbox.getAttribute("value");
        checkbox.click();
        driver.findElement(By.id("deleteButton")).click();
        driver.navigate().refresh();

        // Comprobar que se ha actualizado la lista (se ha eliminado el vehículo)
        List<WebElement> updatedRows = driver.findElements(By.xpath("//table/tbody/tr"));
        boolean vehicleStillExists = updatedRows.stream()
                .anyMatch(row -> row.findElement(By.xpath(".//input[@type='checkbox']"))
                        .getAttribute("value").equals(vehicleId));

        Assertions.assertFalse(vehicleStillExists, "El vehículo aún está presente después de la eliminación");
        Assertions.assertEquals("http://localhost:8090/vehiculo/list", driver.getCurrentUrl());
    }



    @Test
    @Order(22)
    public void Prueba22() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678Z", "@Dm1n1str@D0r");
        driver.navigate().to("http://localhost:8090/vehiculo/list");

        // Navegar a la última página
        List<WebElement> paginationLinks = driver.findElements(By.xpath("//ul[@class='pagination']//a"));
        if (!paginationLinks.isEmpty()) {
            paginationLinks.get(paginationLinks.size() - 1).click(); // Hacer clic en la última página
        }

        List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));
        // Eliminar el último vehículo
        WebElement checkbox = rows.get(rows.size() - 1).findElement(By.xpath(".//input[@type='checkbox']"));
        String vehicleId = checkbox.getAttribute("value");
        checkbox.click();
        driver.findElement(By.id("deleteButton")).click();
        driver.get("http://localhost:8090/vehiculo/list");

        // Comprobar que se ha actualizado la lista (se ha eliminado el vehículo)
        List<WebElement> updatedRows = driver.findElements(By.xpath("//table/tbody/tr"));
        boolean vehicleStillExists = updatedRows.stream()
                .anyMatch(row -> row.findElement(By.xpath(".//input[@type='checkbox']"))
                        .getAttribute("value").equals(vehicleId));

        Assertions.assertFalse(vehicleStillExists, "El vehículo aún está presente después de la eliminación");
        Assertions.assertEquals("http://localhost:8090/vehiculo/list", driver.getCurrentUrl());
    }

    @Test
    @Order(23)
    public void Prueba23() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678Z", "@Dm1n1str@D0r");
        driver.navigate().to("http://localhost:8090/vehiculo/list");

        // Seleccionar los tres primeros vehículos de la lista
        List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));
        WebElement checkbox1 = rows.get(0).findElement(By.xpath(".//input[@type='checkbox']"));
        WebElement checkbox2 = rows.get(1).findElement(By.xpath(".//input[@type='checkbox']"));
        WebElement checkbox3 = rows.get(2).findElement(By.xpath(".//input[@type='checkbox']"));
        List<WebElement> checkboxes = new ArrayList<WebElement>();
        checkboxes.add(checkbox1);
        checkboxes.add(checkbox2);
        checkboxes.add(checkbox3);

        String vehicle1Id = checkbox1.getAttribute("value");
        checkbox1.click();
        String vehicle2Id = checkbox1.getAttribute("value");
        checkbox2.click();
        String vehicle3Id = checkbox1.getAttribute("value");
        checkbox3.click();

        driver.findElement(By.id("deleteButton")).click();
        driver.get("http://localhost:8090/vehiculo/list");

        // Comprobar que se ha actualizado la lista (se ha eliminado el vehículo)
        List<WebElement> updatedRows = driver.findElements(By.xpath("//table/tbody/tr"));
        boolean vehicleStillExists = updatedRows.stream()
                .anyMatch(row -> row.findElement(By.xpath(".//input[@type='checkbox']"))
                        .getAttribute("value").equals(vehicle1Id)) ||
                updatedRows.stream()
                        .anyMatch(row -> row.findElement(By.xpath(".//input[@type='checkbox']"))
                                .getAttribute("value").equals(vehicle2Id)) ||
                updatedRows.stream()
                        .anyMatch(row -> row.findElement(By.xpath(".//input[@type='checkbox']"))
                                .getAttribute("value").equals(vehicle3Id));

        Assertions.assertFalse(vehicleStillExists, "El vehículo aún está presente después de la eliminación");
        Assertions.assertEquals("http://localhost:8090/vehiculo/list", driver.getCurrentUrl());
    }
    @Test
    @Order(51)
    public void Prueba51() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678C", "123456"); // Log in como empleado
        driver.navigate().to("http://localhost:8090/incidencia/add");
        //Rellenamos el formulario
        PO_IncidenciaView.fillIncidenciaNoEsperadaForm(driver, "Vehículo averiado", "Reventó la rueda");
        //Se registra correctamente
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/incidencia/list", currentUrl);
        //Se encuentra en el listado de incidencias
        Assertions.assertTrue(PO_IncidenciaView.existeIncidenciaNoEsperada(driver, "Vehículo averiado", "Reventó la rueda"));
    }
    @Test
    @Order(52)
    public void Prueba52() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678C", "123456"); // Log in como empleado
        driver.navigate().to("http://localhost:8090/incidencia/add");
        //Rellenamos el formulario
        PO_IncidenciaView.fillIncidenciaEsperadaForm(driver, "Vehículo estropeado", "Reventó el cristal",
                "Comprar un nuevo cristal");
        //Se registra correctamente
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/incidencia/list", currentUrl);
        //Se encuentra en el listado de incidencias
        Assertions.assertTrue(PO_IncidenciaView.existeIncidenciaEsperada(driver, "Vehículo estropeado",
                "Reventó el cristal", "Comprar un nuevo cristal"));
    }
}
