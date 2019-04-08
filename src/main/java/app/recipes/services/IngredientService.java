package app.recipes.services;

import app.recipes.commands.IngredientCommand;

public interface IngredientService
{
    IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand command, String recipeId);

    void deleteById(String recipeId, String id);
}
