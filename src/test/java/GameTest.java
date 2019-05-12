import model.GameState;
import model.enums.Color;
import model.enums.PlayerType;
import org.junit.Assert;
import org.junit.Test;


public class GameTest
{
    @Test
    public void getAllNonOccupiedPositionsTest()
    {
        Game g = new Game(PlayerType.AI,PlayerType.AI);
        GameState gs =  new GameState(PlayerType.AI,PlayerType.AI);

        Assert.assertEquals(gs.getPositions(),g.getAllNonOccupiedPositions(gs));
    }

    @Test
    public void getAllNonOccupiedPositionsModifyTest()
    {
        Game g = new Game(PlayerType.AI,PlayerType.AI);
        GameState gs =  new GameState(PlayerType.AI,PlayerType.AI);
        gs.getPositions().get(3).setPositionColor(Color.BLACK);

        System.out.println(gs.getPositions());

        Assert.assertFalse(gs.getPositions() == g.getAllNonOccupiedPositions(gs));
    }
}
