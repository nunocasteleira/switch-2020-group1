package switchtwentytwenty.project.controllers;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import switchtwentytwenty.project.applicationservices.iservices.ICategoryService;
import switchtwentytwenty.project.controllers.icontrollers.*;
import switchtwentytwenty.project.domain.exceptions.DuplicateObjectException;
import switchtwentytwenty.project.domain.exceptions.InvalidNameException;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.dto.category.*;
import switchtwentytwenty.project.repositories.CategoryRepository;

import java.io.IOException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CategoryController implements ICreateStandardCategoryController, ICreateFamilyCategoryController, IGetStandardCategoriesTreeController, IGetFamilyCategoriesTreeController, IGetStandardCategoriesListWithExternalCategoriesController, IGetStandardCategories, IGetFamilyCategories {

    private static final String CATEGORIES = "categories";

    @Autowired
    private ICategoryService categoryService;

    /**
     * Method to create a new Standard Category.
     *
     * @param categoryInputDTO a Data Transfer Object containing all relevant category data.
     * @return the id of the category created.
     */
    @Override
    @PostMapping("/categories")
    public ResponseEntity<Object> createStandardCategory(@NonNull @RequestBody CategoryInputDTO categoryInputDTO) {
        try {
            StandardCategoryOutputDTO categoryOutputDTO = categoryService.createStandardCategory(categoryInputDTO);
            addLinksToDTO(categoryOutputDTO);
            return new ResponseEntity<>(categoryOutputDTO, HttpStatus.CREATED);
        } catch (NumberFormatException | ObjectDoesNotExistException | DuplicateObjectException | InvalidNameException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InvalidDataAccessApiUsageException exception) {
            return new ResponseEntity<>(CategoryRepository.DUPLICATE_CATEGORY, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Method to create a new Family Category.
     *
     * @param familyCategoryInputDTO a Data Transfer Object containing all relevant category data.
     * @return the id of the category created.
     */
    @Override
    @PostMapping("/categories/{familyId}")
    public ResponseEntity<Object> createFamilyCategory(@NonNull @RequestBody CategoryInputDTO familyCategoryInputDTO, @PathVariable("familyId") long familyId) {
        try {
            FamilyCategoryOutputDTO familyCategoryOutputDTO = categoryService.createFamilyCategory(familyCategoryInputDTO, familyId);
            Link selfLink = linkTo(CategoryController.class).slash(CATEGORIES).withSelfRel();
            familyCategoryOutputDTO.add(selfLink);
            return new ResponseEntity<>(familyCategoryOutputDTO, HttpStatus.CREATED);
        } catch (NumberFormatException | ObjectDoesNotExistException | DuplicateObjectException | InvalidNameException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InvalidDataAccessApiUsageException exception) {
            return new ResponseEntity<>(CategoryRepository.DUPLICATE_CATEGORY, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Method to add hateoas links to an instance of the CategoryOutputDTO.
     *
     * @param categoryOutputDTO instance of CategoryOutputDTO
     */

    private void addLinksToDTO(StandardCategoryOutputDTO categoryOutputDTO) {
        Link selfLink = linkTo(CategoryController.class).slash(CATEGORIES).withSelfRel();
        categoryOutputDTO.add(selfLink);
    }

    @Override
    @GetMapping("/categories/standard/tree")
    public ResponseEntity<Object> getStandardCategoriesTree() {
        CategoriesTreeDTO response = categoryService.getStandardCategoriesTree();

        Link selfLink = linkTo(CategoryController.class).slash(CATEGORIES).withSelfRel();
        response.add(selfLink);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @GetMapping("/categories/{familyId}")
    public ResponseEntity<Object> getFamilyCategoriesTree(@PathVariable("familyId") long familyId) {
        try {
            CategoriesTreeDTO response = categoryService.getFamilyCategoryTree(familyId);
            Link selfLink = linkTo(methodOn(CategoryController.class).getFamilyCategoriesTree(familyId)).withRel(String.valueOf(familyId));
            response.add(selfLink);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ObjectDoesNotExistException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This method allows to obtain the list of standard categories with external categories to
     * include those loaded from a complementary system included defined by configuration.
     *
     * @return DTO with the list of categories (internal and external).
     */
    @Override
    @GetMapping("/categories/standard/all")
    public ResponseEntity<Object> getStandardCategoriesListWithExternalCategories() {
        try {
            MergedStandardCategoriesListDTO mergedStandardCategoriesListDTO = categoryService.getMergedStandardCategoriesList();
            Link selfLink = linkTo(methodOn(CategoryController.class).getStandardCategoriesListWithExternalCategories()).withSelfRel();
            mergedStandardCategoriesListDTO.add(selfLink);
            return new ResponseEntity<>(mergedStandardCategoriesListDTO, HttpStatus.OK);
        } catch (IOException | JSONException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This method obtains the list of internal standard categories.
     *
     * @return lists of internal Standard Categories and links in a DTO.
     */
    @Override
    @GetMapping("/categories/standard/list")
    public ResponseEntity<Object> getStandardCategories() {
        StandardCategoriesDTO standardCategoriesDTO = categoryService.getStandardCategories();
        Link selfLink = linkTo(methodOn(CategoryController.class).getStandardCategories()).withSelfRel();
        standardCategoriesDTO.add(selfLink);
        return new ResponseEntity<>(standardCategoriesDTO, HttpStatus.OK);
    }

    /**
     * This method obtains the list of internal standard categories.
     *
     * @return lists of internal Standard Categories and links in a DTO.
     */
    @Override
    @GetMapping("/categories/list/{familyId}")
    public ResponseEntity<Object> getStandardAndFamilyCategories(@PathVariable("familyId") long familyId) {

            StandardCategoriesDTO standardCategoriesDTO = categoryService.getMergedStandardFamilyCategoriesList(familyId);
            Link selfLink = linkTo(methodOn(CategoryController.class).getStandardCategories()).withSelfRel();
            standardCategoriesDTO.add(selfLink);
            return new ResponseEntity<>(standardCategoriesDTO, HttpStatus.OK);

    }
}

