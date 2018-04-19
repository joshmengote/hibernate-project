package ecc.hibernate.xml.util;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.apache.commons.lang3.StringUtils;

public class UserInputUtil {
	private static Scanner scan = new Scanner(System.in);
	
	public static int numberWithLimit (String header, int numberOfChoices) {
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
	
	public static int number (String header) {
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

    public static float decimal (String header) {
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

	
	public static String string (String header) {
		String inputString = null;
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

    public static String stringWithNull (String header) {
        String inputString = null;
        String confirmationChar = null;
        boolean validInput = false;
        while(!validInput) {
            System.out.print(header);
            inputString = scan.nextLine();
            if (StringUtils.isBlank(inputString)) {
                System.out.print("\t Leave blank? (Y/N): ");
                confirmationChar = scan.nextLine();
                if (confirmationChar.equalsIgnoreCase("Y")) {
                    inputString = null;
                    break;
                }
                validInput = false;
            } else {
                validInput = true;
            }
        }
        return inputString;
    }

    public static String date(String header) {
        boolean validInput = false;
        String input = "";
        int month;
        int day;
        int year;
        while(!validInput) {
            System.out.print(header);
            input = scan.nextLine();
            if (StringUtils.isBlank(input)) {
                System.out.println("\tEnter a date.");
            } else {
                Matcher dateFormat = Pattern.compile("(\\d{2})/(\\d{2})/(\\d{4})").matcher(input);
                if (dateFormat.find()) {
                    month = Integer.parseInt(dateFormat.group(1));
                    day = Integer.parseInt(dateFormat.group(2));
                    year = Integer.parseInt(dateFormat.group(3));
                    if (month<=12 && day<=31) {
                        validInput = true;
                    } else {
                        System.out.println("\tNo such date");
                    }
                } else {
                    System.out.println("\tUse the format MM/DD/YYYY");
                }
            }
        }
      return input;
   }
	
}