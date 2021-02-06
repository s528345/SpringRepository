package com.example.demo;

public class fakeData{
    public static String name = "Matthew Berry";
    public static int number = 0;
    private  String fName = "";
    private  String age = "";

    public fakeData(String fName, String age){
        this.fName = fName;
        this.age = age;

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
}
