package app.recipes.models;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;


@Data
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Ingredient
{
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String description;
    private BigDecimal amount;

    @ManyToOne
    private Recipe recipe;

    @OneToOne(fetch = FetchType.EAGER)
    private UnitOfMeasure uom;

    public Ingredient()
    {
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom, Recipe recipe)
    {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
        this.recipe = recipe;

    }

}
