package switchtwentytwenty.project.applicationservices.implservices;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import switchtwentytwenty.project.applicationservices.iservices.ICategoryService;
import switchtwentytwenty.project.assemblers.CategoryAssembler;
import switchtwentytwenty.project.domain.exceptions.ObjectDoesNotExistException;
import switchtwentytwenty.project.domain.model.category.BaseCategory;
import switchtwentytwenty.project.domain.model.category.FamilyCategory;
import switchtwentytwenty.project.domain.model.category.StandardCategory;
import switchtwentytwenty.project.domain.model.shared.CategoryId;
import switchtwentytwenty.project.domain.model.shared.CategoryName;
import switchtwentytwenty.project.domain.model.shared.FamilyId;
import switchtwentytwenty.project.dto.category.*;
import switchtwentytwenty.project.repositories.CategoryRepository;
import switchtwentytwenty.project.repositories.FamilyRepository;
import switchtwentytwenty.project.repositories.irepositories.IExternalCategoryRepository;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    public static final String FAMILY_DOESNT_EXIST = "The requested family does not exist.";
    IExternalCategoryRepository iExternalCategoryRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private FamilyRepository familyRepository;
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryAssembler categoryAssembler;

    public CategoryService() {
        ApplicationContext context = new ClassPathXmlApplicationContext("ExternalCategoryBeans.xml");
        iExternalCategoryRepository = (IExternalCategoryRepository) context.getBean("external");
    }

    /**
     * Method to create a standard category.
     *
     * @param categoryInputDTO a Data Transfer Object containing all relevant category data.
     * @return an instance of a DTO containing the relevant information from the standard category.
     */
    @Override
    public StandardCategoryOutputDTO createStandardCategory(CategoryInputDTO categoryInputDTO) {
        StandardCategoryOutputDTO result;

        CategoryName categoryName = categoryAssembler.fromDTOToCategoryName(categoryInputDTO);

        if (categoryInputDTO.getParentId() == null) {
            result = createStandardCategory(categoryName);
        } else {
            CategoryId parentCategoryId = categoryAssembler.fromDTOToCategoryParent(categoryInputDTO);
            result = createStandardCategory(categoryName, parentCategoryId);
        }

        return result;
    }

    /**
     * Method to create a family category.
     *
     * @param familyCategoryInputDTO a Data Transfer Object containing all relevant family category
     *                               data.
     * @param familyId               the family id to which the category will belong.
     * @return a DTO containing the relevant information from the family category.
     */
    @Override
    public FamilyCategoryOutputDTO createFamilyCategory(CategoryInputDTO familyCategoryInputDTO, long familyId) {
        FamilyCategoryOutputDTO result;

        FamilyId familyIdVO = new FamilyId(familyId);
        validateFamily(familyIdVO);

        CategoryName categoryName = categoryAssembler.fromDTOToCategoryName(familyCategoryInputDTO);

        if (familyCategoryInputDTO.getParentId() == null) {
            result = createFamilyCategory(categoryName, familyIdVO);
        } else {
            CategoryId parentCategoryId = categoryAssembler.fromDTOToCategoryParent(familyCategoryInputDTO);
            result = createFamilyCategory(categoryName, parentCategoryId, familyIdVO);
        }
        return result;
    }

    /**
     * Method to validate if the family exists or not. If the family does not exist an error will be
     * thrown.
     *
     * @param familyId the value object of the family id.
     */
    private void validateFamily(FamilyId familyId) {
        if (!familyRepository.existsById(familyId)) {
            throw new ObjectDoesNotExistException(FAMILY_DOESNT_EXIST);
        }
    }

    /**
     * Method to create a root standard category. A category without a category parent.
     *
     * @param categoryName categoryName of the standard category
     */
    private StandardCategoryOutputDTO createStandardCategory(CategoryName categoryName) {
        StandardCategory aStandardCategory = new StandardCategory(categoryName);
        StandardCategory standardCategoryFromJPA = (StandardCategory) categoryRepository.saveCategory(aStandardCategory);
        return categoryMapper.standardCategoryToDTO(standardCategoryFromJPA);
    }

    /**
     * Method to create a standard child category. A category with a category parent.
     *
     * @param categoryName categoryName of the standard child category
     * @param parentId     the id of the parent standard category
     * @return the category id
     */
    private StandardCategoryOutputDTO createStandardCategory(CategoryName categoryName, CategoryId parentId) {
        categoryRepository.validateCategoryParent(parentId);
        StandardCategory aStandardCategory = new StandardCategory(categoryName, parentId);
        StandardCategory standardCategoryFromJPA = (StandardCategory) categoryRepository.saveCategory(aStandardCategory);
        return categoryMapper.standardCategoryToDTO(standardCategoryFromJPA);
    }

    /**
     * Method to create a family root category. A family category with a category parent.
     *
     * @param categoryName the name of the family category.
     * @param familyId     the family id to which the category is going to belong.
     * @return a DTO containing the relevant information from the family category.
     */
    private FamilyCategoryOutputDTO createFamilyCategory(CategoryName categoryName, FamilyId familyId) {
        FamilyCategory aFamilyCategory = new FamilyCategory(categoryName, familyId);
        FamilyCategory familyCategoryFromJPA = (FamilyCategory) categoryRepository.saveCategory(aFamilyCategory);
        return categoryMapper.familyCategoryToDTO(familyCategoryFromJPA);
    }

    /**
     * Method to create a child family root category. A family category with a category parent.
     *
     * @param categoryName the name of the family category.
     * @param familyId     the family id to which the category is going to belong.
     * @return a DTO containing the relevant information from the family category.
     */
    private FamilyCategoryOutputDTO createFamilyCategory(CategoryName categoryName, CategoryId parentCategoryId, FamilyId familyId) {
        FamilyCategory aFamilyCategory = new FamilyCategory(categoryName, parentCategoryId, familyId);
        FamilyCategory familyCategoryFromJPA = (FamilyCategory) categoryRepository.saveCategory(aFamilyCategory);
        return categoryMapper.familyCategoryToDTO(familyCategoryFromJPA);
    }

    public CategoriesTreeDTO getStandardCategoriesTree() {
        List<StandardCategory> standardCategories = categoryRepository.getStandardCategories();
        StandardCategoriesTreeMapper standardCategoriesTreeMapper = new StandardCategoriesTreeMapper();

        return standardCategoriesTreeMapper.getStandardCategoriesTreeDTO(standardCategories);
    }

    @Override
    public CategoriesTreeDTO getFamilyCategoryTree(long familyId) {
        FamilyId familyId1 = new FamilyId(familyId);
        List<StandardCategory> standardCategories = categoryRepository.getStandardCategories();
        List<FamilyCategory> familyCategories = categoryRepository.getFamilyCategories(familyId1);
        List<BaseCategory> unorderedFamilyCategories = unorderedFamilyCategoryList(standardCategories, familyCategories);

        FamilyCategoryTreeMapper mapper = new FamilyCategoryTreeMapper();

        return mapper.getFamilyCategoryTreeDTO(unorderedFamilyCategories);
    }

    private List<BaseCategory> unorderedFamilyCategoryList(List<StandardCategory> standardCategories, List<FamilyCategory> familyCategories) {
        List<BaseCategory> allCategories = new ArrayList<>();

        allCategories.addAll(standardCategories);
        allCategories.addAll(familyCategories);

        return allCategories;
    }

    /**
     * Method to get the Standard categories lists (internal and external) and merge them into a
     * DTO.
     *
     * @return mergedStandardCategoriesList DTO object
     */
    @Override
    public MergedStandardCategoriesListDTO getMergedStandardCategoriesList() throws IOException, JSONException {
        List<StandardCategory> standardCategories = categoryRepository.getStandardCategories();
        List<StandardCategory> externalStandardCategories;
        try {
            externalStandardCategories = iExternalCategoryRepository.getExternalStandardCategories();
        } catch (UnknownHostException exception) {
            externalStandardCategories = new ArrayList<>();
        }

        MergeCategoriesListMapper categoriesListMapper = new MergeCategoriesListMapper();

        return categoriesListMapper.mapMergedStandardCategoriesList(standardCategories, externalStandardCategories);
    }

    /**
     * Method to get the internal Standard categories in a list inside a DTO.
     *
     * @return StandardCategoriesDTO object that contains a list of DTOs with the Standard
     *         Categories.
     */
    @Override
    public StandardCategoriesDTO getStandardCategories() {
        List<StandardCategory> standardCategories = categoryRepository.getStandardCategories();

        List<CategoryDTO> categoryDTOs = new ArrayList<>();

        StandardCategoriesTreeMapper standardCategoriesTreeMapper = new StandardCategoriesTreeMapper();

        StandardCategoriesMapper standardCategoriesMapper = new StandardCategoriesMapper();

        for (StandardCategory standardCategory : standardCategories) {
            CategoryDTO categoryDTO = standardCategoriesTreeMapper.toDTO(standardCategory);
            categoryDTOs.add(categoryDTO);
        }

        return standardCategoriesMapper.mapStandardCategories(categoryDTOs);
    }


    /**
     * Method to get the categories lists with the standard and family categories and merge them into a
     * DTO.
     *
     * @return mergedStandardCategoriesList DTO object
     */
    public StandardCategoriesDTO getMergedStandardFamilyCategoriesList(long familyId)  {
        FamilyId familyIdVO= new FamilyId(familyId);
        List<StandardCategory> standardCategories = categoryRepository.getStandardCategories();
        List<FamilyCategory> familyCategories ;
        try{
            familyCategories= categoryRepository.getFamilyCategories(familyIdVO);
        }catch(ObjectDoesNotExistException exception){
            familyCategories = new ArrayList<>();
        }

        MergeStandardFamilyCategoriesListMapper categoriesListMapper = new MergeStandardFamilyCategoriesListMapper();

        return categoriesListMapper.mapMergedStandardFamilyCategoriesList(standardCategories, familyCategories);
    }
}