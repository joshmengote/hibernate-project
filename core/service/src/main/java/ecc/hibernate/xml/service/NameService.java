package ecc.hibernate.xml.service;

import ecc.hibernate.xml.model.Name;

public class NameService {

    public String nameToString(Name name) {
        String title = "";
        String suffix = "";

        title = (name.getTitle() != null && !name.getTitle().isEmpty()) ? name.getTitle() + "." : "";
        suffix = (name.getSuffix() != null && !name.getSuffix().isEmpty()) ? "," + name.getSuffix() : "";

        return title + name.getFirstName() + " " 
                + name.getMiddleName() + " " 
                + name.getLastName() + suffix;
    }

}