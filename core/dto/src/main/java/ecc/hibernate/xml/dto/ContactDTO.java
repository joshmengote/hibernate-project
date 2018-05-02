package ecc.hibernate.xml.dto;

public class ContactDTO {
    private Long id;
    private String type;
    private String information;
    public ContactDTO() {}
    public ContactDTO(String type, String information) {
        this.type = type;
        this.information = information;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getInformation() {
        return information;
    }
    public void setInformation(String information) {
        this.information = information;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}