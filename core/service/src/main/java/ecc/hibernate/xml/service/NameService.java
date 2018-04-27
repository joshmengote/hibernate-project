package ecc.hibernate.xml.service;

import ecc.hibernate.xml.model.Name;
import org.apache.commons.lang3.StringUtils;

public class NameService {

    public static String nameToString(Name name) {
        String title = "";
        String suffix = "";
        if (name.getTitle() != null && !name.getTitle().isEmpty()) {
            title = name.getTitle() + ". ";
        } else if (name.getSuffix() != null && !name.getSuffix().isEmpty()) {
            suffix = ", " + name.getSuffix();
        } else {
            title = "";
            suffix = "";
        }

        return title + name.getFirstName() + " " 
                + name.getMiddleName() + " " 
                + name.getLastName() + suffix;
    }

}