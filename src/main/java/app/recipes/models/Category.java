package app.recipes.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import jakarta.persistence.*;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"recipes"})
@JsonIgnoreProperties("recipes")
@Entity
public class Category
{
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String description;

    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes;

}
