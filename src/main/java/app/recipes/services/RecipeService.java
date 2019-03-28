package app.recipes.services;

import app.recipes.models.Recipe;

import java.util.Set;

public interface RecipeService
{
    Set<Recipe> getRecipes();

    Recipe findById(Long id);

}
