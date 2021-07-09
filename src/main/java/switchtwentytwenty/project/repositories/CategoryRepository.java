package switchtwentytwenty.project.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import switchtwentytwenty.project.datamodel.assembler.CategoryDomainDataAssembler;
import switchtwentytwenty.project.datamodel.category.BaseCategoryJPA;
import switchtwentytwenty.project.datamodel.category.FamilyCategoryJPA;
import switchtwentytwenty.project.datamodel.shared.CategoryIdJPA;
import switchtwentytwenty.project.domain.exceptions.DuplicateObjectException;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.category.BaseCategory;
import switchtwentytwenty.project.domain.model.category.FamilyCategory;
import switchtwentytwenty.project.domain.model.category.StandardCategory;
import switchtwentytwenty.project.domain.model.shared.CategoryId;
import switchtwentytwenty.project.domain.model.shared.CategoryName;
import switchtwentytwenty.project.domain.model.shared.FamilyId;
import switchtwentytwenty.project.repositories.irepositories.ICategoryRepositoryJPA;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CategoryRepository {

    public static final String DUPLICATE_CATEGORY = "Category already exists.";
    @Autowired
    private CategoryDomainDataAssembler categoryAssembler;
    @Autowired
    private ICategoryRepositoryJPA categoryRepositoryJPA;

    /**
     * Method to save a category in the repository.
     *
     * @param category an instance of Category.
     */
    public BaseCategory saveCategory(BaseCategory category) {
        CategoryId categoryId = category.getId();
        existsRepeatedCategory(categoryId);
        BaseCategoryJPA categoryJPA = categoryAssembler.toData(category);
        categoryRepositoryJPA.save(categoryJPA);

        return getCategory(categoryId);
    }

    /**
     * Method to check if a category with a specific id already exists.
     *
     * @param categoryId id of the category
     */
    private void existsRepeatedCategory(CategoryId categoryId) {
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(categoryId.getId());
        if (categoryRepositoryJPA.existsByCategoryId(categoryIdJPA)) {
            throw new DuplicateObjectException(DUPLICATE_CATEGORY);
        }
    }

    /**
     * Method to validate the parent of a standard category, i.e, if the parent of a standard
     * category exists.
     *
     * @param parentId id of the parent category.
     */
    public void validateCategoryParent(CategoryId parentId) {
        CategoryIdJPA categoryParentIdJPA = new CategoryIdJPA(parentId.getId());
        if (!categoryRepositoryJPA.existsByCategoryId(categoryParentIdJPA)) {
            throw new ObjectDoesNotExistException("Parent Category does not exist.");
        }
    }

    /**
     * Method to return the number of categories saved in the repository.
     *
     * @return number of categories in the repository
     */
    public long getCategoryListSize() {
        return categoryRepositoryJPA.count();
    }

    /**
     * Method to get the category through its value objects.
     *
     * @param categoryId the category id of the category we want to get
     * @return the category that matches that id
     */
    public BaseCategory getCategory(CategoryId categoryId) {
        BaseCategory result;
        BaseCategoryJPA categoryJPA = getCategoryJPA(categoryId);
        long id = categoryJPA.getId();

        CategoryName categoryName = categoryAssembler.fromDataToDomainCategoryName(categoryJPA);

        if (categoryJPA.getParentId() == null) {
            result = getCategory(categoryJPA, categoryName);
        } else {
            CategoryId categoryParentId = categoryAssembler.fromDataToDomainParentCategoryId(categoryJPA);
            result = getCategory(categoryJPA, categoryName, categoryParentId);
        }
        result.setIdDatabase(id);
        return result;
    }

    /**
     * Method to get the category without the parent.
     *
     * @param categoryJPA  the categoryJPA saved in the JPA repository.
     * @param categoryName the name of the category.
     * @return an instance of the category.
     */
    private BaseCategory getCategory(BaseCategoryJPA categoryJPA, CategoryName categoryName) {
        BaseCategory aCategory;
        if (!categoryJPA.isStandard()) {
            FamilyId familyId = categoryAssembler.fromDataToDomainFamilyId((FamilyCategoryJPA) categoryJPA);
            aCategory = new FamilyCategory(categoryName, familyId);
        } else {
            aCategory = new StandardCategory(categoryName);
        }
        return aCategory;
    }

    /**
     * Method to get the category with the parent category.
     *
     * @param categoryJPA      the categoryJPA saved in the JPA repository.
     * @param categoryName     the name of the category.
     * @param categoryParentId the id of the parent category.
     * @return an instance of the category.
     */
    private BaseCategory getCategory(BaseCategoryJPA categoryJPA, CategoryName categoryName, CategoryId categoryParentId) {
        BaseCategory aCategory;
        if (!categoryJPA.isStandard()) {
            FamilyId familyId = categoryAssembler.fromDataToDomainFamilyId((FamilyCategoryJPA) categoryJPA);
            aCategory = new FamilyCategory(categoryName, categoryParentId, familyId);
        } else {
            aCategory = new StandardCategory(categoryName, categoryParentId);
        }
        return aCategory;
    }

    /**
     * Method to get a saved categoryJPA from the JPA repository.
     *
     * @param categoryId the category id of the category we want to get.
     * @return an instance of CategoryJPA.
     */
    private BaseCategoryJPA getCategoryJPA(CategoryId categoryId) {
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(categoryId.getId());

        Optional<BaseCategoryJPA> categoryJPAOptional =
                categoryRepositoryJPA.findByCategoryId(categoryIdJPA);
        if (categoryJPAOptional.isPresent()) {
            return categoryJPAOptional.get();
        } else {
            throw new ObjectDoesNotExistException("The category does not exist.");
        }
    }

    /**
     * This method allows to obtain the internal standard categories.
     *
     * @return a list with all internal standard categories.
     */
    public List<StandardCategory> getStandardCategories() {
        Iterable<BaseCategoryJPA> standardCategoriesJPAOptional = categoryRepositoryJPA.findAllStandardCategories();
        List<BaseCategoryJPA> standardCategoriesJPA = convertIterableToList(standardCategoriesJPAOptional);
        List<StandardCategory> standardCategories = new ArrayList<>();
        StandardCategory standardCategory;

        for (BaseCategoryJPA standardCategoryJPA : standardCategoriesJPA) {
            CategoryName categoryName = categoryAssembler.fromDataToDomainCategoryName(standardCategoryJPA);
            CategoryId parentCategoryId = categoryAssembler.fromDataToDomainParentCategoryId(standardCategoryJPA);
            if (parentCategoryId == null) {
                standardCategory = new StandardCategory(categoryName);
            } else {
                standardCategory = new StandardCategory(categoryName, parentCategoryId);
            }
            standardCategory.setIdDatabase(standardCategoryJPA.getId());
            standardCategories.add(standardCategory);
        }
        return standardCategories;
    }

    public List<FamilyCategory> getFamilyCategories(FamilyId familyId) {
        Iterable<FamilyCategoryJPA> familyCategoryJPASIterable = categoryRepositoryJPA.findAllFamilyCategories(familyId.getFamilyId());
        List<FamilyCategoryJPA> familyCategoriesJPA = convertFamilyIterableToList(familyCategoryJPASIterable);
        List<FamilyCategory> familyCategories = new ArrayList<>();
        FamilyCategory familyCategory;

        for (FamilyCategoryJPA familyCategoryJPA : familyCategoriesJPA) {
            CategoryName categoryName = categoryAssembler.fromDataToDomainCategoryName(familyCategoryJPA);
            CategoryId parentCategoryId = categoryAssembler.fromDataToDomainParentCategoryId(familyCategoryJPA);
            if (parentCategoryId == null) {
                familyCategory = new FamilyCategory(categoryName, familyId);

            } else {
                familyCategory = new FamilyCategory(categoryName, parentCategoryId, familyId);
            }
            familyCategory.setIdDatabase(familyCategoryJPA.getFamilyId());
            familyCategories.add(familyCategory);
        }
        if (familyCategories.isEmpty()) {
            throw new ObjectDoesNotExistException("There are no family categories.");
        } else {
            return familyCategories;
        }
    }

    private List<BaseCategoryJPA> convertIterableToList(Iterable<BaseCategoryJPA> standardCategoriesJPAIterable) {
        List<BaseCategoryJPA> standardCategoriesJPA = new ArrayList<>();
        standardCategoriesJPAIterable.forEach(standardCategoriesJPA::add);
        return standardCategoriesJPA;
    }

    private List<FamilyCategoryJPA> convertFamilyIterableToList(Iterable<FamilyCategoryJPA> familyCategoryJPAIterable) {
        List<FamilyCategoryJPA> familyCategoriesJPA = new ArrayList<>();
        familyCategoryJPAIterable.forEach(familyCategoriesJPA::add);
        return familyCategoriesJPA;
    }
}