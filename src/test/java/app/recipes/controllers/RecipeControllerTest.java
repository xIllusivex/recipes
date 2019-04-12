package app.recipes.controllers;

import app.recipes.converters.CategoryToCategoryCommand;
import app.recipes.exceptions.NotFoundException;
import app.recipes.models.Recipe;
import app.recipes.repositorys.CategoryRepository;
import app.recipes.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest
{
    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    CategoryToCategoryCommand categoryToCategoryCommand;

    RecipeController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);

        controller = new RecipeController(recipeService, categoryRepository, categoryToCategoryCommand);

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    public void getRecipeTest() throws Exception
    {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        when(recipeService.findById(anyLong())).thenReturn(recipe);

        mockMvc.perform(get("/recipe/show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void getRecipeNotFound() throws Exception
    {
        when (recipeService.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/recipe/show/4"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));


    }

    @Test
    public void getRecipeIncorrectDataTypeTest() throws Exception
    {
        mockMvc.perform(get("/recipe/show/asdf"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));

    }
}