package app.recipes.controllers;

import app.recipes.commands.RecipeCommand;
import app.recipes.converters.CategoryToCategoryCommand;
import app.recipes.exceptions.NotFoundException;
import app.recipes.models.Category;
import app.recipes.repositorys.CategoryRepository;
import app.recipes.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Controller
public class RecipeController
{
    private final RecipeService recipeService;
    private final CategoryRepository categoryRepository;
    private final CategoryToCategoryCommand categoryToCategoryCommand;

    public RecipeController(RecipeService recipeService, CategoryRepository categoryRepository, CategoryToCategoryCommand categoryToCategoryCommand)
    {
        this.recipeService = recipeService;
        this.categoryRepository = categoryRepository;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
    }

    @GetMapping("/recipe/show/{id}")
    public String getRecipe(@PathVariable Long id, Model model)
    {
        log.debug("Requesting recipe page");

        model.addAttribute("recipe", recipeService.findById(id));

        return "recipe/show";
    }

    @GetMapping("recipe/new")
    public String newRecipe(Model model)
    {
        model.addAttribute("recipe", new RecipeCommand());
        model.addAttribute("categories", categoryRepository.findAll());

        return "recipe/recipeForm";
    }

    @GetMapping("/recipe/update/{id}")
    public String updateRecipe(@PathVariable Long id, Model model)
    {
        model.addAttribute("recipe", recipeService.findCommandById(id));
        model.addAttribute("categories", categoryRepository.findAll());

        return "recipe/recipeForm";
    }

    @PostMapping("recipe")
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult result)
    {
        if (result.hasErrors())
        {
            result.getAllErrors().forEach(error -> { log.debug(error.toString()); });

            return "recipe/recipeForm";
        }
        if (command.getCategoryNames().length > 0)
        {
            for (String category : command.getCategoryNames())
            {
                Optional<Category> categoryOptional = categoryRepository.findByDescription(category);

                if (categoryOptional.isPresent())
                {
                    command.getCategories().add(categoryToCategoryCommand.convert(categoryOptional.get()));
                }
            }
        }

        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/show/" + savedCommand.getId();
    }

    @GetMapping("/recipe/delete/{id}")
    public String deleteRecipe(@PathVariable Long id)
    {
        recipeService.deleteById(id);

        log.debug("deleting id: " + id);

        return "redirect:/index";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception e)
    {
        log.error("Handling not found exception");
        log.error(e.getMessage());

        ModelAndView modelAndView = new ModelAndView("404error");
        modelAndView.addObject("exception", e);
        return modelAndView;
    }
}
