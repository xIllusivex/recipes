package app.recipes.models;

import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Data
@Entity
public class UnitOfMeasure
{
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String description;

}
