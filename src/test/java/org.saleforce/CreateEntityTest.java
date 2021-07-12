package org.saleforce;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class CreateEntityTest {
    private WebDriver driver;
    private String pathDriver;
    private Map<String, Object> vars;
    JavascriptExecutor js;

    @BeforeEach
    public void setUp() {
        pathDriver = "C:\\Daniela\\JavaAutomationJars\\chromedriver\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", pathDriver);
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    public void login() {
        driver.get("https://login.salesforce.com/?locale=mx");
        driver.manage().window().maximize();
        driver.findElement(By.id("username")).sendKeys(vars.get("username").toString());
        driver.findElement(By.id("password")).sendKeys(vars.get("password").toString());
        driver.findElement(By.id("Login")).click();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.get("https://freeorg01com-dev-ed.lightning.force.com/lightning/o/LegalEntity/list?filterName=Recent");
    }

    @Test
    public void createSimpleEntity() {
        vars.put("username", "dsantacruz@freeorg01.com");
        vars.put("password", "Daniela123");
        vars.put("nameEntity", "New entity");
        login();
        driver.findElement(By.xpath("//li[contains(@class, \'slds-button--neutral\')]//a")).click();
        {
            WebDriverWait wait = new WebDriverWait(driver, 3);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label/span[text()=\"Name\"]/../..//input")));
        }
        driver.findElement(By.xpath("//label/span[text()=\"Name\"]/../..//input")).sendKeys(vars.get("nameEntity").toString());
        driver.findElement(By.xpath("//button[@title=\"Save\"]")).click();
        {
            WebDriverWait wait = new WebDriverWait(driver, 2);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".slds-theme--success")));
        }
        Assertions.assertEquals("success\nLegal Entity " + "\"" + vars.get("nameEntity").toString() + "\"" + " was created.\nClose", driver.findElement(By.cssSelector(".slds-theme--success")).getText());
        delete();
    }

    @Test
    public void createCompleteEntity() {
        vars.put("username", "dsantacruz@freeorg01.com");
        vars.put("password", "Daniela123");
        vars.put("nameEntity", "New complete entity");
        vars.put("CompanyName", "First company");
        vars.put("street", "S. Elm # 557");
        vars.put("description", "Blue door");
        vars.put("city", "New city");
        vars.put("state", "The state");
        vars.put("postalCode", "0023");
        vars.put("country", "Boolivia");
        login();
        driver.findElement(By.xpath("//li[contains(@class, \'slds-button--neutral\')]//a")).click();
        {
            WebDriverWait wait = new WebDriverWait(driver, 3);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label/span[text()=\"Name\"]/../..//input")));
        }
        driver.findElement(By.xpath("//label/span[text()=\"Name\"]/../..//input")).sendKeys(vars.get("nameEntity").toString());
        driver.findElement(By.xpath("//label/span[text()=\"Company Name\"]/../..//input")).sendKeys(vars.get("CompanyName").toString());
        driver.findElement(By.xpath("//div/textarea[@placeholder=\"Street\"]")).sendKeys(vars.get("street").toString());
        driver.findElement(By.xpath("//div/textarea[@class=\" textarea\"]")).sendKeys(vars.get("description").toString());
        driver.findElement(By.xpath("//label/span[text()=\"City\"]/../..//input")).sendKeys(vars.get("city").toString());
        driver.findElement(By.xpath("//label/span[text()=\"State\"]/../..//input")).sendKeys(vars.get("state").toString());
        driver.findElement(By.xpath("//label/span[text()=\"Postal Code\"]/../..//input")).sendKeys(vars.get("postalCode").toString());
        driver.findElement(By.xpath("//label/span[text()=\"Country\"]/../..//input")).sendKeys(vars.get("country").toString());
        driver.findElement(By.xpath("//a[@class=\"select\"]")).click();
        driver.findElement(By.xpath("//div[@class=\"select-options\"]//a[@title=\"Active\"]")).click();
        driver.findElement(By.xpath("//button[@title=\"Save\"]")).click();
        {
            WebDriverWait wait = new WebDriverWait(driver, 3);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".slds-theme--success")));
        }
        Assertions.assertEquals("success\nLegal Entity " + "\"" + vars.get("nameEntity").toString() + "\"" + " was created.\nClose", driver.findElement(By.cssSelector(".slds-theme--success")).getText());
        delete();
    }

    public void delete() {
        driver.get("https://freeorg01com-dev-ed.lightning.force.com/lightning/o/LegalEntity/list?filterName=Recent");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElement(By.cssSelector("tr:nth-child(1) .slds-icon_container > span:nth-child(1)")).click();
        driver.findElement(By.linkText("Delete")).click();
        driver.findElement(By.cssSelector(".uiButton--brand:nth-child(2) > .label")).click();
        driver.findElement(By.cssSelector(".profileTrigger > .uiImage")).click();
        driver.findElement(By.linkText("Log Out")).click();
    }
}
