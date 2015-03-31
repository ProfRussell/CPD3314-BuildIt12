/*
 * Copyright 2014 Len Payne <len.payne@lambtoncollege.ca>.
 * Updated 2015 Mark Russell <mark.russell@lambtoncollege.ca>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cpd3314.buildit12;

import java.io.Serializable;
import org.json.simple.JSONObject;

/**
 * Build a Car class that has make, model and year. Make it both Serializable,
 * and give it a toJSON output method, and a JSONObject constructor.
 *
 * @author Len Payne <len.payne@lambtoncollege.ca>
 */
public class Car implements Serializable {

    private String make;
    private String model;
    private long year;

    public Car() {
    }

    public Car(String make, String model, long year) {
        this.make = make;
        this.model = model;
        this.year = year;
    }

    public Car(JSONObject json) {
        this.make = (String) json.get("make");
        this.model = (String) json.get("model");
        this.year = (long) json.get("year");
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("make", make);
        json.put("model", model);
        json.put("year", year);
        return json;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public long getYear() {
        return year;
    }

    public void setYear(long year) {
        this.year = year;
    }

}
