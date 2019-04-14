package app.recipes.api.v1.controllers;

import app.recipes.api.v1.models.RecipeDTO;
import app.recipes.api.v1.services.RESTRecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping(RecipeControllerRest.BASE_URL)
public class RecipeControllerRest
{
    public final static String BASE_URL = "api/v1/";
    public final RESTRecipeService restRecipeService;

    public RecipeControllerRest(RESTRecipeService restRecipeService)
    {
        this.restRecipeService = restRecipeService;
    }

    @CrossOrigin
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String sanitationCheck()
    {
        log.debug("Successfully requested a sanity check.");
        return "Hello, World!";
    }

    @CrossOrigin
    @GetMapping("recipes/")
    @ResponseStatus(HttpStatus.OK)
    public List<RecipeDTO> getAllRecipes(@Param("category") String category)
    {
        log.debug("serving up the recipe resource.");

        if (category != null)
        {
            return restRecipeService.findAllRecipesByCategory(category);
        }

        return restRecipeService.getAllRecipes();
    }

    @CrossOrigin
    @GetMapping("recipes/{id}/")
    @ResponseStatus(HttpStatus.OK)
    public RecipeDTO getRecipe(@PathVariable Long id)
    {
        return restRecipeService.findById(id);
    }


}
