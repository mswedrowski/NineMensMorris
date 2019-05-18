// Just hardcoding cuz I got no time
import model.GameState;
import model.PlayerState;
import model.enums.Color;
import model.enums.Phase;

import java.util.ArrayList;
import java.util.Arrays;

public class BoardInfo
{
    public static ArrayList<Integer> getNeighboursOfPosition(int requestedPosition)
    {
        ArrayList<Integer> neighbors = new ArrayList<Integer>();

        switch(requestedPosition)
        {
            case 0  : neighbors.addAll(Arrays.asList(1, 9)); break;
            case 1  : neighbors.addAll(Arrays.asList(0, 2, 4)); break;
            case 2  : neighbors.addAll(Arrays.asList(1, 14)); break;
            case 3  : neighbors.addAll(Arrays.asList(4,10)); break;
            case 4  : neighbors.addAll(Arrays.asList(1,3,5,7)); break;
            case 5  : neighbors.addAll(Arrays.asList(4,13)); break;
            case 6  : neighbors.addAll(Arrays.asList(7,11)); break;
            case 7  : neighbors.addAll(Arrays.asList(4,6,8)); break;
            case 8  : neighbors.addAll(Arrays.asList(7,12));break;
            case 9  : neighbors.addAll(Arrays.asList(0,9,21,10)); break;
            case 10 : neighbors.addAll(Arrays.asList(9,11,3,18)); break;
            case 11 : neighbors.addAll(Arrays.asList(6,10,15)); break;
            case 12 : neighbors.addAll(Arrays.asList(8,13,17)); break;
            case 13 : neighbors.addAll(Arrays.asList(5,12,14,20)); break;
            case 14 : neighbors.addAll(Arrays.asList(2,13,23)); break;
            case 15 : neighbors.addAll(Arrays.asList(11,16)); break;
            case 16 : neighbors.addAll(Arrays.asList(15,17,19)); break;
            case 17 : neighbors.addAll(Arrays.asList(12,16)); break;
            case 18 : neighbors.addAll(Arrays.asList(10,19)); break;
            case 19 : neighbors.addAll(Arrays.asList(16,18,20,22)); break;
            case 20 : neighbors.addAll(Arrays.asList(13,19)); break;
            case 21 : neighbors.addAll(Arrays.asList(9,22)); break;
            case 22 : neighbors.addAll(Arrays.asList(19,21,23)); break;
            case 23 : neighbors.addAll(Arrays.asList(22,14)); break;
            default : break;
        }
        return neighbors;
    }


    public static boolean isNeighbour(int positionToCheck,int neighborPosition)
    {
        System.out.println(positionToCheck);
        System.out.println(neighborPosition);
        System.out.println(getNeighboursOfPosition(positionToCheck));

        return getNeighboursOfPosition(positionToCheck).contains(neighborPosition);
    }


    public static ArrayList<Integer> getVerticalMill(int position)
    {
        ArrayList<Integer> verticalMill = new ArrayList<>();

        switch (position)
        {
            case 0 :
            case 9 :
            case 21 : verticalMill.add(0);verticalMill.add(9);verticalMill.add(21);
                break;

            case 1 :
            case 4 :
            case 7 : verticalMill.add(1);verticalMill.add(4);verticalMill.add(7);
                break;

            case 2 :
            case 14 :
            case 23 : verticalMill.add(2);verticalMill.add(14);verticalMill.add(23);
                break;

            case 3 :
            case 10 :
            case 18 : verticalMill.add(3);verticalMill.add(10);verticalMill.add(18);
                break;

            case 5 :
            case 13 :
            case 20 : verticalMill.add(5);verticalMill.add(13);verticalMill.add(20);
                break;

            case 6 :
            case 11 :
            case 15 : verticalMill.add(6);verticalMill.add(11);verticalMill.add(15);
                break;

            case 8 :
            case 12 :
            case 17 : verticalMill.add(8);verticalMill.add(12);verticalMill.add(17);
                break;

            case 16 :
            case 19 :
            case 22 : verticalMill.add(16);verticalMill.add(19);verticalMill.add(22);
                break;
        }
        return verticalMill;
    }


    // TODO : Change to modulo version
    public static ArrayList<Integer> getHorizontalMill(int position)
    {
        ArrayList<Integer> horizontalMill = new ArrayList<>();

        switch (position)
        {
            case 0:
            case 1:
            case 2:
                horizontalMill.add(1);
                horizontalMill.add(2);
                horizontalMill.add(0);
                break;

            case 3:
            case 4:
            case 5:
                horizontalMill.add(3);
                horizontalMill.add(4);
                horizontalMill.add(5);
                break;

            case 6:
            case 7:
            case 8:
                horizontalMill.add(6);
                horizontalMill.add(7);
                horizontalMill.add(8);
                break;

            case 9:
            case 10:
            case 11:
                horizontalMill.add(9);
                horizontalMill.add(10);
                horizontalMill.add(11);
                break;

            case 12:
            case 13:
            case 14:
                horizontalMill.add(12);
                horizontalMill.add(13);
                horizontalMill.add(14);
                break;

            case 15:
            case 16:
            case 17:
                horizontalMill.add(15);
                horizontalMill.add(16);
                horizontalMill.add(17);
                break;

            case 18:
            case 19:
            case 20:
                horizontalMill.add(18);
                horizontalMill.add(19);
                horizontalMill.add(20);
                break;

            case 21:
            case 22:
            case 23:
                horizontalMill.add(21);
                horizontalMill.add(22);
                horizontalMill.add(23);
                break;
        }

        return horizontalMill;
    }


    public static boolean checkMillOnPositions(GameState gameState, ArrayList<Integer> positions)
    {
        Color expectedColor = gameState.currentPlayer().getColorOfPlayer();

        if(expectedColor == Color.NONE)
        {
            return false;
        }

        for (Integer indexOfPosition: positions)
        {
            if(gameState.getAllPositions().get(indexOfPosition).getPositionColor() != expectedColor)
            {
                return false;
            }
        }
        return true;
    }

    public static boolean checkPotentialMillOnPositions(GameState gameState, ArrayList<Integer> positions)
    {
        Color expectedColor = gameState.getPosition(positions.get(0)).getPositionColor();

        if(expectedColor == Color.NONE)
        {
            return false;
        }

        for (Integer indexOfPosition: positions)
        {
            if(gameState.getAllPositions().get(indexOfPosition).getPositionColor() != expectedColor)
            {
                return false;
            }
        }
        return true;
    }



    // need to insert position in ArrayList
    public static int getMills(GameState gameState,int positionToCheck)
    {
        int countedMills = 0;

        if(checkMillOnPositions(gameState, getHorizontalMill(positionToCheck)))
        {
            countedMills++;
        }

        if(checkMillOnPositions(gameState,getVerticalMill(positionToCheck)))
        {
            countedMills++;
        }
        return countedMills;
    }


    public static int getPotentialMills(GameState gameState,int positionToCheck)
    {
        int counterPotentialMills = 0;
        ArrayList<Integer> horizontalMill = getHorizontalMill(positionToCheck);

        horizontalMill.remove( new Integer(positionToCheck));

        if(checkPotentialMillOnPositions(gameState,horizontalMill))
        {
            counterPotentialMills++;
        }

        ArrayList<Integer> verticalMill = getVerticalMill(positionToCheck);
        verticalMill.remove( new Integer(positionToCheck));

        if(checkPotentialMillOnPositions(gameState,verticalMill))
        {
            counterPotentialMills++;
        }

        return counterPotentialMills;
    }

    // move it to gamestate
    public static Color colorOfEnemy(PlayerState playerState)
    {
        if(playerState.getColorOfPlayer() == Color.BLACK)
        {
            return Color.WHITE;
        }

        return Color.BLACK;
    }

    public static boolean canRemove(GameState gameState,int requestedPositionInArray)
    {
        PlayerState playerWhoIsRemoving = gameState.currentPlayer();
        if(gameState.getPosition(requestedPositionInArray).getPositionColor() == BoardInfo.colorOfEnemy(playerWhoIsRemoving))
        {
            if(getMills(gameState,requestedPositionInArray) == 0 ||
                    playerWhoIsRemoving.getPiecesOnBoard() == 3 && gameState.getPhase() == Phase.MOVE_PIECES)
            {
                return true;
            }
        }

        return false;
    }


}
