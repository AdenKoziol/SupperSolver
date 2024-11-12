package supperSolver.Controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import supperSolver.DataTransferObjects.RatingDTO;
import supperSolver.Models.MRating;
import supperSolver.Repositories.RRating;
import supperSolver.Repositories.RUser;
import supperSolver.Repositories.RRecipe;

import java.util.List;
import java.util.Optional;

@RestController
public class RatingController
{
    @Autowired
    private RRating rRating;

    @Autowired
    private RUser rUser;

    @Autowired
    private RRecipe rRecipe;

    // Gets a MRating object by their ID
    @GetMapping(path = "/rating/{ID}")
    public MRating GetRatingByID(@PathVariable("ID") int ID)
    {
        return rRating.findById(ID).get();
    }

    // Gets Rating objects by their recipeIDs
    @GetMapping(path = "/rating/byrecipe/{recipeID}")
    public List<MRating> GetRatingsByRecipeID(@PathVariable("recipeID") int recipeID)
    {
        return rRating.findByRecipeID(recipeID);
    }

    // Gets average rating for recipe
    @GetMapping(path = "/rating/avg/{recipeID}")
    public int avgRating(@PathVariable("recipeID") int recipeID)
    {
        //grabs list of all the ratings for the recipe
        List<MRating> allRatingsForRecipe = rRating.findByRecipeID(recipeID);
        int avg = 0;
        //loops through and adds all the ratings
        for (MRating mRating : allRatingsForRecipe) {
            avg += mRating.getRating();
        }
        //divides by size of list to get average
        avg /= allRatingsForRecipe.size();
        return avg;
    }

    // Gets ratings by their userID
    @GetMapping(path = "/rating/byuserid/{userID}")
    public List<MRating> GetRatingsByUserID(@PathVariable("userID") int userID)
    {
        return rRating.findByUserID(userID);
    }

    // Creates a new MRating
    @PostMapping(path = "/rating")
    public MRating CreateRating(@RequestBody RatingDTO rating)
    {
        if(rating.getRating() > 5 || rating.getRating() < 0)
            throw new IllegalArgumentException("Rating must be less than 5 or greater than -1");
        else
        {
            MRating newRating = new MRating();

            newRating.setRating(rating.getRating());
            newRating.setUser(rUser.findById(rating.getUserID()).get());
            newRating.setRecipe(rRecipe.findById(rating.getRecipeID()).get());

            return rRating.save(newRating);
        }
    }

    // Updates an existing MRating by its ID
    @PutMapping(path = "/rating/{ID}")
    public ResponseEntity<MRating> updateRating(@PathVariable int ID, @RequestBody MRating rating) {

        MRating existingRating = rRating.findById(ID).get();

        existingRating.setUser(rating.getUser());
        existingRating.setRating(rating.getRating());
        existingRating.setRecipe(rating.getRecipe());

        rRating.save(existingRating);
        return ResponseEntity.ok(existingRating);
    }


    // Deletes a rating by its ID
    @DeleteMapping(path = "/rating/{ID}")
    public String DeleteRating(@PathVariable int ID)
    {
        rRating.deleteById(ID);
        return "Rating ID: " + ID + "has been deleted";
    }
}