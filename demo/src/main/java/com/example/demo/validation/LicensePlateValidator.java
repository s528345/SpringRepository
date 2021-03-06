package com.example.demo.validation;

import com.example.demo.validation.CheckCase;
import com.example.demo.validation.CheckCaseEnum;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.util.regex.*;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;

public class LicensePlateValidator implements ConstraintValidator<CheckCase, String> {
// @Checkcase(...)
    //String someData;
    private CheckCaseEnum caseMode;
    private int _myValue;
    private String message;

    private static String staticVariable = CheckCase.GENERIC_ERROR_MESSAGE;

    @Override
    public void initialize(CheckCase constraintAnnotation) {
        this.caseMode = constraintAnnotation.value();
        this._myValue = constraintAnnotation.myTestValue();
        this.message = this.caseMode.getValue();
    }

    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
        if ( object == null ) {
            return true;
        }
        regexTests();
        useJsonObject();
        System.out.println("word after splice: " + replaceEmailSuffix());
        boolean isValid;
        if ( caseMode == CheckCaseEnum.Upper && this._myValue == 0) {
            isValid = object.equals( object.toUpperCase() );
        }
        else {
            isValid = object.equals( object.toLowerCase() );
        }

        if ( !isValid ) {
            constraintContext.disableDefaultConstraintViolation();
            constraintContext.buildConstraintViolationWithTemplate(
                    "{org.hibernate.validator.referenceguide.chapter03." +
                            "constraintvalidatorcontext.CheckCase.message}" +
                            "you've done fucked up boi"
            )
                    .addConstraintViolation();
        }

        return isValid;
    }

    private static void regexExamples(){
        Pattern myPattern = Pattern.compile(".[(][.]\'\"[\'-]" ); // a single char  ".s"
        // regex pattern

        Pattern testPattern = Pattern.compile("^[1-9][0-9]{3}-(0[1-9]|1[0,1,2])-([0,1,2][1-9]|3[0-1])$");

        Pattern horseCrap = Pattern.compile("^.*(0|[1-9])[0-9]*$");

        Pattern escape = Pattern.compile("^.*\n.*\\[$");

        Matcher m = myPattern.matcher("a(.'\"-"); // input string
        boolean b = m.matches();
        System.out.println(b);

        Matcher n = testPattern.matcher("2011-1,-,1");
        System.out.println(n.matches());

        Matcher horse = horseCrap.matcher("a01");
        System.out.println(horse.matches());

        Matcher escapeM = escape.matcher("blah\n[");
        System.out.println(escapeM.matches());
    }
    // private static final Pattern studentIdPattern = Pattern.compile("^919[0-9]{6,6}$");

    private static void regexTests(){

        // 919 pattern
        Pattern studentIdPattern = Pattern.compile("^919[0-9]{6,6}$");
        // inclusive upper & lower bound
        // yes, {6,6} == {6} I want to show that
        Matcher studentIdMatcher = studentIdPattern.matcher("919123456");
        System.out.println("919 matcher: " + studentIdMatcher.matches());

        /*
        S_Number (note: there is two)
          1) follows form S + 6 numbers
          OR
          2) S + 6 numbers + @ + anything else

          if 1 fails - abort
          if 1 passes and 2 passes - splice end
          if 1 passes and 2 fails - proceed
        */
        Pattern sNumberPrefix = Pattern.compile("^[s,S][0-9]{6}(@(.*)|)$");
        Pattern sNumberSuffix = Pattern.compile("^.{7}@.*$"); // don't care if nw domain right now

        final String sNumberNoSuffix1 = "s123456";
        final String sNumberNoSuffix2 = "S123456";
        final String sNumberSuffix1 = "s123456@nwmissouri.edu";
        final String sNumberSuffix2 = "S123456@nwmissouri.edu";

        final String[] noSuffixTest = {sNumberNoSuffix1, sNumberNoSuffix2};
        final String[] suffixTest = {sNumberSuffix1, sNumberSuffix2};

        System.out.println("test for S-number (no suffix):");

        for(String testString : noSuffixTest){
            Matcher matcher = sNumberPrefix.matcher(testString);
            Matcher matcher1 = sNumberSuffix.matcher(testString);
            System.out.println("prefix matcher: " + matcher.matches());
            System.out.println("suffix matcher: " + matcher1.matches());
        }

        System.out.println("test for S-number (suffix):");
        for(String testString : suffixTest){
            Matcher matcher = sNumberPrefix.matcher(testString);
            Matcher matcher1 = sNumberSuffix.matcher(testString);
            System.out.println("prefix matcher: " + matcher.matches());
            System.out.println("suffix matcher: " + matcher1.matches());
        }

    }

    private static void useJsonObject(){
        try {
            JSONObject json = new JSONObject();
            // can create json object with a json string too in constructor
            // can capture api (NOT web controller form data) data is the consumption is labeled
            // application/json -- @RequestBody String jsonString
            // Map<String, Object> suits our purposes, however

            JSONObject subJson = new JSONObject();
            subJson.put("subTestInt", 5);
            subJson.put("subTestString", "string");

            int[] array = {1, 2, 3};

            json.put("testInt", 3);
            json.put("testBool", true);
            json.put("testNull", null);
            json.put("testObject", subJson);

            JSONArray jsonArray = new JSONArray();
            for(int value: array)
                jsonArray.put(value); // keep as same data type... or else

            json.put("arrayOfInts", jsonArray);

            System.out.println(json.toString());

            jsonArray = json.getJSONArray("arrayOfInts");

            for (int i = 0; i < jsonArray.length(); i++)
                System.out.println("Index value(" + i + "): " + jsonArray.getInt(i));

        }
        catch(Exception ex){
            System.out.println("oops: " + ex.getMessage());
        }

    }

    private static @NotNull String replaceEmailSuffix(){
        // "s123456@gmail.com"
        return "s123456@gmail.com".replaceAll("@.*","");
    }
}
