package switchtwentytwenty.project.dto.family;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FamilyInputDTO {
    private String name;
    private String vat;
    private String email;
    private String phoneNumber;
    private String location;
    private String street;
    private String postCode;
    private String birthDate;
    private String familyName;
    private String password = "password";

    public FamilyInputDTO(String name, String vat, String email, String phoneNumber, String location, String street, String postCode, String birthDate, String familyName) {
        this.name = name;
        this.vat = vat;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.street = street;
        this.postCode = postCode;
        this.birthDate = birthDate;
        this.familyName = familyName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

}
