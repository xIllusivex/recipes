package app.recipes.commands;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotesCommand
{
    private Long id;
    private String recipeNotes;

    @Builder
    public NotesCommand(Long id, String recipeNotes)
    {
        this.id = id;
        this.recipeNotes = recipeNotes;
    }
}
