package supperSolver.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import supperSolver.DataTransferObjects.CommentDTO;
import supperSolver.Models.MComment;
import supperSolver.Models.MRating;
import supperSolver.Repositories.RComment;
import supperSolver.Repositories.RUser;
import supperSolver.Repositories.RRecipe;

@RestController
public class CommentController
{
    @Autowired
    private RComment rComment;

    @Autowired
    private RUser rUser;

    @Autowired
    private RRecipe rRecipe;

    // Gets a MComment by its ID
    @GetMapping("/comments/{id}")
    public MComment getCommentByID(@PathVariable("id") int id)
    {
        return rComment.findById(id).get();
    }

    // Creates a new MComment
    @PostMapping("/comments")
    public MComment createComment(@RequestBody CommentDTO comment)
    {
        MComment newComment = new MComment();

        newComment.setUser(rUser.findById(comment.getUserID()).get());
        newComment.setRecipe(rRecipe.findById(comment.getRecipeID()).get());
        newComment.setComment(comment.getComment());

        return rComment.save(newComment);
    }

    // Updates a MComment by its ID
    @PutMapping("/comments/{id}")
    public ResponseEntity<MComment> updateComment(@PathVariable("id") int id, @RequestBody CommentDTO comment)
    {
        MComment existingComment = rComment.findById(id).get();

        existingComment.setRecipe(rRecipe.findById(comment.getRecipeID()).get());
        existingComment.setUser(rUser.findById(comment.getUserID()).get());
        existingComment.setComment(comment.getComment());

        rComment.save(existingComment);
        return ResponseEntity.ok(existingComment);
    }

    // Deletes a MComment by its ID
    @DeleteMapping("/comments/{id}")
    public void deleteComment(@PathVariable("id") int id)
    {
        rComment.deleteById(id);
    }
}
