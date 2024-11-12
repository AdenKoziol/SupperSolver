package supperSolver.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import supperSolver.Models.MImage;
import supperSolver.Models.MRating;
import supperSolver.Repositories.RImage;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.*;

@RestController
public class ImageController
{
    @Autowired
    private RImage rImage;

    //directory to store images
    private static final String DIR = "images/";

    //uploading images
    @PostMapping("/image")
    public MImage uploadImage(@RequestParam("file") MultipartFile file, @RequestParam int imgPos, @RequestParam int recipeID, @RequestParam int userID) throws Exception
    {
        if(file.isEmpty())
            throw new IllegalArgumentException("No File found");

        MImage obj = new MImage();

        obj.setImagePos(imgPos);
        obj.setRecipeID(recipeID);
        obj.setUserID(userID);

        int id = rImage.save(obj).getID();

        MImage existing = rImage.findById(id).get();

        Path filePath = Paths.get(DIR + id + file.getOriginalFilename()); //so the image doesn't overlap with another add id to filename

        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes()); //writing the file to the server

        existing.setImgUrl(filePath.toString()); //filling out image auto creates ID and url is decided on create

        return rImage.save(existing); //put image back with filepath, needed generated id for filename
    }

    //getting all images by recipeID
    @GetMapping("/image/byrecipe/{recipeID}")
    public List<MImage> findByRecipe(@PathVariable("recipeID") int recipeID)
    {
        return rImage.findByRecipeID(recipeID);
    }

    // Gets profile picture for user
    @GetMapping("image/user/{userID}")
    public MImage findByUserID(@PathVariable("userID") int userID)
    {
        return rImage.findByUserID(userID);
    }

    //Getting first image
    @GetMapping("/image/first/{recipeID}")
    public MImage getFirstImage(@PathVariable("recipeID") int recipeID)
    {
        return rImage.findByRecipeIDAndImagePos(recipeID,0);
    }

    // Deletes an image by its ID
    @DeleteMapping("/image/{id}")
    public String deleteImage(@PathVariable("id") int id) throws IOException
    {
        if(rImage.findById(id).isEmpty())
        {
            throw new InvalidParameterException("No image with that ID found");
        }
        else
        {
            //deleting the actual file from server using path from database
            Path delPath = Path.of(rImage.findById(id).get().getImgUrl());
            Files.delete(delPath);

            //deleting image entry in database
            rImage.deleteById(id);
            return "Image ID: " + id + " was deleted";
        }
    }

    // Create a new image
    @PostMapping("/image/link")
    public MImage link(@RequestBody MImage image)
    {
        return rImage.save(image);
    }
}