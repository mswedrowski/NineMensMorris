package model;

import org.junit.Assert;
import org.junit.Test;

public class PositionTest
{
    @Test
    public void positionEqualsTest()
    {
        Position pos11 = new Position(1,1);
        Position pos11Too = new Position(1,1);
        Assert.assertTrue(pos11.equals(pos11Too));
    }
}
