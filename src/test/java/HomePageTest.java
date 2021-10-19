import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import java.net.MalformedURLException;


public class HomePageTest {

    @BeforeTest
    public void setupDriver() throws MalformedURLException, InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\benme\\Desktop\\Wassim\\training\\selenium\\udemy training\\docs du formateur\\chromedriver_win32\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        HomePage homePage = new HomePage(driver);
        homePage.goTo();
        homePage.acceptCookies();
        homePage.searchFiFA();
        double[] values = homePage.addToChart();
        //double nbreOfElements = homePage.addToChart()[1];
        System.out.println(" price element is "+values[0]);
        homePage.compareChartPrice(values[0], values[1]);
    }
}
