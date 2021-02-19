package com.example.demo;


import com.example.demo.validation.CheckCase;
import com.example.demo.validation.CheckCaseEnum;
import org.hibernate.annotations.Check;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.*;
import java.util.Optional;

import static java.util.Optional.empty;

public class fakeData{
    public static String name = "Matthew Berry";
    public static int number = 0;

    @Size(min = 0, max = 10)
    private  String fName = "";
    @NotNull
    @Size(min = 0, max = 100, message = "whoops")
    @CheckCase(value = CheckCaseEnum.Lower, myTestValue = 0)
    private  String age = "";
    private InnerTest _innerTest = new InnerTest(0, 1);

    public fakeData(String fName, String age){
        this.fName = fName;
        this.age = age;
    }

    // dummy constructor for testing static context threshold of functional-mapping
    public fakeData(@NotNull Car car){
        this.fName = "some car owner";
        this.age = "95"; // who made this a string like da fuq?
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public static String staticCallTest(@NotNull Car car){
        return car.toString() + " called successful :)";
    }

     public class InnerTest{
        private int x, y;

        public InnerTest(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
}
/*
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
//@Constraint(validatedBy = { ValidPassengerCountValidator.class })
@Documented
 @interface ValidPassengerCount {

    String message() default "{org.hibernate.validator.referenceguide.chapter06.classlevel." +
            "ValidPassengerCount.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
} */

//enum CaseMode {upper, lower}
//
//@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, TYPE }) // add TYPE to be applicable to class
//@Retention(RUNTIME)
//@Constraint(validatedBy = CheckCaseValidator.class)
//@Documented
//@interface CheckCase {
//
//    String message() default "{org.hibernate.validator.referenceguide.chapter06.CheckCase." +
//            "message}";
//
//    Class<?>[] groups() default { };
//
//    Class<? extends Payload>[] payload() default { };
//
//    CaseMode value() default CaseMode.lower;
//
//    int myTestValue() default 0;
//
//    @Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
//    @Retention(RUNTIME)
//    @Documented
//    @interface List {
//        CheckCase[] value();
//    }
//}
// class CheckCaseValidator implements ConstraintValidator<CheckCase, String> {
//
//    private CheckCaseEnum caseMode;
//    private int _myValue;
//
//    @Override
//    public void initialize(CheckCase constraintAnnotation) {
//        this.caseMode = constraintAnnotation.value();
//        this._myValue = constraintAnnotation.myTestValue();
//    }
//
//    @Override
//    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
//        if ( object == null ) {
//            return true;
//        }
//        boolean isValid;
//        if ( caseMode == CheckCaseEnum.Upper && this._myValue == 0) {
//            isValid = object.equals( object.toUpperCase() );
//        }
//        else {
//            isValid = object.equals( object.toLowerCase() );
//        }
//
//        if ( !isValid ) {
//            constraintContext.disableDefaultConstraintViolation();
//            constraintContext.buildConstraintViolationWithTemplate(
//                    "{org.hibernate.validator.referenceguide.chapter03." +
//                            "constraintvalidatorcontext.CheckCase.message}" +
//                            "you've done fucked up boi"
//            )
//                    .addConstraintViolation();
//        }
//
//        return isValid;
//    }
//}

// @CheckCase(value = CaseMode.lower, myTestValue = 0, message = "now I is here")
// ofc it's nonsense but ya can add it if target is TYPE
// class Car {
//
//    @NotNull
//    private String manufacturer;
//
//    @NotNull
//    @Size(min = 2, max = 14)
//    @CheckCase(value = CheckCaseEnum.Upper, myTestValue = 0, message = "oops")
//    private String licensePlate;
//
//    @Min(2)
//    private int seatCount;
//
//    public Car ( String manufacturer, String licencePlate, int seatCount ) {
//        this.manufacturer = manufacturer;
//        this.licensePlate = licencePlate;
//        this.seatCount = seatCount;
//    }
//
//    public Optional<Car> getSelfCar(){
//        Optional<String> opt = Optional.of("");
//        Optional<String> opt1 = Optional.ofNullable(null);
//        Optional<Car> opt2 = Optional.ofNullable(this);
//        return opt2;
//    }
//
//    //getters and setters ...
//}