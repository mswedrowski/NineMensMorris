import model.GameState;
import model.PlayerState;
import model.Position;
import model.enums.Color;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class HumanIO
{
    //TODO: move to boardinfo?
    private HashMap<Integer, Integer> boardPositionToArrayPosition;

   HumanIO()
   {
       boardPositionToArrayPosition = new HashMap<>();
       boardPositionToArrayPosition.put(0,0);
       boardPositionToArrayPosition.put(3,1);
       boardPositionToArrayPosition.put(6,2);
       boardPositionToArrayPosition.put(11,3);
       boardPositionToArrayPosition.put(13,4);
       boardPositionToArrayPosition.put(15,5);
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

    public int placeNewPieceIO(GameState gameState) throws IOException
    {
       boolean isCorrectInput =false;

       PlayerState currentPlayer = gameState.currentPlayer();

       System.out.println(gameState.toString());

       System.out.println("Move of " + currentPlayer.toString());

       int requestedArrayPosition = -1;

       while (!isCorrectInput)
       {
            try
            {
                System.out.println("\nType where do you want to place piece");
                Scanner input = new Scanner(System.in);

                int requestedField = input.nextInt();
                requestedArrayPosition = boardPositionToArrayPosition.get(requestedField);
                Position positionToChange= gameState.getAllPositions().get(requestedArrayPosition);

                if (positionToChange.isEmpty())
                {
                    currentPlayer.putPieceOnBoard(positionToChange);

                    int numberOfMills = BoardInfo.getMills(gameState,requestedArrayPosition);
                    for(int i=0;i<numberOfMills;i++)
                    {
                        removePieceIO(gameState);
                    }

                    isCorrectInput = true;
                }
                else
                    {
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


    public int removePieceIO(GameState gameState)
    {
        //PlayerState playerWhoIsRemoving = gameState.currentPlayer();

        boolean isCorrectInput =false;
        int requestedPositionInArray = -1;

        while(!isCorrectInput)
        {
            try
            {
                System.out.println(gameState);
                System.out.println("Type position of piece which you'd like to remove");
                Scanner input = new Scanner(System.in);

                int requestedField = input.nextInt();
                requestedPositionInArray = boardPositionToArrayPosition.get(requestedField);

                if (BoardInfo.canRemove(gameState, requestedPositionInArray)) {
                    gameState.getPosition(requestedPositionInArray).setPositionColor(Color.NONE);
                    isCorrectInput = true;
                }
                else
                    {
                    System.out.println("Wrong position!");
                }
            }
            catch(Exception e)
            {
                System.out.println("\n Wrong input during removal!");
            }
        }
        return requestedPositionInArray;
    }

    public int  movePieceIO(GameState gameState, PlayerState player)
    {
        boolean isCorrectInput =false;
        int requestedPositionMoveFromInArray = -1;

        while(!isCorrectInput)
        {
            boolean properPositionMoveFrom = false;

            while(!properPositionMoveFrom)
            {
                try
                {
                    System.out.println(gameState);

                    System.out.println("Move of " + player.toString());
                    System.out.println("Which piece would you like to move?");
                    Scanner input = new Scanner(System.in);

                    int requestedField = input.nextInt();
                    requestedPositionMoveFromInArray = boardPositionToArrayPosition.get(requestedField);
                    Position positionMoveFrom = gameState.getAllPositions().get(requestedPositionMoveFromInArray);

                    if (positionMoveFrom.getPositionColor() == player.getColorOfPlayer())
                    {
                        gameState.getPosition(requestedPositionMoveFromInArray).setPositionColor(Color.NONE);
                        properPositionMoveFrom = true;


                        boolean properPositionMoveTo = false;

                        while (!properPositionMoveTo)
                        {
                            try
                            {
                                int requestedPositionToMoveInArray = 1;
                                //Scanner input = new Scanner(System.in);
                                int requestedFieldToMove = input.nextInt();

                                requestedPositionToMoveInArray = boardPositionToArrayPosition.get(requestedFieldToMove);
                                Position positionToMove = gameState.getAllPositions().get(requestedPositionToMoveInArray);

                                System.out.println("Where do you want to move?");
                                if(positionToMove.getPositionColor() == Color.NONE && BoardInfo.isNeighbour(requestedPositionToMoveInArray,requestedPositionMoveFromInArray))
                                {
                                    if(player.getPreviousPosition() != positionToMove)
                                    {
                                        player.setPreviousPosition(positionMoveFrom);

                                        properPositionMoveTo = true;
                                        positionMoveFrom.setPositionColor(Color.NONE);
                                        positionToMove.setPositionColor(player.getColorOfPlayer());
                                    }
                                }

                            } catch (Exception e) {
                                System.out.println("Cannot move to this position");
                            }
                        }

                    }
                    else
                        {
                        System.out.println("Pick proper position!");
                    }
                }
                catch(Exception e)
                {
                    System.out.println("\n Wrong input!");
                }
            }
            System.out.println("Pick position where you want to move");



        }
        return requestedPositionMoveFromInArray;
    }
}
