package com.bankrate;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CalculatePayoffMonthsTest { // Testing a calculator that outputs the number of months required to payoff
											// credit card debt.

	private WebDriver driver;

	@BeforeMethod(alwaysRun = true)
	public void setUp() {

		// Steps to test calculate function
		System.out.println("Starting CalculatePayoffMonthsTest");

		// Step 1: Create Driver (What is a driver)
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		 driver = new ChromeDriver();

		driver.manage().window().maximize(); // Full screen

		// Step 2: Open Page
		String url = "https://www.bankrate.com/calculators/credit-cards/credit-card-payoff-calculator.aspx";
		driver.get(url);
		System.out.println("Page is opened succefully");

	}

	@Test(priority = 1, groups = { "positiveTest" })
	public void positivecalcualteNumMonthsTest() {// login test Case

		// Enter Credit Card Balance
		driver.findElement(By.xpath("//input[@id='creditCardBalance']")).sendKeys("20000");
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		// Enter Credit card's interest rate
		driver.findElement(By.xpath("//input[@id='creditCardInterestRate']")).sendKeys("3");
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		// Enter Payments per month
		driver.findElement(By.xpath("//input[@id='creditCardMonthlyPayment']")).sendKeys("300");
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		// Sometimes we need to use JavascriptExecutor interface in order to click some
		// elements.(ElementClickIntercepted Exception)
		WebElement el = driver.findElement(By.xpath("/html//button[@id='calculate']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", el);

		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		// Verify that Actual number of months to pay off debt does match expected
		// number of months
		String ActualnumMonths = driver.findElement(By.xpath("//div[@class='payoff-results__large-number']")).getText();
		Assert.assertEquals(ActualnumMonths, "74",
				"Actual number of months to payoff debt does not match expected months");

	}

	@Parameters({ "balance" })
	@Test(priority = 2, groups = { "largeTest" })
	public void veryLargeBalanceTest(String balance) {

		// Enter invalid Credit Card Balance
		driver.findElement(By.xpath("//input[@id='creditCardBalance']")).sendKeys(balance);
		;
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		// Enter Credit card's interest rate
		driver.findElement(By.xpath("//input[@id='creditCardInterestRate']")).sendKeys("3");
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		// Enter Payments per month
		driver.findElement(By.xpath("//input[@id='creditCardMonthlyPayment']")).sendKeys("300");
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		// Sometimes we need to use JavascriptExecutor interface in order to click some
		// elements
		WebElement el = driver.findElement(By.xpath("/html//button[@id='calculate']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", el);

		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		// Print the number of months to payoff debt
		String numMonths = driver.findElement(By.xpath("//div[@class='payoff-results__large-number']")).getText();
		System.out.println("It takes " + numMonths + " months to payoff the debt ");

		String ActualErrorMessage = driver.findElement(By.xpath(
				"//div[@id='credit-card-payoff-calculator']//form/div[@value='1,000,000']/span[@class='error-message']"))
				.getText();
		Assert.assertTrue(ActualErrorMessage.contains("Please enter a value between 1 and 999,999"),
				"Actual Message: " + ActualErrorMessage);

	}
	
	@Parameters({ "balance" })
	@Test(priority = 3, groups = { "zeroTest" })
	public void zeroBalanceTest(String balance) {

		// Enter invalid Credit Card Balance
		driver.findElement(By.xpath("//input[@id='creditCardBalance']")).sendKeys(balance);
		;
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		// Enter Credit card's interest rate
		driver.findElement(By.xpath("//input[@id='creditCardInterestRate']")).sendKeys("3");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		// Enter Payments per month
		driver.findElement(By.xpath("//input[@id='creditCardMonthlyPayment']")).sendKeys("300");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		// Sometimes we need to use JavascriptExecutor interface in order to click some
		// elements
		WebElement el = driver.findElement(By.xpath("/html//button[@id='calculate']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", el);

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		// Print the number of months to payoff debt
		String numMonths = driver.findElement(By.xpath("//div[@class='payoff-results__large-number']")).getText();
		System.out.println("It takes " + numMonths + " months to payoff the debt ");

		String ActualErrorMessage = driver.findElement(By.xpath(
				"//div[@id='credit-card-payoff-calculator']//form/div[@value='0']/span[@class='error-message']"))
				.getText();
		Assert.assertTrue(ActualErrorMessage.contains("Please enter a value between 1 and 999,999"),
				"Actual Message: " + ActualErrorMessage);

	}

	@AfterMethod(alwaysRun = true)
	private void tearDown() {
		// Close Browser - Destroys driver instance
		driver.quit();
	}

}
