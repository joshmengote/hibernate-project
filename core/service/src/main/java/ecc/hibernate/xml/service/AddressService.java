package ecc.hibernate.xml.service;

import ecc.hibernate.xml.model.Address;
import org.apache.commons.lang3.StringUtils;

public class AddressService {

    public static String addressToString(Address address) {
        return address.getStreet() + " "
                + address.getBarangay() + " "
                + address.getCity() + " " 
                + address.getZipCode();
    }

}