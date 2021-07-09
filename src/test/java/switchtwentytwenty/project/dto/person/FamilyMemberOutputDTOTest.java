package switchtwentytwenty.project.dto.person;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FamilyMemberOutputDTOTest {
    @Test
    void ensureAllArgsConstructorIsWorking() {
        String name = "Jolie";
        String email = "jojo@gmail.com";
        FamilyMemberOutputDTO memberDTO  = new FamilyMemberOutputDTO(name, email);

        assertNotNull(memberDTO);
        assertEquals(name, memberDTO.getName());
        assertEquals(email, memberDTO.getEmail());
    }

    @Test
    void ensureNoArgsConstructorIsWorking() {
        String name = "Jolie";
        String email = "jojo@gmail.com";
        FamilyMemberOutputDTO memberDTO  = new FamilyMemberOutputDTO();
        memberDTO.setName(name);
        memberDTO.setEmail(email);

        assertNotNull(memberDTO);
        assertEquals(name, memberDTO.getName());
        assertEquals(email, memberDTO.getEmail());
    }

    @Test
    void equalsAndHashCode() {
        String name1 = "Jolie";
        String email1 = "jojo@gmail.com";
        String name2 = "Tony";
        String email2 = "toto@gmail.com";

        FamilyMemberOutputDTO memberDTO  = new FamilyMemberOutputDTO(name1, email1);
        FamilyMemberOutputDTO memberDTOSame = memberDTO;
        FamilyMemberOutputDTO memberDTOEqual = new FamilyMemberOutputDTO(name1, email1);
        FamilyMemberOutputDTO memberDTOOther = new FamilyMemberOutputDTO(name2, email2);

        assertEquals(memberDTO, memberDTOSame);
        assertEquals(memberDTO, memberDTOEqual);
        assertSame(memberDTO, memberDTOSame);
        assertNotSame(memberDTO, memberDTOEqual);
        assertEquals(memberDTO.getName(), memberDTOSame.getName());
        assertEquals(memberDTO.getEmail(), memberDTOSame.getEmail());
        assertEquals(memberDTO.hashCode(), memberDTOSame.hashCode());
        assertEquals(memberDTO.getName(), memberDTOEqual.getName());
        assertEquals(memberDTO.getEmail(), memberDTOEqual.getEmail());
        assertEquals(memberDTO.hashCode(), memberDTOEqual.hashCode());
        assertNotEquals(memberDTO, memberDTOOther);
        assertNotEquals(memberDTO.getName(), memberDTOOther.getName());
        assertNotEquals(memberDTO.getEmail(), memberDTOOther.getEmail());
        assertNotEquals(memberDTO.hashCode(), memberDTOOther.hashCode());
        assertTrue(memberDTO.equals(memberDTOEqual));
        assertFalse(memberDTO.equals(memberDTOOther));
        assertFalse(memberDTO.equals(name1));
        assertFalse(memberDTO.equals(email1));
        assertNotEquals(memberDTO,name2);
        assertNotEquals(memberDTO,email2);
        assertNotEquals(0, memberDTO.hashCode());
        assertNotEquals(null, memberDTO);
        assertFalse(memberDTO.equals(null));
    }
}