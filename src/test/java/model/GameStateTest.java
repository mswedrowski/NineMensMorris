package model;

import model.enums.PlayerType;
import org.junit.Assert;
import org.junit.Test;

public class GameStateTest
{
    @Test
    public void getRandomPlayerTest()
    {
        GameState g = new GameState(PlayerType.AI,PlayerType.AI);
        Assert.assertEquals(PlayerState.class,g.getRandomPlayer().getClass());
    }
}
