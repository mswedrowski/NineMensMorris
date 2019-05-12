package model;

import model.enums.Color;

public class Position
{
    private int x;
    private int y;
    private Color positionColor;

    public Position(int x, int y)
    {
        this.x = x;
        this.y = y;
        positionColor = Color.NONE;
    }

    public boolean isEmpty()
    {
        return (positionColor == Color.NONE);
    }

    public Color getPositionColor()
    {
        return positionColor;
    }

    public String getPositionColorAsString()
    {
        if(positionColor == Color.BLACK)
        {
            return "B";
        }
        else if (positionColor == Color.WHITE)
        {
            return "W";
        }
        else
        {
            return "[]";
        }
    }

    public void setPositionColor(Color positionColor) {
        this.positionColor = positionColor;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Position: "+x + " " + y;
    }
}
