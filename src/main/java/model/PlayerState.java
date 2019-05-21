package model;

import model.enums.AlgorithmType;
import model.enums.Color;
import model.enums.HeuristicType;
import model.enums.PlayerType;

import java.io.Serializable;

public class PlayerState implements Serializable
{

    private Color colorOfPlayer;
    private int piecesOnBoard;
    private int piecesInDrawer;
    private int piecesLost;
    private Position previousPosition;
    private PlayerType playerType;
    private AlgorithmType algorithmType;
    private HeuristicType heuristicType;

    PlayerState(Color colorOfPlayer, PlayerType playerType)
    {
        this.piecesInDrawer = 9;
        this.piecesOnBoard = 0;
        this.piecesLost = 0;
        this.colorOfPlayer = colorOfPlayer;
        this.playerType = playerType;
        this.previousPosition = new Position(-1,-1);

        if(this.playerType == PlayerType.HUMAN)
        {
            algorithmType = AlgorithmType.PLAYER;
            heuristicType = HeuristicType.PLAYER;
        }

        else
        {
            algorithmType = AlgorithmType.ALPHABETA;
            heuristicType = HeuristicType.PieceCount;
        }
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

    public AlgorithmType getAlgorithmType() {
        return algorithmType;
    }

    public void setAlgorithmType(AlgorithmType algorithmType) {
        this.algorithmType = algorithmType;
    }

    public HeuristicType getHeuristicType() {
        return heuristicType;
    }

    public void setHeuristicType(HeuristicType heuristicType) {
        this.heuristicType = heuristicType;
    }

    public void putPieceOnBoard(Position position)
    {
        position.setPositionColor(this.getColorOfPlayer());
        setPiecesInDrawer(piecesInDrawer - 1);
        setPiecesOnBoard(piecesOnBoard + 1);
    }

    public void getsPieceRemoved(Position position)
    {
        position.setPositionColor(Color.NONE);
        setPiecesLost(piecesLost + 1);
        setPiecesOnBoard(piecesOnBoard - 1);
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

    public int getPiecesLost() {
        return piecesLost;
    }

    public void setPiecesLost(int piecesLost) {
        this.piecesLost = piecesLost;
    }

    @Override
    public String toString() {
        return (colorOfPlayer==Color.BLACK)?"Player: Black":"Player: White";
    }
}
