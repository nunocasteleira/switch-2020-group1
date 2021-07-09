package switchtwentytwenty.project.repositories;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
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
public class ExternalCategoryRepositoryFour implements IExternalCategoryRepository {
    @Autowired
    ExternalCategoryAssembler assembler;

    /**
     * Method to obtain the list of standard categories imported from an external API from Group
     * Four.
     *
     * @return list of Standard Category objects
     * @throws IOException   thrown to indicate a problem with the I/O operation.
     * @throws JSONException thrown to indicate a problem with the JSON API.
     */
    public List<StandardCategory> getExternalStandardCategories() throws IOException, JSONException {
        String uriGroupFour = "http://vs260.dei.isep.ipp.pt:8080/api/categories";
        String response = sendHttpClientRequestGet(uriGroupFour);
        CategoryAdapter adapter = new CategoryAdapter();
        List<ExternalCategoryDTO> externalCategoryPrimitivesList = adapter.fromHttpToPrimitivesGroupFour(response, uriGroupFour);

        List<StandardCategory> externalStandardCategories = new ArrayList<>();
        StandardCategory standardCategory;
        for (ExternalCategoryDTO externalPrimitivesDTO : externalCategoryPrimitivesList
        ) {
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
     * Method to send a GET Http request to external API from Group Four.
     *
     * @param uri external URI to the Group Four's API
     * @return String with the response (JSON Object)
     * @throws IOException thrown to indicate a problem with the I/O operation
     */
    private String sendHttpClientRequestGet(String uri) throws IOException {
        HttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(uri);
        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity);
    }
}


