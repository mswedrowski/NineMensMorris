// Just hardcoding cuz I got no time

import model.GameState;
import model.enums.Color;
import java.util.ArrayList;

public class BoardInfo
{


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
    public static ArrayList<Integer> getHorizonalMill(int position)
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

        Color expectedColor = gameState.getPosition(positions.get(0)).getPositionColor();

        if(expectedColor == Color.NONE)
        {
            return false;
        }

        for (Integer indexOfPosition: positions)
        {
            if(gameState.getPositions().get(indexOfPosition).getPositionColor() != expectedColor)
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

        if(checkMillOnPositions(gameState,getHorizonalMill(positionToCheck)))
        {
            countedMills++;
        }

        if(checkMillOnPositions(gameState,getVerticalMill(positionToCheck)))
        {
            countedMills++;
        }

        return countedMills;
    }

}
