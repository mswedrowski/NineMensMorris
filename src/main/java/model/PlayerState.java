package model;

import model.enums.Color;
import model.enums.PlayerType;

import java.io.Serializable;

public class PlayerState implements Serializable
{

    private Color colorOfPlayer;
    private int piecesOnBoard;
    private int piecesInDrawer;
    private int score;
    private Position previousPosition;
    private PlayerType playerType;

    PlayerState(Color colorOfPlayer, PlayerType playerType)
    {
        this.score = 0;
        this.piecesInDrawer = 9;
        this.piecesOnBoard = 0;
        this.colorOfPlayer = colorOfPlayer;
        this.playerType = playerType;
    }

    public Position getPreviousPosition()
    {
        return previousPosition;
    }

    public void setPreviousPosition(Position previousPosition)
    {
        this.previousPosition = previousPosition;
    }

    public Color getColorOfPlayer() {
        return colorOfPlayer;
    }

    public PlayerType getPlayerType()
    {
        return playerType;
    }

    public void putPieceOnBoard(Position position)
    {
        position.setPositionColor(this.getColorOfPlayer());
        setPiecesInDrawer(piecesInDrawer - 1);
        setPiecesOnBoard(piecesOnBoard + 1);
    }

    public int getPiecesOnBoard() {
        return piecesOnBoard;
    }

    public void setPiecesOnBoard(int piecesOnBoard) {
        this.piecesOnBoard = piecesOnBoard;
    }

    public int getPiecesInDrawer() {
        return piecesInDrawer;
    }

    public void setPiecesInDrawer(int piecesInDrawer) {
        this.piecesInDrawer = piecesInDrawer;
    }



    @Override
    public String toString() {
        return (colorOfPlayer==Color.BLACK)?"Player: Black":"Player: White";
    }
}
