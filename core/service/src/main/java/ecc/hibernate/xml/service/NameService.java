package ecc.hibernate.xml.service;

import ecc.hibernate.xml.model.Name;
import org.apache.commons.lang3.StringUtils;

public class NameService {

    public static String nameToString(Name name) {
        String nameString;
        nameString = name.getTitle() + " "
                    + name.getFirstName() + " "
                    + name.getMiddleName() + " " 
                    + name.getLastName() + ", "
                    + name.getSuffix();
        return nameString;
    }

    public static Name stringToName(String nameString) {
        String[] fullName;
        String title;
        String firstName;
        String middleName;
        String lastName;
        String suffix;        

        fullName = StringUtils.split(nameString, '\n');
        
        title = fullName[0];
        firstName = fullName[1];
        middleName = fullName[2];
        lastName = fullName[3];
        suffix = fullName[4];


        Name name = new Name(firstName,middleName,lastName);
        name.setTitle(title);
        name.setSuffix(suffix);
        return name;
    }

}