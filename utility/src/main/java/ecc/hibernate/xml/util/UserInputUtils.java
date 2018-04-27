package ecc.hibernate.xml.util;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.apache.commons.lang3.StringUtils;

public class UserInputUtils {
    private static Scanner scan = new Scanner(System.in);
    
    public int numberWithLimit (String header, int numberOfChoices) {
        int optionSelected = 0;
        boolean inputValid = false;
        while(!inputValid) {
            try {
                System.out.print(header + " (" + numberOfChoices + " choices): ");
                String readString = scan.nextLine();
                if (StringUtils.isBlank(readString)) {
                    System.out.println("\tEnter a number.");
                } else {
                    optionSelected = Integer.parseInt(readString);
                    if (optionSelected > numberOfChoices || optionSelected <= 0){
                        System.out.println("\tInput not within option.");
                    } else {
                        inputValid = true;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("\tInvalid Input! Input not a number!");
            }
        }
        return optionSelected;
    }
    
    public int number (String header) {
        int input = 0;
        boolean inputValid = false;
        while(!inputValid) {
            try {
                System.out.print(header);
                String readString = scan.nextLine();
                if (StringUtils.isBlank(readString)) {
                    System.out.println("\tEnter a number.");
                } else {
                    input =  Integer.parseInt(readString);
                    inputValid = true;
                }
            } catch (NumberFormatException e) {
                    System.out.println("\tInvalid Input! Input not a number!");
                }
        }
        return input;
    }

    public float decimal (String header) {
        float input = 0;
        boolean inputValid = false;
        while(!inputValid) {
            try {
                System.out.print(header);
                String readString = scan.nextLine();
                if (StringUtils.isBlank(readString)) {
                    System.out.println("\tEnter a number.");
                } else {
                    input =  Float.parseFloat(readString);
                    inputValid = true;
                }
            } catch (NumberFormatException e) {
                    System.out.println("\tInvalid Input! Input not a number!");
                }
        }
        return input;
    }
    
    public String string (String header) {
        String inputString = "";
        boolean validInput = false;
        while(!validInput) {
            System.out.print(header);
            inputString = scan.nextLine();
            if (StringUtils.isBlank(inputString)) {
                System.out.println("\t Enter a string.");
                validInput = false;
            } else {
                validInput = true;
            }
        }
        return inputString;
    }

    public String stringWithNull (String header) {
        String inputString = "";
        boolean validInput = false;
        while(!validInput) {
            System.out.print(header);
            inputString = scan.nextLine();
            if (StringUtils.isBlank(inputString)) {
                System.out.print("\t Leave blank? (Y/N): ");
                String confirmationChar = scan.nextLine();
                if (confirmationChar.equalsIgnoreCase("Y")) {
                    inputString = "";
                    break;
                }
                validInput = false;
            } else {
                validInput = true;
            }
        }
        return inputString;
    }

    public Date date(String header) {
        Date date = new Date();

        System.out.print(header);
        String userDate = scan.nextLine();
                  
         String pattern = "MM/dd/yyyy";
         SimpleDateFormat sdf = new SimpleDateFormat();
         try {
            sdf.applyPattern(pattern);
            sdf.setLenient(false);
            date = sdf.parse(userDate);
         } catch (ParseException e) {
            System.out.println("\t No such date. Please try again.");
            date(header);
         }
        return date;
    }
    
    public String mobileNumber (String header) {
        String inputString = "";
        boolean validInput = false;
        while(!validInput) {
            System.out.print(header);
            inputString = scan.nextLine();
            if (inputString.matches("09\\d{9}")) {
                validInput = true;
            } else {
                System.out.println("\t Enter a local mobile number [Pattern: 09XXXXXXXXX]. Please try again.");
            }
        }
        return inputString;
    }

    public String landline (String header) {
        String inputString = "";
        boolean validInput = false;
        while(!validInput) {
            System.out.print(header);
            inputString = scan.nextLine();
            if (inputString.matches("\\d{7}")) {
                validInput = true;
            } else {
                System.out.println("\t Enter a local landline [Pattern: XXX-XXXX]. Please try again.");
            }
        }
        return inputString;
    }

}