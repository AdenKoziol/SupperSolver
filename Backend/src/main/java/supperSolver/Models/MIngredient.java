package supperSolver.Models;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import supperSolver.Repositories.RRecipe;
import supperSolver.Repositories.RUser;

@Entity
@Table(name = "tbl_ingredient")
public class MIngredient
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int ID;

    @ManyToOne
    @JoinColumn(name = "user")
    public MUser user;

    @ManyToOne
    @JoinColumn(name = "recipe")
    public MRecipe recipe;

    @Column(name = "name")
    public String name;

    @Column(name = "quantity")
    public double quantity;

    public MIngredient() { }

    public MIngredient(MUser user, MRecipe recipe, String name, double quantity)
    {
        this.user = user;
        this.recipe = recipe;
        this.name = name;
        this.quantity = quantity;
    }

    public int getId() { return ID; }

    public MUser getUser() { return user; }
    public void setUser(MUser user) { this.user = user; }

    public MRecipe getRecipe() { return recipe; }
    public void setRecipe(MRecipe recipe) { this.recipe = recipe; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }
}