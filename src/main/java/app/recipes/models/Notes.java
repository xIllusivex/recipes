package app.recipes.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = {"recipe"})
@JsonIgnoreProperties("recipe")
@Entity
public class Notes
{
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private @OneToOne Recipe recipe;
    private @Lob String recipeNotes;

}
