package ru.msu.cmc.webapp;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeleniumTests {

    /********************************* VacanciesTests ******************************/
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
    @Test
    void testVacanciesAddUpdateDelete() {
        System.setProperty("webdriver.chrome.driver", "D:\\study\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1920, 1080));

        driver.get("http://localhost:8080/");

        driver.findElement(By.partialLinkText("Вакансии")).click();

        int fullSize = driver.findElements(By.tagName("tr")).size();

        driver.findElement(By.name("nameFilter")).sendKeys("Deutsche Bank");
        driver.findElement(By.name("nameFilter")).sendKeys(Keys.RETURN);
        int size = driver.findElements(By.tagName("tr")).size();

        driver.findElement(By.cssSelector("button.btn.btn-success.mb-2:nth-child(1)")).click();

        WebElement selectElement = driver.findElement(By.name("companyId"));
        Select select = new Select(selectElement);
        select.selectByVisibleText("Deutsche Bank");

        driver.findElement(By.name("position")).sendKeys("test");

        driver.findElement(By.name("salary")).sendKeys("100000");

        driver.findElement(By.name("experience")).sendKeys("100");

        selectElement = driver.findElement(By.name("inputEducation"));
        select = new Select(selectElement);
        select.selectByVisibleText("начальное");

        driver.findElement(By.cssSelector("button.btn")).click();
        driver.findElement(By.partialLinkText("Вакансии")).click();
        driver.findElement(By.name("nameFilter")).sendKeys("Deutsche Bank");
        driver.findElement(By.name("nameFilter")).sendKeys(Keys.RETURN);
        assertEquals( size + 1, driver.findElements(By.tagName("tr")).size());

        List<WebElement> toUpdate = driver.findElements(By.cssSelector("button.btn.btn-success"));
        toUpdate.get(4).click();

        /* Проверка на update values & change them*/
        selectElement = driver.findElement(By.name("companyId"));
        select = new Select(selectElement);
        assertEquals("Deutsche Bank", select.getFirstSelectedOption().getText());
        select.selectByVisibleText("EPAM");

        assertEquals("test", driver.findElement(By.name("position")).getAttribute("value"));
        driver.findElement(By.name("position")).clear();
        driver.findElement(By.name("position")).sendKeys("new test");

        assertEquals("100000", driver.findElement(By.name("salary")).getAttribute("value"));
        driver.findElement(By.name("salary")).clear();
        driver.findElement(By.name("salary")).sendKeys("500000");

        assertEquals("100.0", driver.findElement(By.name("experience")).getAttribute("value"));
        driver.findElement(By.name("experience")).clear();
        driver.findElement(By.name("experience")).sendKeys("15");

        selectElement = driver.findElement(By.name("inputEducation"));
        select = new Select(selectElement);
        assertEquals("начальное", select.getFirstSelectedOption().getText());
        select.selectByVisibleText("Высшее (бакалавриат)");

        driver.findElement(By.cssSelector("button.btn")).click();
        driver.findElement(By.partialLinkText("Вакансии")).click();

        WebElement test = driver.findElements(By.tagName("tr")).get(35);
        List<WebElement> elems = test.findElements(By.tagName("td"));
        assertEquals("EPAM", elems.get(0).getText());
        assertEquals("new test", elems.get(1).getText());
        assertEquals("500000 руб", elems.get(2).getText());
        assertEquals("15.0 лет", elems.get(3).getText());
        assertEquals("Высшее (бакалавриат)", elems.get(4).getText());

        elems.get(6).findElement(By.cssSelector("button.btn")).click();
        assertEquals(fullSize, driver.findElements(By.tagName("tr")).size());
        driver.quit();
    }

    @Test
    void testResumesFilterByVacancy() {
        System.setProperty("webdriver.chrome.driver", "D:\\study\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1920, 1080));

        driver.get("http://localhost:8080/");
        driver.findElement(By.partialLinkText("Вакансии")).click();
        driver.findElements(By.tagName("tr")).get(20).findElements(By.tagName("td")).get(5).findElement(By.cssSelector("button.btn")).click();;
        assertEquals( 1 + 1, driver.findElements(By.tagName("tr")).size());
        driver.quit();
    }

    /********************************* CompaniesTests ******************************/
    @Test
    void testCompaniesFiletByName() {
        System.setProperty("webdriver.chrome.driver", "D:\\study\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1920, 1080));

        driver.get("http://localhost:8080/");
        assertEquals("Кадровое агенство", driver.getTitle());
        driver.findElement(By.partialLinkText("Компании")).click();
        driver.findElement(By.name("nameFilter")).sendKeys("Bank");
        driver.findElement(By.name("nameFilter")).sendKeys(Keys.RETURN);
        assertEquals( 2 + 1, driver.findElements(By.tagName("tr")).size());

        driver.quit();
    }

    @Test
    void testCompaniessAddUpdateDelete() {
        System.setProperty("webdriver.chrome.driver", "D:\\study\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1920, 1080));

        driver.get("http://localhost:8080/");

        driver.findElement(By.partialLinkText("Компании")).click();
        int fullSize = driver.findElements(By.tagName("tr")).size();

        driver.findElement(By.cssSelector("button.btn.btn-success.mb-2")).click();

        driver.findElement(By.name("name")).sendKeys("test");
        driver.findElement(By.name("fullName")).sendKeys("OAO test");
        driver.findElement(By.cssSelector("button.btn")).click();


        driver.findElement(By.partialLinkText("Компании")).click();
        /* Проверка значений в таблице */
        WebElement test = driver.findElements(By.tagName("tr")).get(fullSize);
        List<WebElement> elems = test.findElements(By.tagName("td"));
        assertEquals("test", elems.get(0).getText());
        assertEquals("OAO test", elems.get(1).getText());
        elems.get(2).findElement(By.cssSelector("button.btn")).click();

        /* Проверка значений на личной странице */
        assertEquals("test", driver.findElement(By.tagName("h4")).getText());
        elems = driver.findElements(By.tagName("p"));
        assertEquals("Название компании: test", elems.get(0).getText());
        assertEquals("Полное название: OAO test", elems.get(1).getText());

        /* Реадктирование */
        elems = driver.findElements(By.tagName("form"));
        elems.get(2).findElement(By.cssSelector("button.btn")).click();


        /* Проверка на update values & change them*/
        assertEquals("test", driver.findElement(By.name("name")).getAttribute("value"));
        driver.findElement(By.name("name")).clear();
        driver.findElement(By.name("name")).sendKeys("new test");

        assertEquals("OAO test", driver.findElement(By.name("fullName")).getAttribute("value"));
        driver.findElement(By.name("fullName")).clear();
        driver.findElement(By.name("fullName")).sendKeys("OAO new test");

        driver.findElement(By.cssSelector("button.btn")).click();

        /* Проверка на личной странице */
        assertEquals("new test", driver.findElement(By.tagName("h4")).getText());
        elems = driver.findElements(By.tagName("p"));
        assertEquals("Название компании: new test", elems.get(0).getText());
        assertEquals("Полное название: OAO new test", elems.get(1).getText());

        /* Удаление и проверка количества компаний */
        elems = driver.findElements(By.tagName("form"));
        elems.get(1).findElement(By.cssSelector("button.btn")).click();

        driver.findElement(By.partialLinkText("Компании")).click();
        assertEquals(fullSize, driver.findElements(By.tagName("tr")).size());
        driver.quit();
    }

    @Test
    void testCompanieGetVacancies() {
        System.setProperty("webdriver.chrome.driver", "D:\\study\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1920, 1080));

        driver.get("http://localhost:8080/");
        assertEquals("Кадровое агенство", driver.getTitle());
        driver.findElement(By.partialLinkText("Компании")).click();

        WebElement test = driver.findElements(By.tagName("tr")).get(1);
        List<WebElement> elems = test.findElements(By.tagName("td"));
        assertEquals("ВымпелКом (Билайн)", elems.get(0).getText());
        assertEquals("Публичное акционерное общество \"Вымпел-коммуникации\"", elems.get(1).getText());
        elems.get(2).findElement(By.cssSelector("button.btn")).click();

        elems = driver.findElements(By.tagName("form"));
        elems.get(0).findElement(By.cssSelector("button.btn")).click();

        assertEquals(3 + 1, driver.findElements(By.tagName("tr")).size());
        driver.quit();
    }

    /********************************* ResumesTests ******************************/
    @Test
    void testResumesFilterByPerson() {
        System.setProperty("webdriver.chrome.driver", "D:\\study\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1920, 1080));

        driver.get("http://localhost:8080/");
        assertEquals("Кадровое агенство", driver.getTitle());
        driver.findElement(By.partialLinkText("Резюме")).click();
        driver.findElement(By.name("nameFilter")).sendKeys("Зайцев");
        driver.findElement(By.name("nameFilter")).sendKeys(Keys.RETURN);
        assertEquals( 1 + 1, driver.findElements(By.tagName("tr")).size());

        driver.findElement(By.partialLinkText("Резюме")).click();
        driver.findElement(By.name("nameFilter")).sendKeys("Илья");
        driver.findElement(By.name("nameFilter")).sendKeys(Keys.RETURN);
        assertEquals( 2 + 1, driver.findElements(By.tagName("tr")).size());

        driver.quit();
    }

    @Test
    void testResumesFilterByPosition() {
        System.setProperty("webdriver.chrome.driver", "D:\\study\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1920, 1080));

        driver.get("http://localhost:8080/");
        driver.findElement(By.partialLinkText("Резюме")).click();
        driver.findElement(By.name("positionFilter")).sendKeys("Программист Java");
        driver.findElement(By.name("positionFilter")).sendKeys(Keys.RETURN);
        assertEquals( 1 + 1, driver.findElements(By.tagName("tr")).size());

        driver.findElement(By.partialLinkText("Резюме")).click();
        driver.findElement(By.name("positionFilter")).sendKeys("Тестировщик");
        driver.findElement(By.name("positionFilter")).sendKeys(Keys.RETURN);
        assertEquals( 14 + 1, driver.findElements(By.tagName("tr")).size());

        driver.quit();
    }

    @Test
    void testResumesFilterByEducation() {
        System.setProperty("webdriver.chrome.driver", "D:\\study\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1920, 1080));

        driver.get("http://localhost:8080/");
        driver.findElement(By.partialLinkText("Резюме")).click();
        WebElement selectElement = driver.findElement(By.name("educationFilter"));
        Select select = new Select(selectElement);
        select.selectByVisibleText("Высшее (специалитет)");
        driver.findElement(By.cssSelector("button.btn:nth-child(1)")).click();
        assertEquals( 8 + 1, driver.findElements(By.tagName("tr")).size());

        driver.quit();
    }

    @Test
    void testResumesFilterByExperience() {
        System.setProperty("webdriver.chrome.driver", "D:\\study\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1920, 1080));

        driver.get("http://localhost:8080/");
        driver.findElement(By.partialLinkText("Резюме")).click();
        driver.findElement(By.name("experienceFilter")).sendKeys("9");
        driver.findElement(By.name("experienceFilter")).sendKeys(Keys.RETURN);
        assertEquals( 4 + 1, driver.findElements(By.tagName("tr")).size());

        driver.quit();
    }

    @Test
    void testResumesFilterBySalary() {
        System.setProperty("webdriver.chrome.driver", "D:\\study\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1920, 1080));

        driver.get("http://localhost:8080/");
        driver.findElement(By.partialLinkText("Резюме")).click();
        driver.findElement(By.name("fromFilter")).sendKeys("100000");
        driver.findElement(By.name("toFilter")).sendKeys("150000");
        driver.findElement(By.cssSelector("button.btn.btn-secondary.mb-2:nth-child(1)")).click();
        assertEquals( 10 + 1, driver.findElements(By.tagName("tr")).size());

        driver.quit();
    }

    @Test
    void testResumesAddUpdateDelete() {
        System.setProperty("webdriver.chrome.driver", "D:\\study\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1920, 1080));

        driver.get("http://localhost:8080/");

        driver.findElement(By.partialLinkText("Резюме")).click();

        int fullSize = driver.findElements(By.tagName("tr")).size();

        driver.findElement(By.name("nameFilter")).sendKeys("Зайцев");
        driver.findElement(By.name("nameFilter")).sendKeys(Keys.RETURN);
        int size = driver.findElements(By.tagName("tr")).size();

        driver.findElement(By.cssSelector("button.btn.btn-success.mb-2:nth-child(1)")).click();

        WebElement selectElement = driver.findElement(By.name("inputPerson"));
        Select select = new Select(selectElement);
        select.selectByVisibleText("Зайцев Артём Михайлович");

        driver.findElement(By.name("position")).sendKeys("test");

        driver.findElement(By.name("salary")).sendKeys("100000");

        driver.findElement(By.name("experience")).sendKeys("100");

        driver.findElement(By.cssSelector("button.btn")).click();
        driver.findElement(By.partialLinkText("Резюме")).click();
        driver.findElement(By.name("nameFilter")).sendKeys("Зайцев");
        driver.findElement(By.name("nameFilter")).sendKeys(Keys.RETURN);
        assertEquals( size + 1, driver.findElements(By.tagName("tr")).size());

        List<WebElement> toUpdate = driver.findElements(By.cssSelector("button.btn.btn-success"));
        toUpdate.get(2).click();

        /* Проверка на update values & change them*/
        selectElement = driver.findElement(By.name("inputPerson"));
        select = new Select(selectElement);
        assertEquals("Зайцев Артём Михайлович", select.getFirstSelectedOption().getText());
        select.selectByVisibleText("Орлов Илья Артёмович");

        assertEquals("test", driver.findElement(By.name("position")).getAttribute("value"));
        driver.findElement(By.name("position")).clear();
        driver.findElement(By.name("position")).sendKeys("new test");

        assertEquals("100000", driver.findElement(By.name("salary")).getAttribute("value"));
        driver.findElement(By.name("salary")).clear();
        driver.findElement(By.name("salary")).sendKeys("500000");

        assertEquals("100.0", driver.findElement(By.name("experience")).getAttribute("value"));
        driver.findElement(By.name("experience")).clear();
        driver.findElement(By.name("experience")).sendKeys("15");


        driver.findElement(By.cssSelector("button.btn")).click();
        driver.findElement(By.partialLinkText("Резюме")).click();

        WebElement test = driver.findElements(By.tagName("tr")).get(fullSize);
        List<WebElement> elems = test.findElements(By.tagName("td"));
        assertEquals("Орлов Илья Артёмович", elems.get(0).getText());
        assertEquals("new test", elems.get(1).getText());
        assertEquals("500000 руб", elems.get(2).getText());
        assertEquals("15.0 лет", elems.get(3).getText());
        assertEquals("без образования", elems.get(4).getText());

        elems.get(6).findElement(By.cssSelector("button.btn")).click();
        assertEquals(fullSize, driver.findElements(By.tagName("tr")).size());
        driver.quit();
    }

    @Test
    void testVacancyFilterByResume() {
        System.setProperty("webdriver.chrome.driver", "D:\\study\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1920, 1080));

        driver.get("http://localhost:8080/");
        driver.findElement(By.partialLinkText("Резюме")).click();
        driver.findElements(By.tagName("tr")).get(3).findElements(By.tagName("td")).get(5).findElement(By.cssSelector("button.btn")).click();;
        assertEquals( 1 + 1, driver.findElements(By.tagName("tr")).size());
        driver.quit();
    }

    /********************************* PeopleTests ******************************/
    @Test
    void testPeopleFilterByName() {
        System.setProperty("webdriver.chrome.driver", "D:\\study\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1920, 1080));

        driver.get("http://localhost:8080/");
        assertEquals("Кадровое агенство", driver.getTitle());
        driver.findElement(By.partialLinkText("Соискатели")).click();
        driver.findElement(By.name("nameFilter")).sendKeys("Илья");
        driver.findElement(By.name("nameFilter")).sendKeys(Keys.RETURN);
        assertEquals( 2 + 1, driver.findElements(By.tagName("tr")).size());

        driver.quit();
    }

    @Test
    void testPersonAddUpdateDelete() {
        System.setProperty("webdriver.chrome.driver", "D:\\study\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1920, 1080));

        driver.get("http://localhost:8080/");

        driver.findElement(By.partialLinkText("Соискатели")).click();
        int fullSize = driver.findElements(By.tagName("tr")).size();

        driver.findElement(By.cssSelector("button.btn.btn-success.mb-2")).click();

        driver.findElement(By.name("lastName")).sendKeys("Тестовик");
        driver.findElement(By.name("firstName")).sendKeys("Тест");
        driver.findElement(By.name("surName")).sendKeys("Тестович");
        WebElement selectElement = driver.findElement(By.name("inputEducation"));
        Select select = new Select(selectElement);
        select.selectByVisibleText("без образования");
        driver.findElement(By.name("address")).sendKeys("Улица Тестовая, дом 1");
        selectElement = driver.findElement(By.name("inputStatus"));
        select = new Select(selectElement);
        select.selectByVisibleText("ищет работу");

        driver.findElement(By.cssSelector("button.btn")).click();
        driver.findElement(By.partialLinkText("Соискатели")).click();

        /* Проверка значений в таблице */
        WebElement test = driver.findElements(By.tagName("tr")).get(fullSize);
        List<WebElement> elems = test.findElements(By.tagName("td"));
        assertEquals("Тестовик", elems.get(0).getText());
        assertEquals("Тест", elems.get(1).getText());
        assertEquals("Тестович", elems.get(2).getText());
        elems.get(3).findElement(By.cssSelector("button.btn")).click();

        /* Проверка значений на личной странице */
        assertEquals("Тестовик Тест Тестович", driver.findElement(By.tagName("h4")).getText());
        elems = driver.findElements(By.tagName("p"));
        assertEquals("Имя: Тест", elems.get(0).getText());
        assertEquals("Фамилия: Тестовик", elems.get(1).getText());
        assertEquals("Отчество: Тестович", elems.get(2).getText());
        assertEquals("Образование: без образования", elems.get(3).getText());
        assertEquals("Домашний адрес: Улица Тестовая, дом 1", elems.get(4).getText());
        assertEquals("Статус поиска: ищет работу", elems.get(5).getText());
        assertEquals("Места работы: ", elems.get(6).getText());

        testPlaceAddUpdateDelete();

        /* Редактирование */
        elems = driver.findElements(By.tagName("form"));
        elems.get(elems.size() - 1).findElement(By.cssSelector("button.btn")).click();


        /* Проверка на update values & change them*/
        assertEquals("Тестовик", driver.findElement(By.name("lastName")).getAttribute("value"));
        driver.findElement(By.name("lastName")).clear();
        driver.findElement(By.name("lastName")).sendKeys("Протестовик");

        assertEquals("Тест", driver.findElement(By.name("firstName")).getAttribute("value"));
        driver.findElement(By.name("firstName")).clear();
        driver.findElement(By.name("firstName")).sendKeys("Протест");

        assertEquals("Тестович", driver.findElement(By.name("surName")).getAttribute("value"));
        driver.findElement(By.name("surName")).clear();
        driver.findElement(By.name("surName")).sendKeys("Протестович");

        selectElement = driver.findElement(By.name("inputEducation"));
        select = new Select(selectElement);
        assertEquals("без образования", select.getFirstSelectedOption().getText());
        select.selectByVisibleText("Высшее (бакалавриат)");

        assertEquals("Улица Тестовая, дом 1", driver.findElement(By.name("address")).getAttribute("value"));
        driver.findElement(By.name("address")).clear();
        driver.findElement(By.name("address")).sendKeys("Улица Протестная, дом 1");

        selectElement = driver.findElement(By.name("inputStatus"));
        select = new Select(selectElement);
        assertEquals("ищет работу", select.getFirstSelectedOption().getText());
        select.selectByVisibleText("открыт к предложениям");

        driver.findElement(By.cssSelector("button.btn")).click();

        /* Проверка на личной странице */
        assertEquals("Протестовик Протест Протестович", driver.findElement(By.tagName("h4")).getText());
        elems = driver.findElements(By.tagName("p"));
        assertEquals("Имя: Протест", elems.get(0).getText());
        assertEquals("Фамилия: Протестовик", elems.get(1).getText());
        assertEquals("Отчество: Протестович", elems.get(2).getText());
        assertEquals("Образование: Высшее (бакалавриат)", elems.get(3).getText());
        assertEquals("Домашний адрес: Улица Протестная, дом 1", elems.get(4).getText());
        assertEquals("Статус поиска: открыт к предложениям", elems.get(5).getText());
        assertEquals("Места работы: ", elems.get(6).getText());

        /* Удаление и проверка количества компаний */

        elems = driver.findElements(By.tagName("form"));
        elems.get(elems.size() - 2).findElement(By.cssSelector("button.btn")).click();

        driver.findElement(By.partialLinkText("Соискатели")).click();
        assertEquals(fullSize, driver.findElements(By.tagName("tr")).size());

        driver.quit();
    }

    void testPlaceAddUpdateDelete() {

    }

    @Test
    void testPersonGetResumes() {
        System.setProperty("webdriver.chrome.driver", "D:\\study\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1920, 1080));

        driver.get("http://localhost:8080/");
        assertEquals("Кадровое агенство", driver.getTitle());
        driver.findElement(By.partialLinkText("Соискатели")).click();

        WebElement test = driver.findElements(By.tagName("tr")).get(1);
        List<WebElement> elems = test.findElements(By.tagName("td"));
        elems.get(3).findElement(By.cssSelector("button.btn")).click();
        elems = driver.findElements(By.tagName("p"));
        assertEquals("Имя: Артём", elems.get(0).getText());
        assertEquals("Фамилия: Зайцев", elems.get(1).getText());
        assertEquals("Отчество: Михайлович", elems.get(2).getText());

        elems = driver.findElements(By.tagName("form"));
        elems.get(elems.size() - 3).findElement(By.cssSelector("button.btn")).click();

        assertEquals(1 + 1, driver.findElements(By.tagName("tr")).size());
        driver.quit();
    }

}