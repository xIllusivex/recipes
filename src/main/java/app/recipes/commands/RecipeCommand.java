package app.recipes.commands;

import app.recipes.models.Difficulty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand
{
    private Long id;

    @Builder
    public RecipeCommand(Long id, @NotBlank @Size(min = 3, max = 255) String description,
                         @Min(1) @Max(999) Integer prepTime, @Min(1) @Max(999) Integer cookTime,
                         @Min(1) @Max(100) Integer servings, String source, @URL @NotBlank String url,
                         @NotBlank String directions, String image, Set<IngredientCommand> ingredients,
                         Difficulty difficulty, NotesCommand notes, Set<CategoryCommand> categories, String[] categoryNames)
    {
        this.id = id;
        this.description = description;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.servings = servings;
        this.source = source;
        this.url = url;
        this.directions = directions;
        this.image = image;
        this.ingredients = ingredients;
        this.difficulty = difficulty;
        this.notes = notes;
        this.categories = categories;
        this.categoryNames = categoryNames;
    }


    @NotBlank
    @Size(min = 3, max = 255)
    private String description;

    @Min(1)
    @Max(999)
    private Integer prepTime;

    @Min(1)
    @Max(999)
    private Integer cookTime;

    @Min(1)
    @Max(100)
    private Integer servings;
    private String source;

    @URL
    @NotBlank
    private String url;

    @NotBlank
    private String directions;
    private String image;
    private Set<IngredientCommand> ingredients = new HashSet<>();
    private Difficulty difficulty;
    private NotesCommand notes;
    private Set<CategoryCommand> categories = new HashSet<>();
    private String[] categoryNames;

}
