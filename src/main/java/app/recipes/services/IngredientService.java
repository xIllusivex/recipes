package app.recipes.services;

import app.recipes.commands.IngredientCommand;
import reactor.core.publisher.Mono;

public interface IngredientService
{
    Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId);

    Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command, String recipeId);

    Mono<Void> deleteById(String recipeId, String id);
}
