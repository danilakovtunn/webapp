package ru.msu.cmc.webapp;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeleniumTests {

    @Test
    void testVacanciesFilterByCompany() {
        System.setProperty("webdriver.chrome.driver", "D:\\study\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1920, 1080));

        driver.get("http://localhost:8080/");
        assertEquals("Кадровое агенство", driver.getTitle());
        driver.findElement(By.partialLinkText("Вакансии")).click();
        driver.findElement(By.name("nameFilter")).sendKeys("Билайн");
        driver.findElement(By.name("nameFilter")).sendKeys(Keys.RETURN);
        assertEquals( 3 + 1, driver.findElements(By.tagName("tr")).size());

        driver.findElement(By.partialLinkText("Вакансии")).click();
        driver.findElement(By.name("nameFilter")).sendKeys("Яндекс");
        driver.findElement(By.name("nameFilter")).sendKeys(Keys.RETURN);
        assertEquals( 4 + 1, driver.findElements(By.tagName("tr")).size());

        driver.quit();
    }

    @Test
    void testVacanciesFilterByPosition() {
        System.setProperty("webdriver.chrome.driver", "D:\\study\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1920, 1080));

        driver.get("http://localhost:8080/");
        driver.findElement(By.partialLinkText("Вакансии")).click();
        driver.findElement(By.name("positionFilter")).sendKeys("Программист Java");
        driver.findElement(By.name("positionFilter")).sendKeys(Keys.RETURN);
        assertEquals( 3 + 1, driver.findElements(By.tagName("tr")).size());

        driver.findElement(By.partialLinkText("Вакансии")).click();
        driver.findElement(By.name("positionFilter")).sendKeys("Тестировщик");
        driver.findElement(By.name("positionFilter")).sendKeys(Keys.RETURN);
        assertEquals( 1 + 1, driver.findElements(By.tagName("tr")).size());

        driver.quit();
    }

    @Test
    void testVacanciesFilterByEducation() {
        System.setProperty("webdriver.chrome.driver", "D:\\study\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1920, 1080));

        driver.get("http://localhost:8080/");
        driver.findElement(By.partialLinkText("Вакансии")).click();
        WebElement selectElement = driver.findElement(By.name("educationFilter"));
        Select select = new Select(selectElement);
        select.selectByVisibleText("Высшее (специалитет)");
        driver.findElement(By.cssSelector("button.btn:nth-child(1)")).click();
        assertEquals( 7 + 1, driver.findElements(By.tagName("tr")).size());

        driver.quit();
    }

    @Test
    void testVacanciesFilterByExperience() {
        System.setProperty("webdriver.chrome.driver", "D:\\study\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1920, 1080));

        driver.get("http://localhost:8080/");
        driver.findElement(By.partialLinkText("Вакансии")).click();
        driver.findElement(By.name("experienceFilter")).sendKeys("9");
        driver.findElement(By.name("experienceFilter")).sendKeys(Keys.RETURN);
        assertEquals( 4 + 1, driver.findElements(By.tagName("tr")).size());

        driver.quit();
    }

    @Test
    void testVacanciesFilterBySalary() {
        System.setProperty("webdriver.chrome.driver", "D:\\study\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1920, 1080));

        driver.get("http://localhost:8080/");
        driver.findElement(By.partialLinkText("Вакансии")).click();
        driver.findElement(By.name("fromFilter")).sendKeys("100000");
        driver.findElement(By.name("toFilter")).sendKeys("150000");
        driver.findElement(By.cssSelector("button.btn.btn-secondary.mb-2:nth-child(1)")).click();
        assertEquals( 8 + 1, driver.findElements(By.tagName("tr")).size());

        driver.quit();
    }
}