package supperSolver.Models;
import jakarta.persistence.*;

@Entity
@Table(name = "tbl_rating")

public class MRating
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @Column(name = "rating")
    private int rating;

    @ManyToOne
    @JoinColumn(name = "user")
    private MUser user;

    @ManyToOne
    @JoinColumn(name = "recipe")
    private MRecipe recipe;

    public MRating() { }

    public MRating(MUser user, int rating, MRecipe recipe)
    {
        this.rating = rating;
        this.user = user;
        this.recipe = recipe;
    }

    public int getID(){
        return ID;
    }

    public int getRating(){
        return rating;
    }

    public void setRating(int rating){
        if(rating < 1 || rating > 5){
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        this.rating = rating;
    }

    public MRecipe getRecipe() {
        return recipe;
    }
    public void setRecipe(MRecipe recipe) { this.recipe = recipe; }

    public MUser getUser(){
        return user;
    }
    public void setUser(MUser user){
        this.user = user;
    }
}