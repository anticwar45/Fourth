package academits;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DemoQATest {

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
        driver.get("https://demoqa.com/automation-practice-form");
        driver.manage().window().maximize();
    }

    String firstName = "Sergey";
    String lastName = "Moshkov";
    String userEmail = "anticwar4@rambler.ru";
    String gender = "Male";
    String userNumber = "9133759347";
    String month = "August";
    String year = "1985";
    String date = "20";
    String subjects = "Maths";
    File file = new File("src/main/japan.jpeg");
    String filePath = file.getAbsolutePath();
    String currentAddress = "Koshurnikova street 37";
    String state = "NCR";
    String city = "Delhi";

    @Test // not found and click Sports checkbox
    public void fillForm() {
        driver.findElement(By.xpath("//*[@id='firstName']")).sendKeys(firstName);
        driver.findElement(By.xpath("//*[@id='lastName']")).sendKeys(lastName);
        driver.findElement(By.xpath("//*[@id='userEmail']")).sendKeys(userEmail);
        driver.findElement(By.xpath("//label[text()='" + gender + "']")).click();
        driver.findElement(By.xpath("//*[@id='userNumber']")).sendKeys(userNumber);
        driver.findElement(By.xpath("//*[@id='dateOfBirthInput']")).click();
        driver.findElement(By.xpath("//select//option[contains(text(), '" + month + "')]")).click();
        driver.findElement(By.xpath("//select//option[contains(text(), '" + year + "')]")).click();
        driver.findElement(By.xpath("//*[contains(@class, 'react-datepicker__day') and contains(text(), '" + date + "')]")).click();
        driver.findElement(By.xpath("//*[@id='subjectsInput']")).sendKeys(subjects);
        driver.findElement(By.xpath("//*[@id='subjectsInput']")).sendKeys(Keys.RETURN);
//        driver.findElement(By.xpath("//label[contains(text(),'Sports')]")).click();
        driver.findElement(By.xpath("//*[@id='uploadPicture']")).sendKeys(filePath);
        driver.findElement(By.xpath("//*[@id='currentAddress']")).sendKeys(currentAddress);
        driver.findElement(By.xpath("//*[@id='react-select-3-input']")).sendKeys(state);
        driver.findElement(By.xpath("//*[@id='react-select-3-input']")).sendKeys(Keys.RETURN);
        driver.findElement(By.xpath("//*[@id='react-select-4-input']")).sendKeys(city);
        driver.findElement(By.xpath("//*[@id='react-select-4-input']")).sendKeys(Keys.RETURN);
        driver.findElement(By.xpath("//*[@id=\"submit\"]")).click();

        Assertions.assertEquals(firstName + " " + lastName, driver.findElement(By.xpath("//tr[1]/td[2]")).getText());
        Assertions.assertEquals(userEmail, driver.findElement(By.xpath("//tr[2]/td[2]")).getText());
        Assertions.assertEquals(gender, driver.findElement(By.xpath("//tr[3]/td[2]")).getText());
        Assertions.assertEquals(userNumber, driver.findElement(By.xpath("//tr[4]/td[2]")).getText());
        Assertions.assertEquals(date + " " + month + "," + year, driver.findElement(By.xpath("//tr[5]/td[2]")).getText());
        Assertions.assertEquals(subjects, driver.findElement(By.xpath("//tr[6]/td[2]")).getText());
//        Assertions.assertEquals("Sports", driver.findElement(By.xpath("//tr[7]/td[2]")).getText());
        Assertions.assertEquals(file.getName(), driver.findElement(By.xpath("//tr[8]/td[2]")).getText());
        Assertions.assertEquals(currentAddress, driver.findElement(By.xpath("//tr[9]/td[2]")).getText());
        Assertions.assertEquals(state + " " + city, driver.findElement(By.xpath("//tr[10]/td[2]")).getText());
    }

    @Test // passed
    public void testingCheckbox() {
        driver.findElement(By.xpath("//label[contains(text(),'Sports')]")).click();
        WebElement sportsHobby = driver.findElement(By.xpath("//*[@id='hobbies-checkbox-1']"));
        assertEquals(sportsHobby.getAttribute("checked"), "true");
    }

    @Test // not passed
    public void testingCheckboxVisible() {
        WebElement sportsHobby = driver.findElement(By.xpath("//*[@id='hobbies-checkbox-1']"));
        Assertions.assertTrue(sportsHobby.isDisplayed());
    }

    @Test // passed
    public void assertForm() {
        WebElement firstName = driver.findElement(By.xpath("//*[@id='firstName']"));
        Assertions.assertTrue(firstName.isDisplayed());

        WebElement lastName = driver.findElement(By.xpath("//*[@id='lastName']"));
        Assertions.assertTrue(lastName.isDisplayed());

        WebElement userEmail = driver.findElement(By.xpath("//*[@id='userEmail']"));
        Assertions.assertTrue(userEmail.isDisplayed());
        assertEquals("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$", userEmail.getAttribute("pattern"));

        WebElement maleGender = driver.findElement(By.xpath("//*[@id='gender-radio-1']"));
        Assertions.assertTrue(maleGender.isEnabled());

        WebElement femaleGender = driver.findElement(By.xpath("//*[@id='gender-radio-2']"));
        Assertions.assertTrue(femaleGender.isEnabled());

        WebElement otherMale = driver.findElement(By.xpath("//*[@id='gender-radio-3']"));
        Assertions.assertTrue(otherMale.isEnabled());

        WebElement userNumber = driver.findElement(By.xpath("//*[@id='userNumber']"));
        Assertions.assertTrue(userNumber.isDisplayed());
        assertEquals("\\d*", userNumber.getAttribute("pattern"));
        assertEquals("10", userNumber.getAttribute("minlength"));
        assertEquals("10", userNumber.getAttribute("maxlength"));

        WebElement birthDate = driver.findElement(By.xpath("//*[@id='dateOfBirthInput']"));
        Assertions.assertTrue(birthDate.isDisplayed());

        WebElement subjectsInput = driver.findElement(By.xpath("//*[@id='subjectsInput']"));
        Assertions.assertTrue(subjectsInput.isDisplayed());

        WebElement sportsHobby = driver.findElement(By.xpath("//*[@id='hobbies-checkbox-1']"));
        Assertions.assertTrue(sportsHobby.isEnabled());

        WebElement readingHobby = driver.findElement(By.xpath("//*[@id='hobbies-checkbox-2']"));
        Assertions.assertTrue(readingHobby.isEnabled());

        WebElement musicHobby = driver.findElement(By.xpath("//*[@id='hobbies-checkbox-3']"));
        Assertions.assertTrue(musicHobby.isEnabled());

        WebElement uploadPicture = driver.findElement(By.xpath("//*[@id='uploadPicture']"));
        Assertions.assertTrue(uploadPicture.isDisplayed());

        WebElement currentAddress = driver.findElement(By.xpath("//*[@id='currentAddress']"));
        Assertions.assertTrue(currentAddress.isDisplayed());

        WebElement selectState = driver.findElement(By.xpath("//*[@id='react-select-3-input']"));
        Assertions.assertTrue(selectState.isDisplayed());

        WebElement selectCity = driver.findElement(By.xpath("//*[@id='react-select-4-input']"));
        Assertions.assertTrue(selectCity.isDisplayed());
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}