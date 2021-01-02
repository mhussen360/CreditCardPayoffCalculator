package com.bankrate;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class creditCardPayoffCalculatorTest {

	@Test
	public void calculatePayoffMonthsTest() throws InterruptedException {

		// Steps to test calculate function
		System.out.println("Starting searchFlightTest");

		// Step 1: Create Driver (What is a driver)
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		WebDriver driver = new ChromeDriver();

		driver.manage().window().maximize(); // Full screen

		// Step 2: Open Page
		String url = "https://www.bankrate.com/calculators/credit-cards/credit-card-payoff-calculator.aspx";
		driver.get(url);
		System.out.println("Page is opened succefully");

		// Enter Credit Card Balance
		driver.findElement(By.xpath("//input[@id='creditCardBalance']")).sendKeys("20000");
		Thread.sleep(2000);

		// Enter Credit card's interest rate
		driver.findElement(By.xpath("//input[@id='creditCardInterestRate']")).sendKeys("3");
		Thread.sleep(2000);

		// Enter Payments per month
		driver.findElement(By.xpath("//input[@id='creditCardMonthlyPayment']")).sendKeys("300");
		Thread.sleep(3000);

		// Sometimes we need to use JavascriptExecutor interface in order to click some elements.(ElementClickIntercepted Exception)
		WebElement el = driver.findElement(By.xpath("/html//button[@id='calculate']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", el);

		Thread.sleep(5000);

		// Print the number of months to payoff debt
		String numMonths = driver.findElement(By.xpath("//div[@class='payoff-results__large-number']")).getText();
		System.out.println("It takes " + numMonths + " months to payoff the debt ");

		// Close browser
		driver.quit();

	}
}
