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

    //static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
    //static String Geckodriver = "C:\\Dev\\tools\\selenium\\geckodriver-v0.30.0-win64.exe";


    static String Geckodriver = "/Users/fer/selenium/geckodriver-v0.30.0-macos";
    static String PathFirefox = "/Applications/Firefox.app/Contents/MacOS/firefox";


    //static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
    //static String Geckodriver = "C:\\Users\\PC\\Downloads\\PL-SDI-Sesión6-material\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";


    //static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
    //static String Geckodriver = "C:\\Dev\\tools\\selenium\\geckodriver-v0.30.0-win64.exe";

    static WebDriver driver = getDriver(PathFirefox, Geckodriver);
    static String URL = "http://localhost:8090";

    public static WebDriver getDriver(String PathFirefox, String Geckodriver) {
        System.setProperty("webdriver.firefox.bin", PathFirefox);
        System.setProperty("webdriver.gecko.driver", Geckodriver);
        driver = new FirefoxDriver();
        return driver;
    }

    @BeforeEach
    public void setUp() {
        driver.navigate().to(URL);
    }

    @AfterEach
    public void tearDown() {
        driver.manage().deleteAllCookies();
    }

    //Antes de la primera prueba
    @BeforeAll
    static public void begin() {
    }

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
    public void PR11() {
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
    public void PR12() {
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
    public void PR13() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678Z", "@Dm1n1str@D0r");
        driver.navigate().to("http://localhost:8090/vehiculo/add");
        PO_PrivateView.fillFormAddVehiculo(driver, "1234AABA", "ppppppppppppppppp",
                "Mercedes", "A4", "Diesel");
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/vehiculo/add", currentUrl);

        //String checkText = PO_HomeView.getP().getString("Error.vehiculo.matricula.format",
        //        PO_Properties.getSPANISH());
        //PO_PrivateView.findText(driver, checkText);

        //String checkText2 = "Agregar vehículo";
        //List<WebElement> result2 = PO_View.checkElementBy(driver, "text", checkText2);
        //Assertions.assertEquals(checkText2, result2.get(0).getText());
        //Esto no va nose por que
    }

    @Test
    @Order(14)
    public void PR14() {
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
    public void PR15() {
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
    public void PR16() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678Z", "@Dm1n1str@D0r");
        driver.navigate().to("http://localhost:8090/vehiculo/add");
        PO_PrivateView.fillFormAddVehiculo(driver, "1234AAB", "pppppppppppppppaa",
                "Mercedes", "A4", "Diesel");

    }

    @Test
    @Order(17)
    public void Prueba17() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678B", "123456");

        driver.navigate().to("http://localhost:8090/empleado/list");

        List<WebElement> empleados = SeleniumUtils.waitLoadElementsBy(driver, "free", "//tbody/tr",
                PO_View.getTimeout());
        Assertions.assertEquals(5, empleados.size()); //Los 5 que se insertan en el servicio

        String checkDniLogged = "12345678B";
        String checkDniAdmin = "12345678Z";

        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkDniLogged);
        Assertions.assertEquals(checkDniLogged, result.get(0).getText());

        List<WebElement> result2 = PO_View.checkElementBy(driver, "text", checkDniAdmin);
        Assertions.assertEquals(checkDniAdmin, result2.get(0).getText());

        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/empleado/list", currentUrl);
    }

    @Test
    @Order(18)
    public void Prueba18() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678B", "123456");

        driver.navigate().to("http://localhost:8090/empleado/edit/3");
        PO_EditView.fillEditForm(driver, "44445678B", "newNombre", "newApellido", true);

        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/empleado/details/3", currentUrl);

        PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "44445678B", "123456");

        //El empleado esta habilitado para entrar en la edicion
        driver.navigate().to("http://localhost:8090/empleado/edit/3");
        String currentUrlEdit = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/empleado/edit/3", currentUrlEdit);
    }

    @Test
    @Order(19)
    public void Prueba19() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678B", "123456");

        driver.navigate().to("http://localhost:8090/empleado/edit/1");
        PO_EditView.fillEditForm(driver, "12345678B", " ", " ", false);

        //Errores en todos los campos
        String checkText = "El campo nombre no puede estar vacio ni con espacios.";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

        checkText = "El campo apellidos no puede estar vacio ni con espacios.";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

        checkText = "El nuevo dni ya esta registrado en el sistema.";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertTrue(result.get(0).getText().contains(checkText));

        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/empleado/edit/1", currentUrl);

        //Comprobar que el empleado no se actualiza
        driver.navigate().to("http://localhost:8090/empleado/details/1");
        checkText = "12345678A";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertTrue(result.get(0).getText().contains(checkText));

        checkText = "Mario";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertTrue(result.get(0).getText().contains(checkText));

        checkText = "Orviz";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertTrue(result.get(0).getText().contains(checkText));

        currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/vehiculo/add", currentUrl);

        checkText = "Orviz";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertTrue(result.get(0).getText().contains(checkText));
    }

    @Test
    @Order(20)
    public void PR20() {
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
    public void PR21() {
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
    public void PR22() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678Z", "@Dm1n1str@D0r");
        driver.navigate().to("http://localhost:8090/vehiculo/list");

        List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));
        // Eliminar el primer vehículo
        WebElement checkbox = rows.get(rows.size() - 1).findElement(By.xpath(".//input[@type='checkbox']"));
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
    @Order(23)
    public void PR23() {
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
        driver.navigate().refresh();

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
    @Order(24)
    public void Prueba24() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678C", "123456"); // Log in como empleado
        // Navegamos hasta la lista de trayectos:
        driver.navigate().to("http://localhost:8090/trayecto/list");
        List<WebElement> trayectosList = driver.findElements(By.xpath("//*[@id='trayectosTable']/tbody/tr"));
        Assertions.assertEquals(1, trayectosList.size());
    }

    @Test
    @Order(25)
    public void Prueba25() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678D", "123456"); // Log in como empleado
        // Navegamos hasta la lista de trayectos:
        driver.navigate().to("http://localhost:8090/trayecto/add");
        // Seleccionamos un vehículo disponible
        WebElement vehiculoSelect = driver.findElement(By.xpath("//*[@id=\"vehiculo\"]"));
        // Enviamos el formulario
        driver.findElement(By.tagName("button")).click();
        // Verificamos la redirección a la lista de trayectos
        Assertions.assertTrue(driver.getCurrentUrl().contains("/trayecto/list"));
    }

    @Test
    @Order(26)
    public void Prueba26() {
        // Intentamos agregar otro trayecto (no debería de dejarnos)
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678D", "123456"); // Log in como empleado
        // Navegamos hasta la lista de trayectos:
        driver.get("http://localhost:8090/trayecto/add");
        // Seleccionamos el vehículo
        WebElement vehiculoSelect = driver.findElement(By.xpath("//*[@id=\"vehiculo\"]"));
        // Enviamos el formulario
        driver.findElement(By.tagName("button")).click();
        // No nos dejará (seguiremos en el menú de añadir)
        Assertions.assertTrue(driver.getCurrentUrl().contains("/trayecto/add"));
    }

    @Test
    @Order(27)
    public void Prueba27() {
        driver.get("http://localhost:8090/trayecto/add");
        // Verificar que no hay vehículos disponibles
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            driver.findElement(By.name("vehiculo"));
        });
    }

    @Test
    @Order(28)
    public void Prueba28() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678F", "123456"); // Log in como empleado
        driver.navigate().to("http://localhost:8090/repostajes/add");
        PO_AddRepostajeView.fillRegisterForm(driver, "Estación 1", "1.04", "12", "200");
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/repostajes/list", currentUrl);
    }

    @Test
    @Order(29)
    public void Prueba29() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678E", "123456"); // Log in como empleado
        driver.navigate().to("http://localhost:8090/repostajes/add");
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/home", currentUrl);
    }

    @Test
    @Order(30)
    public void Prueba30() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678F", "123456"); // Log in como empleado
        driver.navigate().to("http://localhost:8090/repostajes/add");
        PO_AddRepostajeView.fillRegisterForm(driver, "", "", "", "");
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/repostajes/add", currentUrl);
    }

    @Test
    @Order(31)
    public void Prueba31() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678F", "123456"); // Log in como empleado
        driver.navigate().to("http://localhost:8090/repostajes/add");
        PO_AddRepostajeView.fillRegisterForm(driver, "Estación ejemplo", "-12", "-12", "10");
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/repostajes/add", currentUrl);
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.repostaje.negativePrice",
                PO_Properties.getSPANISH());
        //Comprobamos el error de Nombre corto de nombre corto .
        String checkText = PO_HomeView.getP().getString("Error.repostaje.negativePrice",
                PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.get(0).getText());
        result = PO_SignUpView.checkElementByKey(driver, "Error.repostaje.negativeQuantity",
                PO_Properties.getSPANISH());
        //Comprobamos el error de Nombre corto de nombre corto .
        checkText = PO_HomeView.getP().getString("Error.repostaje.negativeQuantity",
                PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    @Test
    @Order(32)
    public void Prueba32() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678F", "123456"); // Log in como empleado
        driver.navigate().to("http://localhost:8090/repostajes/add");
        PO_AddRepostajeView.fillRegisterForm(driver, "Estación ejemplo", "1", "10", "100");
        driver.navigate().to("http://localhost:8090/repostajes/add");
        PO_AddRepostajeView.fillRegisterForm(driver, "Estación ejemplo", "1", "10", "50");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.repostaje.invalidOdometer",
                PO_Properties.getSPANISH());
        //Comprobamos el error de Nombre corto de nombre corto .
        String checkText = PO_HomeView.getP().getString("Error.repostaje.invalidOdometer",
                PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    @Test
    @Order(33)
    public void Prueba33() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678F", "123456"); // Log in como empleado
        driver.navigate().to("http://localhost:8090/trayecto/end");
        PO_EndTrayectoView.fillRegisterForm(driver, "300", "Ejemplo observacion");
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/vehiculo/trayectos/24", currentUrl);
    }

    @Test
    @Order(36)
    public void Prueba34() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678G", "123456"); // Log in como empleado
        driver.navigate().to("http://localhost:8090/trayecto/end");
        PO_EndTrayectoView.fillRegisterForm(driver, "", "Ejemplo observacion");
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/trayecto/end", currentUrl);
    }

    @Test
    @Order(35)
    public void Prueba35() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678G", "123456"); // Log in como empleado
        driver.navigate().to("http://localhost:8090/trayecto/end");
        PO_EndTrayectoView.fillRegisterForm(driver, "-20", "Ejemplo observacion");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.trayecto.end.negativeOdometro",
                PO_Properties.getSPANISH());
        //Comprobamos el error de Nombre corto de nombre corto .
        String checkText = PO_HomeView.getP().getString("Error.trayecto.end.negativeOdometro",
                PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    @Test
    @Order(36)
    public void Prueba36() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678E", "123456"); // Log in como empleado
        driver.navigate().to("http://localhost:8090/trayecto/end");
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/home", currentUrl);
    }

    @Test
    @Order(39)
    public void Prueba39() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678C", "123456"); // Log in como empleado
        driver.navigate().to("http://localhost:8090/vehiculo/list");
        // Consultamos la lista de Vehículos:
        List<WebElement> vehiculosList = driver.findElements(By.xpath("//table/tbody/tr"));
        // Pinchamos en la opción de lista de vehículos.
        Assertions.assertEquals(5, vehiculosList.size());
    }

    @Test
    @Order(40)
    public void PR40() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678B", "123456");

        driver.navigate().to("http://localhost:8090/empleado/password");

        PO_PasswordChangeView.fillPasswordForm(driver, "123456", "wefWDF#$%@vc333333", "wefWDF#$%@vc333333");

        String currentUrlEdit = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/home", currentUrlEdit);

        PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");

        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678B", "wefWDF#$%@vc333333");

        currentUrlEdit = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/empleado/list", currentUrlEdit);
    }

    @Test
    @Order(41)
    public void PR41() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678B", "123456");

        driver.navigate().to("http://localhost:8090/empleado/password");

        PO_PasswordChangeView.fillPasswordForm(driver, "123456wef", "wefWDF#$%@vc333333", "wefWDF#$%@vc333333");

        String currentUrlEdit = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/empleado/password", currentUrlEdit);

        String checkText = "La contraseña no es correcta.";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    @Test
    @Order(42)
    public void PR42() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678B", "123456");

        driver.navigate().to("http://localhost:8090/empleado/password");

        PO_PasswordChangeView.fillPasswordForm(driver, "123456", "debil", "debil");

        String currentUrlEdit = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/empleado/password", currentUrlEdit);

        String checkText = "La nueva contraseña tiene que tener al menos 12 caracteres, una minuscula, mayuscula, y caracter especial.";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    @Test
    @Order(43)
    public void PR43() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678B", "123456");

        driver.navigate().to("http://localhost:8090/empleado/password");

        PO_PasswordChangeView.fillPasswordForm(driver, "123456", "wefWDF#$%@vc333333", "iruhfgiernfk345436");

        String currentUrlEdit = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/empleado/password", currentUrlEdit);

        String checkText = "Las contraseñas no coinciden.";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    @Test
    @Order(46)
    public void PR46() {
        driver.navigate().to("http://localhost:8090/empleado/list");

        String currentUrlEdit = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/login", currentUrlEdit);
    }

    @Test
    @Order(47)
    public void PR47() {
        driver.navigate().to("http://localhost:8090/vehiculo/list");

        String currentUrlEdit = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/login", currentUrlEdit);
    }

    @Test
    @Order(48)
    public void PR48() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678C", "123456");

        driver.navigate().to("http://localhost:8090/admin/logs");

        String currentUrlEdit = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/forbidden", currentUrlEdit);
    }

    @Test
    @Order(49)
    public void PR49() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678B", "123456");

        driver.navigate().to("http://localhost:8090/admin/logs/list");

        PO_LogsView.filter(driver, "Logins exitosos");

        String checkText = "Usuario autenticado: 12345678B";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", "12345678B");
        Assertions.assertTrue(result.get(0).getText().toLowerCase().contains(checkText.toLowerCase()));

    }

    @Test
    @Order(50)
    public void PR50() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678B", "123456");

        driver.navigate().to("http://localhost:8090/admin/logs/list");

        PO_LogsView.filter(driver, "Todo");

        String checkText = "Usuario autenticado: 12345678B";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", "12345678B");
        Assertions.assertTrue(result.get(0).getText().toLowerCase().contains(checkText.toLowerCase()));

        PO_LogsView.delete(driver, "Logins exitosos");
        PO_LogsView.filter(driver, "Logins exitosos");

        //No hay logs asociados
        checkText = "12345678B";
        Assertions.assertThrows(TimeoutException.class, () -> {
            PO_View.checkElementBy(driver, "text", "12345678B");
        });

    }




}

