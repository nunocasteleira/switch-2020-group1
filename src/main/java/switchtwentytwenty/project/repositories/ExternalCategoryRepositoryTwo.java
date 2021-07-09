package switchtwentytwenty.project.repositories;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import switchtwentytwenty.project.domain.model.category.StandardCategory;
import switchtwentytwenty.project.domain.model.shared.CategoryId;
import switchtwentytwenty.project.domain.model.shared.CategoryName;
import switchtwentytwenty.project.dto.category.ExternalCategoryDTO;
import switchtwentytwenty.project.externaldata.CategoryAdapter;
import switchtwentytwenty.project.externaldata.assembler.ExternalCategoryAssembler;
import switchtwentytwenty.project.repositories.irepositories.IExternalCategoryRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ExternalCategoryRepositoryTwo implements IExternalCategoryRepository {
    @Autowired
    ExternalCategoryAssembler assembler;
    private static final String URI_GROUP_TWO = "http://vs118.dei.isep.ipp.pt:8080/categories/standard";

    /**
     * This method allows to obtain the external standard categories provided by group two.
     *
     * @return a list with all external standard categories from group two.
     * @throws IOException   thrown to indicate that improper data has been inputted into the
     *                       program.
     * @throws JSONException thrown to indicate a problem with the JSON API.
     */
    public List<StandardCategory> getExternalStandardCategories() throws IOException, JSONException {
        String response = sendHttpClientRequestGet();
        List<ExternalCategoryDTO> externalCategoryPrimitivesList = CategoryAdapter.fromHttpToPrimitivesGroupTwo(response, URI_GROUP_TWO);

        List<StandardCategory> externalStandardCategories = new ArrayList<>();
        StandardCategory standardCategory;
        for (ExternalCategoryDTO externalPrimitivesDTO : externalCategoryPrimitivesList) {
            CategoryId categoryId = assembler.fromPrimitiveToCategoryId(externalPrimitivesDTO);
            CategoryName categoryName = assembler.fromPrimitiveToCategoryName(externalPrimitivesDTO);
            CategoryId parentId = assembler.fromPrimitiveToParentId(externalPrimitivesDTO);
            if (parentId == null) {
                standardCategory = new StandardCategory(categoryId, categoryName);
            } else {
                standardCategory = new StandardCategory(categoryId, categoryName, parentId);
            }
            externalStandardCategories.add(standardCategory);
        }
        return externalStandardCategories;
    }

    /**
     * This method allows to retrieve data via the CloseableHttpClient class, namely the content of
     * uri after HttpGet request.
     *
     * @return the response body by converting the response entity to a String.
     * @throws IOException thrown to indicate that improper data has been inputted into the
     *                     program.
     */
    private String sendHttpClientRequestGet() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(URI_GROUP_TWO);
        CloseableHttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity);
        client.close();
        return responseString;
    }
}