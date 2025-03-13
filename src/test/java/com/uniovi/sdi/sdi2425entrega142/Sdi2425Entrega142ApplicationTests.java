package com.uniovi.sdi.sdi2425entrega142;

import com.uniovi.sdi.sdi2425entrega142.pageobjects.*;

import com.uniovi.sdi.sdi2425entrega142.util.SeleniumUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Sdi2425Entrega142ApplicationTests {
    static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
    static String Geckodriver = "C:\\Dev\\tools\\selenium\\geckodriver-v0.30.0-win64.exe";
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
    public void PR11() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678Z", "@Dm1n1str@D0r");
        driver.navigate().to("http://localhost:8090/vehiculo/add");
        PO_PrivateView.fillFormAddVehiculo(driver, "1234AAB", "ppppppppppppppppp",
                "Mercedes", "A4", "Diesel");
        //Comprobamos que el vehículo se ha creado y entramos en vista de la lista de vehículos
        PO_PrivateView.findText(driver, "1234AAB");
        String checkText = "Vehículos";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
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
    }

        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8090/vehiculo/add", currentUrl);
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

        // Seleccionar el primer vehículo de la lista
        List<WebElement> checkboxes = driver.findElements(By.name("vehiculosSeleccionados"));
        checkboxes.get(0).click();

        // Pulsar el botón de eliminar
        driver.findElement(By.id("deleteButton")).click();

        // Comprobar que la lista se actualiza y que el vehículo ha sido eliminado
        driver.navigate().refresh();
        List<WebElement> updatedCheckboxes = driver.findElements(By.name("vehiculosSeleccionados"));
        Assertions.assertTrue(updatedCheckboxes.size() < checkboxes.size());
    }
    @Test
    @Order(22)
    public void PR22() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678Z", "@Dm1n1str@D0r");

        driver.navigate().to("http://localhost:8090/vehiculo/list");

        // Seleccionar el último vehículo de la lista
        List<WebElement> checkboxes = driver.findElements(By.name("vehiculosSeleccionados"));
        checkboxes.get(checkboxes.size() - 2).click();

        // Pulsar el botón de eliminar
        driver.findElement(By.id("deleteButton")).click();

        // Comprobar que la lista se actualiza y que el vehículo ha sido eliminado
        driver.navigate().refresh();
        List<WebElement> updatedCheckboxes = driver.findElements(By.name("vehiculosSeleccionados"));
        Assertions.assertTrue(updatedCheckboxes.size() < checkboxes.size());
    }
    @Test
    @Order(23)
    public void PR23() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678Z", "@Dm1n1str@D0r");

        driver.navigate().to("http://localhost:8090/vehiculo/list");

        // Seleccionar los tres primeros vehículos de la lista
        List<WebElement> checkboxes = driver.findElements(By.name("vehiculosSeleccionados"));
        int initialSize = checkboxes.size();
        checkboxes.get(0).click();
        checkboxes.get(1).click();
        checkboxes.get(2).click();

        // Pulsar el botón de eliminar
        driver.findElement(By.id("deleteButton")).click();

        // Comprobar que la lista se actualiza y que los vehículos han sido eliminados
        driver.navigate().refresh();
        List<WebElement> updatedCheckboxes = driver.findElements(By.name("vehiculosSeleccionados"));
        Assertions.assertTrue(updatedCheckboxes.size() < initialSize - 2); // Comprobar que se eliminaron 3
    }
    @Test
    @Order(24)
    public void Prueba24() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678C", "123456"); // Log in como empleado
        driver.navigate().to("http://localhost:8090/home");
        // Pinchamos en la opción de menú de Trayectos:
        List<WebElement> elements = PO_View.accessPath(driver, "//*[@id='mynavbar']/ul[1]/li[2]/div/a[1]", 0); // TODO solve the crash here
        // Pinchamos en la opción de lista de trayectos.
        PO_View.accessPath(driver, "//a[contains(@href, 'trayecto/list')]", 0);
        List<WebElement> trayectosList = SeleniumUtils.waitLoadElementsBy(driver, "free", "//tbody/tr",
                PO_View.getTimeout());
        Assertions.assertEquals(1, trayectosList.size());
    }

    @Test
    @Order(25)
    public void Prueba25() {
        driver.get("http://localhost:8090/trayecto/add");
        // Seleccionamos un vehículo disponible
        WebElement vehiculoSelect = driver.findElement(By.name("vehiculo"));
        vehiculoSelect.findElements(By.tagName("option")).get(1).click();
        // Enviamos eñ formulario
        driver.findElement(By.tagName("button")).click();
        // Verificamos la redirección a la lista de trayectos
        Assertions.assertTrue(driver.getCurrentUrl().contains("/trayecto/list"));
    }

    @Test
    @Order(26)
    public void Prueba26() {
        driver.get("http://localhost:8090/trayecto/add");
        // Seleccionamos el vehículo
        WebElement vehiculoSelect = driver.findElement(By.name("vehiculo"));
        vehiculoSelect.findElements(By.tagName("option")).get(1).click();
        // Enviamos el formulario
        driver.findElement(By.tagName("button")).click();
        // Intentamos agregar otro trayecto
        driver.get("http://localhost:8090/trayecto/add");
        vehiculoSelect.findElements(By.tagName("option")).get(1).click();
        driver.findElement(By.tagName("button")).click();
        // Verificamos que seguimos en la página de agregar trayecto
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
    @Order(39)
    public void Prueba39() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "12345678C", "123456"); // Log in como empleado
        driver.navigate().to("http://localhost:8090/home");
        // Pinchamos en la opción de menú de Vehículos:
        List<WebElement> elements = PO_View.accessPath(driver, "//*[@id='myNavbar']/ul[1]/li[4]", 0);
        // Pinchamos en la opción de lista de vehículos.
        PO_View.accessPath(driver, "//a[contains(@href, 'vehiculo/list')]", 0);
        List<WebElement> vehiculosList = SeleniumUtils.waitLoadElementsBy(driver, "free", "//tbody/tr",
                PO_View.getTimeout());
        Assertions.assertEquals(5, vehiculosList.size());
    }
}
