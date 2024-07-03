package academits;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FormdesignerTest {

    WebDriver driver;

    String browser = System.getProperty("browser");

    @BeforeEach
    public void setUp() {
        if (browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equals("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }
        driver.get("https://formdesigner.ru/examples/order.html");
        driver.manage().window().maximize();
        driver.findElement(By.xpath("//*[@id=\"c-p-bn\"]")).click();
    }

    @Test // passed
    public void errorTextTest() {

        WebElement iframe = driver.findElement(By.xpath("//*[@id='form_1006']//iframe"));
        driver.switchTo().frame(iframe);

        WebElement form = driver.findElement(By.xpath("//h3[contains(text(), 'Форма оформления заказа')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", form);

        driver.findElement(By.xpath("//*[contains(text(),'Отправить')]")).click();
        Assertions.assertEquals("ФИО: field is required.", driver.findElement(By.xpath("//div[contains(@class, 'errorSummary')]//ul//li[1]")).getText());
        Assertions.assertEquals("E-mail field is required.", driver.findElement(By.xpath("//div[contains(@class, 'errorSummary')]//ul//li[2]")).getText());
        Assertions.assertEquals("Количество field is required.", driver.findElement(By.xpath("//div[contains(@class, 'errorSummary')]//ul//li[3]")).getText());
        Assertions.assertEquals("Дата доставки field is required.", driver.findElement(By.xpath("//div[contains(@class, 'errorSummary')]//ul//li[4]")).getText());
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}