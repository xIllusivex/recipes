package app.recipes.repositorys;

import app.recipes.models.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RecipeRepository extends CrudRepository<Recipe, Long>
{
}
