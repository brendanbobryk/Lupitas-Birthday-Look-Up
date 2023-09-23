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

    // This is a private and static hashmap to store the birthdays
    private static HashMap<String, String> birthdayMap = new HashMap<String, String>();

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

    public static void initializeMap(final String pathToFile) {
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
    }

    public static void main(final String[] args) {
        String pathToFile = "C:\\Users\\Brendan\\Documents\\GitHub\\Lupitas-Birthday-Look-Up\\lupitasbirthdaylookup\\src\\main\\java\\com\\cis084101javaprogramming\\birthday.json";

        // Initializes the hash map
        initializeMap(pathToFile);

        // Prompt to read user's input from keyboard
        System.out.println("Reading user input into a string");

        // Get user input
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a name: ");
        String inputtedName = input.nextLine().toLowerCase();

        // Ends program if user hits enter without entering prompt
        if (inputtedName.length() < 1) {
            System.out.println("There are no birthdays in the file for that entry.");
            return;
        }

        // Declarations
        JSONArray jsonData = readJSONArrayFile(pathToFile);
        String name, birthday;
        JSONObject obj;
        // Array List to store name matches
        ArrayList<String> nameArrayList = new ArrayList<String>();

        // Loop to traverse JSON file
        int nameCount = 0;
        for (Integer i = 0; i < jsonData.size(); i++) {

            // Parse the object and pull out the name
            obj = (JSONObject) jsonData.get(i);
            name = (String) obj.get("name");

            // Checks name to see if it matches input
            if (name.toLowerCase().contains(inputtedName.substring(0, inputtedName.length()))) {
                nameArrayList.add(name);
                nameCount++;
            }
        }

        // Loops through nameArrayList and prints the name and birthday for each person
        // found in the search
        for (int i = 0; i < nameCount; i++) {
            // Prints user's input
            System.out.println("Name = " + nameArrayList.get(i));

            // Prints the name's birthday
            birthday = birthdayMap.get(nameArrayList.get(i));
            System.out.println("Their birthday is: " + birthday);
        }

        // Close the scanner
        input.close();
    }
}
