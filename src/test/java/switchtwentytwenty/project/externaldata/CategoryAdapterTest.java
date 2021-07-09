package switchtwentytwenty.project.externaldata;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.dto.category.ExternalCategoryDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CategoryAdapterTest {

    ///// GROUP 2:
    @Test
    void convertHttpResponseToPrimitivesSuccessfullyGroupTwo_OneCategory() throws JSONException {
        String response = "[{designation: Groceries, id: 500}]";
        String URI = "http://anURI.pt/categories";
        String expectedDesignation = "Groceries";
        String expectedId = "http://anURI.pt/categories/500";
        List<ExternalCategoryDTO> expectedPrimitivesList = new ArrayList<>();
        ExternalCategoryDTO categoryDTO = new ExternalCategoryDTO(expectedId, expectedDesignation, "null");
        expectedPrimitivesList.add(categoryDTO);

        List<ExternalCategoryDTO> result = CategoryAdapter.fromHttpToPrimitivesGroupTwo(response, URI);

        assertNotNull(result);
        assertEquals(expectedPrimitivesList, result);
    }

    @Test
    void convertHttpResponseToPrimitivesSuccessfullyGroupTwo_ListOfCategories() throws JSONException {
        String response = "[{designation: Car, id: 500}, {designation: Gas, id: 600, parentID: 500} ]";
        String URI = "http://anURI.pt/categories";
        String expectedDesignation1 = "Car";
        String expectedId1 = "http://anURI.pt/categories/500";
        String expectedDesignation2 = "Gas";
        String expectedId2 = "http://anURI.pt/categories/600";
        String expectedParentId2 = "http://anURI.pt/categories/500";
        List<ExternalCategoryDTO> expectedPrimitivesList = new ArrayList<>();
        ExternalCategoryDTO categoryDTO1 = new ExternalCategoryDTO(expectedId1, expectedDesignation1, "null");
        ExternalCategoryDTO categoryDTO2 = new ExternalCategoryDTO(expectedId2, expectedDesignation2, expectedParentId2);
        expectedPrimitivesList.add(categoryDTO1);
        expectedPrimitivesList.add(categoryDTO2);

        List<ExternalCategoryDTO> result = CategoryAdapter.fromHttpToPrimitivesGroupTwo(response, URI);

        assertNotNull(result);
        assertEquals(expectedPrimitivesList, result);
    }
    ///// GROUP 3:
    @Test
    void convertHttpResponseToPrimitivesSuccessfullyGroupThree_OneCategory() throws JSONException {
        String response = "{outputCategoryDTOList:[{categoryName: Hobbies, categoryID: 500}]}";
        String URI = "http://anURI.pt/categories";
        String expectedCategoryName = "Hobbies";
        String expectedCategoryID = "http://anURI.pt/categories/500";
        List<ExternalCategoryDTO> expectedPrimitivesList = new ArrayList<>();
        ExternalCategoryDTO categoryDTO = new ExternalCategoryDTO(expectedCategoryID, expectedCategoryName, "null");
        expectedPrimitivesList.add(categoryDTO);

        List<ExternalCategoryDTO> result = CategoryAdapter.fromHttpToPrimitivesGroupThree(response, URI);

        assertNotNull(result);
        assertEquals(expectedPrimitivesList, result);
    }

    @Test
    void convertHttpResponseToPrimitivesSuccessfullyGroupThree_ListOfCategories() throws JSONException {
        String response = "{outputCategoryDTOList:[{categoryName: Hobbies, categoryID: 500}, {categoryName: Travel, categoryID: 600, parentID: 500}]}";
        String URI = "http://anURI.pt/categories";
        String expectedCategoryName1 = "Hobbies";
        String expectedCategoryID1 = "http://anURI.pt/categories/500";
        String expectedCategoryName2 = "Travel";
        String expectedCategoryID2 = "http://anURI.pt/categories/600";
        String expectedParentId2 = "http://anURI.pt/categories/500";
        List<ExternalCategoryDTO> expectedPrimitivesList = new ArrayList<>();
        ExternalCategoryDTO categoryDTO1 = new ExternalCategoryDTO(expectedCategoryID1, expectedCategoryName1, "null");
        ExternalCategoryDTO categoryDTO2 = new ExternalCategoryDTO(expectedCategoryID2, expectedCategoryName2, expectedParentId2);
        expectedPrimitivesList.add(categoryDTO1);
        expectedPrimitivesList.add(categoryDTO2);

        List<ExternalCategoryDTO> result = CategoryAdapter.fromHttpToPrimitivesGroupThree(response, URI);

        assertNotNull(result);
        assertEquals(expectedPrimitivesList, result);
    }

    ///// GROUP 4:
    @Test
    void convertHttpResponseToPrimitivesSuccessfullyGroupFour_OneCategory() throws JSONException {
        String response = "[{name: Gym, id: 500}]";
        String URI = "http://anURI.pt/categories";
        String expectedName = "Gym";
        String expectedId = "http://anURI.pt/categories/500";
        List<ExternalCategoryDTO> expectedPrimitivesList = new ArrayList<>();
        ExternalCategoryDTO categoryDTO = new ExternalCategoryDTO(expectedId, expectedName, "null");
        expectedPrimitivesList.add(categoryDTO);

        CategoryAdapter adapter = new CategoryAdapter();
        List<ExternalCategoryDTO> result = adapter.fromHttpToPrimitivesGroupFour(response, URI);

        assertNotNull(result);
        assertEquals(expectedPrimitivesList, result);
    }

    @Test
    void convertHttpResponseToPrimitivesSuccessfullyGroupFour_ListOfCategories() throws JSONException {
        String response = "[{name: Gym, id: 500}, {name: Yoga, id: 600, parentId: 500} ]";
        String URI = "http://anURI.pt/categories";
        String expectedName1 = "Gym";
        String expectedId1 = "http://anURI.pt/categories/500";
        String expectedName2 = "Yoga";
        String expectedId2 = "http://anURI.pt/categories/600";
        String expectedParentId2 = "http://anURI.pt/categories/500";
        List<ExternalCategoryDTO> expectedPrimitivesList = new ArrayList<>();
        ExternalCategoryDTO categoryDTO1 = new ExternalCategoryDTO(expectedId1, expectedName1, "null");
        ExternalCategoryDTO categoryDTO2 = new ExternalCategoryDTO(expectedId2, expectedName2, expectedParentId2);
        expectedPrimitivesList.add(categoryDTO1);
        expectedPrimitivesList.add(categoryDTO2);

        CategoryAdapter adapter = new CategoryAdapter();
        List<ExternalCategoryDTO> result = adapter.fromHttpToPrimitivesGroupFour(response, URI);

        assertNotNull(result);
        assertEquals(expectedPrimitivesList, result);
    }

    @Test
    void ensureNoArgsConstructorIsWorking(){
        CategoryAdapter adapter = new CategoryAdapter();
        assertNotNull(adapter);
    }
}

