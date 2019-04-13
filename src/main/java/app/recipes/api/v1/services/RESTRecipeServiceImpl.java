package app.recipes.api.v1.services;

import app.recipes.api.v1.mapper.RecipeMapper;
import app.recipes.api.v1.models.RecipeDTO;
import app.recipes.repositorys.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RESTRecipeServiceImpl implements RESTRecipeService
{
    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;

    public RESTRecipeServiceImpl(RecipeRepository recipeRepository, RecipeMapper recipeMapper)
    {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
    }

    @Override
    public List<RecipeDTO> getAllRecipes()
    {
        return StreamSupport.stream(recipeRepository.findAll().spliterator(), false)
                .map(recipeMapper::recipeToRecipeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RecipeDTO findById(Long id)
    {
        return recipeMapper.recipeToRecipeDTO(recipeRepository.findById(id).orElse(null));
    }

    @Override
    public List<RecipeDTO> findAllRecipesByCategory(String category)
    {
        return recipeRepository.findRecipesByCategories(category)
                .stream()
                .map(recipeMapper::recipeToRecipeDTO)
                .collect(Collectors.toList());
    }
}
