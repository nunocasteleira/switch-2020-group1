package switchtwentytwenty.project.repositories;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import switchtwentytwenty.project.domain.model.category.StandardCategory;
import switchtwentytwenty.project.domain.model.shared.CategoryId;
import switchtwentytwenty.project.domain.model.shared.CategoryName;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExternalCategoryRepositoryTwoItTest {

/*    @Test
    void getExternalStandardCategories_Successfully() throws JSONException, IOException {
        //arrange
        List<StandardCategory> expected = new ArrayList<>();

        //create groceries standard category
        CategoryId categoryIdGroceries = new CategoryId("http://vs118.dei.isep.ipp.pt:8080/categories/standard/be482698-17e9-4138-bd6d-58a171dfa9ae");
        CategoryName categoryNameGroceries = new CategoryName("Groceries");
        StandardCategory groceries = new StandardCategory(categoryIdGroceries, categoryNameGroceries);

        //create vegetables standard category
        CategoryId categoryIdVegetables = new CategoryId("http://vs118.dei.isep.ipp.pt:8080/categories/standard/473600d1-0197-4081-bc2b-9d6faa60d9ba");
        CategoryName categoryNameVegetables = new CategoryName("Vegetables");
        CategoryId parentIdVegetables = new CategoryId("http://vs118.dei.isep.ipp.pt:8080/categories/standard/be482698-17e9-4138-bd6d-58a171dfa9ae");
        StandardCategory vegetables = new StandardCategory(categoryIdVegetables, categoryNameVegetables, parentIdVegetables);

        //create fruits standard category
        CategoryId categoryIdFruits = new CategoryId("http://vs118.dei.isep.ipp.pt:8080/categories/standard/96cf5ca2-d6c6-47ec-9b2f-25d6a2e99a74");
        CategoryName categoryNameFruits = new CategoryName("Fruits");
        CategoryId parentIdFruits = new CategoryId("http://vs118.dei.isep.ipp.pt:8080/categories/standard/be482698-17e9-4138-bd6d-58a171dfa9ae");
        StandardCategory fruits = new StandardCategory(categoryIdFruits, categoryNameFruits, parentIdFruits);

        //create sausages standard category
        CategoryId categoryIdSausages = new CategoryId("http://vs118.dei.isep.ipp.pt:8080/categories/standard/54979dec-fff4-4ad8-82a0-504a62f0839c");
        CategoryName categoryNameSausages = new CategoryName("Sausages");
        CategoryId parentIdSausages = new CategoryId("http://vs118.dei.isep.ipp.pt:8080/categories/standard/be482698-17e9-4138-bd6d-58a171dfa9ae");
        StandardCategory sausages = new StandardCategory(categoryIdSausages, categoryNameSausages, parentIdSausages);

        //create transportation standard category
        CategoryId categoryIdTransportation = new CategoryId("http://vs118.dei.isep.ipp.pt:8080/categories/standard/a9949b93-2835-4f2b-beda-7621265a1f87");
        CategoryName categoryNameTransportation = new CategoryName("Transportation");
        StandardCategory transportation = new StandardCategory(categoryIdTransportation, categoryNameTransportation);

        //create bus standard category
        CategoryId categoryIdBus = new CategoryId("http://vs118.dei.isep.ipp.pt:8080/categories/standard/6298f3a3-0ad8-4c8b-8ba2-0e7467839106");
        CategoryName categoryNameBus = new CategoryName("Bus");
        CategoryId parentIdBus = new CategoryId("http://vs118.dei.isep.ipp.pt:8080/categories/standard/a9949b93-2835-4f2b-beda-7621265a1f87");
        StandardCategory bus = new StandardCategory(categoryIdBus, categoryNameBus, parentIdBus);

        //create train standard category
        CategoryId categoryIdTrain = new CategoryId("http://vs118.dei.isep.ipp.pt:8080/categories/standard/91315171-f2f1-4353-b18b-ec2469ac68a7");
        CategoryName categoryNameTrain = new CategoryName("Train");
        CategoryId parentIdTrain = new CategoryId("http://vs118.dei.isep.ipp.pt:8080/categories/standard/a9949b93-2835-4f2b-beda-7621265a1f87");
        StandardCategory train = new StandardCategory(categoryIdTrain, categoryNameTrain, parentIdTrain);

        //create education standard category
        CategoryId categoryIdEducation = new CategoryId("http://vs118.dei.isep.ipp.pt:8080/categories/standard/8f780070-9f8e-4443-b424-fa96185ee2b5");
        CategoryName categoryNameEducation = new CategoryName("Education");
        StandardCategory education = new StandardCategory(categoryIdEducation, categoryNameEducation);

        //create coffe to work late standard category
        CategoryId categoryIdCoffeToWorkLate = new CategoryId("http://vs118.dei.isep.ipp.pt:8080/categories/standard/0a5f5d69-935f-45bb-86cc-f1afc99fd2a3");
        CategoryName categoryNameCoffeToWorkLate = new CategoryName("Coffe To Work Late");
        StandardCategory coffeToWorkLate = new StandardCategory(categoryIdCoffeToWorkLate, categoryNameCoffeToWorkLate);

        //add categories to the list
        expected.add(groceries);
        expected.add(vegetables);
        expected.add(fruits);
        expected.add(sausages);
        expected.add(transportation);
        expected.add(bus);
        expected.add(train);
        expected.add(education);
        expected.add(coffeToWorkLate);

        //act
        ExternalCategoryRepositoryTwo repository = new ExternalCategoryRepositoryTwo();
        List<StandardCategory> result = repository.getExternalStandardCategories();

        //assert
        assertNotNull(result);
        assertEquals(expected, result);
    }*/

    @Test
    void getExternalStandardCategories_Invalid_ThrowException() {
        ExternalCategoryRepositoryTwo repository = new ExternalCategoryRepositoryTwo();

        assertThrows(UnknownHostException.class, () -> repository.getExternalStandardCategories());
    }
}