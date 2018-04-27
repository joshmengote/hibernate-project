package ecc.hibernate.xml.service;

import ecc.hibernate.xml.model.Name;

public class NameService {

    public String nameToString(Name name) {
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