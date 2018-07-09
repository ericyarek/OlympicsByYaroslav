package olympics;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.github.javafaker.Faker;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Main {
	
	String url ="https://en.wikipedia.org/wiki/2016_Summer_Olympics#Medal_table";
	WebDriver driver;
	
	
	@BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		
	}
	@BeforeMethod
	public void start() {
		driver = new ChromeDriver();
		driver.get(url);
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}
		
	//@Ignore
	@Test (description = "TC-1 @Dmytriy", priority =1)
 	public void medalTable() {
		
		
// ======================================Checking if the RANK is in ascending order =====================================
		
		
		List<WebElement> ranks = driver.findElements
				(By.xpath("//table[@class = 'wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/td[1]"));
		
		List<Integer> rankActualOrder= new ArrayList<Integer>();
		  for(int i = 1; i<ranks.size()-1; i++) {
			   rankActualOrder.add(Integer.parseInt(ranks.get(i).getText()));
			   }
		List<Integer> rankExpectedOrder = new ArrayList<Integer>();
		rankExpectedOrder.addAll(rankActualOrder);
		    Collections.sort(rankExpectedOrder);
		    
		assertEquals(rankActualOrder, rankExpectedOrder, "rankActualOrder == rankExpectedOrder");
		
		System.out.println("Rank is in ascending order");
		
	
//================================================Clicking link NOC ============================================================
		
		
		   driver.findElement
		   (By.xpath("//table[@class = 'wikitable sortable plainrowheaders jquery-tablesorter']/thead/tr/th[contains(text(),'NOC')]")).click();
		   
		  
		    
//============================Checking if the COUNTRY NAMES are in ascending order after clicking =========================================
		   
		   
		     List <WebElement> countries =  driver.findElements
				   (By.xpath("//table[@class = 'wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/td[2]"));
		   
		     List <String> actualCountriesOrder = new ArrayList<String>();
		      for(int i = 1;i< countries.size() -1; i++) {
		    	  actualCountriesOrder.add(countries.get(i).toString());
		    	  
		     }
		     List <String> expectedCountriesOrder = new ArrayList<String>();
		     expectedCountriesOrder.addAll(actualCountriesOrder);
		       Collections.sort(expectedCountriesOrder);
		     
		     assertEquals(actualCountriesOrder, expectedCountriesOrder ,"actualCountriesOrder==expectedCountriesOrder");
		     
		     System.out.println("Country names are in ascending order");
		     
		     
	                                            
 
		     
 //============================Checking if the rank is in ascending order after clicking=========================================		
		     
		     
		     
		     List <WebElement> ranksNew =(List<WebElement>) driver.findElements
		    		 (By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/td[1]"));
		     
		     List <Integer> actualRankOrderAfterClick = new ArrayList<Integer>();
		       for(int i = 0; i<ranksNew.size()-1; i++) {
		    	   actualRankOrderAfterClick.add(Integer.parseInt(ranksNew.get(i).getText()));      
		            }
		     List <Integer> sortedRankOrder = new ArrayList<Integer>();
		     sortedRankOrder.addAll(actualRankOrderAfterClick);
		       Collections.sort(sortedRankOrder);
		       
		     assertNotEquals( sortedRankOrder, actualRankOrderAfterClick, "sortedRankOrder !== actualRankOrderAfterClick");    
		     
		      System.out.println("Runk is not in ascending order");
		
				
	}
	@Ignore
	@Test(description= "@Oleksandr" , priority = 2)
	public void theMost() {

		SoftAssert sofAssert = new SoftAssert();

		System.out.println(mostGoldContry());
		System.out.println(mostSilverContry());
		System.out.println(mostBronzeContry());
		System.out.println(mostMedals());

		sofAssert.assertTrue(mostGoldContry().contains("United States"));
		sofAssert.assertTrue(mostSilverContry().contains("United States"));
		sofAssert.assertTrue(mostBronzeContry().contains("United States"));
		sofAssert.assertTrue(mostMedals().contains("United States"));

		//sofAssert.assertAll();
	}
	@Ignore
	@Test (description= "@Begimai" , priority = 3)
	public void COUNTRY_BY_MEDAL() throws InterruptedException {

		List<String> expected = Arrays.asList(" China (CHN)", " France (FRA)");
		List<String> acttual = silverEqual18();
		assertEquals(acttual, expected);

	}
	
	@Ignore
	@Test(description= "@Damira" , priority = 4)
	public void GET_INDEX() throws InterruptedException {

		String actual = returnRowCol(" Japan (JPN)");
		String expected = "(6,2)";
		assertEquals(actual, expected);
	}
	@Ignore
	@Test(description= "@Tarik" , priority = 5)
	public void Sum18() {
		List<String> list= new ArrayList<String>();
		list.add("Italy");
		list.add("Australia");
		Assert.assertEquals(sumofBronze(), list);
	}
	
	
	
	
	
	
	@AfterMethod
	public void tearDown() {
		driver.close();
	}
	
	 //========================================METHODS =================================================================
	
	// OLEKSANDR START
public String mostGoldContry() {
		
		
		//Read Gold Medals Column and store it to a Map

		//Create a Map
		Map<String, Integer> goldContryList = new HashMap<String, Integer>();
		//loop through the column down 
		for (int i = 1; i < 11; i++) {
			//get name of every country while looping
			String countryName = driver.findElement(By.xpath(
					"//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr[" + i + "]/th/a"))
					.getText();
			//get all numbers of gold medals while looping
			Integer numberOfMedals = Integer.valueOf(driver.findElement(
					By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr[" + i
							+ "]/td[2]"))
					.getText());
			//store them into a Map
			goldContryList.put(countryName, numberOfMedals);  // ( key, value ) 
			
			

		}
		// Looping through the MAP and checking the KEY AND Value
		// Lets store it into a Set in order to avoid duplicates
		//getKey() - has our Country name
		//getValue() - has our Number of Medals
		Set<Entry<String, Integer>> entries = goldContryList.entrySet();

		String nameOfCountry = "";
		int countOfGoldMed = 0;

		for (Entry<String, Integer> each : entries) {

			if (each.getValue() > countOfGoldMed) {
				countOfGoldMed = each.getValue();
				nameOfCountry = each.getKey();
			}
		}

		return nameOfCountry + " has: " + countOfGoldMed + " Gold Medals";
	}

	public String mostSilverContry() {

		Map<String, Integer> goldContryList = new HashMap<String, Integer>();
		for (int i = 1; i < 11; i++) {
			String countryName = driver.findElement(By.xpath(
					"//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr[" + i + "]/th/a"))
					.getText();
			Integer numberOfMedals = Integer.valueOf(driver.findElement(
					By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr[" + i
							+ "]/td[3]"))
					.getText());
			goldContryList.put(countryName, numberOfMedals);

		}
		// Looping through the MAP
		Set<Entry<String, Integer>> entries = goldContryList.entrySet();

		String nameOfCountry = "";
		int recordGoldMed = 0;

		for (Entry<String, Integer> each : entries) {

			if (each.getValue() > recordGoldMed) {
				recordGoldMed = each.getValue();
				nameOfCountry = each.getKey();
			}
		}

		return nameOfCountry + " has: " + recordGoldMed + " Silver Medals";
	}

	public String mostBronzeContry() {

		Map<String, Integer> goldContryList = new HashMap<String, Integer>();
		for (int i = 1; i < 11; i++) {
			String countryName = driver.findElement(By.xpath(
					"//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr[" + i + "]/th/a"))
					.getText();
			Integer numberOfMedals = Integer.valueOf(driver.findElement(
					By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr[" + i
							+ "]/td[4]"))
					.getText());
			goldContryList.put(countryName, numberOfMedals);

		}
		// Looping through the MAP
		Set<Entry<String, Integer>> entries = goldContryList.entrySet();

		String nameOfCountry = "";
		int recordGoldMed = 0;

		for (Entry<String, Integer> each : entries) {

			if (each.getValue() > recordGoldMed) {
				recordGoldMed = each.getValue();
				nameOfCountry = each.getKey();
			}
		}

		return nameOfCountry + " has: " + recordGoldMed + " Bronze Medals";
	}

	
	
	public String mostMedals() {
		Map<String, Integer> mostContryList = new HashMap<String, Integer>();
		for (int i = 1; i < 11; i++) {
			String countryName = driver.findElement(By.xpath(
					"//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr[" + i + "]/th/a"))
					.getText();
			Integer numberOfMedals = Integer.valueOf(driver.findElement(
					By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr[" + i
							+ "]/td[5]"))
					.getText());
			mostContryList.put(countryName, numberOfMedals);

		}
		// Looping through the MAP
		Set<Entry<String, Integer>> entries = mostContryList.entrySet();

		String nameOfCountry = "";
		int recordOfTotalMed = 0;

		for (Entry<String, Integer> each : entries) {

			if (each.getValue() > recordOfTotalMed) {
				recordOfTotalMed = each.getValue();
				nameOfCountry = each.getKey();
			}
		}

		return nameOfCountry + " has total of: " + recordOfTotalMed + " Medals";
	}
	// OLEKSANDR END
	
	// Begimai Start
	public List<String> silverEqual18() throws InterruptedException {
		Thread.sleep(2000);

		List<WebElement> actualSilver = driver.findElements(
				By.xpath("(//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr)/td[3]"));
		List<WebElement> names = driver.findElements(
				By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/th"));
		List<String> result = new ArrayList<String>();

		for (int i = 0; i < actualSilver.size() - 1; i++) {
			WebElement each = actualSilver.get(i);

			if (Integer.parseInt(each.getText().trim()) == 18) {
				result.add(names.get(actualSilver.indexOf(each)).getText());
			}
		}

		return result;

	}
	// Begimai end
	
	// Damira start
	public String returnRowCol(String temp) throws InterruptedException {
		Thread.sleep(1000);
		List<WebElement> list = driver.findElements(
				By.xpath("(//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/th)"));

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getText().equals(temp))
				return "(" + (i + 1) + ",2)";
		}
		return null;
	}
	// Damira end
	
	// TARIK start
	public List<String> sumofBronze() {
		List<WebElement> table = new ArrayList<WebElement>();
		driver.findElement(By.xpath(
				
				"//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']//thead//th[contains(text(), 'Bronze')]"))
				.click();
		for (int i = 1; i < 11; i++) {
			if (Integer.parseInt(driver.findElement(
					By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']//tbody//tr[" + i
							+ "]//td[4]"))
					.getText()) < 18)
				table.add(driver.findElement(
						By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']//tbody//tr["
								+ i + "]//td[4]")));
		}
		Integer sum = 0;
		Faker f = new Faker();
		int index=0;
		int index2=0;
		do {
			 index= f.number().numberBetween(0, table.size());
			 do{index2=f.number().numberBetween(0, table.size());}while(index2==index);
			 
			sum = Integer.parseInt(table.get(index).getText())
					+ Integer.parseInt(table.get(index2).getText());
		} while (sum != 18);
		int tempindex=0;
		if(index>index2) {
		tempindex=index;
		index=index2;
		index2=tempindex;
		}
		List<String> sum18 = new ArrayList<String>();
	
		sum18.add(driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']//tbody//tr["
								+ (index+1) + "]//th/a")).getText());
		sum18.add(driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']//tbody//tr["
				+ (index2+1) + "]//th/a")).getText());
		System.out.println(index+", "+index2);
		return sum18;
	}
	// TARIK end
	
	
}
