package switchtwentytwenty.project.datamodel.shared;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.model.shared.PersonName;
import switchtwentytwenty.project.domain.model.shared.PersonVat;

import static org.junit.jupiter.api.Assertions.*;

class PersonNameJPATest {

    @Test
    void ensureEqualsAndHashCode(){
        String personName = "Equals";
        String differentPersonName = "Different";
        PersonNameJPA personNameJPA = new PersonNameJPA(personName);
        //noinspection UnnecessaryLocalVariable
        PersonNameJPA personNameJPASame = personNameJPA;
        PersonNameJPA secondPersonNameJPA = new PersonNameJPA(personName);
        PersonNameJPA nullName = null;
        PersonNameJPA differentPersonNameJPA = new PersonNameJPA(differentPersonName);
        PersonVat vat = new PersonVat("222222222");

        assertEquals(personNameJPA, secondPersonNameJPA);
        assertEquals(personNameJPA.getPersonName(), personName);
        assertNotEquals(personNameJPA, nullName);
        assertEquals(personNameJPA.hashCode(), secondPersonNameJPA.hashCode());
        assertNotEquals(personNameJPA.hashCode(), hashCode());
        assertNotEquals(personNameJPA, differentPersonNameJPA);
        assertNotEquals(personNameJPA.hashCode(), differentPersonNameJPA.hashCode());
        assertNotEquals(0, personNameJPA.hashCode());
        assertEquals(personNameJPA, personNameJPASame);
        assertNotEquals(personNameJPA, vat);
    }
}