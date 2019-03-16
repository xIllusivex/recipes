package app.recipes.models;

import javax.persistence.*;

@Entity
public class Notes
{
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private @OneToOne Recipe recipe;
    private @Lob String recipeNotes;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Recipe getRecipe()
    {
        return recipe;
    }

    public void setRecipe(Recipe recipe)
    {
        this.recipe = recipe;
    }

    public String getRecipeNotes()
    {
        return recipeNotes;
    }

    public void setRecipeNotes(String recipeNotes)
    {
        this.recipeNotes = recipeNotes;
    }
}
