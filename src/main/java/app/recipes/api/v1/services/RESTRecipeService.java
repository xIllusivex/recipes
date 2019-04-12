package app.recipes.api.v1.services;

import app.recipes.api.v1.models.RecipeDTO;

import java.util.List;

public interface RESTRecipeService
{
    List<RecipeDTO> getAllRecipes();

    RecipeDTO findById(Long id);
}
