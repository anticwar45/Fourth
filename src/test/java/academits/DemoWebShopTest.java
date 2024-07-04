package academits;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DemoWebShopTest {

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
        driver.get("https://demowebshop.tricentis.com");
        driver.manage().window().maximize();
    }

    @ParameterizedTest // passed
    @CsvSource({"Laptop", "Smartphone", "Fiction"})

    public void addGoodToCart(String good) {
        driver.findElement(By.xpath("//*[@id='small-searchterms']")).sendKeys(good);
        driver.findElement(By.xpath("//*[@id='small-searchterms']")).sendKeys(Keys.RETURN);
        Assertions.assertEquals("https://demowebshop.tricentis.com/search?q=" + good, driver.getCurrentUrl());
        String item = driver.findElement(By.xpath("//*[@class='product-grid']/div[1]//h2/a")).getText();
        driver.findElement(By.xpath("//*[@class='product-grid']/div[1]//*[@value='Add to cart']")).click();
        driver.findElement(By.xpath("//*[contains(text(),'Shopping cart')]")).click();
        Assertions.assertEquals(item, driver.findElement(By.xpath("//tr[1]//a[@class='product-name']")).getText());
    }

    @Test // passed
    public void goodsQuantitiesDisplaying() {
        driver.findElement(By.xpath("//a[contains(text(), 'Books')]")).click();

        int itemsCount = driver.findElements(By.xpath("//div[@class='item-box']")).size();
        int goodsDisplaying = Integer.parseInt(driver.findElement(By.xpath("//select[@id='products-pagesize']//option[@selected='selected']")).getText());
        Assertions.assertTrue(itemsCount <= goodsDisplaying);

        driver.findElement(By.xpath("//select[@id='products-pagesize']//option[text()=4]")).click();
        itemsCount = driver.findElements(By.xpath("//div[@class='item-box']")).size();
        goodsDisplaying = Integer.parseInt(driver.findElement(By.xpath("//select[@id='products-pagesize']//option[@selected='selected']")).getText());
        Assertions.assertTrue(itemsCount <= goodsDisplaying);

        driver.findElement(By.xpath("//select[@id='products-pagesize']//option[text()=12]")).click();
        itemsCount = driver.findElements(By.xpath("//div[@class='item-box']")).size();
        goodsDisplaying = Integer.parseInt(driver.findElement(By.xpath("//select[@id='products-pagesize']//option[@selected='selected']")).getText());
        Assertions.assertTrue(itemsCount <= goodsDisplaying);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}