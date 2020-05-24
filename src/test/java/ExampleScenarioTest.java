import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class ExampleScenarioTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void startUp() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver"); //join driver
        driver = new ChromeDriver();    //create new obj for driver chrome
        driver.manage().window().maximize();    //opening FULL window
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);   //max time of waiting to open window
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //max time of waiting element
        wait = new WebDriverWait(driver, 20);   //waiting object
    }

    @Test
    public void exampleTest() throws InterruptedException {
        driver.get("https://www.sberbank.ru/ru/person");    //opening site

        //finding div with regions and click
        String regionLinkXpath = "//div[contains(@class, 'paste-region__region header')]//a[@class='hd-ft-region']";    //xPath of required element
        WebElement regionLinkElement = driver.findElement(By.xpath(regionLinkXpath));      //command to find element by xPath
        regionLinkElement.click();      //click on element

        //waiting for open and find by region and click
        String concreteRegionXpath = "//a[contains(text(), 'Нижегородская область')]";      //xPath of required element
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(concreteRegionXpath)));     //waiting for ability to click element
        driver.findElement(By.xpath(concreteRegionXpath)).click();

        //verification by span region
        Assert.assertEquals("Содержимое не соответствует ожиданиям",
                "Нижегородская область",
                driver.findElement(By.xpath("//div[contains(@class, 'paste-region__region header__region')]//a[@class='hd-ft-region']//span")).getText());

        //getting element by tag
        String footerTag = "footer";    //tag of footer
        WebElement footerElement = driver.findElement(By.tagName(footerTag));

        //creating and executing js script
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].scrollIntoView();", footerElement);

        //getting Facebook Icon by xPath and verification
        String facebookIconXpath = "//a[@title='Поделиться в Facebook']";
        WebElement facebookIconElement = driver.findElement(By.xpath(facebookIconXpath));
        Assert.assertTrue("Элемент отсутствует на странице", facebookIconElement.isDisplayed());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
