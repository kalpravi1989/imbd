package imdb;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ImdbPro {
	WebDriver driver;

	@BeforeMethod
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

	}

	@Test
	public void test1() throws InterruptedException {
		driver.get("https://www.imdb.com");
		driver.findElement(By.xpath("//input[@placeholder='Search IMDb']")).sendKeys("Pushpa: The Rise");
		driver.findElement(By.xpath("//div[contains(text(),'Pushpa: The Rise - Part 1')]")).click();
		WebElement ele = driver.findElement(By.xpath("//a[contains(text(),'Release date')]"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", ele);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(50));
		wait.until(ExpectedConditions.elementToBeClickable(ele));
		try {
			ele.click();
		} catch (ElementClickInterceptedException e) {
			ele.click();
		}
		List<WebElement> country = driver.findElements(By.xpath(
				"//table[@class='ipl-zebra-list ipl-zebra-list--fixed-first release-dates-table-test-only']/tbody/tr/td/a"));
		for (WebElement c : country) {
			String countryname = c.getText();
			System.out.print(countryname + "   ");
			countryname = countryname.trim();
			String releaseDate = driver.findElement(By.xpath(
					"//table[@class='ipl-zebra-list ipl-zebra-list--fixed-first release-dates-table-test-only']/tbody/tr/td/a[contains(text(),'"
							+ countryname + "')]/parent::td/following-sibling::td[@class='release-date-item__date']"))
					.getText();
			System.out.println(releaseDate);
		}
		
	}

	@Test
	public void test2() {
		driver.get("https://en.wikipedia.org/wiki/Wiki");
		driver.findElement(By.xpath("//input[@placeholder='Search Wikipedia']")).sendKeys("Pushpa: The Rise");
		driver.findElement(By.xpath("//input[@id='searchButton']")).click();

		String releaseDate = driver
				.findElement(By.xpath("//div[contains(text(),'Release date')]/parent::th/parent::tr/td/div/ul/li"))
				.getText();
		System.out.println(releaseDate);
		String country = driver.findElement(By.xpath("//th[contains(text(),'Country')]/following-sibling::td"))
				.getText();
		System.out.println(country);

	}

	@AfterMethod
	public void teardown() {
		driver.quit();
	}
}
