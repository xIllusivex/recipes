package app.recipes.api.v1.controllers;

import app.recipes.api.v1.models.RecipeDTO;
import app.recipes.api.v1.services.RESTRecipeService;
import app.recipes.commands.RecipeCommand;
import app.recipes.services.AWSImageService;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping(ImageControllerREST.BASE_URL)
public class ImageControllerREST
{
    public final static String BASE_URL = "api/v1/";
    public final RESTRecipeService restRecipeService;
    public final AWSImageService awsImageService;

    public ImageControllerREST(RESTRecipeService restRecipeService, AWSImageService awsImageService)
    {
        this.restRecipeService = restRecipeService;
        this.awsImageService = awsImageService;
    }

    @CrossOrigin
    @GetMapping("recipe/{id}/image")
    public void serveImage(@PathVariable Long id, HttpServletResponse response) throws IOException
    {
        RecipeDTO recipeDTO = restRecipeService.findById(id);

        S3Object image = awsImageService.getFile(recipeDTO.getImage());

        log.debug(image.getObjectContent().toString());

        response.setContentType("image/jpeg");
        IOUtils.copy(image.getObjectContent(), response.getOutputStream());
    }


}
