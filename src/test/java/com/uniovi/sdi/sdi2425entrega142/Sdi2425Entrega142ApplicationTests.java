package com.uniovi.sdi.sdi2425entrega142;

import com.uniovi.sdi.sdi2425entrega142.pageobjects.PO_HomeView;
import com.uniovi.sdi.sdi2425entrega142.pageobjects.PO_LoginView;
import com.uniovi.sdi.sdi2425entrega142.pageobjects.PO_View;
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
