import model.GameState;
import model.PlayerState;
import model.enums.Color;
import model.enums.PlayerType;
import org.junit.Assert;
import org.junit.Test;

public class AI_Test
{
    @Test
    public void canFormMillTest()
    {
        AI ai = new AI();
        GameState gameState = new GameState(PlayerType.AI,PlayerType.AI);

        gameState.getPosition(0).setPositionColor(Color.BLACK);
        gameState.getPosition(1).setPositionColor(Color.BLACK);
        gameState.setTurnOfPlayer(gameState.getPlayerBlack());

        Assert.assertTrue(ai.canFormMill(2,gameState));
    }

    @Test
    public void deepCloneFieldsTest()
    {
        GameState gameStateToCopy = new GameState(PlayerType.AI,PlayerType.AI);
        GameState gameStateCopied = (GameState) AI.deepClone(gameStateToCopy);
        Assert.assertEquals(gameStateCopied.currentPlayer().getColorOfPlayer(),gameStateToCopy.currentPlayer().getColorOfPlayer());
    }

    @Test
    public void deepCloneReferenceTest()
    {
        GameState gameStateToCopy = new GameState(PlayerType.AI,PlayerType.AI);
        GameState gameStateCopied = (GameState) AI.deepClone(gameStateToCopy);
        Assert.assertFalse(gameStateCopied == gameStateToCopy);
    }

    @Test
    public void evaluationTest()
    {
        GameState gameState = new GameState(PlayerType.AI,PlayerType.AI);

        gameState.getPlayerBlack().putPieceOnBoard(gameState.getPosition(2));

        Assert.assertEquals(1,AI.getEvaluation(gameState,Color.BLACK));

        Assert.assertEquals(-1,AI.getEvaluation(gameState,Color.WHITE));
    }


}
