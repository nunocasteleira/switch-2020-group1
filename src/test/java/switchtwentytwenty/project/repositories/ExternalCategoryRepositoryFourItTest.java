package switchtwentytwenty.project.repositories;

import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ExternalCategoryRepositoryFourItTest {

   /*@Test
    void getExternalStandardCategories_fromGroupFour() throws JSONException, IOException {
       CategoryId healthCategoryId = new CategoryId("http://vs260.dei.isep.ipp.pt:8080/api/categories/0490a571-824a-4e41-b9f3-b195de67abc6");
       CategoryName healthCategoryName = new CategoryName("HEALTH");
       StandardCategory health = new StandardCategory(healthCategoryId,healthCategoryName);
       CategoryId televisionCategoryId = new CategoryId("http://vs260.dei.isep.ipp.pt:8080/api/categories/aa967e58-4332-4e60-a042-a3b971c85636");
       CategoryName televisionCategoryName = new CategoryName("TELEVISION");
       StandardCategory television = new StandardCategory(televisionCategoryId,televisionCategoryName);
       CategoryId gymCategoryId = new CategoryId("http://vs260.dei.isep.ipp.pt:8080/api/categories/d98c85f7-9489-4e21-abb0-b9cdd88aabb9");
       CategoryName gymCategoryName = new CategoryName("GYM");
       CategoryId gymParentId = new CategoryId("http://vs260.dei.isep.ipp.pt:8080/api/categories/0490a571-824a-4e41-b9f3-b195de67abc6");
       StandardCategory gym = new StandardCategory(gymCategoryId,gymCategoryName, gymParentId);
       CategoryId gamesCategoryId = new CategoryId("http://vs260.dei.isep.ipp.pt:8080/api/categories/1");
       CategoryName gamesCategoryName = new CategoryName("GAMES");
       StandardCategory games = new StandardCategory(gamesCategoryId,gamesCategoryName);
       CategoryId earCategoryId = new CategoryId("http://vs260.dei.isep.ipp.pt:8080/api/categories/3fa09af4-bfdf-481d-90d1-feafb3daf0ed");
       CategoryName earCategoryName = new CategoryName("EAR PLUGS");
       StandardCategory ear = new StandardCategory(earCategoryId, earCategoryName);
       List<StandardCategory> expectedStandardCategories = new ArrayList<>();
       expectedStandardCategories.add(health);
       expectedStandardCategories.add(television);
       expectedStandardCategories.add(gym);
       expectedStandardCategories.add(games);
       expectedStandardCategories.add(ear);

        ExternalCategoryRepositoryFour repo = new ExternalCategoryRepositoryFour();

        List<StandardCategory> result = repo.getExternalStandardCategories();

        assertNotNull(result);
        assertEquals(expectedStandardCategories, result);
    }*/

    @Test
    void getExternalStandardCategories_GroupFour_NoConnection() {
        ExternalCategoryRepositoryFour repo = new ExternalCategoryRepositoryFour();

        assertThrows(UnknownHostException.class,
                () -> repo.getExternalStandardCategories());
    }
}