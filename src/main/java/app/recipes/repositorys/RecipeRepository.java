package app.recipes.repositorys;

import app.recipes.models.Recipe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;


public interface RecipeRepository extends CrudRepository<Recipe, Long>
{
    @Query(value = "SELECT recipe.* FROM recipe JOIN recipe_category ON recipe_category.recipe_id = recipe.id JOIN category ON category.id = recipe_category.category_id WHERE category.description LIKE :category", nativeQuery = true)
    Set<Recipe> findRecipesByCategories(@Param("category") String category);
}
