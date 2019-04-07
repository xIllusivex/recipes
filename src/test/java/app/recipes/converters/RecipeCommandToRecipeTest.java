package app.recipes.converters;

import app.recipes.commands.NotesCommand;
import app.recipes.commands.RecipeCommand;
import app.recipes.models.Difficulty;
import app.recipes.models.Recipe;
import app.recipes.services.RecipeService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class RecipeCommandToRecipeTest
{
    private RecipeCommandToRecipe recipeCommandToRecipe;
    private RecipeCommand recipeCommand;
    private final String id = "1";
    private final NotesCommand notesCommand = new NotesCommand().builder().id("1").recipeNotes("Hello, World").build();

    @Before
    public void setUp() throws Exception
    {
        recipeCommandToRecipe = new RecipeCommandToRecipe(
                new CategoryCommandToCategory(),
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                new NotesCommandToNotes());

        recipeCommand = new RecipeCommand().builder().id(id).cookTime(10).prepTime(10)
                .description("chocolate chip cookies")
                .difficulty(Difficulty.EASY)
                .notes(notesCommand)
                .build();

    }

    @Test
    public void convert()
    {
        Recipe recipe = recipeCommandToRecipe.convert(recipeCommand);

        assertNotNull(recipe);

        assertEquals(id, recipe.getId());

        assertEquals(id, recipeCommand.getId());
    }

    @Test
    public void convertNull()
    {
        Recipe recipe = recipeCommandToRecipe.convert(null);

        assertNull(recipe);

        assertEquals(id, recipeCommand.getId());
    }
}