package app.recipes.services;

import app.recipes.models.Recipe;
import app.recipes.repositorys.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService
{
    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository)
    {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void saveImage(Long recipeId, String fileName)
    {
        log.debug("saving image" + fileName + " name to recipeId: " + recipeId);

        Recipe recipe = recipeRepository.findById(recipeId).get();

        recipe.setImage(fileName);

        recipeRepository.save(recipe);
    }
}
