package switchtwentytwenty.project.applicationservices.iservices;

import org.json.JSONException;
import switchtwentytwenty.project.dto.category.*;

import java.io.IOException;

public interface ICategoryService {

    StandardCategoryOutputDTO createStandardCategory(CategoryInputDTO inputDTO);

    FamilyCategoryOutputDTO createFamilyCategory(CategoryInputDTO inputDTO, long familyId);

    CategoriesTreeDTO getStandardCategoriesTree();

    CategoriesTreeDTO getFamilyCategoryTree(long familyId);

    MergedStandardCategoriesListDTO getMergedStandardCategoriesList() throws IOException, JSONException;

    StandardCategoriesDTO getStandardCategories();

    StandardCategoriesDTO getMergedStandardFamilyCategoriesList(long familyId);
}
