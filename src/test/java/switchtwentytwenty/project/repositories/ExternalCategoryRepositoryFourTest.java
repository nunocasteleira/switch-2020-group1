package switchtwentytwenty.project.repositories;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;
import switchtwentytwenty.project.domain.model.category.StandardCategory;
import switchtwentytwenty.project.domain.model.shared.CategoryId;
import switchtwentytwenty.project.domain.model.shared.CategoryName;
import switchtwentytwenty.project.dto.category.ExternalCategoryDTO;
import switchtwentytwenty.project.externaldata.CategoryAdapter;
import switchtwentytwenty.project.externaldata.assembler.ExternalCategoryAssembler;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
@Profile("ExternalCategoryRepositoryFourTest")
class ExternalCategoryRepositoryFourTest {
    @Mock
    CloseableHttpClient client;
    @Mock
    HttpGet request;
    @Mock
    CloseableHttpResponse httpResponse;
    @Mock
    HttpEntity entity;
    @Mock
    CategoryAdapter adapter;
    @Mock
    ExternalCategoryAssembler assembler;
    @Spy
    List<StandardCategory> standardCategories = new ArrayList<>();
    @Mock
    ExternalCategoryRepositoryFour repositoryFour;

    @Test
    void getExternalStandardCategories_fromGroupFour() throws JSONException, IOException {

        String stringResponse = "[{\"name\": \"Gym\", \"id\": 500}]";
        String expectedName = "Gym";
        String expectedId = "http://anURI.pt/categories/500";
        CategoryId categoryId = new CategoryId(expectedId);
        CategoryName categoryName = new CategoryName(expectedName);
        CategoryId parentId = new CategoryId("null");
        List<ExternalCategoryDTO> expectedPrimitivesList = new ArrayList<>();
        ExternalCategoryDTO categoryDTO = new ExternalCategoryDTO(expectedId, expectedName, "null");
        expectedPrimitivesList.add(categoryDTO);
        StandardCategory standardCategory = new StandardCategory(categoryId, categoryName);
        List<StandardCategory> expectedStandardCategories = new ArrayList<>();

        lenient().when(client.execute(request)).thenReturn(httpResponse);
        lenient().when(httpResponse.getEntity()).thenReturn(entity);
        lenient().when(entity.toString()).thenReturn(stringResponse);
        lenient().doNothing().when(client).close();
        lenient().when(adapter.fromHttpToPrimitivesGroupFour(stringResponse,expectedId)).thenReturn(expectedPrimitivesList);
        lenient().when(assembler.fromPrimitiveToCategoryId(categoryDTO)).thenReturn(categoryId);
        lenient().when(assembler.fromPrimitiveToCategoryName(categoryDTO)).thenReturn(categoryName);
        lenient().when(assembler.fromPrimitiveToParentId(categoryDTO)).thenReturn(parentId);
        lenient().when(standardCategories.add(standardCategory)).thenReturn(true);

        List<StandardCategory> result = repositoryFour.getExternalStandardCategories();

        assertNotNull(result);
        assertEquals(expectedStandardCategories, result);
    }

    @Test
    void getExternalStandardCategories_GroupFour_NoConnection() throws IOException {
        ExternalCategoryRepositoryFour repositoryFour = new ExternalCategoryRepositoryFour();

        lenient().when(client.execute(request)).thenThrow(UnknownHostException.class);

        assertThrows(UnknownHostException.class,
                () -> repositoryFour.getExternalStandardCategories());
    }
}
