import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;
    private List<WebElement> searchResults;

    @FindBy (id = "sp-cc-accept")
    private WebElement acceptCookies;


    public HomePage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 30);
    }

    public void goTo(){
        this.driver.get("https://www.amazon.fr/");
    }

    public void acceptCookies(){
        //this.wait.until(ExpectedConditions.visibilityOf(this.acceptCookies));
        //this.acceptCookies.click();
        driver.findElement(By.id("sp-cc-accept")).click();
    }

    public void searchFiFA() throws InterruptedException {
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("FIFA");
        Thread.sleep(1000);
        //Handle autoSuggestiveList
        driver.findElement(By.id("nav-search-submit-button")).click();
        Thread.sleep(1000);
        searchResults = driver.findElements(By.cssSelector("div[data-cel-widget^='search_result_']"));
        int searchResultNb = searchResults.size();
        //System.out.println("nbre fifa results: "+searchResultNb);
    }

    public double[] addToChart() throws InterruptedException {
        double priceValue1 = 1000;
        int chepeastindex = 0;
        double[] values = new double[2];
        List<WebElement> priceTxt = driver.findElements(By.className("a-price-whole"));
        for (WebElement priceTxtElement:priceTxt){
            if (! (priceTxtElement.getText().isEmpty())) {
                //System.out.println("===========================================================");
                //System.out.println(priceTxtElement.getText());
                //System.out.println(priceTxtElement.getText().length());
                if (priceTxtElement.getText().length() < 6) {
                    double priceValue = Double.parseDouble(priceTxtElement.getText().replaceAll(",", "."));
                    if (priceValue < priceValue1){
                        //System.out.println(priceValue);
                        priceValue1 = priceValue;
                        chepeastindex = priceTxt.indexOf(priceTxtElement);
                    }

                    //System.out.println("the cheapest is: "+priceValue1);
                    //System.out.println("index is: "+chepeastindex);
                }
            }
        }
        priceTxt.get(chepeastindex).click();
        driver.findElement(By.id("add-to-cart-button")).click();
        Thread.sleep(5000);
        driver.findElement(By.id("attach-close_sideSheet-link")).click();
        driver.findElement(By.id("nav-cart-count")).click();
        Thread.sleep(2000);
        driver.findElement(By.className("a-dropdown-label")).click();
        double nbreOfElements = Double.parseDouble(driver.findElement(By.id("quantity_6")).getText()) ;
        driver.findElement(By.id("quantity_6")).click();
        System.out.println(priceValue1);
        System.out.println("tableau = "+values[0]+" and "+values[1]);
        values[0] = priceValue1;
        values[1] = nbreOfElements;
        System.out.println("tableau = "+values[0]+" and "+values[1]);
        return values;
    }

    public void compareChartPrice(double priceElement, double nbreOfElements) throws InterruptedException {
        Thread.sleep(2000);
        String totalPrice = driver.findElement(By.cssSelector("span[class='a-size-medium a-color-base sc-price sc-white-space-nowrap']")).getText();
        System.out.println("total price is: "+totalPrice);
        String price = totalPrice.split(":")[0].split(" ")[0].replaceAll(",",".");
        float priceTotal = Float.parseFloat(price);
        System.out.println(priceTotal);
        System.out.println(priceElement*6);
        Assert.assertEquals(priceElement*nbreOfElements,priceTotal);
    }

}
