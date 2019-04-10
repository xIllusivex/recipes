package app.recipes.services;

import app.recipes.commands.IngredientCommand;
import app.recipes.converters.IngredientCommandToIngredient;
import app.recipes.converters.IngredientToIngredientCommand;
import app.recipes.converters.UnitOfMeasureCommandToUnitOfMeasure;
import app.recipes.converters.UnitOfMeasureToUnitOfMeasureCommand;
import app.recipes.models.Ingredient;
import app.recipes.models.Recipe;
import app.recipes.repositorys.RecipeRepository;
import app.recipes.repositorys.UnitOfMeasureRepository;
import app.recipes.repositorys.reactive.RecipeReactiveRepository;
import app.recipes.repositorys.reactive.UnitOfMeasureReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest
{
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    @Mock
    RecipeReactiveRepository recipeReactiveRepository;

    @Mock
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    IngredientService ingredientService;

    public IngredientServiceImplTest()
    {
        // init converters
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);

        ingredientService = new IngredientServiceImpl(
                ingredientToIngredientCommand,
                ingredientCommandToIngredient,
                recipeReactiveRepository,
                unitOfMeasureReactiveRepository);
    }

    @Test
    public void findByRecipeIdAndIngredientId() throws Exception
    {
        //given
        Recipe recipe = new Recipe();
        recipe.setId("1");

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId("1");

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId("2");

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId("3");

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));

        //then
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId("1", "2").block();

        //when
        assertEquals("2", ingredientCommand.getId());
        verify(recipeReactiveRepository, times(1)).findById(anyString());
    }
}