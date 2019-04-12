package app.recipes.api.v1.models;

import app.recipes.models.Category;
import app.recipes.models.Difficulty;
import app.recipes.models.Ingredient;
import app.recipes.models.Notes;
import lombok.Data;
import org.mapstruct.Mapping;

import java.util.Set;

@Data
public class RecipeDTO
{
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Set<Ingredient> ingredients;
    private Byte[] image;
    private Difficulty difficulty;
    private Notes notes;
    private Set<Category> categories;
}
