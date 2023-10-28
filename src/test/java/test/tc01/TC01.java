package test.tc01;

import driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

@Test
public class TC01 {

    public static void testTC01() {

        WebDriver driver = DriverFactory.getChromeDriver();
        try {
            driver.get("http://live.techpanda.org/");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement mobileBtn = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".level0"))));
            mobileBtn.click();

            By selSelector = By.cssSelector(".sort-by > select");
            WebElement sortBtn = driver.findElement(selSelector);
            sortBtn.click();

            WebElement sortNameBtn = sortBtn.findElement(By.cssSelector("option:nth-child(2)"));
            sortNameBtn.click();

            List<WebElement> names = driver.findElements(By.cssSelector(".product-name > a"));
            if (!isSorted(names)) {
                throw new AssertionError("The list is not sorted!");
            } else {
                System.out.println("This list is sorted!");
            }

            Thread.sleep(3000);
        }catch (Exception e){
            e.printStackTrace();
        }
        driver.quit();
    }

    public static boolean isSorted(List<WebElement> elements) {
        for (int i = 0; i < elements.size() - 1; i++) {
            String name1 = elements.get(i).getText();
            String name2 = elements.get(i + 1).getText();

            if (name1.compareTo(name2) > 0) {
                return false;
            }
        }
        return true;
    }
}
