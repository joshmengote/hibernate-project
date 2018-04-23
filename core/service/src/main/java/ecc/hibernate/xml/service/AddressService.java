package ecc.hibernate.xml.service;

import ecc.hibernate.xml.model.Address;
import org.apache.commons.lang3.StringUtils;

public class AddressService {

    public static String addressToString(Address address) {
        String addressString;
        addressString = address.getStreet() + " "
                    + address.getBarangay() + " "
                    + address.getCity() + " " 
                    + address.getZipCode();
        return addressString;
    }

    public static Address stringToAddress(String addressString) {
        String street;
        String barangay;
        String city;
        int zip;

        String[] fullAddress;
        fullAddress = StringUtils.split(addressString, '\n');
        street = fullAddress[0];
        barangay = fullAddress[1];
        city = fullAddress[2];
        zip = Integer.parseInt(fullAddress[3]);
        Address address = new Address(street, barangay, city, zip);
        return address;
    }

}