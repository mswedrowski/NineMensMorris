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
        gameState.getPlayerBlack().putPieceOnBoard(gameState.getPosition(1));
        gameState.getPlayerBlack().putPieceOnBoard(gameState.getPosition(0));
        gameState.setTurnOfPlayer(gameState.getPlayerBlack());
        System.out.println(gameState);

        Assert.assertEquals(6,AI.getEvaluation(gameState,Color.BLACK));

        Assert.assertEquals(-3,AI.getEvaluation(gameState,Color.WHITE));
    }

    @Test
    public void millCounterTest()
    {
        GameState gs = new GameState(PlayerType.AI,PlayerType.AI);

        gs.getPosition(0).setPositionColor(Color.BLACK);
        gs.getPosition(1).setPositionColor(Color.BLACK);
        gs.getPosition(2).setPositionColor(Color.BLACK);

        gs.getPosition(16).setPositionColor(Color.BLACK);
        gs.getPosition(19).setPositionColor(Color.BLACK);
        gs.getPosition(22).setPositionColor(Color.BLACK);

        gs.getPosition(2).setPositionColor(Color.BLACK);



        Assert.assertEquals(6,AI.getPotentialMillCount(gs,Color.BLACK));
    }

    @Test
    public void checkChangeTurnOfPlayer()
    {
        GameState gs  = new GameState(PlayerType.AI,PlayerType.AI);

        //Assert.assertEquals(gs.currentPlayer().getColorOfPlayer(),MinMax.minMaxAlgorithm(gs,2).currentPlayer().getColorOfPlayer());
    }


    @Test
    public void removeIOTest()
    {

    }


}
