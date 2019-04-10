package app.recipes.services;

import app.recipes.commands.IngredientCommand;
import app.recipes.converters.IngredientCommandToIngredient;
import app.recipes.converters.IngredientToIngredientCommand;
import app.recipes.models.Ingredient;
import app.recipes.models.Recipe;
import app.recipes.repositorys.reactive.RecipeReactiveRepository;
import app.recipes.repositorys.reactive.UnitOfMeasureReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService
{
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeReactiveRepository recipeReactiveRepository;
    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 RecipeReactiveRepository recipeReactiveRepository,
                                 UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository)
    {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
    }

    @Override
    public Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId)
    {
        Optional<Recipe> recipe = recipeReactiveRepository.findById(recipeId).blockOptional();

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

        return Mono.just(ingredientCommandOptional.get());
    }

    @Override
    @Transactional
    public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command, String recipeId)
    {
        Optional<Recipe> recipeOptional = recipeReactiveRepository.findById(recipeId).blockOptional();

        if(!recipeOptional.isPresent())
        {
            // todo throw error if not found.
            log.error("Recipe not found for id " + command.getRecipeId());

            return Mono.just(new IngredientCommand());
        } else
        {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId())).findFirst();

            if (ingredientOptional.isPresent())
            {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setUom(unitOfMeasureReactiveRepository
                        .findById(command.getUom().getId()).blockOptional()
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND!")));
            } else
            {
                Ingredient ingredient = ingredientCommandToIngredient.convert(command);
                ingredient.setId(UUID.randomUUID().toString());
//                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);

            }

            Recipe savedRecipe = recipeReactiveRepository.save(recipe).block();

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId())).findFirst();

            if(!savedIngredientOptional.isPresent())
            {
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(ingredient -> ingredient.getDescription().equals(command.getDescription()))
                        .filter(ingredient -> ingredient.getAmount().equals(command.getAmount()))
                        .filter(ingredient -> ingredient.getUom().getId().equals(command.getUom().getId()))
                        .findFirst();
            }

            IngredientCommand ingredientCommandSaved = ingredientToIngredientCommand.convert(savedIngredientOptional.get());
            ingredientCommandSaved.setRecipeId(recipe.getId());

            return Mono.just(ingredientCommandSaved);
        }
    }

    @Override
    public Mono<Void> deleteById(String recipeId, String id)
    {
        log.debug("deleting ingredient: " + id + " from recipe id: " + recipeId);

        Optional<Recipe> recipeOptional = recipeReactiveRepository.findById(recipeId).blockOptional();

        if (!recipeOptional.isPresent())
        {
            throw new RuntimeException("No Recipe found!");
        }

        Recipe recipe = recipeOptional.get();

        Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(id))
                .findFirst();

        if (ingredientOptional.isPresent())
        {
            log.debug("found ingredient!");
            Ingredient ingredientToDelete = ingredientOptional.get();
//            ingredientToDelete.setRecipe(null);
            recipe.getIngredients().remove(ingredientToDelete);
        }else
        {
            log.debug("Ingredient Id Nout Found.");
        }

        recipeReactiveRepository.save(recipe).block();

        return Mono.empty();
    }
}
