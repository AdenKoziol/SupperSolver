package supperSolver.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_comment")
public class MComment
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int ID;

    @ManyToOne
    @JoinColumn(name = "recipe", nullable = false)
    public MRecipe recipe;

    @ManyToOne
    @JoinColumn(name = "user", nullable = false)
    public MUser user;

    @Column(name = "comment")
    public String comment;

    public MComment() { }

    public MComment(MRecipe recipe, MUser user, String comment)
    {
        this.recipe = recipe;
        this.user = user;
        this.comment = comment;
    }

    public int getID() { return ID; }

    public MRecipe getRecipe() { return recipe; }
    public void setRecipe(MRecipe recipe) { this.recipe = recipe; }

    public MUser getUser() { return user; }
    public void setUser(MUser user) { this.user = user; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}