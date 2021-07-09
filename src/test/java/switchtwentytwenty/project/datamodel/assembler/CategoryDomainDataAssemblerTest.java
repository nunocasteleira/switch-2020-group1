package switchtwentytwenty.project.datamodel.assembler;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.datamodel.category.StandardCategoryJPA;
import switchtwentytwenty.project.datamodel.shared.CategoryIdJPA;
import switchtwentytwenty.project.datamodel.shared.CategoryNameJPA;
import switchtwentytwenty.project.datamodel.shared.ParentCategoryIdJPA;
import switchtwentytwenty.project.domain.model.category.StandardCategory;
import switchtwentytwenty.project.domain.model.shared.CategoryId;
import switchtwentytwenty.project.domain.model.shared.CategoryName;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CategoryDomainDataAssemblerTest {

    @Test
    void toData() {
        int childCategoryParentId = new Random().nextInt();
        String childCategoryName = "Fruits";
        CategoryName categoryName = new CategoryName(childCategoryName);
        CategoryId parentCategoryId = new CategoryId(childCategoryParentId);

        StandardCategory childCategory = new StandardCategory(categoryName, parentCategoryId);
        CategoryId expected = childCategory.getId();

        CategoryDomainDataAssembler categoryDomainDataAssembler = new CategoryDomainDataAssembler();
        StandardCategoryJPA standardCategoryJPA = (StandardCategoryJPA) categoryDomainDataAssembler.toData(childCategory);
        CategoryIdJPA result = standardCategoryJPA.getCategoryId();

        assertEquals(expected.getId(), result.getId());
        assertNotNull(standardCategoryJPA);
    }

    @Test
    void ensureDomainToDataRootStandardCategory() {
        String name = "Fruits";
        CategoryName categoryName = new CategoryName(name);
        StandardCategory standardCategory = new StandardCategory(categoryName);
        CategoryId expected = standardCategory.getId();

        CategoryDomainDataAssembler categoryDomainDataAssembler = new CategoryDomainDataAssembler();
        StandardCategoryJPA standardCategoryJPA = (StandardCategoryJPA) categoryDomainDataAssembler.toData(standardCategory);
        CategoryIdJPA result = standardCategoryJPA.getCategoryId();

        assertEquals(expected.getId(), result.getId());
        assertNotNull(standardCategoryJPA);
    }

    @Test
    void ensureDataToDomainCategoryName() {
        //arrange
        int categoryId = new Random().nextInt();
        String childCategoryName = "Fruits";
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(categoryId);
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA(childCategoryName);
        StandardCategoryJPA categoryJPA = new StandardCategoryJPA(categoryIdJPA, categoryNameJPA);

        CategoryName expected = new CategoryName(categoryJPA.getName().getName());
        CategoryName result;

        //act
        CategoryDomainDataAssembler categoryDomainDataAssembler = new CategoryDomainDataAssembler();
        result = categoryDomainDataAssembler.fromDataToDomainCategoryName(categoryJPA);


        //assert
        assertEquals(expected, result);

    }

    @Test
    void ensureDataToDomainOfParentCategoryIdWhenItIsANumber() {
        //arrange
        int categoryId = new Random().nextInt();
        int parentId = new Random().nextInt();
        String childCategoryName = "Fruits";
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(categoryId);
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA(childCategoryName);
        ParentCategoryIdJPA parentCategoryIdJPA = new ParentCategoryIdJPA(parentId);
        StandardCategoryJPA categoryJPA = new StandardCategoryJPA(categoryIdJPA, categoryNameJPA, parentCategoryIdJPA);

        CategoryId expected = new CategoryId(Integer.parseInt(categoryJPA.getParentId().getId()));
        CategoryId result;

        //act
        CategoryDomainDataAssembler categoryDomainDataAssembler = new CategoryDomainDataAssembler();
        result = categoryDomainDataAssembler.fromDataToDomainParentCategoryId(categoryJPA);

        //assert
        assertEquals(expected, result);
    }

    @Test
    void ensureDataToDomainOfParentCategoryIdWhenItIsAString() {
        //arrange
        int categoryId = new Random().nextInt();
        String parentId = "http://vs118.dei.isep.ipp.pt:8080/categories/standard/8a78bb00-bfc7-49b5-9d85-811d5d792706";
        String childCategoryName = "Fruits";
        CategoryIdJPA categoryIdJPA = new CategoryIdJPA(categoryId);
        CategoryNameJPA categoryNameJPA = new CategoryNameJPA(childCategoryName);
        ParentCategoryIdJPA parentCategoryIdJPA = new ParentCategoryIdJPA(parentId);
        StandardCategoryJPA categoryJPA = new StandardCategoryJPA(categoryIdJPA, categoryNameJPA, parentCategoryIdJPA);

        CategoryId expected = new CategoryId(categoryJPA.getParentId().getId());
        CategoryId result;

        //act
        CategoryDomainDataAssembler categoryDomainDataAssembler = new CategoryDomainDataAssembler();
        result = categoryDomainDataAssembler.fromDataToDomainParentCategoryId(categoryJPA);

        //assert
        assertEquals(expected, result);
    }


}