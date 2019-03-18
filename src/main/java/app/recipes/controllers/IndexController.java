package app.recipes.controllers;

import app.recipes.models.Category;
import app.recipes.models.UnitOfMeasure;
import app.recipes.repositorys.CategoryRepository;
import app.recipes.repositorys.RecipeRepository;
import app.recipes.repositorys.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class IndexController
{
    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private RecipeRepository recipeRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, RecipeRepository recipeRepository)
    {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndex(Model model)
    {
        model.addAttribute("recipes", recipeRepository.findAll());
        return "index";
    }
}
