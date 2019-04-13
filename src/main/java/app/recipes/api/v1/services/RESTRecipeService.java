package app.recipes.api.v1.services;

import app.recipes.api.v1.models.RecipeDTO;

import java.util.List;

public interface RESTRecipeService
{
    List<RecipeDTO> getAllRecipes();

    List<RecipeDTO> findAllRecipesByCategory(String category);

    RecipeDTO findById(Long id);
}
