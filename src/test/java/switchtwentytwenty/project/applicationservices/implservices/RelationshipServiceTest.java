package switchtwentytwenty.project.applicationservices.implservices;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;
import switchtwentytwenty.project.domain.model.family.Family;
import switchtwentytwenty.project.domain.model.person.Person;
import switchtwentytwenty.project.domain.model.shared.*;
import switchtwentytwenty.project.dto.family.RelationshipListDTO;
import switchtwentytwenty.project.repositories.FamilyRepository;
import switchtwentytwenty.project.repositories.PersonRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Profile("RelationshipServiceTest")
class RelationshipServiceTest {
    @InjectMocks
    RelationshipService relationshipService;

    @Mock
    FamilyRepository familyRepository;

    @Mock
    PersonRepository personRepository;

    @Test
    @DisplayName("Get a Relationship List with one relationship from a designated family")
    void getFamilyRelationshipListWithOneRelationship() {
        //Arrange
        String familyName = "Rodrigues";
        String mainUserString = "david@teste.com";
        Email mainUserId = new Email(mainUserString);
        String mainUserPersonName = "David";
        Person mainUser = new Person.Builder(mainUserId)
                .withName(new PersonName(mainUserPersonName))
                .build();
        Family family = new Family.FamilyBuilder(new FamilyName(familyName), mainUserId)
                .withRegistrationDate()
                .withId()
                .build();
        FamilyId familyIdVO = family.getFamilyId();
        long familyId = familyIdVO.getFamilyId();
        String otherUserString = "maria@teste.com";
        Email otherUserId = new Email(otherUserString);
        String otherUserPersonName = "Maria";
        Person otherUser = new Person.Builder(mainUserId)
                .withName(new PersonName(otherUserPersonName))
                .build();
        RelationshipType relationshipType = RelationshipType.CHILD;
        Relationship relationship = new Relationship(relationshipType, mainUserId, otherUserId);
        List<Relationship> listOfRelationships = new ArrayList<>();
        listOfRelationships.add(relationship);

        when(familyRepository.getRelationshipsList(familyIdVO)).thenReturn(listOfRelationships);
        when(personRepository.getByEmail(mainUserId)).thenReturn(mainUser);
        when(personRepository.getByEmail(otherUserId)).thenReturn(otherUser);

        //Act
        RelationshipListDTO result = relationshipService.getFamilyRelationshipList(familyId);

        //Assert
        assertNotNull(result);
        assertNotEquals(0, result.getRelationshipList().size());
        assertEquals(mainUserPersonName, result.getRelationshipList().get(0).getMainUser().getUserName());
        assertEquals(otherUserPersonName, result.getRelationshipList().get(0).getOtherUser().getUserName());
        assertEquals(relationshipType.toString(), result.getRelationshipList().get(0).getRelationshipType());
    }

    @Test
    @DisplayName("Get a Relationship List with ZERO relationship from a designated family")
    void getFamilyRelationshipListWithNoRelationship() {
        //Arrange
        String familyName = "Rodrigues";
        Email mainUserEmail = new Email("david@teste.com");
        Family family = new Family.FamilyBuilder(new FamilyName(familyName), mainUserEmail)
                .withRegistrationDate()
                .withId()
                .build();
        FamilyId familyIdVO = family.getFamilyId();
        long familyId = familyIdVO.getFamilyId();

        //Act
        RelationshipListDTO result = relationshipService.getFamilyRelationshipList(familyId);

        //Assert
        assertNotNull(result);
        assertEquals(0, result.getRelationshipList().size());
    }
}