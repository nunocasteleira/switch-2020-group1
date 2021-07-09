package switchtwentytwenty.project.dto.person;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.dto.person.ProfileInformationDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProfileInformationDTOTest {

    @Test
    void createProfileInformationDTOWithValidInput() {
        //Arrange
        String name = "Fabio";
        String mainEmailAddress = "fabio@gmail.com";
        String birthdate = "09/11/1988";
        String vat = "222333444";
        String address = "Rua das flores, Porto, 4444-123";
        List<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add("931234343");
        phoneNumbers.add("961231223");
        List<String> emailAddresses = new ArrayList<>();
        emailAddresses.add("abc@hotmail.com");
        emailAddresses.add("def@gmail.com");
        int familyId = 10;
        String familyName = "Maia";
        boolean isAdmin = true;

        //Act
        ProfileInformationDTO profileInformationDTO = new ProfileInformationDTO(name,
                mainEmailAddress, birthdate, vat, address, phoneNumbers, emailAddresses, familyId, familyName, isAdmin);
        String actualName = profileInformationDTO.getName();
        String actualVat = profileInformationDTO.getVat();
        String actualEmail = profileInformationDTO.getMainEmailAddress();
        String actualBirthdate = profileInformationDTO.getBirthDate();
        String actualAddress = profileInformationDTO.getAddress();
        List<String> actualPhoneNumbers = profileInformationDTO.getPhoneNumbers();
        List<String> actualEmailAddresses = profileInformationDTO.getEmailAddresses();
        long actualFamilyId = profileInformationDTO.getFamilyId();
        String actualFamilyName = profileInformationDTO.getFamilyName();
        boolean actualIsAdmin = profileInformationDTO.isAdmin();

        //Assert
        assertNotNull(profileInformationDTO);
        assertEquals(name, actualName);
        assertEquals(vat, actualVat);
        assertEquals(mainEmailAddress, actualEmail);
        assertEquals(birthdate, actualBirthdate);
        assertEquals(address, actualAddress);
        assertEquals(phoneNumbers, actualPhoneNumbers);
        assertEquals(emailAddresses, actualEmailAddresses);
        assertEquals(familyId, actualFamilyId);
        assertEquals(familyName, actualFamilyName);
        assertTrue(actualIsAdmin);
        assertTrue(profileInformationDTO.isAdmin());
    }

    @Test
    void testSetters() {
        ProfileInformationDTO profileInformationDTO = new ProfileInformationDTO();
        String expectedName = "Fábio";
        profileInformationDTO.setName(expectedName);
        String expectedBirthdate = "09/11/1988";
        profileInformationDTO.setBirthDate(expectedBirthdate);
        String expectedMainEmail = "fabio@gmail.com";
        profileInformationDTO.setMainEmailAddress(expectedMainEmail);
        List<String> expectedEmailAddresses = new ArrayList<>();
        expectedEmailAddresses.add("firstEmail@gmail.com");
        expectedEmailAddresses.add("secondEmail@gmail.com");
        profileInformationDTO.setEmailAddresses(new ArrayList<String>(expectedEmailAddresses));
        String expectedAddress = "Rua das flores, porto, 4444-5555";
        profileInformationDTO.setAddress(expectedAddress);
        String expectedVat = "222333444";
        profileInformationDTO.setVat(expectedVat);
        List<String> expectedPhoneNumbers = new ArrayList<>();
        expectedPhoneNumbers.add("912223366");
        profileInformationDTO.setPhoneNumbers(expectedPhoneNumbers);
        int expectedFamilyId = 10;
        profileInformationDTO.setFamilyId(expectedFamilyId);
        String expectedFamilyName = "Maia";
        profileInformationDTO.setFamilyName(expectedFamilyName);
        boolean expectedAdmin = false;
        profileInformationDTO.setAdmin(expectedAdmin);

        String actualName = profileInformationDTO.getName();
        String actualVat = profileInformationDTO.getVat();
        String actualEmail = profileInformationDTO.getMainEmailAddress();
        String actualBirthdate = profileInformationDTO.getBirthDate();
        String actualAddress = profileInformationDTO.getAddress();
        List<String> actualPhoneNumbers = profileInformationDTO.getPhoneNumbers();
        List<String> actualEmailAddresses = profileInformationDTO.getEmailAddresses();
        int actualFamilyId = 10;
        String actualFamilyName = "Maia";
        boolean actualAdmin = false;

        assertNotNull(profileInformationDTO);
        assertEquals(expectedName, actualName);
        assertEquals(expectedVat, actualVat);
        assertEquals(expectedMainEmail, actualEmail);
        assertEquals(expectedBirthdate, actualBirthdate);
        assertEquals(expectedAddress, actualAddress);
        assertEquals(expectedPhoneNumbers, actualPhoneNumbers);
        assertEquals(expectedEmailAddresses, actualEmailAddresses);
        assertEquals(expectedFamilyId, actualFamilyId);
        assertEquals(expectedFamilyName, actualFamilyName);
        assertEquals(expectedAdmin, actualAdmin);
        assertFalse(profileInformationDTO.isAdmin());
    }

    @Test
    void testEquals() {
        //Arrange
        String name = "Fabio";
        String mainEmailAddress = "fabio@gmail.com";
        String birthdate = "09/11/1988";
        String vat = "222333444";
        String address = "Rua das flores, Porto, 4444-123";
        List<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add("931234343");
        phoneNumbers.add("961231223");
        List<String> emailAddresses = new ArrayList<>();
        emailAddresses.add("abc@hotmail.com");
        emailAddresses.add("def@gmail.com");
        int familyId = 10;
        String familyName = "Maia";
        boolean isAdmin = true;

        ProfileInformationDTO profileInformationDTO = new ProfileInformationDTO(name,
                mainEmailAddress, birthdate, vat, address, phoneNumbers, emailAddresses, familyId, familyName, isAdmin);

        //noinspection UnnecessaryLocalVariable
        ProfileInformationDTO profileInformationDTOSame = profileInformationDTO;
        ProfileInformationDTO profileInformationDTOOther = new ProfileInformationDTO(name,
                mainEmailAddress, birthdate, vat, address, phoneNumbers, emailAddresses, familyId, familyName, isAdmin);

        assertEquals(profileInformationDTO, profileInformationDTOSame);
        assertSame(profileInformationDTO, profileInformationDTOSame);
        assertEquals(profileInformationDTO.hashCode(), profileInformationDTOSame.hashCode());
        assertNotEquals(0, profileInformationDTO.hashCode());
        assertEquals(profileInformationDTO, profileInformationDTOOther);
        assertNotSame(profileInformationDTO, profileInformationDTOOther);
        assertEquals(profileInformationDTO.hashCode(), profileInformationDTOOther.hashCode());
        assertNotEquals(profileInformationDTO, name);
        assertFalse(profileInformationDTO.equals(phoneNumbers));
    }

    @Test
    void TestEquals_SameProfileInformationDTO_DifferentName() {
        String firstName = "Fabio";
        String mainEmailAddress = "fabio@gmail.com";
        String birthdate = "09/11/1988";
        String vat = "222333444";
        String address = "Rua das flores, Porto, 4444-123";
        List<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add("931234343");
        phoneNumbers.add("961231223");
        List<String> emailAddresses = new ArrayList<>();
        emailAddresses.add("abc@hotmail.com");
        emailAddresses.add("def@gmail.com");
        int familyId = 10;
        String familyName = "Maia";
        boolean isAdmin = true;

        ProfileInformationDTO profileInformationDTO = new ProfileInformationDTO(firstName,
                mainEmailAddress, birthdate, vat, address, phoneNumbers, emailAddresses, familyId, familyName, isAdmin);

        String secondName = "Amaro";

        ProfileInformationDTO secondProfileInformationDTO = new ProfileInformationDTO(secondName,
                mainEmailAddress, birthdate, vat, address, phoneNumbers, emailAddresses, familyId, familyName, isAdmin);


        assertNotEquals(secondProfileInformationDTO, profileInformationDTO);
    }

    @Test
    void TestEquals_SameProfileInformationDTO_DifferentMainEmail() {
        String firstName = "Fabio";
        String mainEmailAddress = "fabio@gmail.com";
        String birthdate = "09/11/1988";
        String vat = "222333444";
        String address = "Rua das flores, Porto, 4444-123";
        List<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add("931234343");
        phoneNumbers.add("961231223");
        List<String> emailAddresses = new ArrayList<>();
        emailAddresses.add("abc@hotmail.com");
        emailAddresses.add("def@gmail.com");
        int familyId = 10;
        String familyName = "Maia";
        boolean isAdmin = true;

        ProfileInformationDTO profileInformationDTO = new ProfileInformationDTO(firstName,
                mainEmailAddress, birthdate, vat, address, phoneNumbers, emailAddresses, familyId, familyName, isAdmin);

        String otherMainEmailAddress = "Amaro";

        ProfileInformationDTO secondProfileInformationDTO = new ProfileInformationDTO(firstName,
                otherMainEmailAddress, birthdate, vat, address, phoneNumbers, emailAddresses, familyId, familyName, isAdmin);


        assertNotEquals(secondProfileInformationDTO, profileInformationDTO);
    }

    @Test
    void TestEquals_SameProfileInformationDTO_DifferentBirthdate() {
        String firstName = "Fabio";
        String mainEmailAddress = "fabio@gmail.com";
        String birthdate = "09/11/1988";
        String vat = "222333444";
        String address = "Rua das flores, Porto, 4444-123";
        List<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add("931234343");
        phoneNumbers.add("961231223");
        List<String> emailAddresses = new ArrayList<>();
        emailAddresses.add("abc@hotmail.com");
        emailAddresses.add("def@gmail.com");
        int familyId = 10;
        String familyName = "Maia";
        boolean isAdmin = true;

        ProfileInformationDTO profileInformationDTO = new ProfileInformationDTO(firstName,
                mainEmailAddress, birthdate, vat, address, phoneNumbers, emailAddresses, familyId, familyName, isAdmin);

        String otherBirthday = "Amaro";

        ProfileInformationDTO secondProfileInformationDTO = new ProfileInformationDTO(firstName,
                mainEmailAddress, otherBirthday, vat, address, phoneNumbers, emailAddresses, familyId, familyName, isAdmin);


        assertNotEquals(secondProfileInformationDTO, profileInformationDTO);
    }

    @Test
    void TestEquals_SameProfileInformationDTO_DifferentVat() {
        String firstName = "Fabio";
        String mainEmailAddress = "fabio@gmail.com";
        String birthdate = "09/11/1988";
        String vat = "222333444";
        String address = "Rua das flores, Porto, 4444-123";
        List<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add("931234343");
        phoneNumbers.add("961231223");
        List<String> emailAddresses = new ArrayList<>();
        emailAddresses.add("abc@hotmail.com");
        emailAddresses.add("def@gmail.com");
        int familyId = 10;
        String familyName = "Maia";
        boolean isAdmin = true;

        ProfileInformationDTO profileInformationDTO = new ProfileInformationDTO(firstName,
                mainEmailAddress, birthdate, vat, address, phoneNumbers, emailAddresses, familyId, familyName, isAdmin);

        String otherVat = "222555333";

        ProfileInformationDTO secondProfileInformationDTO = new ProfileInformationDTO(firstName,
                mainEmailAddress, birthdate, otherVat, address, phoneNumbers, emailAddresses, familyId, familyName, isAdmin);


        assertNotEquals(secondProfileInformationDTO, profileInformationDTO);
    }

    @Test
    void TestEquals_SameProfileInformationDTO_DifferentAddress() {
        String firstName = "Fabio";
        String mainEmailAddress = "fabio@gmail.com";
        String birthdate = "09/11/1988";
        String vat = "222333444";
        String address = "Rua das flores, Porto, 4444-123";
        List<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add("931234343");
        phoneNumbers.add("961231223");
        List<String> emailAddresses = new ArrayList<>();
        emailAddresses.add("abc@hotmail.com");
        emailAddresses.add("def@gmail.com");
        int familyId = 10;
        String familyName = "Maia";
        boolean isAdmin = true;

        ProfileInformationDTO profileInformationDTO = new ProfileInformationDTO(firstName,
                mainEmailAddress, birthdate, vat, address, phoneNumbers, emailAddresses, familyId, familyName, isAdmin);

        String otherAddress = "Rua das folhas, Porto, 4444-123";

        ProfileInformationDTO secondProfileInformationDTO = new ProfileInformationDTO(firstName,
                mainEmailAddress, birthdate, vat, otherAddress, phoneNumbers, emailAddresses, familyId, familyName, isAdmin);


        assertNotEquals(secondProfileInformationDTO, profileInformationDTO);
    }

    @Test
    void TestEquals_SameProfileInformationDTO_DifferentPhoneNumbers() {
        String firstName = "Fabio";
        String mainEmailAddress = "fabio@gmail.com";
        String birthdate = "09/11/1988";
        String vat = "222333444";
        String address = "Rua das flores, Porto, 4444-123";
        List<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add("931234343");
        phoneNumbers.add("961231223");
        List<String> emailAddresses = new ArrayList<>();
        emailAddresses.add("abc@hotmail.com");
        emailAddresses.add("def@gmail.com");
        int familyId = 10;
        String familyName = "Maia";
        boolean isAdmin = true;

        ProfileInformationDTO profileInformationDTO = new ProfileInformationDTO(firstName,
                mainEmailAddress, birthdate, vat, address, phoneNumbers, emailAddresses, familyId, familyName, isAdmin);

        List<String> otherPhoneNumbers = new ArrayList<>();

        ProfileInformationDTO secondProfileInformationDTO = new ProfileInformationDTO(firstName,
                mainEmailAddress, birthdate, vat, address, otherPhoneNumbers, emailAddresses, familyId, familyName, isAdmin);


        assertNotEquals(secondProfileInformationDTO, profileInformationDTO);
    }

    @Test
    void TestEquals_SameProfileInformationDTO_DifferentOtherEmails() {
        String firstName = "Fabio";
        String mainEmailAddress = "fabio@gmail.com";
        String birthdate = "09/11/1988";
        String vat = "222333444";
        String address = "Rua das flores, Porto, 4444-123";
        List<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add("931234343");
        phoneNumbers.add("961231223");
        List<String> emailAddresses = new ArrayList<>();
        emailAddresses.add("abc@hotmail.com");
        emailAddresses.add("def@gmail.com");
        int familyId = 10;
        String familyName = "Maia";
        boolean isAdmin = true;

        ProfileInformationDTO profileInformationDTO = new ProfileInformationDTO(firstName,
                mainEmailAddress, birthdate, vat, address, phoneNumbers, emailAddresses, familyId, familyName, isAdmin);

        List<String> otherEmails = new ArrayList<>();

        ProfileInformationDTO secondProfileInformationDTO = new ProfileInformationDTO(firstName,
                mainEmailAddress, birthdate, vat, address, phoneNumbers, otherEmails, familyId, familyName, isAdmin);


        assertNotEquals(secondProfileInformationDTO, profileInformationDTO);
    }

    @Test
    void TestEquals_SameProfileInformationDTO_DifferentFamilyId() {
        String firstName = "Fabio";
        String mainEmailAddress = "fabio@gmail.com";
        String birthdate = "09/11/1988";
        String vat = "222333444";
        String address = "Rua das flores, Porto, 4444-123";
        List<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add("931234343");
        phoneNumbers.add("961231223");
        List<String> emailAddresses = new ArrayList<>();
        emailAddresses.add("abc@hotmail.com");
        emailAddresses.add("def@gmail.com");
        int familyId = 10;
        String familyName = "Maia";
        boolean isAdmin = true;

        ProfileInformationDTO profileInformationDTO = new ProfileInformationDTO(firstName,
                mainEmailAddress, birthdate, vat, address, phoneNumbers, emailAddresses, familyId, familyName, isAdmin);

        List<String> otherEmails = new ArrayList<>();

        ProfileInformationDTO secondProfileInformationDTO = new ProfileInformationDTO(firstName,
                mainEmailAddress, birthdate, vat, address, phoneNumbers, otherEmails, 11, familyName, isAdmin);


        assertNotEquals(secondProfileInformationDTO, profileInformationDTO);
    }

    @Test
    void TestEquals_SameProfileInformationDTO_DifferentFamilyName() {
        String firstName = "Fabio";
        String mainEmailAddress = "fabio@gmail.com";
        String birthdate = "09/11/1988";
        String vat = "222333444";
        String address = "Rua das flores, Porto, 4444-123";
        List<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add("931234343");
        phoneNumbers.add("961231223");
        List<String> emailAddresses = new ArrayList<>();
        emailAddresses.add("abc@hotmail.com");
        emailAddresses.add("def@gmail.com");
        int familyId = 10;
        String familyName = "Maia";
        boolean isAdmin = true;

        ProfileInformationDTO profileInformationDTO = new ProfileInformationDTO(firstName,
                mainEmailAddress, birthdate, vat, address, phoneNumbers, emailAddresses, familyId, familyName, isAdmin);

        List<String> otherEmails = new ArrayList<>();

        ProfileInformationDTO secondProfileInformationDTO = new ProfileInformationDTO(firstName,
                mainEmailAddress, birthdate, vat, address, phoneNumbers, otherEmails, familyId, "Silva", isAdmin);


        assertNotEquals(secondProfileInformationDTO, profileInformationDTO);
    }

    @Test
    void TestEquals_SameProfileInformationDTO_DifferentAdminBoolean() {
        String firstName = "Fabio";
        String mainEmailAddress = "fabio@gmail.com";
        String birthdate = "09/11/1988";
        String vat = "222333444";
        String address = "Rua das flores, Porto, 4444-123";
        List<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add("931234343");
        phoneNumbers.add("961231223");
        List<String> emailAddresses = new ArrayList<>();
        emailAddresses.add("abc@hotmail.com");
        emailAddresses.add("def@gmail.com");
        int familyId = 10;
        String familyName = "Maia";
        boolean isAdmin = true;

        ProfileInformationDTO profileInformationDTO = new ProfileInformationDTO(firstName,
                mainEmailAddress, birthdate, vat, address, phoneNumbers, emailAddresses, familyId, familyName, isAdmin);

        List<String> otherEmails = new ArrayList<>();

        ProfileInformationDTO secondProfileInformationDTO = new ProfileInformationDTO(firstName,
                mainEmailAddress, birthdate, vat, address, phoneNumbers, otherEmails, familyId, familyName, false);


        assertNotEquals(secondProfileInformationDTO, profileInformationDTO);
    }
}
