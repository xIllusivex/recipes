package app.recipes.models;

import lombok.*;

import java.util.Set;

@Getter
@Setter
public class Category
{
    private String id;
    private String description;
    private Set<Recipe> recipes;

}
