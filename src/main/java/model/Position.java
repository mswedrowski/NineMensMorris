package model;

import model.enums.Color;

import java.io.Serializable;

public class Position implements Serializable
{
    private int x;
    private int y;
    private Color positionColor;
    private Position previousPiecePosition;

    public Position(int x, int y)
    {
        this.x = x;
        this.y = y;
        positionColor = Color.NONE;
        previousPiecePosition = null;
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

    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof Position)
        {
            Position posCP  = (Position) obj;
            return posCP.getX() == this.getX() && posCP.getY() == this.getY();
        }
        return false;
    }

    public void setPositionColor(Color positionColor) {
        this.positionColor = positionColor;
    }

    public int getX() {
        return x;
    }

    //PART OF GARBAGE
    public int getPositionBoardName(){return (Integer.parseInt(Integer.toString(x)+Integer.toString(y)));}

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    public Position getPreviousPiecePosition()
    {
        return previousPiecePosition;
    }

    public void setPreviousPiecePosition(Position previousPiecePosition)
    {
        this.previousPiecePosition = previousPiecePosition;
    }

    @Override
    public String toString() {
        return "Position: "+ x + " " + y;
    }
}
