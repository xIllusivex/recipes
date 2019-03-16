package app.recipes.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Category
{
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String description;

    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
