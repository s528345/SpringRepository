package com.example.demo.DemoViewModel;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

public final class DemoPerson {

    public String personName;

    public int personAge;

    public DemoPerson(String personName, int personAge){
        this.personAge = personAge;
        this.personName = personName;
    }

    public JSONObject toJsonObject(){
        final JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("personName", this.personName);
            jsonObject.put("personAge", this.personAge);

            return jsonObject;
        }
        catch(JSONException ex){
            System.out.println("oh this is not good...");

            return null;
        }
    }

}
