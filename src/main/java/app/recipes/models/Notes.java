package app.recipes.models;

import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Notes
{
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private @OneToOne Recipe recipe;
    private @Lob String recipeNotes;

}
