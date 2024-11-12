package supperSolver.WebSockets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import supperSolver.Controllers.RecipeController;
import supperSolver.Models.MRecipe;
import supperSolver.Models.MUser;
import supperSolver.Repositories.RUser;
import supperSolver.Controllers.ImageController;
import supperSolver.Models.MImage;

@Controller
@ServerEndpoint("/wshomefeed/{userID}")
public class HomeFeedWebSocket
{
    private static RecipeController recipeController;
    private static RUser rUser;
    private static ImageController imageController;

    @Autowired
    public void setRecipeController(RecipeController recipeController)
    {
        this.recipeController = recipeController;
    }

    @Autowired
    public void setUserRepository(RUser rUser)
    {
        this.rUser = rUser;
    }

    @Autowired
    public void setImageController(ImageController imageController)
    {
        this.imageController = imageController;
    }

    private static final Logger logger = LoggerFactory.getLogger(HomeFeedWebSocket.class);
    private static final Map<Integer, List<MRecipe>> userRecipes = new Hashtable<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("userID") int userID) throws IOException
    {
        logger.info("[onOpen] Connection opened for userID: " + userID);
        userRecipes.putIfAbsent(userID, new ArrayList<>());
        nextRecipe(userID, session);
    }

    @OnMessage
    public void onMessage(String message, Session session, @PathParam("userID") int userID) throws IOException
    {
        logger.info("[onMessage] Message from userID " + userID + ": " + message);

        if ("scrolled".equals(message))
            nextRecipe(userID, session);
    }

    @OnClose
    public void onClose(Session session, @PathParam("userID") int userID)
    {
        logger.info("[onClose] Connection closed for userID: " + userID);
        userRecipes.remove(userID);
    }

    // Helper method to fetch more recipes for the user
    private void nextRecipe(int userID, Session session) throws IOException
    {
        List<MRecipe> recipes = userRecipes.get(userID);

        MRecipe recipe = recipeController.recipeAlgorithm(userID, recipes);
        sendRecipe(session, recipe);

        recipes.add(recipe);
        userRecipes.put(userID, recipes);
    }

    // Helper method to send recipe to the client
    private void sendRecipe(Session session, MRecipe recipe) throws IOException
    {
        if(recipe == null)
            session.getBasicRemote().sendText("No more recipes in database");
        else
        {
            String recipesJson = convertRecipesToJson(recipe);
            String userJson = convertUserToJson(recipe.getUser());
            String imageURL = getRecipeImage(recipe);
            session.getBasicRemote().sendText(recipesJson);
        }
    }

    // Helper method to convert the list of recipes to JSON
    private String convertRecipesToJson(MRecipe recipe) throws IOException
    {
        StringBuilder json = new StringBuilder();

        json.append("{");
        json.append("\"ID\":").append(recipe.getID()).append(",");
        json.append("\"name\":\"").append(recipe.getName()).append("\",");
        json.append("\"description\":\"").append(recipe.getDescription()).append("\",");
        json.append("\"user\":").append(convertUserToJson(recipe.getUser())).append(",");
        json.append("\"imgURL\":\"").append(getRecipeImage(recipe)).append("\"");
        json.append("}");

        return json.toString();
    }

    // Helper method to convert user to JSON
    private String convertUserToJson(MUser user)
    {
        StringBuilder json = new StringBuilder();

        json.append("{");
        json.append("\"ID\": ").append(user.getID()).append(",");
        json.append("\"name\": \"").append(user.getName()).append("\",");
        json.append("\"username\": \"").append(user.getUsername()).append("\",");
        json.append("\"password\": \"").append(user.getPassword()).append("\",");
        json.append("\"isAdmin\": ").append(user.getIsAdmin()).append(",");
        json.append("\"isAdvertiser\": ").append(user.getIsAdvertiser()).append(",");
        json.append("\"bio\": \"").append(user.getBio()).append("\",");
        json.append("\"imageID\": ").append(user.getImage()).append(",");
        json.append("\"groupID\": ").append(user.getGroupID());
        json.append("}");

        return json.toString();
    }

    // Helper method to get the front image for a recipe
    private String getRecipeImage(MRecipe recipe)
    {
        List<MImage> images = imageController.findByRecipe(recipe.getID());

        for(MImage image : images)
        {
            if(image.getImagePos() == 0)
                return image.getImgUrl();
        }
        return "";
    }
}
