package app.recipes.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class UnitOfMeasure
{
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String description;

}
