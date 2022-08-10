import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import jdk.jfr.Description;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class Tehnomanija {
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @AfterMethod
    public void tearDown() {
//        driver.quit();
    }

    @Test(description = "Ime testa")
    @Description("Opis testa")
    @Epic("EP-001")
    @Feature("FE-001")
    @Story("US-001")
    public void tehnomanija() throws Exception {
        driver.get("https://www.tehnomanija.rs/");

        selectMenuSubItem("telefoni"," Smart telefoni ");
        selectCheckboxFilter("Interna memorija","8.0");

        Thread.sleep(2000);
        reportScreenshot("Product","Filter");
        Assert.assertEquals(driver.findElement(By.cssSelector(".product-list>.product p")).getText(),"Alcatel 1C DS - Crni");
    }

    public void selectCheckboxFilter(String filterName, String filterValue) throws Exception {
        if(driver.findElements(By.xpath("//span[text()='"+filterName+"']/../..//a[@data-cx-focus='"+filterValue+"']")).size()>0 && driver.findElements(By.xpath("//span[text()='"+filterName+"']/../..//a[@data-cx-focus='"+filterValue+"']")).get(0).isDisplayed()){
            driver.findElement(By.xpath("//span[text()='"+filterName+"']/../..//a[@data-cx-focus='"+filterValue+"']")).click();
        }else {
            if(driver.findElements(By.xpath("//button/span[text()='"+filterName+"']")).size()>0){
                driver.findElements(By.xpath("//button/span[text()='"+filterName+"']")).get(0).click();
            }else {
                throw new Exception("No such filter was found: "+filterName+ " "+filterValue);
            }
            if(driver.findElements(By.xpath("//span[text()='"+filterName+"']/../..//a[@data-cx-focus='"+filterValue+"']")).size()>0 && driver.findElements(By.xpath("//span[text()='"+filterName+"']/../..//a[@data-cx-focus='"+filterValue+"']")).get(0).isDisplayed()){
                driver.findElement(By.xpath("//span[text()='"+filterName+"']/../..//a[@data-cx-focus='"+filterValue+"']")).click();
            } else {
                if(driver.findElements(By.xpath("//span[text()='"+filterName+"']/../..//button[contains(text(),'Prikaži više')]")).size()>0) {
                    driver.findElements(By.xpath("//span[text()='"+filterName+"']/../..//button[contains(text(),'Prikaži više')]")).get(0).click();
                    if(driver.findElements(By.xpath("//span[text()='" + filterName + "']/../..//a[@data-cx-focus='" + filterValue + "']")).size()>0){
                        driver.findElements(By.xpath("//span[text()='" + filterName + "']/../..//a[@data-cx-focus='" + filterValue + "']")).get(0).click();
                    } else {
                        throw new Exception("No such filter was found: "+filterName+ " "+filterValue);
                    }
                }
            }
        }
    }

    public void selectMenuSubItem(String menuItem, String subMenuItem){
        Actions actions = new Actions(driver);

        actions.moveToElement(driver.findElement(By.cssSelector(".all-categories--title>div:first-child")))
                .moveToElement(driver.findElement(By.cssSelector(".top-category-name-icon a[href='/"+menuItem+"']")))
                .moveToElement(driver.findElement(By.xpath("//a[text()='"+subMenuItem+"']")))
                .click()
                .build()
                .perform();
    }

    public void takeScreenshot(String name) throws IOException {
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file,new File("src/results/"+name+".png"));
    }

    public void reportScreenshot(String name, String desc) throws IOException {
        takeScreenshot(name);
        Path content = Paths.get("src/results/"+name+".png");
        InputStream is = Files.newInputStream(content);
        Allure.addAttachment(desc,is);
    }
}