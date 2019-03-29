package app.recipes.controllers;

import app.recipes.commands.RecipeCommand;
import app.recipes.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class RecipeController
{
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService)
    {
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipe/show/{id}")
    public String getRecipe(@PathVariable Long id, Model model)
    {
        log.debug("Requesting recipe page");

        model.addAttribute("recipe", recipeService.findById(id));

        return "recipe/show";
    }

    @RequestMapping("recipe/new")
    public String newRecipe(Model model)
    {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeForm";
    }

    @RequestMapping("/recipe/update/{id}")
    public String updateRecipe(@PathVariable Long id, Model model)
    {
        model.addAttribute("recipe", recipeService.findCommandById(id));

        return "recipe/recipeForm";
    }

    @PostMapping
    @RequestMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command)
    {
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/show/" + savedCommand.getId();
    }
}
