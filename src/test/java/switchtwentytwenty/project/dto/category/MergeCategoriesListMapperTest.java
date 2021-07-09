package switchtwentytwenty.project.dto.category;

import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.model.category.StandardCategory;
import switchtwentytwenty.project.domain.model.shared.CategoryName;
import switchtwentytwenty.project.dto.category.CategoryDTO;
import switchtwentytwenty.project.dto.category.MergeCategoriesListMapper;
import switchtwentytwenty.project.dto.category.MergedStandardCategoriesListDTO;
import switchtwentytwenty.project.dto.category.StandardCategoriesTreeMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MergeCategoriesListMapperTest {

    MergeCategoriesListMapper mergeCategoriesListMapper = new MergeCategoriesListMapper();

    StandardCategoriesTreeMapper standardCategoriesTreeMapper = new StandardCategoriesTreeMapper();

    @Test
    void mergeCategoriesListMapper_Successfully() {
        //arrange
        //create standard category and add to list
        List<StandardCategory> standardCategories = new ArrayList<>();
        StandardCategory groceries = new StandardCategory(new CategoryName("groceries"));
        standardCategories.add(groceries);

        //create external standard category and add to list
        List<StandardCategory> externalStandardCategories = new ArrayList<>();
        StandardCategory fruits = new StandardCategory(new CategoryName("fruits"));
        externalStandardCategories.add(fruits);

        //create merged list with internal and external categories
        List<StandardCategory> mergedCategories = new ArrayList<>();
        mergedCategories.addAll(standardCategories);
        mergedCategories.addAll(externalStandardCategories);

        //convert StandardCategory into categoryDTO and add to list
        List<CategoryDTO> mergedCategoriesDTO = new ArrayList<>();
        CategoryDTO groceriesDTO = standardCategoriesTreeMapper.toDTO(groceries);
        CategoryDTO fruitDTO = standardCategoriesTreeMapper.toDTO(fruits);
        mergedCategoriesDTO.add(groceriesDTO);
        mergedCategoriesDTO.add(fruitDTO);

        //create DTO with the list of DTOs
        MergedStandardCategoriesListDTO expected = new MergedStandardCategoriesListDTO(mergedCategoriesDTO);

        //act
        MergedStandardCategoriesListDTO result = mergeCategoriesListMapper.mapMergedStandardCategoriesList(standardCategories, externalStandardCategories);

        //assert
        assertNotNull(result);
        assertEquals(expected, result);
    }
}