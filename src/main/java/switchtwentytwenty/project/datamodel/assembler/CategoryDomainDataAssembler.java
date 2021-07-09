package switchtwentytwenty.project.datamodel.assembler;

import org.springframework.stereotype.Service;
import switchtwentytwenty.project.datamodel.category.BaseCategoryJPA;
import switchtwentytwenty.project.datamodel.category.FamilyCategoryJPA;
import switchtwentytwenty.project.datamodel.category.StandardCategoryJPA;
import switchtwentytwenty.project.datamodel.shared.CategoryIdJPA;
import switchtwentytwenty.project.datamodel.shared.CategoryNameJPA;
import switchtwentytwenty.project.datamodel.shared.ParentCategoryIdJPA;
import switchtwentytwenty.project.domain.model.category.BaseCategory;
import switchtwentytwenty.project.domain.model.category.FamilyCategory;
import switchtwentytwenty.project.domain.model.category.StandardCategory;
import switchtwentytwenty.project.domain.model.shared.CategoryId;
import switchtwentytwenty.project.domain.model.shared.CategoryName;
import switchtwentytwenty.project.domain.model.shared.FamilyId;

import java.util.regex.Pattern;

@Service
public class CategoryDomainDataAssembler {


    /**
     * Method to convert BaseCategory in BaseCategoryJPA.
     *
     * @param category an instance of BaseCategory.
     * @return a baseCategoryJPA
     */
    public BaseCategoryJPA toData(BaseCategory category) {
        if (!category.isStandard()) {
            return toData((FamilyCategory) category);
        }
        return toData((StandardCategory) category);
    }


    /**
     * Method to convert StandardCategory in StandardCategoryJPA.
     *
     * @param standardCategory an instance of StandardCategory.
     * @return a standardCategoryJPA
     */
    private BaseCategoryJPA toData(StandardCategory standardCategory) {
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(standardCategory.getId().getId());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA(standardCategory.getName().toString());

        if (standardCategory.getParentId() != null) {
            ParentCategoryIdJPA categoryParentJPA =
                    new ParentCategoryIdJPA(standardCategory.getParentId().getId());
            return new StandardCategoryJPA(categoryIdJPA, categoryNameJPA, categoryParentJPA);
        } else {
            return new StandardCategoryJPA(categoryIdJPA, categoryNameJPA);
        }
    }

    /**
     * Method to convert FamilyCategory in FamilyCategoryJPA.
     *
     * @param familyCategory an instance of FamilyCategory.
     * @return a familyCategoryJPA
     */
    private BaseCategoryJPA toData(FamilyCategory familyCategory) {
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(familyCategory.getId().getId());
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA(familyCategory.getName().toString());
        long familyIdJPA = familyCategory.getFamilyId().getFamilyId();

        if (familyCategory.getParentId() != null) {
            ParentCategoryIdJPA categoryParentJPA =
                    new ParentCategoryIdJPA(familyCategory.getParentId().getId());
            return new FamilyCategoryJPA(categoryIdJPA, categoryNameJPA, categoryParentJPA, familyIdJPA);
        } else {
            return new FamilyCategoryJPA(categoryIdJPA, categoryNameJPA, familyIdJPA);
        }
    }

    /**
     * Method to get the value object categoryName from a standardCategoryJPA.
     *
     * @param aCategoryJPA a generic categoryJPA object.
     * @return a categoryName
     */
    public CategoryName fromDataToDomainCategoryName(BaseCategoryJPA aCategoryJPA) {
        return new CategoryName(aCategoryJPA.getName().getName());
    }

    /**
     * Method to get the value object parentCategoryId from a standardCategoryJPA.
     *
     * @param aCategoryJPA a generic categoryJPA object.
     * @return a categoryId
     */
    public CategoryId fromDataToDomainParentCategoryId(BaseCategoryJPA aCategoryJPA) {
        CategoryId result;
        ParentCategoryIdJPA parentId = aCategoryJPA.getParentId();
        if (parentId == null) {
            return null;
        }

        if (checkIfParentCategoryIdJPAIsANumber(aCategoryJPA.getParentId().getId())) {
            result = new CategoryId(Integer.parseInt(aCategoryJPA.getParentId().getId()));
        } else {
            result = new CategoryId(aCategoryJPA.getParentId().getId());
        }
        return result;
    }

    /**
     * Method to get the value object parentCategoryId from a standardCategoryJPA.
     *
     * @param familyCategoryJPA a familyCategoryJPA object.
     * @return a Family Id
     */
    public FamilyId fromDataToDomainFamilyId(FamilyCategoryJPA familyCategoryJPA) {
        return new FamilyId(familyCategoryJPA.getFamilyId());
    }

    /**
     * Method to check if the parentCategoryIdJPA of a categoryJPA has only numbers.
     *
     * @param id the parent category id
     * @return true id the id has only numbers, false otherwise.
     */
    private boolean checkIfParentCategoryIdJPAIsANumber(String id) {
        String alphanumericRegex = "^-?[0-9]*$";
        Pattern pat = Pattern.compile(alphanumericRegex);

        return pat.matcher(id).matches();
    }

}
