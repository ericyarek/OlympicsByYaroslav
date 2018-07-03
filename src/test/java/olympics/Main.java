package olympics;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
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
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}
		
	//@Ignore
	@Test
	(priority = 1) 
	public void medalTable() {

		//  1  Opening URL
		driver.get(url);
		
		//   2  Creating Expected and Actual Lists
		List<Integer> expectedAscOrder = new ArrayList<Integer>();
		List<Integer> actualAscOrder = new ArrayList<Integer>();
		
		//Setting our rankCells to WebElement LIST and expectedAscOrder LIST
		List<WebElement> rankCells = new ArrayList<WebElement>();
		
		for (int i = 1; i < 11; i++) {
		rankCells.add(driver.findElement(By.xpath("(//table[8]/tbody/tr["+i+"]/td)[1]")));
		expectedAscOrder.add(i);
		}
		
		// Setting actualAscOrder list by parcing the rankCells into the INTEGER
		for(WebElement each : rankCells) {
			actualAscOrder.add(Integer.valueOf(each.getText()));	
		}
		
		//VERIFYING the result.
		assertEquals(actualAscOrder, expectedAscOrder,  "actualAscOrder == expectedAscOrder");
		
		
		// 3. ClicklinkNOC.
		driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/thead/tr/th[2]")).click();
		
		
		// 4. Now verify that the table is now sorted by the country names.
		List<String> coutryListExpected = new ArrayList<String>();
		List<String> coutryListActual = new ArrayList<String>();
		
		List<WebElement> coutryWebs = new ArrayList<WebElement>();
		coutryWebs = driver.findElements(By.xpath("//table[8]//tbody//th"));
		
		//Setting 2 lists with COMPARISON DATA
		for(WebElement each : coutryWebs) {
			System.out.println(each.getText());
			coutryListExpected.add(each.getText());
			coutryListActual.add(each.getText());
		}
		//Sorting EXPECteD list in ASC order
		Collections.sort(coutryListExpected);
		
		System.out.println(coutryListExpected);
		System.out.println(coutryListActual);
		
		assertEquals(coutryListActual, coutryListExpected, "Comparing 2 contries lists");
		
		//5 VerifythatRankcolumnisnotinascendingorderanymore.
		
		for (int i = 1; i < 11; i++) {
			rankCells.add(driver.findElement(By.xpath("(//table[8]/tbody/tr["+i+"]/td)[1]")));
			expectedAscOrder.add(i);
			}
			
			// Setting actualAscOrder list by parcing the rankCells into the INTEGER
			for(WebElement each : rankCells) {
				actualAscOrder.add(Integer.valueOf(each.getText()));	
			}
			
			//VERIFYING the result.
			assertFalse(actualAscOrder.equals(expectedAscOrder));
		
		
				
	}
	//@Ignore
	@Test
	(priority = 2) 
	public void theMostGold() {
		driver.get(url);
		SoftAssert sofAssert = new SoftAssert(); // IF FAILS DOES NOT STOPS THE EXECUTION
		
		System.out.println(mostGoldContry());
		System.out.println(mostSilverContry());
		System.out.println(mostBronzeContry());
		
		sofAssert.assertTrue(mostGoldContry().contains("United States"));
		sofAssert.assertTrue(mostSilverContry().contains("United States"));
		sofAssert.assertTrue(mostBronzeContry().contains("United States"));
		
		sofAssert.assertAll();
	}
	
	//@Ignore
	@Test
	(priority = 3) 
	public void TestCase3CountryByMedal() {
		driver.get(url);
		System.out.println(CountryByMedal());
		assertTrue(CountryByMedal().contains("China"));
		assertTrue(CountryByMedal().contains("France"));
		assertFalse(CountryByMedal().contains("Italy"));
	}
	//@Ignore
	@Test
	(priority = 4) 
	public void TestCase4GetIndex() {
		driver.get(url);
	System.out.println(getIndex1("Japan"));
	System.out.println(getIndex2("Italy"));
	
	assertTrue(getIndex1("Japan").contains("row number: 6"));
	
	}
	
	@Test
	(priority = 5) 
	public void TestCase5GetSum() {
	driver.get(url);
	System.out.println(listOfSum18());
	assertEquals(listOfSum18(), Arrays.asList("Italy-Australia"));
	}
	
	
	
	@AfterMethod
	public void tearDown() {
		driver.close();
	}
	
	
	public String mostGoldContry() {
		
		Map<String,Integer> goldContryList = new HashMap<String,Integer>();
		for(int i=1; i<11; i++) {
			String countryName = driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr["+i+"]/th/a")).getText(); 
			Integer numberOfMedals = Integer.valueOf(driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr["+i+"]/td[2]")).getText());
			goldContryList.put(countryName, numberOfMedals);
			
		}
		//Looping thrue the MAP and checking the KEY AND Value
		Set<Entry<String, Integer>> entries = goldContryList.entrySet();

		String nameOfCountry = "";
		int recordGoldMed = 0;

		for (Entry<String, Integer> each : entries) {

			if (each.getValue() > recordGoldMed) {
				recordGoldMed = each.getValue();
				nameOfCountry = each.getKey();
			}
		}
		
		return nameOfCountry + " holds the recod of: "+ recordGoldMed+ " Gold Medals";
	}
	
public String mostSilverContry() {
		
		Map<String,Integer> goldContryList = new HashMap<String,Integer>();
		for(int i=1; i<11; i++) {
			String countryName = driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr["+i+"]/th/a")).getText(); 
			Integer numberOfMedals = Integer.valueOf(driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr["+i+"]/td[3]")).getText());
			goldContryList.put(countryName, numberOfMedals);
			
		}
		//Looping thrue the MAP
		Set<Entry<String, Integer>> entries = goldContryList.entrySet();

		String nameOfCountry = "";
		int recordGoldMed = 0;

		for (Entry<String, Integer> each : entries) {

			if (each.getValue() > recordGoldMed) {
				recordGoldMed = each.getValue();
				nameOfCountry = each.getKey();
			}
		}
		
		return nameOfCountry + " holds the recod of: "+ recordGoldMed+ " Silver Medals";
	}

public String mostBronzeContry() {
	
	Map<String,Integer> goldContryList = new HashMap<String,Integer>();
	for(int i=1; i<11; i++) {
		String countryName = driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr["+i+"]/th/a")).getText(); 
		Integer numberOfMedals = Integer.valueOf(driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr["+i+"]/td[4]")).getText());
		goldContryList.put(countryName, numberOfMedals);
		
	}
	//Looping thrue the MAP
	Set<Entry<String, Integer>> entries = goldContryList.entrySet();

	String nameOfCountry = "";
	int recordGoldMed = 0;

	for (Entry<String, Integer> each : entries) {

		if (each.getValue() > recordGoldMed) {
			recordGoldMed = each.getValue();
			nameOfCountry = each.getKey();
		}
	}
	
	return nameOfCountry + " holds the recod of: "+ recordGoldMed+ " Bronze Medals";
}

public List<String> CountryByMedal(){
	Map<String,Integer> goldContryList = new HashMap<String,Integer>();
	List<String> returnListCountries = new ArrayList<String>();
	
	//Setting Map
	for(int i=1; i<11; i++) {
		String countryName = driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr["+i+"]/th/a")).getText(); 
		Integer numberOfMedals = Integer.valueOf(driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr["+i+"]/td[3]")).getText());
		goldContryList.put(countryName, numberOfMedals);
		
	}
	//Looping thrue the MAP
	Set<Entry<String, Integer>> entries = goldContryList.entrySet();

//	String nameOfCountry = "";
//	int recordGoldMed = 0;

	for (Entry<String, Integer> each : entries) {

		if (each.getValue() == 18) {

			returnListCountries.add(each.getKey());
		}
	}	
	
	
	
	return returnListCountries;
}

public String getIndex1(String countryName) {
	int colNumber = 2;
	int rowNumber =-1;
	
	for( int i=1; i<11; i++) {
		try {
		if(driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr["+i+"]/th/a[text()='"+countryName+"']") ).isDisplayed()) {
			rowNumber=i;
			break;
		}
			}catch(Exception e) {
			}
	}
	
	String result = "The country of-" + countryName + " has column number: "+colNumber+ " and row number: "+rowNumber;
	return result;
}

public String getIndex2(String countryName) {
	int colNumber = 1;
	int rowNumber =10;
	int colActual = -1;
	int rowActual =-11;
	String [][] eachElement = new String [colNumber][rowNumber];
	
	label:
	for(int i=1; i<=rowNumber; i++) {
		
		for(int j=1; j<=colNumber; j++) {
			if(driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr["+i+"]/th["+j+"]/a")).getText().equals(countryName)) {
				rowActual=i+1;
				colActual=j+1;
				break label;
			}
		}
	}
		
	
		String result = "The country of-" + countryName + " has column number: "+colActual+ " and row number: "+rowActual;
	
	return result;
}

public List<String> listOfSum18() {
	List<String> returnList = new ArrayList<String>();
	
	List<WebElement> countries = 
			driver.findElements(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/th[1]/a"));
	List<WebElement> brozMedals = 
			driver.findElements(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr/td[4]"));
	
	
	brozMedals.remove(brozMedals.size()-1); // because 11 FYI
	
	Map<String, Integer> myMap = new HashMap<String, Integer>();
	for(int i=0; i<10; i++) {
		myMap.put(countries.get(i).getText(), Integer.parseInt( brozMedals.get(i).getText() ));
		
	}
	
	Set<Entry<String,Integer>> firstEntry = myMap.entrySet();
	Set<Entry<String,Integer>> secondEntry = myMap.entrySet();
	
	for(Entry<String,Integer> eachFirst : firstEntry) {
		for(Entry<String,Integer> eachSecond : secondEntry) {
			if (!eachFirst.getKey().equals(eachSecond.getKey()) && !returnList.contains(eachSecond.getKey()+"-"+eachFirst.getKey()) &&eachFirst.getValue()+eachSecond.getValue() == 18 ) {
				
				returnList.add(eachFirst.getKey()+"-"+eachSecond.getKey());
			}
		}
		
	}

	return returnList;
}


}
