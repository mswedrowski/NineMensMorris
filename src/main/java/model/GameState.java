package model;

import model.enums.Color;
import model.enums.Phase;
import model.enums.PlayerType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class GameState implements Serializable
{
    private static int BOARD_SIZE = 7;

    private ArrayList<Position> positions;
    private PlayerState turnOfPlayer;
    private Phase phase;


    private PlayerState playerBlack;
    private PlayerState playerWhite;

    public GameState(PlayerType playerBlackType, PlayerType playerWhiteType)
    {
        positions = new ArrayList<>();

        for (int x = 0; x < BOARD_SIZE; ++x)
        {
            for (int y = 0; y < BOARD_SIZE; ++y)
            {
                if ((Math.abs(x - 3) == Math.abs(y - 3) || x == 3 || y == 3) && !(x == 3 && y == 3))
                {
                    positions.add(new Position(x,y));
                }
            }
        }

        playerBlack = new PlayerState(Color.BLACK,playerBlackType);
        playerWhite = new PlayerState(Color.WHITE,playerWhiteType);

        phase = Phase.PLACE_PIECES;
        turnOfPlayer =  getRandomPlayer();
    }

    public void changePlayer()
    {
        if(turnOfPlayer == playerBlack)
        {
            turnOfPlayer = playerWhite;
        }
        else
        {
            turnOfPlayer = playerBlack;
        }
    }


    public PlayerState getRandomPlayer()
    {
        return (new Random().nextInt(2) == 1) ? playerWhite : playerBlack;
    }

    public PlayerState currentPlayer() {
        return turnOfPlayer;
    }

    public void setTurnOfPlayer(PlayerState turnOfPlayer) {
        this.turnOfPlayer = turnOfPlayer;
    }

    // check if i can del later on??
    public void changeTurnOfPlayer(PlayerState player)
    {
        if(player.getColorOfPlayer() == Color.BLACK)
        {
            this.turnOfPlayer = playerWhite;
        }
        else
        {
            this.turnOfPlayer = playerBlack;
        }
    }

    public ArrayList<Position> getAllPositions() {
        return positions;
    }

    public void setPositions(ArrayList<Position> positions)
    {
        this.positions = positions;
    }

    public Position getPosition(int index)
    {
        return this.positions.get(index);
    }

    public Phase getPhase()
    {
        return phase;
    }

    public void setPhase(Phase phase)
    {
        this.phase = phase;
    }



    @Override
    public String toString()
    {


        String sb = positions.get(0).getPositionColorAsString() + "(00)----------------------" + positions.get(1).getPositionColorAsString() + "(03)----------------------" + positions.get(2).getPositionColorAsString() + "(06)\n" +
                "|                           |                           |\n" +
                "|                           |                           |\n" +
                "|       " + positions.get(3).getPositionColorAsString() + "(11)--------------" + positions.get(4).getPositionColorAsString() + "(13)--------------" + positions.get(5).getPositionColorAsString() + "(15)  |\n" +
                "|       |                   |                    |      |\n" +
                "|       |                   |                    |      |\n" +
                "|       |        " + positions.get(6).getPositionColorAsString() + "(22)-----" + positions.get(7).getPositionColorAsString() + "(23)-----" + positions.get(8).getPositionColorAsString() + "(24)    |      |\n" +
                "|       |         |                   |          |      |\n" +
                "|       |         |                   |          |      |\n" +
                positions.get(9).getPositionColorAsString() + "(30)--" + positions.get(10).getPositionColorAsString() + "(31)----" + positions.get(11).getPositionColorAsString() + "(32)              " + positions.get(12).getPositionColorAsString() + "(34)----" + positions.get(13).getPositionColorAsString() + "(35)---" + positions.get(14).getPositionColorAsString() + "(36)\n" +
                "|       |         |                   |          |      |\n" +
                "|       |         |                   |          |      |\n" +
                "|       |        " + positions.get(15).getPositionColorAsString() + "(42)-----" + positions.get(16).getPositionColorAsString() + "(43)-----" + positions.get(17).getPositionColorAsString() + "(44)    |      |\n" +
                "|       |                   |                    |      |\n" +
                "|       |                   |                    |      |\n" +
                "|       " + positions.get(18).getPositionColorAsString() + "(51)--------------" + positions.get(19).getPositionColorAsString() + "(53)--------------" + positions.get(20).getPositionColorAsString() + "(55)  |\n" +
                "|                           |                           |\n" +
                "|                           |                           |\n" +
                positions.get(21).getPositionColorAsString() + "(60)----------------------" + positions.get(22).getPositionColorAsString() + "(63)----------------------" + positions.get(23).getPositionColorAsString() + "(66)\n";
        return sb;
    }

    public PlayerState getPlayerBlack() {
        return playerBlack;
    }

    public void setPlayerBlack(PlayerState playerBlack) {
        this.playerBlack = playerBlack;
    }

    public PlayerState getPlayerWhite() {
        return playerWhite;
    }

    public void setPlayerWhite(PlayerState playerWhite) {
        this.playerWhite = playerWhite;
    }
}