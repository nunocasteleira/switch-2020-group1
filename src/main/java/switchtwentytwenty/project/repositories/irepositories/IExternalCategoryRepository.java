package switchtwentytwenty.project.repositories.irepositories;

import org.json.JSONException;
import switchtwentytwenty.project.domain.model.category.StandardCategory;

import java.io.IOException;
import java.util.List;

public interface IExternalCategoryRepository {

    List<StandardCategory> getExternalStandardCategories() throws IOException, JSONException;
}
