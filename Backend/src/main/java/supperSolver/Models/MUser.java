package supperSolver.Models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_user")
public class MUser
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "isadmin")
    private boolean isAdmin;

    @Column(name = "isadvertiser")
    private boolean isAdvertiser;

    @Column(name = "bio")
    private String bio;

    @Column(name = "imageID")
    private int imageID;

    @Column(name = "groupID")
    private int groupID;

    public MUser() { }

    public MUser(int ID, String name, String username, String password, boolean isAdmin, boolean isAdvertiser, String bio, int imageID, int groupID)
    {
        this.ID = ID;
        this.name = name;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.isAdvertiser = isAdvertiser;
        this.bio = bio;
        this.imageID = imageID;
        this.groupID = groupID;
    }

    public int getID(){
        return ID;
    }

    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public boolean getIsAdmin(){
        return isAdmin;
    }
    public void setIsAdmin(boolean admin){
        this.isAdmin = admin;
    }

    public boolean getIsAdvertiser(){
        return isAdvertiser;
    }
    public void setIsAdvertiser(boolean advertiser) {
        isAdvertiser = advertiser;
    }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public int getImage() { return imageID; }
    public void setImage(int imageID) { this.imageID = imageID; }

    public int getGroupID() { return groupID; }
    public void setGroupID(int groupID) { this.groupID = groupID; }
}