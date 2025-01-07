package pages;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage_eBay {
	private WebDriver driver;
	
	By searchBtn = By.id("gh-btn");
	By searchArea = By.id("gh-ac");
    By searchItems = By.cssSelector(".srp-results.srp-list.clearfix");
    By addToCart = By.id("atcBtn_btn_1");	
    By cartIcon = By.className("gh-cart-icon");
    By cartItemNumer = By.id("gh-cart-n");
	
	
	public HomePage_eBay(WebDriver driver) {
		this.driver = driver;
	}
	
	public boolean searchItem(String item) {
		driver.findElement(searchArea).sendKeys(item);
		
		driver.findElement(searchBtn).click();
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
		try {
            wait.until(ExpectedConditions.presenceOfElementLocated(searchItems)); 
            return true;  
        } catch (Exception e) {
            return false; 
        }     
	}
	
	public boolean addToCart(int itemIndex) {
		String parentWindwHandle = driver.getWindowHandle();
		
		WebElement book =  driver.findElement(searchItems);
	    List<WebElement> list = book.findElements(By.xpath(".//li[contains(@class,'s-item__pl-on-bottom')]"));
	    
	    if(list.size() == 0 && list.size() < itemIndex)
	    	return false;
	    
	    for(int i =0;i<list.size();i++) {
	    	if(i == (itemIndex-1)) {
	    		list.get(itemIndex).findElement(By.xpath(".//div[@class = 's-item__image-section']")).click();
	    		break;
	    	}
	    }
	    
	    WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
	    try {
	    	wait.until(ExpectedConditions.numberOfWindowsToBe(2));
	    } catch (Exception e) {
            return false; 
        }  
	    
	    Set<String> newHandles = driver.getWindowHandles();
	    for (String handle : newHandles) {
            if (!parentWindwHandle.equals(handle)) {
                driver.switchTo().window(handle);
                break;
            }
        }
	    
	    //add to cart
	    driver.findElement(addToCart).click();
	    
	    String itemCountOnCart = driver.findElement(cartItemNumer).getText();
	    
	    if(itemCountOnCart.equals("1"))
	       return true;
	    return false; 
	}
	
	
}
