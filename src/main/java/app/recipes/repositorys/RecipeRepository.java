package app.recipes.repositorys;

import app.recipes.models.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long>
{

}
