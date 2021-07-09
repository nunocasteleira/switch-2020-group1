package switchtwentytwenty.project.dto.category;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.model.category.StandardCategory;
import switchtwentytwenty.project.domain.model.shared.CategoryId;
import switchtwentytwenty.project.domain.model.shared.CategoryName;
import switchtwentytwenty.project.dto.category.CategoryMapper;
import switchtwentytwenty.project.dto.category.StandardCategoryOutputDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CategoryMapperTest {

    @Test
    void ensureStandardCategoryToDTOSuccessfully() {
        //arrange
        String name = "Groceries";
        CategoryName categoryName = new CategoryName(name);
        StandardCategory standardCategory = new StandardCategory(categoryName);
        String expected = name.toUpperCase();
        StandardCategoryOutputDTO result;

        //act
        CategoryMapper categoryMapper = new CategoryMapper();
        result = categoryMapper.standardCategoryToDTO(standardCategory);

        //assert
        assertNotNull(result);
        assertEquals(expected, result.getCategoryName());
    }

    @Test
    void ensureChildStandardCategoryToDTOSuccessfully() {
        //arrange

        //Root Category
        String name = "Groceries";
        CategoryName categoryName = new CategoryName(name);
        StandardCategory standardCategory = new StandardCategory(categoryName);

        //Child Category
        String childName = "Fruits";
        CategoryName childCategoryName = new CategoryName(childName);
        CategoryId parentCategoryId =standardCategory.getId();
        StandardCategory standardChildCategory = new StandardCategory(childCategoryName,parentCategoryId);

        String expected = childName.toUpperCase();
        StandardCategoryOutputDTO result;

        //act
        CategoryMapper categoryMapper = new CategoryMapper();
        result = categoryMapper.standardCategoryToDTO(standardChildCategory);

        //assert
        assertNotNull(result);
        assertEquals(expected, result.getCategoryName());
        assertEquals(parentCategoryId.getId(), result.getParentCategoryId());
    }
}