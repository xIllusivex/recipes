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
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand
{
    private String id;

    @Builder
    public RecipeCommand(String id, @NotBlank @Size(min = 3, max = 255) String description,
                         @Min(1) @Max(999) Integer prepTime, @Min(1) @Max(999) Integer cookTime,
                         @Min(1) @Max(100) Integer servings, String source, @URL @NotBlank String url,
                         @NotBlank String directions, Byte[] image, List<IngredientCommand> ingredients,
                         Difficulty difficulty, NotesCommand notes, List<CategoryCommand> categories)
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
    private String url;

    @NotBlank
    private String directions;

    private Byte[] image;
    private List<IngredientCommand> ingredients = new ArrayList<>();
    private Difficulty difficulty;
    private NotesCommand notes;
    private List<CategoryCommand> categories = new ArrayList<>();

}
