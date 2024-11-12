package supperSolver.Controllers;

import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import supperSolver.Models.*;
import supperSolver.Repositories.RComment;
import supperSolver.Repositories.RIngredient;
import supperSolver.Repositories.RUser;
import supperSolver.Repositories.RImage;
import supperSolver.Repositories.RFriend;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController
{
    @Autowired
    private RUser rUser;

    @Autowired
    private RImage rImage;

    @Autowired
    RFriend rFriend;

    // Gets all users
    @GetMapping("users")
    public List<MUser> getUsers()
    {
        return rUser.findAll();
    }

    // Gets a MUser object by their ID
    @GetMapping("/users/{ID}")
    public Optional<MUser> GetUserByID(@PathVariable int ID)
    {
        return rUser.findById(ID);
    }

    // Gets a user's profile picture by ID
    @GetMapping("users/image/{ID}")
    public MImage GetUserImageByID(@PathVariable int ID)
    {
        MUser user = rUser.findById(ID).get();
        return rImage.findById(user.getImage()).get();
    }

    // Get userID by username and password
    @GetMapping("/userID/{username}/{password}")
    public String GetUserID(@PathVariable String username, @PathVariable String password)
    {
        Optional<MUser> user = rUser.findByUsernameAndPassword(username, password);
        return user.map(mUser -> Integer.toString(mUser.getID())).orElse("-1");
    }

    // Returns users that have a name like username
    @GetMapping("/users/searchByUsername/{username}")
    public List<MUser> searchByUsername(@PathVariable String username)
    {
        return rUser.findByUsernameLike(username);
    }

    // Returns friends that have a name like username
    @GetMapping("/users/searchByFriends/{userID}/{username}")
    public List<MUser> searchByFriends(@PathVariable int userID, @PathVariable String username)
    {
        List<MUser> totalSearch = rUser.findByUsernameLike(username);
        List<MUser> totalFriends = getFriendsByID(userID);

        //only contains elements that are also in friends
        totalSearch.retainAll(totalFriends);

        return totalSearch;
    }

    // Gets all friends by userID
    @GetMapping("users/getFriends/{ID}")
    public List<MUser> getFriendsByID(@PathVariable int ID)
    {
        MUser user = rUser.findById(ID).get();
        return rFriend.findFriends(user);
    }

    // Add friend by userID
    @PostMapping("/users/addFriend/{ID}/{friendID}")
    public void addFriend(@PathVariable int ID, @PathVariable int friendID)
    {
        MUser user = rUser.findById(ID).get();
        MUser friend = rUser.findById(friendID).get();

        MFriend addFriend = new MFriend(user, friend);
        rFriend.save(addFriend);
    }

    // Creates a new MUser
    @PostMapping("/users")
    public @ResponseBody MUser createUser(@RequestBody MUser r)
    {
        //need to check for duplicate usernames if its duplicate throw exception
        if(rUser.findByUsername(r.getUsername()).isPresent()){
            throw new IllegalArgumentException("Duplicate username: " + r.getUsername() + " please make a unique username!");
        }
        else {
            return rUser.save(r);
        }
    }

    // Updates an existing user
    @PutMapping(path = "/users/{ID}")
    public ResponseEntity<MUser> updateUser(@PathVariable int ID, @RequestBody MUser user)
    {
        if (rUser.findByUsername(user.getUsername()).isPresent())
            throw new IllegalArgumentException("Duplicate username: " + user.getUsername() + " please try a unique username");
        else
        {
            MUser existingUser = rUser.findById(ID).get();

            existingUser.setIsAdmin(user.getIsAdmin());
            existingUser.setIsAdvertiser(user.getIsAdvertiser());
            existingUser.setName(user.getName());
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            existingUser.setBio(user.getBio());
            existingUser.setImage(user.getImage());

            rUser.save(existingUser);
            return ResponseEntity.ok(existingUser);
        }
    }

    // User login
    @PostMapping("/login")
    public MUser login(@RequestBody MLoginRequest loginRequest)
    {
        // Try to find the user by username and password
        Optional<MUser> user = rUser.findByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());

        if (user.isPresent())
        {
            // Credentials are valid, return success response
            return user.get();
        }
        else
        {
            // Invalid credentials, return error response
            return null;
        }
    }

    // Deletes a MUser by its ID
    @DeleteMapping("/users/{ID}")
    public void DeleteUser(@PathVariable int ID)
    {
        rUser.deleteById(ID);
    }
}