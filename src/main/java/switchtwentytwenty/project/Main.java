package switchtwentytwenty.project;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import switchtwentytwenty.project.applicationservices.implservices.FamilyAccountService;
import switchtwentytwenty.project.applicationservices.implservices.FamilyMemberService;
import switchtwentytwenty.project.applicationservices.implservices.PersonAccountService;
import switchtwentytwenty.project.applicationservices.implservices.SystemManagerService;
import switchtwentytwenty.project.applicationservices.iservices.ICategoryService;
import switchtwentytwenty.project.controllers.FamilyController;
import switchtwentytwenty.project.domain.model.person.ERole;
import switchtwentytwenty.project.dto.account.AccountInputDTO;
import switchtwentytwenty.project.dto.account.AccountOutputDTO;
import switchtwentytwenty.project.dto.category.CategoryInputDTO;
import switchtwentytwenty.project.dto.category.StandardCategoryOutputDTO;
import switchtwentytwenty.project.dto.family.AddFamilyMemberDTO;
import switchtwentytwenty.project.dto.family.FamilyInputDTO;
import switchtwentytwenty.project.dto.family.FamilyOutputDTO;
import switchtwentytwenty.project.dto.family.RelationshipInputDTO;
import switchtwentytwenty.project.dto.person.PersonInputDTO;
import switchtwentytwenty.project.dto.person.SystemManagerDTO;
import switchtwentytwenty.project.repositories.RoleRepository;

@SpringBootApplication
public class Main {

    private static final String LOCAL_HOST = "http://localhost:3000";
    @Value("${switchtwentytwenty.project.sysManEmail}")
    private String sysManEmail;
    @Value("${switchtwentytwenty.project.sysManName}")
    private String sysManName;
    @Value("${switchtwentytwenty.project.sysManPassword}")
    private String sysManPassword;

    public static void main(String[] args) {
        // write your code here
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner setUpRoles(RoleRepository roleRepository) {
        return args -> {
            roleRepository.addRole(ERole.ROLE_SYSTEM_MANAGER);
            roleRepository.addRole(ERole.ROLE_FAMILY_ADMIN);
            roleRepository.addRole(ERole.ROLE_FAMILY_MEMBER);
        };
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {

                registry.addMapping("/**").allowedOrigins(LOCAL_HOST).allowedMethods("*");
            }
        };
    }

    @Bean
    public CommandLineRunner createSystemManager(SystemManagerService systemManagerService) {
        return args -> {
            SystemManagerDTO systemManagerDTO = new SystemManagerDTO(sysManName, sysManEmail, sysManPassword);
            systemManagerService.addSystemManager(systemManagerDTO);
        };
    }


   /* @Bean
    public CommandLineRunner createRelationships(FamilyMemberService familyMemberService, FamilyController controller){
        return (args) -> {
            FamilyInputDTO familyInputDTO;
            FamilyInputDTO otherFamilyInputDTO;

            familyInputDTO = new FamilyInputDTO();
            familyInputDTO.setName("David");
            familyInputDTO.setEmail("david@gmail.com");
            familyInputDTO.setStreet("Rua clara");
            familyInputDTO.setLocation("Porto");
            familyInputDTO.setPostCode("4000-000");
            familyInputDTO.setPhoneNumber("911111111");
            familyInputDTO.setVat("222333555");
            familyInputDTO.setBirthDate("11/09/1999");
            familyInputDTO.setFamilyName("Silva");

            otherFamilyInputDTO = new FamilyInputDTO();
            otherFamilyInputDTO.setName("Tony");
            otherFamilyInputDTO.setEmail("tony@gmail.com");
            otherFamilyInputDTO.setStreet("Rua escura");
            otherFamilyInputDTO.setLocation("Porto");
            otherFamilyInputDTO.setPostCode("4000-500");
            otherFamilyInputDTO.setPhoneNumber("914111111");
            otherFamilyInputDTO.setVat("222333455");
            otherFamilyInputDTO.setBirthDate("11/09/1995");
            otherFamilyInputDTO.setFamilyName("Bonifácio");

            FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
            long familyIdInt = familyOutputDTO.getFamilyId();
            System.out.println(familyIdInt);

            FamilyOutputDTO otherFamilyOutputDTO = familyMemberService.createFamily(otherFamilyInputDTO);
            long otherFamilyId= otherFamilyOutputDTO.getFamilyId();
            System.out.println(otherFamilyId);

        };
    } */



    @Bean
    public CommandLineRunner bootstrap(ICategoryService categoryService, FamilyMemberService familyMemberService, FamilyAccountService familyAccountService, PersonAccountService personAccountService) {
        return (args) -> {
            FamilyInputDTO familyInputDTO = new FamilyInputDTO();
            familyInputDTO.setName("António");
            familyInputDTO.setEmail("antonio@gmail.com");
            familyInputDTO.setStreet("Rua clara");
            familyInputDTO.setLocation("Porto");
            familyInputDTO.setPostCode("4000-000");
            familyInputDTO.setPhoneNumber("911111111");
            familyInputDTO.setVat("222333222");
            familyInputDTO.setBirthDate("11/09/1999");
            familyInputDTO.setFamilyName("Silva");
            FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
            long familyId = familyOutputDTO.getFamilyId();

            AccountInputDTO accountInputDTO = new AccountInputDTO(familyId, 150, 2, "TestDescription");
            AccountOutputDTO accountOutputDTO = familyAccountService.createFamilyCashAccount(accountInputDTO);
            System.out.println(accountOutputDTO.getAccountId());

            AccountInputDTO personAccountInputDTO = new AccountInputDTO( 150, 2, "Personal Cash Account Test");
            AccountOutputDTO personAccountOutputDTO = personAccountService.createPersonalCashAccount(personAccountInputDTO, familyOutputDTO.getAdminId());
            System.out.println(personAccountOutputDTO.getAccountId());

            AccountInputDTO otherPersonAccountInputDTO = new AccountInputDTO( 150, 2, "Personal Cash Account Test 2");
            AccountOutputDTO otherPersonAccountOutputDTO = personAccountService.createPersonalCashAccount(otherPersonAccountInputDTO, familyOutputDTO.getAdminId());
            System.out.println(otherPersonAccountOutputDTO.getAccountId());


            String nameOne = "G1 TRAVEL";
            Integer parentIdOne = null;
            CategoryInputDTO inputDTOOne = new CategoryInputDTO();
            inputDTOOne.setName(nameOne);
            inputDTOOne.setParentId(parentIdOne);
            StandardCategoryOutputDTO outputDTOOne = categoryService.createStandardCategory(inputDTOOne);
            System.out.println(outputDTOOne.getCategoryId());

            //PaymentInputDTO paymentInputDTO = new PaymentInputDTO(30, 2, "Comida", "20-06-2021 14:00", "Mercado", 292822205);
            //transactionService.registerPayment(paymentInputDTO, accountOutputDTO.getAccountId());

            //PaymentInputDTO paymentInputDTOTwo = new PaymentInputDTO(50, 2, "Transporte", "18-06-2021 14:00", "MetrodoPorto", 292822205);
            //transactionService.registerPayment(paymentInputDTOTwo, accountOutputDTO.getAccountId());

            String nameTwo = "G1 VACATION";
            Object parentIdTwo = outputDTOOne.getCategoryId();
            CategoryInputDTO inputDTOTwo = new CategoryInputDTO();
            inputDTOTwo.setName(nameTwo);
            inputDTOTwo.setParentId(parentIdTwo);
            StandardCategoryOutputDTO outputDTOTwo = categoryService.createStandardCategory(inputDTOTwo);

            String nameThree = "G1 ITALY";
            Object parentIdThree = outputDTOTwo.getCategoryId();
            CategoryInputDTO inputDTOThree = new CategoryInputDTO();
            inputDTOThree.setName(nameThree);
            inputDTOThree.setParentId(parentIdThree);
            StandardCategoryOutputDTO outputDTOThree = categoryService.createStandardCategory(inputDTOThree);

            String nameFour = "G1 TRANSPORTATION";
            Integer parentIdFour = null;
            CategoryInputDTO inputDTOFour = new CategoryInputDTO();
            inputDTOFour.setName(nameFour);
            inputDTOFour.setParentId(parentIdFour);
            StandardCategoryOutputDTO outputDTOFour = categoryService.createStandardCategory(inputDTOFour);

            String nameFive = "G1 CAR";
            Object parentIdFive = outputDTOFour.getCategoryId();
            CategoryInputDTO inputDTOFive = new CategoryInputDTO();
            inputDTOFive.setName(nameFive);
            inputDTOFive.setParentId(parentIdFive);
            StandardCategoryOutputDTO outputDTOFive = categoryService.createStandardCategory(inputDTOFive);

            String nameSix = "G1 BOAT";
            Object parentIdSix = outputDTOFour.getCategoryId();
            CategoryInputDTO inputDTOSix = new CategoryInputDTO();
            inputDTOSix.setName(nameSix);
            inputDTOSix.setParentId(parentIdSix);
            StandardCategoryOutputDTO outputDTOSix = categoryService.createStandardCategory(inputDTOSix);

            String nameSeven = "G1 GAS";
            Object parentIdSeven = outputDTOFive.getCategoryId();
            CategoryInputDTO inputDTOSeven = new CategoryInputDTO();
            inputDTOSeven.setName(nameSeven);
            inputDTOSeven.setParentId(parentIdSeven);
            StandardCategoryOutputDTO outputDTOSeven = categoryService.createStandardCategory(inputDTOSeven);

            String nameEight = "G1 GAS";
            Object parentIdEight = outputDTOSix.getCategoryId();
            CategoryInputDTO inputDTOEight = new CategoryInputDTO();
            inputDTOEight.setName(nameEight);
            inputDTOEight.setParentId(parentIdEight);
            StandardCategoryOutputDTO outputDTOEight = categoryService.createStandardCategory(inputDTOEight);
        };
    }



    /*@Bean
    public CommandLineRunner createRelationships(FamilyMemberService familyMemberService, FamilyController controller){
        return (args) -> {
            FamilyInputDTO familyInputDTO;

            familyInputDTO = new FamilyInputDTO();
            familyInputDTO.setName("David");
            familyInputDTO.setEmail("admin@gmail.com");
            familyInputDTO.setStreet("Rua clara");
            familyInputDTO.setLocation("Porto");
            familyInputDTO.setPostCode("4000-000");
            familyInputDTO.setPhoneNumber("911111111");
            familyInputDTO.setVat("222333555");
            familyInputDTO.setBirthDate("11/09/1999");
            familyInputDTO.setFamilyName("Silva");

            PersonInputDTO personInputDTO1 = new PersonInputDTO();
            personInputDTO1.setName("António");
            personInputDTO1.setEmail("antonio@gmail.com");
            personInputDTO1.setStreet("Rua clara");
            personInputDTO1.setLocation("Porto");
            personInputDTO1.setPostCode("4000-000");
            personInputDTO1.setPhoneNumber("911111111");
            personInputDTO1.setVat("222333222");
            personInputDTO1.setBirthDate("05/03/2000");
            personInputDTO1.setAdminId("admin@gmail.com");

            PersonInputDTO personInputDTO2 = new PersonInputDTO();
            personInputDTO2.setName("Maria");
            personInputDTO2.setEmail("maria@gmail.com");
            personInputDTO2.setStreet("Rua escura");
            personInputDTO2.setLocation("Lisboa");
            personInputDTO2.setPostCode("5000-000");
            personInputDTO2.setPhoneNumber("912222222");
            personInputDTO2.setVat("212333444");
            personInputDTO2.setBirthDate("11/03/2000");
            personInputDTO2.setAdminId("admin@gmail.com");

            PersonInputDTO personInputDTO3 = new PersonInputDTO();
            personInputDTO3.setName("Ze");
            personInputDTO3.setEmail("zedebraga@gmail.com");
            personInputDTO3.setStreet("Rua cansada");
            personInputDTO3.setLocation("Braga");
            personInputDTO3.setPostCode("3000-000");
            personInputDTO3.setPhoneNumber("913333333");
            personInputDTO3.setVat("253333445");
            personInputDTO3.setBirthDate("18/07/2003");
            personInputDTO3.setAdminId("admin@gmail.com");

            FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
            long familyIdInt = familyOutputDTO.getFamilyId();

            AddFamilyMemberDTO person1Id = familyMemberService.addFamilyMember(personInputDTO1,
                    familyOutputDTO.getFamilyId());
            AddFamilyMemberDTO person2Id = familyMemberService.addFamilyMember(personInputDTO2,
                    familyOutputDTO.getFamilyId());
            AddFamilyMemberDTO person3Id = familyMemberService.addFamilyMember(personInputDTO3,
                    familyOutputDTO.getFamilyId());

            RelationshipInputDTO relationshipInputDTO1 =
                    new RelationshipInputDTO(person1Id.getEmail(), person2Id.getEmail(),
                            "1");

            RelationshipInputDTO relationshipInputDTO2 =
                    new RelationshipInputDTO(person2Id.getEmail(), person3Id.getEmail(),
                            "4");

            RelationshipInputDTO relationshipInputDTO3 =
                    new RelationshipInputDTO(person3Id.getEmail(), person1Id.getEmail(),
                            "10");


            controller.createRelationship(relationshipInputDTO1, familyIdInt);
            controller.createRelationship(relationshipInputDTO2, familyIdInt);
            controller.createRelationship(relationshipInputDTO3, familyIdInt);
        };
    }*/


/*
//    @Bean
//    public CommandLineRunner demo(IFamilyMemberService familyMemberService) {
//        return (args) -> {
//            FamilyInputDTO familyInputDTO = new FamilyInputDTO();
//            familyInputDTO.setName("António");
//            familyInputDTO.setEmail("antonio@gmail.com");
//            familyInputDTO.setStreet("Rua clara");
//            familyInputDTO.setLocation("Porto");
//            familyInputDTO.setPostCode("4000-000");
//            familyInputDTO.setPhoneNumber("911111111");
//            familyInputDTO.setVat("222333222");
//            familyInputDTO.setBirthDate("11/09/1999");
//            familyInputDTO.setFamilyName("Silva");
//            FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);
//            FamilyId familyId = new FamilyId(familyOutputDTO.getFamilyId());
//            PersonInputDTO personInputDTO = new PersonInputDTO();
//            personInputDTO.setName("Miguel");
//            personInputDTO.setEmail("miguel@gmail.com");
//            personInputDTO.setStreet("Rua clara");
//            personInputDTO.setLocation("Porto");
//            personInputDTO.setPostCode("4000-000");
//            personInputDTO.setPhoneNumber("911122111");
//            personInputDTO.setVat("222355222");
//            personInputDTO.setBirthDate("11/09/1999");
//            familyMemberService.addFamilyMember(personInputDTO, familyId.getFamilyId());
//            PersonInputDTO personInputDTO1 = new PersonInputDTO();
//            personInputDTO1.setName("Clara");
//            personInputDTO1.setEmail("clara@gmail.com");
//            personInputDTO1.setStreet("Rua escura");
//            personInputDTO1.setLocation("Porto");
//            personInputDTO1.setPostCode("4000-000");
//            personInputDTO1.setPhoneNumber("911122111");
//            personInputDTO1.setVat("224355222");
//            personInputDTO1.setBirthDate("11/09/1998");
//            familyMemberService.addFamilyMember(personInputDTO1, familyId.getFamilyId());
//
//        };
//    }*/

    /*@Bean
    public CommandLineRunner demo(IPersonService personService, IFamilyMemberService familyMemberService, PersonRepository personRepository, RoleRepository roleRepository) {
        return (args) -> {
            FamilyInputDTO familyInputDTO = new FamilyInputDTO();
            familyInputDTO.setName("António");
            familyInputDTO.setEmail("antonio@gmail.com");
            familyInputDTO.setStreet("Rua clara");
            familyInputDTO.setLocation("Porto");
            familyInputDTO.setPostCode("4000-000");
            familyInputDTO.setPhoneNumber("911111111");
            familyInputDTO.setVat("222333222");
            familyInputDTO.setBirthDate("11/09/1999");
            familyInputDTO.setFamilyName("Silva");
            FamilyOutputDTO familyOutputDTO = familyMemberService.createFamily(familyInputDTO);

            FamilyId familyId = new FamilyId(familyOutputDTO.getFamilyId());
            PersonInputDTO personInputDTO = new PersonInputDTO();
            personInputDTO.setName("Miguel");
            personInputDTO.setEmail("miguel@gmail.com");
            personInputDTO.setStreet("Rua clara");
            personInputDTO.setLocation("Porto");
            personInputDTO.setPostCode("4000-000");
            personInputDTO.setPhoneNumber("911122111");
            personInputDTO.setVat("222355222");
            personInputDTO.setBirthDate("11/09/1999");
            String email = "joao@gmail.com";
            AddFamilyMemberDTO familyMemberDTO = familyMemberService.addFamilyMember(personInputDTO, familyId.getFamilyId());
            String personId = familyMemberDTO.getEmail();

            personService.addEmail(personId, email);
        };
    }*/

    /*
        @Bean
        public CommandLineRunner demo(IFamilyMemberService familyMemberService) {
            return (args) -> {
                FamilyInputDTO familyInputDTO = new FamilyInputDTO();
                familyInputDTO.setName("David");
                familyInputDTO.setEmail("something@gmail.com")  ;
                familyInputDTO.setStreet("David St");
                familyInputDTO.setLocation("Porto");
                familyInputDTO.setPostCode("1234-123");
                familyInputDTO.setPhoneNumber("969999999");
                familyInputDTO.setVat("123456789");
                familyInputDTO.setBirthDate("12/01/2000");
                familyInputDTO.setFamilyName("Martins");
                familyMemberService.createFamily(familyInputDTO);
            };
        }
    */
    /*@Bean
    public CommandLineRunner demo(IPersonService personService,
                                  IFamilyMemberService familyMemberService,
                                  IRelationshipService relationshipService) {
        return (args) -> {
            FamilyInputDTO familyInputDTO = new FamilyInputDTO();
            familyInputDTO.setName("António");
            familyInputDTO.setEmail("antonio@gmail.com");
            familyInputDTO.setStreet("Rua clara");
            familyInputDTO.setLocation("Porto");
            familyInputDTO.setPostCode("4000-000");
            familyInputDTO.setPhoneNumber("911111111");
            familyInputDTO.setVat("222333222");
            familyInputDTO.setBirthDate("11/09/1999");
            familyInputDTO.setFamilyName("Silva");
            FamilyId familyId = familyMemberService.createFamily(familyInputDTO);
            PersonInputDTO personInputDTO = new PersonInputDTO();
            personInputDTO.setName("Miguel");
            personInputDTO.setEmail("miguel@gmail.com");
            personInputDTO.setStreet("Rua clara");
            personInputDTO.setLocation("Porto");
            personInputDTO.setPostCode("4000-000");
            personInputDTO.setPhoneNumber("911122111");
            personInputDTO.setVat("222355222");
            personInputDTO.setBirthDate("11/09/1999");
            AddFamilyMemberDTO mainUser = familyMemberService.addFamilyMember(personInputDTO,
                    familyId.getFamilyId());
            PersonInputDTO personInputDTO2 = new PersonInputDTO();
            personInputDTO2.setName("Ricardo");
            personInputDTO2.setEmail("ricardo@gmail.com");
            personInputDTO2.setStreet("Rua clara");
            personInputDTO2.setLocation("Porto");
            personInputDTO2.setPostCode("4000-000");
            personInputDTO2.setPhoneNumber("911122111");
            personInputDTO2.setVat("222366222");
            personInputDTO2.setBirthDate("11/09/1999");
            AddFamilyMemberDTO otherUser = familyMemberService.addFamilyMember(personInputDTO2,
                    familyId.getFamilyId());
            RelationshipInputDTO relationshipInputDTO = new RelationshipInputDTO(familyInputDTO.getEmail(), otherUser.getEmail(), "4");
            relationshipService.createRelationship(familyId.toString(), relationshipInputDTO);
        };
    }*/
}