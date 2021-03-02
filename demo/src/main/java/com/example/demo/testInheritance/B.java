package com.example.demo.testInheritance;

public final class B extends A{

    public String lName;

    public B(String lName, String fName){
        super(fName);
        this.lName = lName;
    }

    @Override
    public String getName(){
        return lName;
    }

    public static void printFullName(){
        // creates dummy instance
        B b = new B("doe", "john");

        System.out.println(((A)b).getName() + " " + b.getName());
    }
}
