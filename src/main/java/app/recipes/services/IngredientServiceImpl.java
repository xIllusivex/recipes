package app.recipes.services;

import app.recipes.commands.IngredientCommand;
import app.recipes.converters.IngredientToIngredientCommand;
import app.recipes.models.Recipe;
import app.recipes.repositorys.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService
{
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final RecipeRepository recipeRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, RecipeRepository recipeRepository)
    {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId)
    {
        Optional<Recipe> recipe = recipeRepository.findById(recipeId);

        if (!recipe.isPresent())
        {
            throw new RuntimeException("No recipe found.");
        }

        Optional<IngredientCommand> ingredientCommandOptional = recipe.get().getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

        if (!ingredientCommandOptional.isPresent())
        {
            throw new RuntimeException("no ingredient found by the id: " + ingredientId);
        }

        return ingredientCommandOptional.get();
    }
}
