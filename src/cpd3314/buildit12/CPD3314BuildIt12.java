/*
 * Copyright 2014 Len Payne <len.payne@lambtoncollege.ca>.
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

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Len Payne <len.payne@lambtoncollege.ca>
 */
public class CPD3314BuildIt12 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Use this Sandbox to call other methods to run the examples.
        
        doBuildIt2Output();
        doBuildIt2Input();
    }

    /**
     * Build a program that asks the user for a filename. It then reads a file
     * line-by-line, and outputs its contents in uppercase.
     *
     * Make sure that the program exits gracefully when the file does not exist.
     *
     */
    public static void doBuildIt1() {
        // Prompt the User for a Filename
        Scanner kb = new Scanner(System.in);
        System.out.println("Filename: ");
        String filename = kb.next();

        try {
            // Attempt to Access the File, this may cause an Exception
            File file = new File(filename);
            Scanner input = new Scanner(file);

            // Iterate through the File and Output in Upper Case
            while (input.hasNext()) {
                System.out.println(input.nextLine().toUpperCase());
            }
        } catch (IOException ex) {
            // If the File is Not Found, Tell the User and Exit Gracefully
            System.err.println("File not found: " + filename);
        }
    }

    /**
     * Build a sample method that saves a handful of car instances as Serialized
     * objects, and as JSON objects.
     */
    public static void doBuildIt2Output() {
        Car[] cars = {
            new Car("Honda", "Civic", 2001),
            new Car("Buick", "LeSabre", 2003),
            new Car("Toyota", "Camry", 2005)
        };

        // Saves as Serialized Objects
        try {
            FileOutputStream objFile = new FileOutputStream("cars.obj");
            ObjectOutputStream objStream = new ObjectOutputStream(objFile);

            for (Car car : cars) {
                objStream.writeObject(car);
            }

            objStream.close();
            objFile.close();
        } catch (IOException ex) {
            Logger.getLogger(CPD3314BuildIt12.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Saves as JSONObject
        try {
            PrintWriter jsonStream = new PrintWriter("cars.json");

            JSONArray arr = new JSONArray();
            for (Car car : cars) {
                arr.add(car.toJSON());
            }

            JSONObject json = new JSONObject();
            json.put("cars", arr);

            jsonStream.println(json.toJSONString());

            jsonStream.close();
        } catch (IOException ex) {
            Logger.getLogger(CPD3314BuildIt12.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Build a sample method that saves a handful of car instances as Serialized
     * objects, and as JSON objects, then re-open the saved versions in a
     * different method
     */
    public static void doBuildIt2Input() {
        // Unserialize the Objects -- Save them to an ArrayList and Output
        try {
            FileInputStream objFile = new FileInputStream("cars.obj");
            ObjectInputStream objStream = new ObjectInputStream(objFile);

            ArrayList<Car> cars = new ArrayList<>();

            boolean eof = false;
            while (!eof) {
                try {
                    cars.add((Car) objStream.readObject());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(CPD3314BuildIt12.class.getName()).log(Level.SEVERE, null, ex);
                } catch (EOFException ex) {
                    // We must catch the EOF exception to leave the loop
                    eof = true;
                }
            }

            System.out.println("Unserialized Data:");
            for (Car car : cars) {
                System.out.printf("Make: %s, Model: %s, Year: %d\n",
                        car.getMake(), car.getModel(), car.getYear());
            }
        } catch (IOException ex) {
            Logger.getLogger(CPD3314BuildIt12.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Read from JSON and Output
        try {
            File file = new File("cars.json");
            Scanner input = new Scanner(file);
            while (input.hasNext()) {
                JSONParser parse = new JSONParser();
                try {
                    // This should be the root JSON Object, which has an array of Car objects
                    JSONObject json = (JSONObject) parse.parse(input.nextLine());

                    // We pull the array out to work with it
                    JSONArray cars = (JSONArray) json.get("cars");
                    
                    System.out.println("Un-JSON-ed Data:");
                    for (Object car : cars) {
                        // Convert each Object to a JSONObject
                        JSONObject carJSON = (JSONObject) car;
                        // Convert each JSONObject to an actual Car
                        Car carObj = new Car(carJSON);
                        // Output from the Actual Car class                        
                        System.out.printf("Make: %s, Model: %s, Year: %d\n",
                                carObj.getMake(), carObj.getModel(), carObj.getYear());
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(CPD3314BuildIt12.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(CPD3314BuildIt12.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
