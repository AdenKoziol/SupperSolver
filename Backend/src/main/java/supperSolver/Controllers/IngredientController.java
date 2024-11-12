package supperSolver.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.DelegatingServerHttpResponse;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import supperSolver.Models.MComment;
import supperSolver.Models.MIngredient;
import supperSolver.Repositories.RIngredient;
import supperSolver.Repositories.RRecipe;
import supperSolver.Repositories.RUser;
import supperSolver.Repositories.RIngredient;
import supperSolver.DataTransferObjects.IngredientDTO;

@RestController
public class IngredientController
{
    @Autowired
    RIngredient rIngredient;

    @Autowired
    RUser rUser;

    @Autowired
    RRecipe rRecipe;

    // Gets all MIngredients by userID
    @GetMapping("/ingredients/user/{userID}")
    public List<MIngredient> GetIngredientsByUser(@PathVariable int userID)
    {
        return rIngredient.findByUserID(userID).get();
    }

    // Gets all MIngredients by recipeID
    @GetMapping("/ingredients/recipe/{recipeID}")
    public List<MIngredient> GetIngredientsByRecipe(@PathVariable int recipeID)
    {
        return rIngredient.findByRecipeID(recipeID).get();
    }

    // Gets a MIngredient by its ID
    @GetMapping("/ingredients/{id}")
    public MIngredient GetIngredientByID(@PathVariable("id") int id)
    {
        return rIngredient.findById(id).get();
    }

    // Creates a new MIngredient
    @PostMapping("/ingredients")
    public MIngredient CreateIngredient(@RequestBody IngredientDTO ingredient)
    {
        MIngredient newIngredient = new MIngredient();

        if(ingredient.getUserID() == -1)
            newIngredient.setUser(null);
        else
            newIngredient.setUser(rUser.findById(ingredient.getUserID()).get());

        if(ingredient.getRecipeID() == -1)
            newIngredient.setRecipe(null);
        else
            newIngredient.setRecipe(rRecipe.findById(ingredient.getRecipeID()).get());

        newIngredient.setName(ingredient.getName());
        newIngredient.setQuantity(ingredient.getQuantity());
        
        return rIngredient.save(newIngredient);
    }

    // Updates a MIngredient by its ID
    @PutMapping("/ingredients/{id}")
    public ResponseEntity<MIngredient> UpdateIngredient(@PathVariable("id") int id, @RequestBody MIngredient ingredient)
    {
        MIngredient existingIngredient = rIngredient.findById(id).get();

        existingIngredient.setUser(ingredient.getUser());
        existingIngredient.setRecipe(ingredient.getRecipe());
        existingIngredient.setName(ingredient.getName());
        existingIngredient.setQuantity(ingredient.getQuantity());

        rIngredient.save(existingIngredient);
        return ResponseEntity.ok(existingIngredient);
    }

    // Deletes a MIngredient by its ID
    @DeleteMapping("/ingredients/{id}")
    public void DeleteIngredient(@PathVariable("id") int id)
    {
        rIngredient.deleteById(id);
    }
}
