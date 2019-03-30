package app.recipes.controllers;

import java.util.Optional;
import java.util.stream.Collectors;

import app.recipes.commands.IngredientCommand;
import app.recipes.commands.RecipeCommand;
import app.recipes.commands.UnitOfMeasureCommand;
import app.recipes.models.Recipe;
import app.recipes.services.IngredientService;
import app.recipes.services.RecipeService;
import app.recipes.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class IngredientController
{
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService)
    {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping
    @RequestMapping("/recipe/{id}/ingredients")
    public String listIngredients(@PathVariable Long id, Model model)
    {
        log.debug("Getting ingredient list for recipe id " + id);

        model.addAttribute("recipe", recipeService.findCommandById(id));

        return "recipe/ingredient/list";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/show")
    public String showIngredient(@PathVariable Long recipeId, @PathVariable Long id, Model model)
    {
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, id));

        return "/recipe/ingredient/show";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/new")
    public String newRecipe(@PathVariable Long recipeId, Model model)
    {
        //todo throw exception if null
        RecipeCommand recipeCommand = recipeService.findCommandById(recipeId);
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipeId);

        model.addAttribute("ingredient", ingredientCommand);

        //initializing a blank unit of measurement
        ingredientCommand.setUom(new UnitOfMeasureCommand());

        //loading in all unit of measurement options for the drop down.
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

        return "recipe/ingredient/ingredientForm";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/update")
    public String updateRecipeIngredient(@PathVariable Long recipeId, @PathVariable Long id, Model model)
    {
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, id));

        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

        return "recipe/ingredient/ingredientForm";
    }

    @PostMapping
    @RequestMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command, @PathVariable Long recipeId)
    {
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        log.debug("saved Ingredient id " + command.getId());

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteIngredient(@PathVariable Long recipeId, @PathVariable Long id, Model model)
    {
        Recipe recipe = recipeService.findById(recipeId);

        if (recipe.getId() == null)
        {
            throw new RuntimeException("No recipe found.");
        }

        recipe.setIngredients(recipe.getIngredients().stream().filter(ingredient -> !ingredient.getId().equals(id)).collect(Collectors.toSet()));

        recipeService.save();
    }
}
