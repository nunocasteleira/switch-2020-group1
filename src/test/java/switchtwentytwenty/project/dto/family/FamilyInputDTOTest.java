package switchtwentytwenty.project.dto.family;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.model.shared.FamilyId;

import static org.junit.jupiter.api.Assertions.*;

class FamilyInputDTOTest {

    @Test
    void verifyNoArgsConstructor() {
        FamilyInputDTO familyInputDTO = new FamilyInputDTO();

        assertNotNull(familyInputDTO);
    }

    @Test
    void verifyAllArgsConstructor() {
        String name = "Antónia";
        String vat = "223456789";
        String email = "antonia@gmail.com";
        String phoneNumber = "918765456";
        String location = "Alentejo";
        String street = "Rua da Amargura";
        String postCode = "4589-689";
        String birthDate = "12/01/1983";
        String familyName = "Silva";
        String password = "password";

        FamilyInputDTO familyInputDTO = new FamilyInputDTO(name, vat, email, phoneNumber, location, street, postCode, birthDate, familyName);

        assertNotNull(familyInputDTO);
    }


    @Test
    void verifyGetter() {
        String name = "Antónia";
        String vat = "223456789";
        String email = "antonia@gmail.com";
        String phoneNumber = "918765456";
        String location = "Alentejo";
        String street = "Rua da Amargura";
        String postCode = "4589-689";
        String birthDate = "12/01/1983";
        String familyName = "Silva";
        String password = "password";

        FamilyInputDTO familyInputDTO = new FamilyInputDTO(name, vat, email, phoneNumber, location, street, postCode, birthDate, familyName);

        //act
        String resultName = familyInputDTO.getName();
        String resultVat = familyInputDTO.getVat();
        String resultEmail = familyInputDTO.getEmail();
        String resultPhoneNumber = familyInputDTO.getPhoneNumber();
        String resultLocation = familyInputDTO.getLocation();
        String resultStreet = familyInputDTO.getStreet();
        String resultPostCode = familyInputDTO.getPostCode();
        String resultBirthDate = familyInputDTO.getBirthDate();
        String resultFamilyName = familyInputDTO.getFamilyName();
        String resultPassword = familyInputDTO.getPassword();


        assertEquals(email, resultEmail);
        assertEquals(name, resultName);
        assertEquals(street, resultStreet);
        assertEquals(location, resultLocation);
        assertEquals(postCode, resultPostCode);
        assertEquals(birthDate, resultBirthDate);
        assertEquals(vat, resultVat);
        assertEquals(familyName, resultFamilyName);
        assertEquals(password, resultPassword);
        assertEquals(phoneNumber, resultPhoneNumber);
    }

    @Test
    void verifySetter() {
        String name = "Antónia";
        String vat = "223456789";
        String email = "antonia@gmail.com";
        String phoneNumber = "918765456";
        String location = "Alentejo";
        String street = "Rua da Amargura";
        String postCode = "4589-689";
        String birthDate = "12/01/1983";
        String familyName = "Silva";
        String password = "password";

        FamilyInputDTO familyInputDTO = new FamilyInputDTO(name, vat, email, phoneNumber, location, street, postCode, birthDate, familyName);

        //setter information
        String othername = "Joaquina";
        String othervat = "223456799";
        String otheremail = "joaquina@gmail.com";
        String otherphoneNumber = "919765456";
        String otherlocation = "Alentejo";
        String otherstreet = "Rua da Tristeza";
        String otherpostCode = "4589-669";
        String otherbirthDate = "12/01/1973";
        String otherfamilyName = "Bento";
        String otherpassword = "passwordnova";

        //act

        familyInputDTO.setName(othername);
        String resultName = familyInputDTO.getName();

        familyInputDTO.setVat(othervat);
        String resultVat = familyInputDTO.getVat();

        familyInputDTO.setEmail(otheremail);
        String resultEmail = familyInputDTO.getEmail();

        familyInputDTO.setPhoneNumber(otherphoneNumber);
        String resultPhoneNumber = familyInputDTO.getPhoneNumber();

        familyInputDTO.setLocation(otherlocation);
        String resultLocation = familyInputDTO.getLocation();

        familyInputDTO.setStreet(otherstreet);
        String resultStreet = familyInputDTO.getStreet();

        familyInputDTO.setPostCode(otherpostCode);
        String resultPostCode = familyInputDTO.getPostCode();

        familyInputDTO.setBirthDate(otherbirthDate);
        String resultBirthDate = familyInputDTO.getBirthDate();

        familyInputDTO.setFamilyName(otherfamilyName);
        String resultFamilyName = familyInputDTO.getFamilyName();

        familyInputDTO.setPassword(otherpassword);
        String resultPassword = familyInputDTO.getPassword();


        assertEquals(otheremail, resultEmail);
        assertEquals(othername, resultName);
        assertEquals(otherstreet, resultStreet);
        assertEquals(otherlocation, resultLocation);
        assertEquals(otherpostCode, resultPostCode);
        assertEquals(otherbirthDate, resultBirthDate);
        assertEquals(othervat, resultVat);
        assertEquals(otherfamilyName, resultFamilyName);
        assertEquals(otherpassword, resultPassword);
        assertEquals(otherphoneNumber, resultPhoneNumber);
    }


    @Test
    void testEqualsAndHashCode() {
        String name = "Antónia";
        String vat = "223456789";
        String email = "antonia@gmail.com";
        String phoneNumber = "918765456";
        String location = "Alentejo";
        String street = "Rua da Amargura";
        String postCode = "4589-689";
        String birthDate = "12/01/1983";
        String familyName = "Silva";
        String password = "password";


        //other information of Other Family

        String otherName = "Joaquina";
        String otherVat = "223456799";
        String otherEmail = "joaquina@gmail.com";
        String otherPhoneNumber = "919765456";
        String otherLocation = "Alentejo";
        String otherStreet = "Rua da Tristeza";
        String otherPostCode = "4589-669";
        String otherBirthDate = "12/01/1973";
        String otherFamilyName = "Bento";
        String otherPassword = "passwordnova";

        FamilyInputDTO familyInputDTO = new FamilyInputDTO(name, vat, email, phoneNumber, location, street, postCode, birthDate, familyName);
        FamilyInputDTO familyInputDTOSame = familyInputDTO;
        FamilyInputDTO familyInputDTOEquals = new FamilyInputDTO(name, vat, email, phoneNumber, location, street, postCode, birthDate, familyName);
        FamilyInputDTO familyInputDTODifferent = new FamilyInputDTO(otherName, otherVat, otherEmail, otherPhoneNumber, otherLocation, otherStreet, otherPostCode, otherBirthDate, otherFamilyName);
        FamilyInputDTO familyInputDTODifferentName = new FamilyInputDTO(name, otherVat, otherEmail, otherPhoneNumber, otherLocation, otherStreet, otherPostCode, otherBirthDate, otherFamilyName);
        FamilyInputDTO familyInputDTODifferentVat = new FamilyInputDTO(otherName, vat, otherEmail, otherPhoneNumber, otherLocation, otherStreet, otherPostCode, otherBirthDate, otherFamilyName);
        FamilyInputDTO familyInputDTODifferentEmail = new FamilyInputDTO(otherName, otherVat, email, otherPhoneNumber, otherLocation, otherStreet, otherPostCode, otherBirthDate, otherFamilyName);
        FamilyInputDTO familyInputDTODifferentPhoneNumber = new FamilyInputDTO(otherName, otherVat, otherEmail, phoneNumber, otherLocation, otherStreet, otherPostCode, otherBirthDate, otherFamilyName);
        FamilyInputDTO familyInputDTODifferentLocation = new FamilyInputDTO(otherName, otherVat, otherEmail, otherPhoneNumber, location, otherStreet, otherPostCode, otherBirthDate, otherFamilyName);
        FamilyInputDTO familyInputDTODifferentStreet= new FamilyInputDTO(otherName, otherVat, otherEmail, otherPhoneNumber, otherLocation, street, otherPostCode, otherBirthDate, otherFamilyName);
        FamilyInputDTO familyInputDTODifferentPostCode = new FamilyInputDTO(otherName, otherVat, otherEmail, otherPhoneNumber, otherLocation, otherStreet, postCode, otherBirthDate, otherFamilyName);
        FamilyInputDTO familyInputDTODifferentBirthDate= new FamilyInputDTO(otherName, otherVat, otherEmail, otherPhoneNumber, otherLocation, otherStreet, otherPostCode, birthDate, otherFamilyName);
        FamilyInputDTO familyInputDTODifferentFamilyName = new FamilyInputDTO(otherName, otherVat, otherEmail, otherPhoneNumber, otherLocation, otherStreet, otherPostCode, otherBirthDate, familyName);
        
        assertEquals(familyInputDTO,familyInputDTOSame);
        assertNotSame(familyInputDTO,familyInputDTOEquals);
        assertSame(familyInputDTO,familyInputDTOSame);
        assertNotEquals(familyInputDTO, familyInputDTODifferent);
        assertNotSame(familyInputDTO, familyInputDTODifferent);
        assertEquals(familyInputDTO.hashCode(), familyInputDTOSame.hashCode());

        assertNotEquals(familyInputDTO.hashCode(), familyInputDTODifferentBirthDate.hashCode());
        assertNotEquals(null, familyInputDTO);
        assertNotEquals(email, familyInputDTO);
        assertFalse(familyInputDTO.equals(null));
        assertFalse(familyInputDTO.equals(new FamilyId(12)));
        assertNotEquals(familyInputDTO, familyInputDTODifferentName);
        assertNotEquals(familyInputDTO, familyInputDTODifferentVat);
        assertNotEquals(familyInputDTO, familyInputDTODifferentEmail);
        assertNotEquals(familyInputDTO, familyInputDTODifferentPhoneNumber);
        assertFalse(familyInputDTO.equals(familyInputDTODifferentName));
        assertFalse(familyInputDTO.equals(familyInputDTODifferentVat));
        assertFalse(familyInputDTO.equals(familyInputDTODifferentEmail));
        assertFalse(familyInputDTO.equals(familyInputDTODifferentLocation));
        assertFalse(familyInputDTO.equals(familyInputDTODifferentStreet));
        assertFalse(familyInputDTO.equals(familyInputDTODifferentPostCode));
        assertFalse(familyInputDTO.equals(familyInputDTODifferentBirthDate));
        assertFalse(familyInputDTO.equals(familyInputDTODifferentPhoneNumber));
        assertFalse(familyInputDTO.equals(familyInputDTODifferentFamilyName));
        assertNotEquals(familyInputDTO.hashCode(), familyInputDTODifferent.hashCode());
        assertNotEquals(familyInputDTO.hashCode(), familyInputDTODifferentFamilyName.hashCode());
        assertNotEquals(familyInputDTO.hashCode(), familyInputDTODifferentVat.hashCode());
        assertNotEquals(familyInputDTO.hashCode(), familyInputDTODifferentName.hashCode());
        assertNotEquals(familyInputDTO.hashCode(), familyInputDTODifferentEmail.hashCode());
        assertNotEquals(familyInputDTO.hashCode(), familyInputDTODifferentBirthDate.hashCode());
        assertNotEquals(familyInputDTO.hashCode(), familyInputDTODifferentLocation.hashCode());
        assertNotEquals(familyInputDTO.hashCode(), familyInputDTODifferentStreet.hashCode());
        assertNotEquals(familyInputDTO.hashCode(), familyInputDTODifferentPostCode.hashCode());
        assertNotEquals(familyInputDTO.hashCode(), familyInputDTODifferentPhoneNumber.hashCode());
        assertNotEquals(0, familyInputDTO.hashCode());
        

    }

}