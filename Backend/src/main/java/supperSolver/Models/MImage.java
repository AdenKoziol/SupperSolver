package supperSolver.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_image")
public class MImage
{

    public MImage(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    public int getID(){
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Column(name = "imgUrl")
    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Column(name = "imagePos")
    private int imagePos;

    public int getImagePos() {
        return imagePos;
    }

    public void setImagePos(int imagePos) {
        this.imagePos = imagePos;
    }

    @Column(name = "recipeID")
    private int recipeID;

    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    @Column(name = "userID")
    private int userID;

    public int getUserID(){
        return userID;
    }
    public void setUserID(int userID){
        this.userID = userID;
    }


    public MImage(int ID, String imgUrl, int imagePos, int recipeID, int userID)
    {
        this.ID = ID;
        this.imagePos = imagePos;
        this.imgUrl = imgUrl;
        this.recipeID = recipeID;
        this.userID = userID;
    }
}