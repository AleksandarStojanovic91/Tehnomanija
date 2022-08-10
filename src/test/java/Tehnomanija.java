import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Tehnomanija {
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriverMac");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test()
    public void tehnomanija() {
        driver.get("https://www.tehnomanija.rs/");

        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.cssSelector(".all-categories--title>div:first-child")))
                .moveToElement(driver.findElement(By.cssSelector(".top-category-name-icon a[href='/telefoni']")))
                .build().perform();

        //HY




    }

    public void takeScreenshot(String name) throws IOException {
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file,new File("src/results/"+name+".png"));
    }
    //test

}