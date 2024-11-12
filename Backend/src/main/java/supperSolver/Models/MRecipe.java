package supperSolver.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_recipe")
public class MRecipe
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @ManyToOne
    @JoinColumn(name="user")
    private MUser user;

    @Column
    private String name;

    @Column(name="description")
    private String description;

    public MRecipe(){ }

    public MRecipe(int ID, MUser user, String name, String description)
    {
        this.ID = ID;
        this.user = user;
        this.description = description;
    }

    public int getID(){
        return ID;
    }
    public void setID(int ID) { this.ID = ID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public MUser getUser() {
        return user;
    }
    public void setUser(MUser user){
        this.user = user;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}