import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;

public class AutoTest {
    static ChromeDriver driver;

    @BeforeClass
    public static void testInitialize() {
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void test() {
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


        //переход на страничку страховки для путешественников вручную
        //driver.navigate().to("https://www.sberbank.ru/ru/person/bank_inshure/insuranceprogram/life/travel");
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

        String[] insuredPersonSurname = {"Petrov", "Sidorov", "Kotova"};
        String[] insuredPersonName = {"Ivan", "Igor", "Anna"};
        String[] insuredPersonDate = {"01052001", "20101997", "28031992"};
        WebElement surnameInput = driver.findElement
                (By.xpath("//input[@id='surname_vzr_ins_0']"));
        WebElement nameInput = driver.findElement
                (By.xpath("//input[@id='name_vzr_ins_0']"));
        WebElement birthDateInput = driver.findElement
                (By.xpath("//input[@id='birthDate_vzr_ins_0']"));
        for(int i = 0; i < insuredPersonSurname.length; i++) {
            surnameInput.click();
            surnameInput.sendKeys(insuredPersonSurname[i]);
            nameInput.click();

            nameInput.sendKeys(insuredPersonName[i]);
            birthDateInput.click();

            birthDateInput.sendKeys(insuredPersonDate[i]);
            birthDateInput.sendKeys(Keys.RETURN);
            WebElement title = driver.findElement(By.xpath("//legend[contains(text(), 'Страхователь')]"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", title);

            if(i==0) {
                WebElement surname = driver.findElement(By.xpath("//input[@id='person_lastName']"));
                surname.click();
                surname.sendKeys("Иванов");
                //Assert.assertEquals(surname.getAttribute("placeholder"),"Иванов");

                WebElement name = driver.findElement(By.xpath("//input[@id='person_firstName']"));
                name.click();
                name.sendKeys("Иван");

                WebElement middleName = driver.findElement(By.xpath("//input[@id='person_middleName']"));
                middleName.click();
                middleName.sendKeys("Иванов");

                WebElement date = driver.findElement(By.xpath("//input[@id='person_birthDate']"));
                date.click();
                date.sendKeys("01051997");

                title = driver.findElement(By.xpath("//legend[contains(text(), 'Паспортные данные')]"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", title);

                WebElement pasSeries = driver.findElement(By.xpath("//input[@id='passportSeries']"));
                pasSeries.click();
                pasSeries.sendKeys("2017");

                WebElement pasNumber = driver.findElement(By.xpath("//input[@id='passportNumber']"));
                pasNumber.click();
                pasNumber.sendKeys("313234");

                WebElement pasDate = driver.findElement(By.xpath("//input[@id='documentDate']"));
                pasDate.click();
                pasDate.sendKeys("17052017");

                WebElement ufms = driver.findElement(By.xpath("//input[@id='documentIssue']"));
                ufms.click();
                ufms.sendKeys("УФМС");

                button = driver.findElement(By.xpath("//button[@class='btn btn-primary page__btn waves-effect']"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
                button.click();

                Assert.assertTrue(driver.findElement(By.xpath("//div[@class='alert-form alert-form-error']")).isDisplayed());
            }
            if(i < insuredPersonSurname.length-1) {
                surnameInput.clear();
                nameInput.clear();
                birthDateInput.clear();
            }
        }
    }
/*  @ParameterizedTest
    @CsvSource( {"Ivanov, Ivan, 30012008", "Petrov, Petr, 20051997", "Sidorova, Anna, 2007"})
    public void paramTestInsured(String surname, String name, String birthDate) {
        WebElement surnameInput = driver.findElement
                (By.xpath("//input[@id='surname_vzr_ins_0']"));
        WebElement nameInput = driver.findElement
                (By.xpath("//input[@id='name_vzr_ins_0']"));
        WebElement birthDateInput = driver.findElement
                (By.xpath("//input[@id='birthDate_vzr_ins_0']"));
        surnameInput.click();
        surnameInput.sendKeys(surname);
        nameInput.click();
        nameInput.sendKeys(name);
        birthDateInput.click();
        birthDateInput.sendKeys(birthDate);
        birthDateInput.sendKeys(Keys.RETURN);
    }*/

    @AfterClass
    public static void endTest(){
        driver.close();
        driver.quit();
    }
}
