package app.recipes.models;

import lombok.*;

@Setter
@Getter
public class Notes
{
    private String id;
    private Recipe recipe;
    private String recipeNotes;

}
