package com.example.demo;

import com.example.demo.DemoViewModel.DemoPerson;
import com.example.demo.validation.ApiValidationHandler.ApiValidationHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@SpringBootTest
class HelloWorldApplicationTests {

	@Autowired
	ApiValidationHandler apiValidationHandler;

	@Test
	void contextLoads() {
	}

	@Test
	void testSomething(){
		assert(this.apiValidationHandler != null);
		assert( 1 == 1 );
	}


	@Test
	void testSomethingElse(){
		assert("" != null); // test fail
	}

	@Test
	void testTermRegex(){

		// generates test pattern for a TermCode
		// composition: 198#/2### w/ 10/20/30
		Pattern termPattern = Pattern.compile("^(19[8-9][0-9]|2[0-9]{3})(10|20|30)$");

		// test good patterns
		String goodPattern1 = "198910";
		String goodPattern2 = "200020";
		String goodPattern3 = "299930";
		String goodPattern4 = "199020";


		assert(termPattern.matcher(goodPattern1).matches());
		assert(termPattern.matcher(goodPattern2).matches());
		assert(termPattern.matcher(goodPattern3).matches());
		assert(termPattern.matcher(goodPattern4).matches());


		// test bad patterns
		String badPattern1 = "197810";
		String badPattern2 = "200040";
		String badPattern3 = "10";
		String badPattern4 = "20";


		assert(!termPattern.matcher(badPattern1).matches());
		assert(!termPattern.matcher(badPattern2).matches());
		assert(!termPattern.matcher(badPattern3).matches());
		assert(!termPattern.matcher(badPattern4).matches());

	}

	@Test
	public void testBarcodePattern(){

		char letterA = (char)97;
		System.out.println(letterA);

		Pattern barcodePattern = Pattern.compile("^307510[0-9]{6}[^;]$");

		// allows all special characters in ASCII subset that incorporates English Keyboard
		// note: ';' allowed so prefix pattern above this one must pass as well
		Pattern barcodePattern1 = Pattern.compile("^307510[0-9]{6}[!-~]*$");

		final String[] patternData =
				{"307510209733/", "307510209734+", "307510209735%",
						"3075102097360", "307510209732$", "307510122471X",
						"307510122493.", "307510056522-", "307510056522!"
		};

		for(String data: patternData)
			assert(barcodePattern.matcher(data).matches() && barcodePattern1.matcher(data).matches());

		final String[] injectionBadData = {"307510209733;", "307510209733\n", "307510209733£"};

		//System.out.println(injectionBadData[0].charAt(injectionBadData[0].length() -1));

		for(String data: injectionBadData)
		assert(
				!(barcodePattern1.matcher(data).matches() // catches all bad but ';'
						&&
				barcodePattern.matcher(data).matches()) // catches bad that has ';'
		);
		// both must passed in order for data to be good
		// either can pass but if both pass then regex faulty

	}

	@Test
	public void testBarcodeRegexBetter(){

		// creates three characters from ascii, decimal literals
		// 0 -> null, 32 -> space, 127 -> delete, 255 -> vacant
		char nullChar = (char)0;
		char spaceChar = (char)32;
		char deleteChar = (char)127;
		char endOfSpecialChar = (char)255;

		// creates regex pattern that follows flow
		// <prefix number: 307510> + 6 number + anything that's not:
		// within null-space, a semicolon (;), or delete
		Pattern barcodePattern = Pattern.compile("^307510[0-9]{6}[^"
				+ nullChar + "-" + spaceChar +
				deleteChar + "-" + endOfSpecialChar +
				";]$");

		// creates array of good input to pass
		final String[] patternData =
				{"307510209733/", "307510209734+", "307510209735%",
						"3075102097360", "307510209732$", "307510122471X",
						"307510122493.", "307510056522-", "307510056522!"
				};

		// asserts good input matches pattern
		for(String data: patternData)
			assert(barcodePattern.matcher(data).matches());

		// creates array of faulty/injection data
		final String[] injectionBadData = {"307510209733;", "307510209733\n", "307510209733£"};

		// asserts faulty data fails to match
		for(String data: injectionBadData)
			assert(!barcodePattern.matcher(data).matches());

	}

	@Test
	public void testWorkShopDemo() throws JSONException {

		// creates composite demo people for data storage within model map
		final DemoPerson demoPerson1 = new DemoPerson("most interesting person ever", 30);
		final DemoPerson demoPerson2 = new DemoPerson("most boring person ever", 35);

		// creates a list literal and store within model map
		List<DemoPerson> demoPersonList = Arrays.asList(demoPerson1, demoPerson2);

		for(DemoPerson demoPerson : demoPersonList)
			assert(demoPerson != null && (
					demoPerson.personName.equals("most interesting person ever") ||
							demoPerson.personName.equals("most boring person ever")
					));

		// creates a JSONArray and enumerates the JSON representation of the demo people
		JSONArray jsonArray = new JSONArray();
		jsonArray.put(demoPerson1.toJsonObject());
		jsonArray.put(demoPerson2.toJsonObject());

		for(int i = 0; i < jsonArray.length(); i++)
			assert(
					jsonArray.getJSONObject(i).getString("personName").equals("most interesting person ever") ||
							jsonArray.getJSONObject(i).getString("personName").equals("most boring person ever")
			);
			//System.out.println(i + ": " + jsonArray.getJSONObject(i).toString());
	}
}
