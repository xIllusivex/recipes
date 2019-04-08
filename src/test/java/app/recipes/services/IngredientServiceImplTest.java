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
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest
{
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

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
                recipeRepository,
                unitOfMeasureRepository);
    }

    @Test
    public void findByRecipeIdAndIngredientId()
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
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        //then
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId("1", "2");

        //when
        assertEquals("2", ingredientCommand.getId());
        verify(recipeRepository, times(1)).findById(anyString());
    }
}