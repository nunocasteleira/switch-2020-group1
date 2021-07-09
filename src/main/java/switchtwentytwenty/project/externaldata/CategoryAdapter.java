package switchtwentytwenty.project.externaldata;

import lombok.NoArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import switchtwentytwenty.project.dto.category.ExternalCategoryDTO;

import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
public class CategoryAdapter {

    /**
     * This method allows to convert the response and URI provided by group two to a list of ExternalCategoryDTO.
     * @param response the response entity in String.
     * @param link http://vs118.dei.isep.ipp.pt:8080/categories/standard.
     * @return a list of ExternalCategoryDTO.
     * @throws JSONException thrown to indicate a problem with the JSON API.
     */
    public static List<ExternalCategoryDTO> fromHttpToPrimitivesGroupTwo(String response, String link) throws JSONException {
        List<ExternalCategoryDTO> primitivesList = new ArrayList<>();

        JSONArray array = new JSONArray(response);
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            String name = object.getString("designation");
            String categoryId = object.getString("id");
            String parentId = object.optString("parentID", "null");
            ExternalCategoryDTO dto = ExternalCategoryDTO.toExternalCategoryDTO(categoryId, name, parentId, link);
            primitivesList.add(dto);
        }
        return primitivesList;
    }

    /**
     * This method allows to convert the response and URI provided by group three to a list of ExternalCategoryDTO.
     * @param response the response entity in String.
     * @param link http://vs116.dei.isep.ipp.pt:8080/categories.
     * @return a list of ExternalCategoryDTO.
     * @throws JSONException thrown to indicate a problem with the JSON API.
     */
    public static List<ExternalCategoryDTO> fromHttpToPrimitivesGroupThree(String response, String link) throws JSONException {
        List<ExternalCategoryDTO> primitivesList = new ArrayList<>();

        JSONObject json = new JSONObject(response);
        String jsonString = json.getString("outputCategoryDTOList");
        JSONArray array = new JSONArray(jsonString);
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            String name = object.getString("categoryName");
            String categoryId = object.getString("categoryID");
            String parentId = object.optString("parentID", "null");
            ExternalCategoryDTO dto = ExternalCategoryDTO.toExternalCategoryDTO(categoryId, name, parentId, link);
            primitivesList.add(dto);
        }
        return primitivesList;
    }

    /**
     * This method converts the response and URI provided by group four to a list of ExternalCategoryDTO.
     * @param response String response from Group 4 containing information about external categories
     * @param link external URI to the Group Four's API
     * @return a list of ExternalCategoryDTO
     * @throws JSONException thrown to indicate a problem with the JSON API.
     */
    public List<ExternalCategoryDTO> fromHttpToPrimitivesGroupFour(String response, String link) throws JSONException {
        List<ExternalCategoryDTO> primitivesList = new ArrayList<>();

        JSONArray array = new JSONArray(response);
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            String name = object.getString("name");
            String categoryId = object.getString("id");
            String parentId = object.optString("parentId", "null");
            ExternalCategoryDTO dto = ExternalCategoryDTO.toExternalCategoryDTO(categoryId, name, parentId, link);
            primitivesList.add(dto);
        }
        return primitivesList;
    }
}