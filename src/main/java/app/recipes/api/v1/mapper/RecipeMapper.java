package app.recipes.api.v1.mapper;

import app.recipes.api.v1.models.RecipeDTO;
import app.recipes.models.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RecipeMapper
{
    RecipeMapper INSTANCE = Mappers.getMapper(RecipeMapper.class);

    RecipeDTO recipeToRecipeDTO(Recipe recipe);
}
