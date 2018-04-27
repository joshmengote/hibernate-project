package ecc.hibernate.xml.service;

import ecc.hibernate.xml.model.Address;

public class AddressService {

    public String addressToString(Address address) {
        return address.getStreet() + " "
                + address.getBarangay() + " "
                + address.getCity() + " " 
                + address.getZipCode();
    }

}