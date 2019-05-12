import model.GameState;
import model.PlayerState;
import model.Position;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class HumanIO
{
    private HashMap<Integer, Integer> boardPositionToArrayPosition;

   HumanIO()
   {
       boardPositionToArrayPosition = new HashMap<>();
       boardPositionToArrayPosition.put(0,0);
       boardPositionToArrayPosition.put(3,1);
       boardPositionToArrayPosition.put(6,2);
       boardPositionToArrayPosition.put(11,3);
       boardPositionToArrayPosition.put(13,4);
       boardPositionToArrayPosition.put(16,5);
       boardPositionToArrayPosition.put(22,6);
       boardPositionToArrayPosition.put(23,7);
       boardPositionToArrayPosition.put(24,8);
       boardPositionToArrayPosition.put(30,9);
       boardPositionToArrayPosition.put(31,10);
       boardPositionToArrayPosition.put(32,11);
       boardPositionToArrayPosition.put(34,12);
       boardPositionToArrayPosition.put(35,13);
       boardPositionToArrayPosition.put(36,14);
       boardPositionToArrayPosition.put(42,15);
       boardPositionToArrayPosition.put(43,16);
       boardPositionToArrayPosition.put(44,17);
       boardPositionToArrayPosition.put(51,18);
       boardPositionToArrayPosition.put(53,19);
       boardPositionToArrayPosition.put(55,20);
       boardPositionToArrayPosition.put(60,21);
       boardPositionToArrayPosition.put(63,22);
       boardPositionToArrayPosition.put(66,23);


   }

    public  int placeNewPiece(GameState gameState) throws IOException
    {

       boolean properIntput =false;

       PlayerState currentPlayer = gameState.getTurnOfPlayer();

       System.out.println(gameState.toString());

       System.out.println("Move of " + currentPlayer.toString());

       int requestedArrayPosition = -1;

       while (!properIntput)
       {
            try
            {


                System.out.println("\n Type where do you want to place piece");

                Scanner input = new Scanner(System.in);
                int requestedField = input.nextInt();
                System.out.println(requestedField);
                System.out.println(boardPositionToArrayPosition.get(60));
                requestedArrayPosition = boardPositionToArrayPosition.get(requestedField);

                Position positionToChange= gameState.getPositions().get(requestedArrayPosition);

                if (positionToChange.isEmpty())
                {

                    currentPlayer.putPieceOnBoard(positionToChange);
                    properIntput = true;

                }
                else{
                    System.out.println("\n Field is occupied! Pick another");
                }
            }
            catch(Exception e)
            {
                System.out.println("\n Wrong input!");
            }
        }
        return requestedArrayPosition;
    }


}
