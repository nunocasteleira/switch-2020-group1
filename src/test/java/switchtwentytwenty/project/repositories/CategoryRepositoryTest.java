package switchtwentytwenty.project.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;
import switchtwentytwenty.project.datamodel.assembler.CategoryDomainDataAssembler;
import switchtwentytwenty.project.datamodel.category.BaseCategoryJPA;
import switchtwentytwenty.project.datamodel.category.FamilyCategoryJPA;
import switchtwentytwenty.project.datamodel.category.StandardCategoryJPA;
import switchtwentytwenty.project.datamodel.shared.CategoryIdJPA;
import switchtwentytwenty.project.datamodel.shared.CategoryNameJPA;
import switchtwentytwenty.project.datamodel.shared.ParentCategoryIdJPA;
import switchtwentytwenty.project.domain.exceptions.DuplicateObjectException;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.category.FamilyCategory;
import switchtwentytwenty.project.domain.model.category.StandardCategory;
import switchtwentytwenty.project.domain.model.shared.CategoryId;
import switchtwentytwenty.project.domain.model.shared.CategoryName;
import switchtwentytwenty.project.domain.model.shared.FamilyId;
import switchtwentytwenty.project.repositories.irepositories.ICategoryRepositoryJPA;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Profile("CategoryRepositoryTest")
class CategoryRepositoryTest {

    @InjectMocks
    CategoryRepository categoryRepository;

    @Mock
    ICategoryRepositoryJPA categoryRepositoryJPA;

    @Mock
    CategoryDomainDataAssembler categoryAssembler;


    @Test
    void saveStandardRootCategorySuccessfully() {
        //arrange
        String name = "Groceries";
        CategoryName categoryName = new CategoryName(name);
        StandardCategory aCategory = new StandardCategory(categoryName);
        Object categoryId = aCategory.getId().getId();
        StandardCategoryJPA standardCategoryJPA = new StandardCategoryJPA(new CategoryIdJPA(categoryId),
                new CategoryNameJPA(name));

        Optional<BaseCategoryJPA> optionalStandardCategoryJPA = Optional.of(standardCategoryJPA);

        StandardCategory result;
        long id = standardCategoryJPA.getId();

        when(categoryRepositoryJPA.existsByCategoryId(standardCategoryJPA.getCategoryId())).thenReturn(false);
        when(categoryAssembler.toData(aCategory)).thenReturn(standardCategoryJPA);
        when(categoryRepositoryJPA.save(standardCategoryJPA)).thenReturn(standardCategoryJPA);
        when(categoryRepositoryJPA.findByCategoryId(standardCategoryJPA.getCategoryId())).thenReturn(optionalStandardCategoryJPA);
        when(categoryAssembler.fromDataToDomainCategoryName(standardCategoryJPA)).thenReturn(aCategory.getName());

        //act
        result = (StandardCategory) categoryRepository.saveCategory(aCategory);

        //assert
        assertNotNull(result);
        assertNotSame(aCategory, result);
        assertEquals(aCategory.getId(), result.getId());
    }

    @Test
    void saveStandardChildCategorySuccessfully() {
        //arrange

        //Root Category
        String rootCategoryName = "Groceries";
        CategoryName rootName = new CategoryName(rootCategoryName);
        StandardCategory aCategory = new StandardCategory(rootName);

        //Child Category
        String childCategoryName = "Fruits";
        CategoryName childName = new CategoryName(childCategoryName);
        Object parentId = aCategory.getId().getId();
        CategoryId parentCategoryId = new CategoryId(parentId);
        StandardCategory aCategoryChild = new StandardCategory(childName, parentCategoryId);

        StandardCategoryJPA standardCategoryJPA =
                new StandardCategoryJPA(new CategoryIdJPA(aCategoryChild.getId().getId()),
                        new CategoryNameJPA(childCategoryName), new ParentCategoryIdJPA(parentId));

        Optional<BaseCategoryJPA> optionalStandardCategoryJPA = Optional.of(standardCategoryJPA);
        StandardCategory result;


        when(categoryRepositoryJPA.existsByCategoryId(standardCategoryJPA.getCategoryId())).thenReturn(false);
        when(categoryAssembler.toData(aCategoryChild)).thenReturn(standardCategoryJPA);
        when(categoryRepositoryJPA.save(standardCategoryJPA)).thenReturn(standardCategoryJPA);
        when(categoryRepositoryJPA.findByCategoryId(standardCategoryJPA.getCategoryId())).thenReturn(optionalStandardCategoryJPA);
        when(categoryAssembler.fromDataToDomainCategoryName(standardCategoryJPA)).thenReturn(aCategoryChild.getName());
        when(categoryAssembler.fromDataToDomainParentCategoryId(standardCategoryJPA)).thenReturn(aCategoryChild.getParentId());

        //act
        result = (StandardCategory) categoryRepository.saveCategory(aCategoryChild);

        //assert
        assertNotNull(result);
        assertEquals(aCategoryChild, result);
        assertNotSame(aCategoryChild, result);
        assertEquals(aCategoryChild.getId(), result.getId());
    }

    @Test
    void ensureTwoRootCategoriesAreSavedSuccessfully() {
        String name = "Groceries";
        CategoryName categoryName = new CategoryName(name);
        StandardCategory aCategory = new StandardCategory(categoryName);
        Object categoryId = aCategory.getId().getId();
        StandardCategoryJPA standardCategoryJPA = new StandardCategoryJPA(new CategoryIdJPA(categoryId),
                new CategoryNameJPA(name));
        Optional<BaseCategoryJPA> optionalStandardCategoryJPA = Optional.of(standardCategoryJPA);

        when(categoryRepositoryJPA.existsByCategoryId(standardCategoryJPA.getCategoryId())).thenReturn(false);
        when(categoryAssembler.toData(aCategory)).thenReturn(standardCategoryJPA);
        when(categoryRepositoryJPA.save(standardCategoryJPA)).thenReturn(standardCategoryJPA);
        when(categoryRepositoryJPA.findByCategoryId(standardCategoryJPA.getCategoryId())).thenReturn(optionalStandardCategoryJPA);
        when(categoryAssembler.fromDataToDomainCategoryName(standardCategoryJPA)).thenReturn(aCategory.getName());
        categoryRepository.saveCategory(aCategory);

        String childCategoryName = "Fruits";
        CategoryName childName = new CategoryName(childCategoryName);
        Object parentId = aCategory.getId().getId();
        CategoryId parentCategoryId = new CategoryId(parentId);
        StandardCategory aCategoryChild = new StandardCategory(childName, parentCategoryId);
        StandardCategoryJPA categoryJPA =
                new StandardCategoryJPA(new CategoryIdJPA(aCategoryChild.getId().getId()),
                        new CategoryNameJPA(childCategoryName), new ParentCategoryIdJPA(parentId));
        Optional<BaseCategoryJPA> optionalCategoryJPA = Optional.of(categoryJPA);

        when(categoryRepositoryJPA.existsByCategoryId(categoryJPA.getCategoryId())).thenReturn(false);
        when(categoryAssembler.toData(aCategoryChild)).thenReturn(categoryJPA);
        when(categoryRepositoryJPA.save(categoryJPA)).thenReturn(categoryJPA);
        when(categoryRepositoryJPA.findByCategoryId(categoryJPA.getCategoryId())).thenReturn(optionalCategoryJPA);
        when(categoryAssembler.fromDataToDomainCategoryName(categoryJPA)).thenReturn(aCategoryChild.getName());
        when(categoryAssembler.fromDataToDomainParentCategoryId(categoryJPA)).thenReturn(aCategoryChild.getParentId());
        categoryRepository.saveCategory(aCategoryChild);

        long expected = 2;
        long result;

        when(categoryRepositoryJPA.count()).thenReturn(expected);

        //act

        result = categoryRepository.getCategoryListSize();

        //assert
        assertEquals(expected, result);
    }

    @Test
    void ensureTwoRootCategoriesAreSavedAndListSizeNotEqualToOne() {
        //arrange
        String name = "Groceries";
        CategoryName categoryName = new CategoryName(name);
        StandardCategory aCategory = new StandardCategory(categoryName);
        Object categoryId = aCategory.getId().getId();
        StandardCategoryJPA standardCategoryJPA = new StandardCategoryJPA(new CategoryIdJPA(categoryId),
                new CategoryNameJPA(name));
        Optional<BaseCategoryJPA> optionalStandardCategoryJPA = Optional.of(standardCategoryJPA);

        when(categoryRepositoryJPA.existsByCategoryId(standardCategoryJPA.getCategoryId())).thenReturn(false);
        when(categoryAssembler.toData(aCategory)).thenReturn(standardCategoryJPA);
        when(categoryRepositoryJPA.save(standardCategoryJPA)).thenReturn(standardCategoryJPA);
        when(categoryRepositoryJPA.findByCategoryId(standardCategoryJPA.getCategoryId())).thenReturn(optionalStandardCategoryJPA);
        when(categoryAssembler.fromDataToDomainCategoryName(standardCategoryJPA)).thenReturn(aCategory.getName());
        categoryRepository.saveCategory(aCategory);

        String childCategoryName = "Fruits";
        CategoryName childName = new CategoryName(childCategoryName);
        Object parentId = aCategory.getId().getId();
        CategoryId parentCategoryId = new CategoryId(parentId);
        StandardCategory aCategoryChild = new StandardCategory(childName, parentCategoryId);
        StandardCategoryJPA categoryJPA =
                new StandardCategoryJPA(new CategoryIdJPA(aCategoryChild.getId().getId()),
                        new CategoryNameJPA(childCategoryName), new ParentCategoryIdJPA(parentId));
        Optional<BaseCategoryJPA> optionalCategoryJPA = Optional.of(categoryJPA);

        when(categoryRepositoryJPA.existsByCategoryId(categoryJPA.getCategoryId())).thenReturn(false);
        when(categoryAssembler.toData(aCategoryChild)).thenReturn(categoryJPA);
        when(categoryRepositoryJPA.save(categoryJPA)).thenReturn(categoryJPA);
        when(categoryRepositoryJPA.findByCategoryId(categoryJPA.getCategoryId())).thenReturn(optionalCategoryJPA);
        when(categoryAssembler.fromDataToDomainCategoryName(categoryJPA)).thenReturn(aCategoryChild.getName());
        when(categoryAssembler.fromDataToDomainParentCategoryId(categoryJPA)).thenReturn(aCategoryChild.getParentId());
        categoryRepository.saveCategory(aCategoryChild);

        long expected = 2;
        long unexpected = 1;
        long result;

        when(categoryRepositoryJPA.count()).thenReturn(expected);

        //act
        result = categoryRepository.getCategoryListSize();

        //assert
        assertNotEquals(unexpected, result);
    }

    @Test
    void ensureNotValidWhenParentCategoryDoesNotExist() {
        int parentId = new Random().nextInt();
        CategoryId parentCategoryId = new CategoryId(parentId);

        when(categoryRepositoryJPA.existsByCategoryId(new CategoryIdJPA(parentId))).thenReturn(false);

        assertThrows(ObjectDoesNotExistException.class,
                () -> categoryRepository.validateCategoryParent(parentCategoryId));
    }

    @Test
    void ensureAlreadyExistingStandardRootCategoryNotSaved() {
        //arrange
        String name = "Groceries";
        CategoryName categoryName = new CategoryName(name);
        StandardCategory aCategory = new StandardCategory(categoryName);
        Object categoryId = aCategory.getId().getId();
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(categoryId);
        StandardCategoryJPA standardCategoryJPA = new StandardCategoryJPA(new CategoryIdJPA(categoryId),
                new CategoryNameJPA(name));

        Optional<BaseCategoryJPA> optionalStandardCategoryJPA = Optional.of(standardCategoryJPA);

        when(categoryRepositoryJPA.existsByCategoryId(categoryIdJPA)).thenReturn(false);
        when(categoryAssembler.toData(aCategory)).thenReturn(standardCategoryJPA);
        when(categoryRepositoryJPA.save(standardCategoryJPA)).thenReturn(standardCategoryJPA);
        when(categoryRepositoryJPA.findByCategoryId(standardCategoryJPA.getCategoryId())).thenReturn(optionalStandardCategoryJPA);
        when(categoryAssembler.fromDataToDomainCategoryName(standardCategoryJPA)).thenReturn(aCategory.getName());
        categoryRepository.saveCategory(aCategory);

        when(categoryRepositoryJPA.existsByCategoryId(categoryIdJPA)).thenReturn(true);

        //assert + act
        assertThrows(DuplicateObjectException.class,
                () -> categoryRepository.saveCategory(aCategory));
    }

    @Test
    void ensureAlreadyExistingChildStandardCategoryNotSaved() {
        //arrange
        String name = "Groceries";
        CategoryName categoryName = new CategoryName(name);
        StandardCategory aCategory = new StandardCategory(categoryName);

        String childCategoryName = "Fruits";
        CategoryName childName = new CategoryName(childCategoryName);
        CategoryId parentId = aCategory.getId();
        StandardCategory aCategoryChild = new StandardCategory(childName, parentId);

        StandardCategoryJPA standardCategoryJPA =
                new StandardCategoryJPA(new CategoryIdJPA(aCategoryChild.getId().getId()),
                        new CategoryNameJPA(childCategoryName), new ParentCategoryIdJPA(parentId.getId()));

        Optional<BaseCategoryJPA> optionalStandardCategoryJPA = Optional.of(standardCategoryJPA);


        when(categoryRepositoryJPA.existsByCategoryId(standardCategoryJPA.getCategoryId())).thenReturn(false);
        when(categoryAssembler.toData(aCategoryChild)).thenReturn(standardCategoryJPA);
        when(categoryRepositoryJPA.save(standardCategoryJPA)).thenReturn(standardCategoryJPA);
        when(categoryRepositoryJPA.findByCategoryId(standardCategoryJPA.getCategoryId())).thenReturn(optionalStandardCategoryJPA);
        when(categoryAssembler.fromDataToDomainCategoryName(standardCategoryJPA)).thenReturn(aCategoryChild.getName());
        when(categoryAssembler.fromDataToDomainParentCategoryId(standardCategoryJPA)).thenReturn(aCategoryChild.getParentId());
        categoryRepository.saveCategory(aCategoryChild);

        when(categoryRepositoryJPA.existsByCategoryId(standardCategoryJPA.getCategoryId())).thenReturn(true);

        //assert + act
        assertThrows(DuplicateObjectException.class,
                () -> categoryRepository.saveCategory(aCategoryChild));

    }

    @Test
    void ensureStandardCategoryJPANotFound() {
        //arrange
        String name = "Groceries";
        CategoryName categoryName = new CategoryName(name);
        StandardCategory aCategory = new StandardCategory(categoryName);
        Object categoryId = aCategory.getId().getId();
        StandardCategoryJPA standardCategoryJPA = new StandardCategoryJPA(new CategoryIdJPA(categoryId),
                new CategoryNameJPA(name));

        Optional<BaseCategoryJPA> optionalStandardCategoryJPA = Optional.empty();

        when(categoryRepositoryJPA.existsByCategoryId(standardCategoryJPA.getCategoryId())).thenReturn(false);
        when(categoryAssembler.toData(aCategory)).thenReturn(standardCategoryJPA);
        when(categoryRepositoryJPA.findByCategoryId(standardCategoryJPA.getCategoryId())).thenReturn(optionalStandardCategoryJPA);

        //assert + act
        assertThrows(ObjectDoesNotExistException.class,
                () -> categoryRepository.saveCategory(aCategory));
    }

    @Test
    void saveFamilyRootCategorySuccessfully() {
        //arrange
        String name = "Groceries";
        CategoryName categoryName = new CategoryName(name);
        long familyId = 123456789;
        FamilyId familyIdVO = new FamilyId(familyId);
        FamilyCategory aCategory = new FamilyCategory(categoryName, familyIdVO);
        Object categoryId = aCategory.getId().getId();
        FamilyCategoryJPA familyCategoryJPA = new FamilyCategoryJPA(new CategoryIdJPA(categoryId),
                new CategoryNameJPA(name), familyId);

        Optional<BaseCategoryJPA> optionalFamilyCategory = Optional.of(familyCategoryJPA);

        FamilyCategory result;

        when(categoryRepositoryJPA.existsByCategoryId(familyCategoryJPA.getCategoryId())).thenReturn(false);
        when(categoryAssembler.toData(aCategory)).thenReturn(familyCategoryJPA);
        when(categoryRepositoryJPA.save(familyCategoryJPA)).thenReturn(familyCategoryJPA);
        when(categoryRepositoryJPA.findByCategoryId(familyCategoryJPA.getCategoryId())).thenReturn(optionalFamilyCategory);
        when(categoryAssembler.fromDataToDomainCategoryName(familyCategoryJPA)).thenReturn(aCategory.getName());
        when(categoryAssembler.fromDataToDomainFamilyId(familyCategoryJPA)).thenReturn(aCategory.getFamilyId());

        //act
        result = (FamilyCategory) categoryRepository.saveCategory(aCategory);

        //assert
        assertNotNull(result);
        assertNotSame(aCategory, result);
        assertEquals(aCategory.getId(), result.getId());
    }

    @Test
    void saveFamilyChildCategorySuccessfully() {
        //arrange

        //Root Category
        String name = "Groceries";
        CategoryName categoryName = new CategoryName(name);
        long familyId = 123456789;
        FamilyId familyIdVO = new FamilyId(familyId);
        FamilyCategory aCategory = new FamilyCategory(categoryName, familyIdVO);

        //Child Category
        String childCategoryName = "Fruits";
        CategoryName childName = new CategoryName(childCategoryName);
        CategoryId parentCategoryId = aCategory.getId();
        FamilyCategory aCategoryChild = new FamilyCategory(childName, parentCategoryId, familyIdVO);

        FamilyCategoryJPA familyCategoryJPA =
                new FamilyCategoryJPA(new CategoryIdJPA(aCategoryChild.getId().getId()),
                        new CategoryNameJPA(childCategoryName), new ParentCategoryIdJPA(parentCategoryId.getId()), familyId);

        Optional<BaseCategoryJPA> optionalFamilyCategory = Optional.of(familyCategoryJPA);
        FamilyCategory result;


        when(categoryRepositoryJPA.existsByCategoryId(familyCategoryJPA.getCategoryId())).thenReturn(false);
        when(categoryAssembler.toData(aCategoryChild)).thenReturn(familyCategoryJPA);
        when(categoryRepositoryJPA.save(familyCategoryJPA)).thenReturn(familyCategoryJPA);
        when(categoryRepositoryJPA.findByCategoryId(familyCategoryJPA.getCategoryId())).thenReturn(optionalFamilyCategory);
        when(categoryAssembler.fromDataToDomainCategoryName(familyCategoryJPA)).thenReturn(aCategoryChild.getName());
        when(categoryAssembler.fromDataToDomainParentCategoryId(familyCategoryJPA)).thenReturn(aCategoryChild.getParentId());
        when(categoryAssembler.fromDataToDomainFamilyId(familyCategoryJPA)).thenReturn(aCategoryChild.getFamilyId());

        //act
        result = (FamilyCategory) categoryRepository.saveCategory(aCategoryChild);

        //assert
        assertNotNull(result);
        assertEquals(aCategoryChild, result);
        assertNotSame(aCategoryChild, result);
        assertEquals(aCategoryChild.getId(), result.getId());
    }

    @Test
    void saveFamilyChildOfStandardCategorySuccessfully() {
        //arrange

        //Root Standard Category
        String name = "Groceries";
        CategoryName categoryName = new CategoryName(name);
        StandardCategory aCategory = new StandardCategory(categoryName);

        //Child Family Category
        String childCategoryName = "Fruits";
        CategoryName childName = new CategoryName(childCategoryName);
        CategoryId parentCategoryId = aCategory.getId();
        long familyId = 123456789;
        FamilyId familyIdVO = new FamilyId(familyId);
        FamilyCategory aCategoryChild = new FamilyCategory(childName, parentCategoryId, familyIdVO);

        FamilyCategoryJPA familyCategoryJPA =
                new FamilyCategoryJPA(new CategoryIdJPA(aCategoryChild.getId().getId()),
                        new CategoryNameJPA(childCategoryName), new ParentCategoryIdJPA(parentCategoryId.getId()), familyId);

        Optional<BaseCategoryJPA> optionalFamilyCategory = Optional.of(familyCategoryJPA);
        FamilyCategory result;


        when(categoryRepositoryJPA.existsByCategoryId(familyCategoryJPA.getCategoryId())).thenReturn(false);
        when(categoryAssembler.toData(aCategoryChild)).thenReturn(familyCategoryJPA);
        when(categoryRepositoryJPA.save(familyCategoryJPA)).thenReturn(familyCategoryJPA);
        when(categoryRepositoryJPA.findByCategoryId(familyCategoryJPA.getCategoryId())).thenReturn(optionalFamilyCategory);
        when(categoryAssembler.fromDataToDomainCategoryName(familyCategoryJPA)).thenReturn(aCategoryChild.getName());
        when(categoryAssembler.fromDataToDomainParentCategoryId(familyCategoryJPA)).thenReturn(aCategoryChild.getParentId());
        when(categoryAssembler.fromDataToDomainFamilyId(familyCategoryJPA)).thenReturn(aCategoryChild.getFamilyId());

        //act
        result = (FamilyCategory) categoryRepository.saveCategory(aCategoryChild);

        //assert
        assertNotNull(result);
        assertEquals(aCategoryChild, result);
        assertNotSame(aCategoryChild, result);
        assertEquals(aCategoryChild.getId(), result.getId());
    }

    @Test
    void ensureAlreadyExistingFamilyRootCategoryNotSaved() {
        //arrange
        String name = "Groceries";
        CategoryName categoryName = new CategoryName(name);
        long familyId = 123456789;
        FamilyId familyIdVO = new FamilyId(familyId);
        FamilyCategory aCategory = new FamilyCategory(categoryName, familyIdVO);
        Object categoryId = aCategory.getId().getId();
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(categoryId);
        FamilyCategoryJPA familyCategoryJPA = new FamilyCategoryJPA(new CategoryIdJPA(categoryId),
                new CategoryNameJPA(name), familyId);

        Optional<BaseCategoryJPA> optionalFamilyCategory = Optional.of(familyCategoryJPA);

        when(categoryRepositoryJPA.existsByCategoryId(categoryIdJPA)).thenReturn(false);
        when(categoryAssembler.toData(aCategory)).thenReturn(familyCategoryJPA);
        when(categoryRepositoryJPA.save(familyCategoryJPA)).thenReturn(familyCategoryJPA);
        when(categoryRepositoryJPA.findByCategoryId(familyCategoryJPA.getCategoryId())).thenReturn(optionalFamilyCategory);
        when(categoryAssembler.fromDataToDomainCategoryName(familyCategoryJPA)).thenReturn(aCategory.getName());
        when(categoryAssembler.fromDataToDomainFamilyId(familyCategoryJPA)).thenReturn(aCategory.getFamilyId());
        categoryRepository.saveCategory(aCategory);

        when(categoryRepositoryJPA.existsByCategoryId(categoryIdJPA)).thenReturn(true);

        //assert + act
        assertThrows(DuplicateObjectException.class,
                () -> categoryRepository.saveCategory(aCategory));
    }

    @Test
    void ensureAlreadyExistingChildFamilyCategoryNotSaved() {
        //arrange
        String name = "Groceries";
        CategoryName categoryName = new CategoryName(name);
        long familyId = 123456789;
        FamilyId familyIdVO = new FamilyId(familyId);
        FamilyCategory aCategory = new FamilyCategory(categoryName, familyIdVO);

        String childCategoryName = "Fruits";
        CategoryName childName = new CategoryName(childCategoryName);
        CategoryId parentId = aCategory.getId();
        FamilyCategory aCategoryChild = new FamilyCategory(childName, parentId, familyIdVO);

        FamilyCategoryJPA familyCategoryJPA =
                new FamilyCategoryJPA(new CategoryIdJPA(aCategoryChild.getId().getId()),
                        new CategoryNameJPA(childCategoryName), new ParentCategoryIdJPA(parentId.getId()), familyId);

        Optional<BaseCategoryJPA> optionalFamilyCategoryJPA = Optional.of(familyCategoryJPA);


        when(categoryRepositoryJPA.existsByCategoryId(familyCategoryJPA.getCategoryId())).thenReturn(false);
        when(categoryAssembler.toData(aCategoryChild)).thenReturn(familyCategoryJPA);
        when(categoryRepositoryJPA.save(familyCategoryJPA)).thenReturn(familyCategoryJPA);
        when(categoryRepositoryJPA.findByCategoryId(familyCategoryJPA.getCategoryId())).thenReturn(optionalFamilyCategoryJPA);
        when(categoryAssembler.fromDataToDomainCategoryName(familyCategoryJPA)).thenReturn(aCategoryChild.getName());
        when(categoryAssembler.fromDataToDomainParentCategoryId(familyCategoryJPA)).thenReturn(aCategoryChild.getParentId());
        when(categoryAssembler.fromDataToDomainFamilyId(familyCategoryJPA)).thenReturn(aCategoryChild.getFamilyId());
        categoryRepository.saveCategory(aCategoryChild);

        when(categoryRepositoryJPA.existsByCategoryId(familyCategoryJPA.getCategoryId())).thenReturn(true);

        //assert + act
        assertThrows(DuplicateObjectException.class,
                () -> categoryRepository.saveCategory(aCategoryChild));

    }

    @Test
    void ensureFamilyCategoryJPANotFound() {
        //arrange
        String name = "Groceries";
        CategoryName categoryName = new CategoryName(name);
        long familyId = 123456789;
        FamilyId familyIdVO = new FamilyId(familyId);
        FamilyCategory aCategory = new FamilyCategory(categoryName, familyIdVO);
        Object categoryId = aCategory.getId().getId();
        FamilyCategoryJPA familyCategoryJPA = new FamilyCategoryJPA(new CategoryIdJPA(categoryId),
                new CategoryNameJPA(name), familyId);

        Optional<BaseCategoryJPA> optionalFamilyCategoryJPA = Optional.empty();

        when(categoryRepositoryJPA.existsByCategoryId(familyCategoryJPA.getCategoryId())).thenReturn(false);
        when(categoryAssembler.toData(aCategory)).thenReturn(familyCategoryJPA);
        when(categoryRepositoryJPA.findByCategoryId(familyCategoryJPA.getCategoryId())).thenReturn(optionalFamilyCategoryJPA);

        //assert + act
        assertThrows(ObjectDoesNotExistException.class,
                () -> categoryRepository.saveCategory(aCategory));
    }


    @Test
    void getStandardCategoriesSuccessfully() {
        //arrange
        List<StandardCategory> expected = new ArrayList<>();
        List<StandardCategory> result;

        //Root Category
        String rootCategoryName = "Groceries";
        CategoryName rootName = new CategoryName(rootCategoryName);
        StandardCategory aCategory = new StandardCategory(rootName);

        //Child Category
        String childCategoryName = "Fruits";
        CategoryName childName = new CategoryName(childCategoryName);
        Object parentId = aCategory.getId().getId();
        CategoryId parentCategoryId = new CategoryId(parentId);
        StandardCategory aCategoryChild = new StandardCategory(childName, parentCategoryId);

        StandardCategoryJPA standardCategoryRootJPA =
                new StandardCategoryJPA(new CategoryIdJPA(aCategory.getId().getId()),
                        new CategoryNameJPA(rootCategoryName));

        StandardCategoryJPA standardCategoryChildJPA =
                new StandardCategoryJPA(new CategoryIdJPA(aCategoryChild.getId().getId()),
                        new CategoryNameJPA(childCategoryName), new ParentCategoryIdJPA(parentId));

        StandardCategoryJPA array[] = {standardCategoryRootJPA, standardCategoryChildJPA};
        Iterable<BaseCategoryJPA> standardCategoriesJPAIterable = Arrays.asList(array);

        when(categoryRepositoryJPA.findAllStandardCategories()).thenReturn(standardCategoriesJPAIterable);
        when(categoryAssembler.fromDataToDomainCategoryName(standardCategoryRootJPA)).thenReturn(aCategory.getName());
        when(categoryAssembler.fromDataToDomainParentCategoryId(standardCategoryRootJPA)).thenReturn(aCategory.getParentId());
        when(categoryAssembler.fromDataToDomainCategoryName(standardCategoryChildJPA)).thenReturn(aCategoryChild.getName());
        when(categoryAssembler.fromDataToDomainParentCategoryId(standardCategoryChildJPA)).thenReturn(aCategoryChild.getParentId());

        //act
        expected.add(aCategory);
        expected.add(aCategoryChild);
        result = categoryRepository.getStandardCategories();

        //assert
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void getZeroStandardCategories() {
        //act + arrange
        StandardCategoryJPA[] array = {};
        Iterable<BaseCategoryJPA> standardCategoriesJPAIterable = Arrays.asList(array);
        List<StandardCategory> expectedCategories = new ArrayList<>();

        when(categoryRepositoryJPA.findAllStandardCategories()).thenReturn(standardCategoriesJPAIterable);
        List<StandardCategory> resultCategories = categoryRepository.getStandardCategories();

        //assert + act
        assertNotNull(resultCategories);
        assertEquals(expectedCategories, resultCategories);
    }
}
