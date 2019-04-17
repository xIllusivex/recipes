package app.recipes.controllers;

import app.recipes.commands.RecipeCommand;
import app.recipes.services.AWSImageService;
import app.recipes.services.ImageService;
import app.recipes.services.RecipeService;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ImageController
{
    private final ImageService imageService;
    private final RecipeService recipeService;
    private final AWSImageService awsImageService;

    public ImageController(ImageService imageService, RecipeService recipeService, AWSImageService awsImageService)
    {
        this.awsImageService = awsImageService;
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}/image")
    public String serveForm(@PathVariable Long id, Model model)
    {
        model.addAttribute("recipe", recipeService.findCommandById(id));

        return "recipe/imageUploadForm";
    }

    @GetMapping("/recipe/{id}/recipeimage")
    public void serveImage(@PathVariable Long id, HttpServletResponse response) throws IOException
    {
        RecipeCommand recipeCommand = recipeService.findCommandById(id);

        S3ObjectInputStream image = awsImageService.getFile(recipeCommand.getImage()).getObjectContent();

        // TODO connect to the s3 bucket and serve up the image from there.

        response.setContentType("image/jpeg");
        IOUtils.copy(image, response.getOutputStream());
    }

    @PostMapping("/recipe/{id}/image")
    public String handleImagePost(@PathVariable Long id, @RequestParam("imagefile") MultipartFile file)
    {
        imageService.saveImage(id, file);

        return "redirect:/recipe/show/" + id;
    }
}
