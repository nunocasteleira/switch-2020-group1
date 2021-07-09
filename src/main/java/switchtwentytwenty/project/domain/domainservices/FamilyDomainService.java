package switchtwentytwenty.project.domain.domainservices;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import switchtwentytwenty.project.domain.exceptions.DuplicateObjectException;
import switchtwentytwenty.project.domain.exceptions.ObjectCanNotBeNullException;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.family.Family;
import switchtwentytwenty.project.domain.model.shared.Email;

@Service
public class FamilyDomainService {

    /**
     * Method to verify if a family has a Cash Account.
     *
     * @param family family object
     */
    public void checkIfFamilyHasCashAccount(Family family) {
        if (family.hasCashAccount()) {
            throw new DuplicateObjectException("Family already has a cash account.");
        }
    }

    /**
     * This method checks if a given person already has a relationship with the administrator of
     * his/her family
     *
     * @param family    family
     * @param mainUserId  id of the person we want to check - the relationship is in relation to
     *                    him/her
     * @param otherUserId id of the other family member in the relationship
     * @return boolean true, if person1 already has a relationship with person2, false if he/she
     *         doesn't
     */
    public boolean existsRelationship(Family family, Email mainUserId, Email otherUserId) {
        boolean result = false;
        if (mainUserId == null || otherUserId == null) {
            throw new ObjectCanNotBeNullException("This email cannot be null");
        }
        if (!family.checkIfPersonExistsInFamily(mainUserId) || !family.checkIfPersonExistsInFamily(otherUserId)) {
            throw new ObjectDoesNotExistException("The person does not exist in the family");
        }
        if (family.checkIfRelationshipExists(mainUserId, otherUserId)) {
            result = true;
        }
        return result;
    }

    /**
     * Method to check if a member is admin of the given family.
     * @param family family
     * @param adminId id of the member (Email)
     */
    public void checkFamilyAdmin(Family family, Email adminId) {
        if (!family.isAdmin(adminId)) {
            throw new AccessDeniedException("ACCESS DENIED: You are not Admin of this family.");
        }
    }
}
