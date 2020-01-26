import org.junit.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;

public class AutoTest {
    private WebDriver driver;
    String browserType;

    @BeforeEach
    public void testInitialize() {
        browserType = System.getenv("selenium.browser.type");
        if (browserType == null) {
            browserType = "chrome";
        }
        if (browserType.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
            driver = new ChromeDriver();
        } else if (browserType.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", "drivers/geckodriver.exe");
            driver = new FirefoxDriver();
        } else if (browserType.equalsIgnoreCase("internet explorer")) {
            System.setProperty("webdriver.ie.driver", "drivers/IEDriverServer.exe");
            driver = new InternetExplorerDriver();
        } else {
            System.out.println("Not supported browser type");
        }

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @ParameterizedTest
    @CsvSource( {"Ivanov, Ivan, 30012008", "Petrov, Petr, 20051997", "Sidorova, Anna, 2007"})
    public void test(String surname, String name, String birthDate) {
        driver.navigate().to("http://www.sberbank.ru/ru/person");
        WebDriverWait wait = new WebDriverWait(driver, 30);
        driver.findElement(By.xpath("//span[.='Страхование']"));
        new Actions(driver).
                moveToElement(driver.findElement(By.xpath("//span[.='Страхование']"))).perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='submenu-5']")));
        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//li[@class ='lg-menu__sub-item']//a[.='Страхование путешественников']")));
        new Actions(driver).
                moveToElement(driver.findElement(By.xpath("//li[@class ='lg-menu__sub-item']//a[.='Страхование путешественников']"))).perform();
        driver.findElement(By.xpath("//li[@class ='lg-menu__sub-item']//a[.='Страхование путешественников']")).click();

        if (driver.findElement(By.xpath("//div[@class='cookie-warning cookie-warning_show']")).isDisplayed()) {
            driver.findElement(By.xpath("//a[@class='cookie-warning__close']")).click();
        }
        WebElement button = driver.findElement(By.xpath("//b[.='Оформить онлайн']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
        button.click();
        driver.findElement(By.xpath("//div[@class='online-card-program']//h3[contains(text(), 'Минимальная')]")).click();
        Assert.assertTrue(driver.findElement
                (By.xpath("//div[@class='online-card-program selected']//div[@class='selected-icon']")).isDisplayed());
        button = driver.findElement(By.xpath("//button[@class='btn btn-primary btn-large']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
        button.click();

        WebElement surnameInput = driver
                .findElement(By.xpath("//input[@id='surname_vzr_ins_0']"));
        WebElement nameInput = driver
                .findElement(By.xpath("//input[@id='name_vzr_ins_0']"));
        WebElement birthDateInput = driver
                .findElement(By.xpath("//input[@id='birthDate_vzr_ins_0']"));
        surnameInput.click();
        surnameInput.sendKeys(surname);
        nameInput.click();
        nameInput.sendKeys(name);
        birthDateInput.click();
        birthDateInput.sendKeys(birthDate);
        birthDateInput.sendKeys(Keys.RETURN);

        WebElement title = driver.findElement(By.xpath("//legend[contains(text(), 'Страхователь')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", title);

        WebElement surnameIns = driver.findElement(By.xpath("//input[@id='person_lastName']"));
        surnameIns.click();
        surnameIns.sendKeys("Иванов");
        Assert.assertEquals(surnameIns.getAttribute("value"),"Иванов");

        WebElement nameIns = driver.findElement(By.xpath("//input[@id='person_firstName']"));
        nameIns.click();
        nameIns.sendKeys("Иван");
        Assert.assertEquals(nameIns.getAttribute("value"),"Иван");

        WebElement middleName = driver.findElement(By.xpath("//input[@id='person_middleName']"));
        middleName.click();
        middleName.sendKeys("Иванович");
        Assert.assertEquals(middleName.getAttribute("value"),"Иванович");

        WebElement date = driver.findElement(By.xpath("//input[@id='person_birthDate']"));
        date.click();
        date.sendKeys("01051997");
        Assert.assertEquals(date.getAttribute("value"),"01.05.1997");

        title = driver.findElement(By.xpath("//legend[contains(text(), 'Паспортные данные')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", title);

        WebElement pasSeries = driver.findElement(By.xpath("//input[@id='passportSeries']"));
        pasSeries.click();
        pasSeries.sendKeys("2017");
        Assert.assertEquals(pasSeries.getAttribute("value"),"2017");

        WebElement pasNumber = driver.findElement(By.xpath("//input[@id='passportNumber']"));
        pasNumber.click();
        pasNumber.sendKeys("313234");
        Assert.assertEquals(pasNumber.getAttribute("value"),"313234");

        WebElement pasDate = driver.findElement(By.xpath("//input[@id='documentDate']"));
        pasDate.click();
        pasDate.sendKeys("17052017");
        Assert.assertEquals(pasDate.getAttribute("value"),"17.05.2017");

        WebElement ufms = driver.findElement(By.xpath("//input[@id='documentIssue']"));
        ufms.click();
        ufms.sendKeys("УФМС");
        Assert.assertEquals(ufms.getAttribute("value"),"УФМС");

        button = driver.findElement(By.xpath("//button[@class='btn btn-primary page__btn waves-effect']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
        button.click();

        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='alert-form alert-form-error']")).isDisplayed());
    }

    @AfterEach
    public void endTest(){
        this.driver.close();
        this.driver.quit();
    }
}
