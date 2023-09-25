package com.cis084101javaprogramming;

import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;

/**
 * Name: Brendan Bobryk
 * Student ID #: 1036738
 * Class: CIS 084 Java Programming
 * Date: 9/22/23
 * Program: Lupita's Birthday Look Up
 */

public class App {

    // Reads the JSON file
    public static JSONArray readJSONArrayFile(String fileName) {
        // JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        JSONArray birthdayArr = null;

        // Read JSON file
        try (FileReader reader = new FileReader(fileName)) {
            Object obj = jsonParser.parse(reader);

            birthdayArr = (JSONArray) obj;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return birthdayArr;
    }

    public static HashMap<String, String> initializeMap(final String pathToFile) {
        HashMap<String, String> birthdayMap = new HashMap<String, String>();
        JSONArray jsonData = readJSONArrayFile(pathToFile);

        // Declarations
        String birthday, name;
        JSONObject obj;

        // Loop over list
        for (Integer i = 0; i < jsonData.size(); i++) {

            // Parse the object and pull out the name and birthday
            obj = (JSONObject) jsonData.get(i);
            birthday = (String) obj.get("birthday");
            name = (String) obj.get("name");

            // Add the name and birthday in to a hashmap
            birthdayMap.put(name, birthday);
        }
        return birthdayMap;
    }

    public static void main(final String[] args) {
        String pathToFile = "./birthday.json";
        // Initializes the hash map
        // NOTE: Changed initializeMap function to explicitly return the hashMap for
        // better visibility
        HashMap<String, String> birthdayMap = initializeMap(pathToFile);

        // Prompt to read user's input from keyboard
        System.out.println("Reading user input into a string");

        // Get user input
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a name: ");
        String inputtedName = input.nextLine();

        // Ends program if user hits enter without entering prompt
        if (inputtedName.length() < 1) {
            System.out.println("An input must be entered to reutrn a birthday.");
            input.close();
            return;
        }

        // Declarations
        String birthday;
        // Array List to store name matches
        ArrayList<String> nameMatchList = new ArrayList<String>();

        // Loop to traverse birthdayMap and gather all names that match the user's input
        // They will be saved inside of an array list named nameMatchList
        for (String nameOfPerson : birthdayMap.keySet()) {
            // Checks name to see if it matches input
            if (nameOfPerson.toLowerCase().contains(inputtedName.toLowerCase())) {
                nameMatchList.add(nameOfPerson);
            }
        }

        if (nameMatchList.size() == 0) {
            System.out.println("No matches were found with the given input \"" + inputtedName + "\".");
            input.close();
            return;
        }

        System.out.println("The search of " + inputtedName + " returned " + nameMatchList.size() + " results.");

        // Loops through nameMatchList and prints the name and birthday for each person
        // found in the search
        for (int i = 0; i < nameMatchList.size(); i++) {
            // Prints user's input
            System.out.println("Name = " + nameMatchList.get(i));

            // Prints the name's birthday
            birthday = birthdayMap.get(nameMatchList.get(i));
            System.out.println("Their birthday is: " + birthday);
        }

        // Close the scanner
        input.close();
    }
}
