package app.recipes.models;

import lombok.*;
import org.springframework.data.annotation.Id;

@Setter
@Getter
public class Notes
{
    @Id
    private String id;
    private Recipe recipe;
    private String recipeNotes;

}
