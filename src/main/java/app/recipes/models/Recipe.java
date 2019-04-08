package app.recipes.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Document
public class Recipe
{
    private @Id String id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Set<Ingredient> ingredients = new HashSet<>();
    private Byte[] image;
    private Difficulty difficulty;
    private Notes notes;

    @DBRef
    private Set<Category> categories = new HashSet<>();


    public Recipe addIngredient(Ingredient ingredient)
    {
//        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
        return this;
    }

    public void setNotes(Notes notes)
    {
//        notes.setRecipe(this);
        if (notes != null)
        {
            this.notes = notes;
        }
    }

    public void addCategory(Category category)
    {
        if (category != null)
        {
            categories.add(category);
        }
    }
}
