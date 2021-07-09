package switchtwentytwenty.project.applicationservices.implservices;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import switchtwentytwenty.project.applicationservices.iservices.IFamilyAccountService;
import switchtwentytwenty.project.domain.exceptions.DuplicateObjectException;
import switchtwentytwenty.project.domain.model.family.Family;
import switchtwentytwenty.project.domain.model.shared.AccountId;
import switchtwentytwenty.project.domain.model.shared.FamilyId;
import switchtwentytwenty.project.dto.account.AccountOutputDTO;
import switchtwentytwenty.project.dto.account.AccountInputDTO;
import switchtwentytwenty.project.dto.family.FamilyInputDTO;
import switchtwentytwenty.project.dto.family.FamilyOutputDTO;
import switchtwentytwenty.project.repositories.AccountRepository;
import switchtwentytwenty.project.repositories.FamilyRepository;
import switchtwentytwenty.project.repositories.irepositories.IFamilyRepositoryJPA;
import switchtwentytwenty.project.repositories.irepositories.IPersonRepositoryJPA;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Profile("FamilyAccountServiceItTest")
class FamilyAccountServiceItTest {
    @Autowired
    FamilyMemberService familyMemberService;
    @Autowired
    IFamilyAccountService familyAccountService;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    FamilyRepository familyRepository;
    @Autowired
    IFamilyRepositoryJPA familyRepositoryJPA;
    @Autowired
    IPersonRepositoryJPA personRepositoryJPA;

    FamilyOutputDTO familyOutputDTO;

    Family family;

    @BeforeEach
    void setUpFamily() {
        String adminId = "micaela@gmail.com";
        FamilyInputDTO familyInputDTO = new FamilyInputDTO();
        familyInputDTO.setName("Micaela");
        familyInputDTO.setEmail(adminId);
        familyInputDTO.setStreet("Rua de Baixo");
        familyInputDTO.setLocation("Porto");
        familyInputDTO.setPostCode("4774-123");
        familyInputDTO.setPhoneNumber("919999999");
        familyInputDTO.setVat("123456789");
        familyInputDTO.setBirthDate("13/05/1990");
        familyInputDTO.setFamilyName("Passos");
        familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
        family = familyRepository.getDatabaseSavedFamily(new FamilyId(familyOutputDTO.getFamilyId()));
    }

    @AfterEach
    void clean() {
        familyRepositoryJPA.deleteAll();
        personRepositoryJPA.deleteAll();
    }

    @Test
    void createFamilyCashAccountSuccessfully() {
        double initialAmount = 50.50;
        String description = "Family cash account.";

        AccountInputDTO inputDTO = new AccountInputDTO(familyOutputDTO.getFamilyId()
                , initialAmount, 1, description);
        AccountOutputDTO result = familyAccountService.createFamilyCashAccount(inputDTO);

        assertNotNull(result);
        assertEquals(initialAmount, result.getInitialAmount());
        assertNotEquals(0, result.getAccountId());
    }

    @Test
    void failToCreateFamilyCashAccount_InvalidAmount() {
        String description = "Family cash account.";
        AccountInputDTO inputDTO = new AccountInputDTO(familyOutputDTO.getFamilyId()
                , -3, 1, description);

        assertThrows(IllegalArgumentException.class,
                () -> familyAccountService.createFamilyCashAccount(inputDTO));
    }

    @Test
    void failToCreateFamilyCashAccount_FamilyAlreadyHasAccount() {
        double initialAmount = 30;
        String description = "Family cash account.";

        AccountInputDTO inputDTO = new AccountInputDTO(familyOutputDTO.getFamilyId()
                , initialAmount, 1, description);
        family.setAccountId(new AccountId(20));
        familyRepository.saveFamilyWithAccount(family);

        assertThrows(DuplicateObjectException.class,
                () -> familyAccountService.createFamilyCashAccount(inputDTO));
    }
}