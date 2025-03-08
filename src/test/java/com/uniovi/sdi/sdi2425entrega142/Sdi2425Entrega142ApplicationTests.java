package com.uniovi.sdi.sdi2425entrega142;

import com.uniovi.sdi.sdi2425entrega142.pageobjects.*;
import com.uniovi.sdi.sdi2425entrega142.util.SeleniumUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Sdi2425Entrega142ApplicationTests {
    //static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
    //static String Geckodriver = "C:\\Dev\\tools\\selenium\\geckodriver-v0.30.0-win64.exe";


    static String Geckodriver = "/Users/fer/selenium/geckodriver-v0.30.0-macos";
    static String PathFirefox = "/Applications/Firefox.app/Contents/MacOS/firefox";

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


}
