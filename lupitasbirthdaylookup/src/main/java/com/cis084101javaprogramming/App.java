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
        String name = input.nextLine();

        // Prints user's input
        System.out.println("Name = " + name);

        // Prints the name's birthday
        String birthday = birthdayMap.get(name);
        System.out.println("Their birthday is: " + birthday);

        // Close the scanner
        input.close();
    }
}
