import model.GameState;
import model.Position;
import model.enums.Color;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class AI
{
    // NEED TO REFACTOR THIS GARBAGE
    private HashMap<Integer, Integer> boardPositionToArrayPosition;
    public AI()
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
    public static Object deepClone(Object object)
    {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean canFormMill(int potentialPosition, GameState gameState)
    {
        return 0 < BoardInfo.getPotentialMills(gameState,potentialPosition);
    }

    public ArrayList<Position> getAllNonOccupiedPositions(GameState gameState)
    {
        return (ArrayList<Position>) gameState.getAllPositions().stream().filter(Position::isEmpty).collect(Collectors.toList());
    }

    public ArrayList<GameState> addPiece(GameState gameState)
    {
        ArrayList<GameState> possibleMoves = new ArrayList<>();
        ArrayList<Position> positions = getAllNonOccupiedPositions(gameState);

        for(int i=0; i<positions.size(); i++ )
        {
            GameState newPossibleMove = (GameState) AI.deepClone(gameState);

            // TO REFACTOR
            newPossibleMove.getPosition(i).setPositionColor(gameState.currentPlayer().getColorOfPlayer());
            int numberOfMills = BoardInfo.getPotentialMills(gameState,positions.get(i).getPositionAsArray());

            if(numberOfMills > 0)
            {
                possibleMoves = removePiece(newPossibleMove,possibleMoves);
            }
            else
            {
                possibleMoves.add(newPossibleMove);
            }
        }
        return possibleMoves;
    }

    public ArrayList<GameState> addPiecesMovingPhase(GameState gameState)
    {
        ArrayList<GameState> possibleMovesArray =new ArrayList<>();

        for(int moveFromIndex=0 ;moveFromIndex< gameState.getAllPositions().size();moveFromIndex++)
        {
            if(gameState.getPosition(moveFromIndex).getPositionColor() == gameState.currentPlayer().getColorOfPlayer())
            {
                ArrayList<Integer> neighboursPositions = BoardInfo.getNeighboursOfPosition(moveFromIndex);

                for(Integer moveToIndex : neighboursPositions)
                {
                    if(gameState.getPosition(moveToIndex).isEmpty())
                    {
                        GameState possibleMove = (GameState) AI.deepClone(gameState);
                        possibleMove.getPosition(moveFromIndex).setPositionColor(Color.NONE);
                        possibleMove.getPosition(moveToIndex).setPositionColor(gameState.currentPlayer().getColorOfPlayer());

                        if(BoardInfo.getMills(possibleMove,moveToIndex) > 0)
                        {
                            possibleMovesArray = removePiece(possibleMove, possibleMovesArray);
                        }
                        else
                        {
                            possibleMovesArray.add(possibleMove);
                        }

                    }

                    /* add this from HumanIO later on
                    if(player.getPreviousPosition() != positionToMove)
                    {
                        player.setPreviousPosition(positionMoveFrom);

                        properPositionMoveTo = true;
                        positionMoveFrom.setPositionColor(Color.NONE);
                        positionToMove.setPositionColor(player.getColorOfPlayer());

                     */
                    }
                }
            }
        return possibleMovesArray;
        }


    public ArrayList<GameState> addPiecesThreePiecesLeft(GameState gameState)
    {
        ArrayList<GameState> possibleMovesArray =new ArrayList<>();

        for(int moveFromIndex=0; moveFromIndex < gameState.getAllPositions().size();moveFromIndex++)
        {
            if(gameState.getPosition(moveFromIndex).getPositionColor() == gameState.currentPlayer().getColorOfPlayer())
            {
                ArrayList<Position> positionsToMove = getAllNonOccupiedPositions(gameState);

                for(Position position : positionsToMove)
                {
                    int moveToIndex = position.getPositionAsArray();

                    GameState possibleMove = (GameState) AI.deepClone(gameState);
                    possibleMove.getPosition(moveFromIndex).setPositionColor(Color.NONE);
                    possibleMove.getPosition(moveToIndex).setPositionColor(gameState.currentPlayer().getColorOfPlayer());

                    if(BoardInfo.getMills(possibleMove,moveToIndex) > 0)
                    {
                        possibleMovesArray = removePiece(possibleMove,possibleMovesArray);
                    }
                    else
                    {
                        possibleMovesArray.add(possibleMove);
                    }
                }
            }
        }

        ArrayList<Position> nonOccupiedPositions = getAllNonOccupiedPositions(gameState);


        return possibleMovesArray;
    }



    public ArrayList<GameState> removePiece(GameState newGameState,ArrayList<GameState> possibleMoves)
    {
        for(int i = 0; i< newGameState.getAllPositions().size(); i++)
        {
            if(BoardInfo.canRemove(newGameState,i))
            {
                if (BoardInfo.getMills(newGameState, i) > 0)
                {
                    GameState possibleRemovalState = (GameState) AI.deepClone(newGameState);
                    possibleRemovalState.getPosition(i).setPositionColor(Color.NONE);
                    possibleMoves.add(possibleRemovalState);
                }
            }
        }
        return possibleMoves;
    }

    public ArrayList<GameState> placeNewPieceAI(GameState gamestate)
    {
        return addPiece(gamestate);
    }
}



