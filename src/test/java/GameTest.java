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

        Assert.assertEquals(gs.getAllPositions(),g.getAllNonOccupiedPositions(gs));
    }

    @Test
    public void getAllNonOccupiedPositionsModifyTest()
    {
        Game g = new Game(PlayerType.AI,PlayerType.AI);
        GameState gs =  new GameState(PlayerType.AI,PlayerType.AI);
        gs.getAllPositions().get(3).setPositionColor(Color.BLACK);

        System.out.println(gs.getAllPositions());

        Assert.assertFalse(gs.getAllPositions() == g.getAllNonOccupiedPositions(gs));
    }
}
