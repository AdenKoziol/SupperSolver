package supperSolver.DataTransferObjects;

public class IngredientDTO
{
    private int userID;
    private int recipeID;
    private String name;
    private double quantity;

    public int getUserID() { return userID; }
    public int getRecipeID() { return recipeID; }
    public String getName() { return name; }
    public double getQuantity() { return quantity; }
}
