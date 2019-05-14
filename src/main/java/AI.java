import model.GameState;
import model.PlayerState;
import model.Position;
import model.enums.Color;
import model.enums.Phase;

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

    public static ArrayList<Position> getAllNonOccupiedPositions(GameState gameState)
    {
        return (ArrayList<Position>) gameState.getAllPositions().stream().filter(Position::isEmpty).collect(Collectors.toList());
    }


    public static void playerPutingPiece(GameState gameState,int indexOfPosition)
    {
        Position toPut = gameState.getPosition(indexOfPosition);
        gameState.currentPlayer().putPieceOnBoard(toPut);
    }

    public static void playerGetsPieceRemoved(GameState gameState,int indexOfPosition)
    {
        Position toPut = gameState.getPosition(indexOfPosition);
        gameState.getEnemy().getsPieceRemoved(toPut);
    }



    public static ArrayList<GameState> addPiece(GameState gameState)
    {
        ArrayList<GameState> possibleMoves = new ArrayList<>();
        ArrayList<Position> positions = getAllNonOccupiedPositions(gameState);

        for(int i=0; i<positions.size(); i++ )
        {
            GameState newPossibleMove = (GameState) AI.deepClone(gameState);

            playerPutingPiece(newPossibleMove,i);

            int numberOfMills = BoardInfo.getPotentialMills(gameState,i);

            if(numberOfMills > 0)
            {
                possibleMoves = removePiece(newPossibleMove,possibleMoves);
            }
            else
            {
                newPossibleMove.changePlayer();
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
                    int moveToIndex = position.getPositionAsArrayInx();

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



    public static ArrayList<GameState> removePiece(GameState newGameState,ArrayList<GameState> possibleMoves)
    {
        for(int i = 0; i< newGameState.getAllPositions().size(); i++)
        {
            if(BoardInfo.canRemove(newGameState,i))
            {
                if (BoardInfo.getMills(newGameState, i) > 0)
                {
                    GameState possibleRemovalState = (GameState) AI.deepClone(newGameState);
                    possibleRemovalState.getPosition(i).setPositionColor(Color.NONE);

                    possibleRemovalState.changePlayer();
                    possibleMoves.add(possibleRemovalState);
                }
            }
        }
        return possibleMoves;
    }

    public static ArrayList<GameState> placeNewPieceAI(GameState gamestate)
    {
        return addPiece(gamestate);
    }




    public static int getEvaluation(GameState gameState, Color colorToEvaluate)
    {
        PlayerState currentPlayer = null;

        if(colorToEvaluate == Color.WHITE)
        {
            currentPlayer = gameState.getPlayerWhite();
        }

        else
        {
            currentPlayer = gameState.getPlayerBlack();
        }


        int currentPlayerPieces = currentPlayer.getPiecesOnBoard();
        int enemyPieces = gameState.getOtherPlayer(currentPlayer).getPiecesOnBoard();

        int evaluationValue = currentPlayerPieces - enemyPieces;

        if( gameState.getPhase() != Phase.PLACE_PIECES)
        {
            if(enemyPieces <= 2){
                evaluationValue = 100000;
            }
            /*
            else if (movablePiecesBlack == 0){
                evaluationValue = 100000;
            }

            */
            else if(currentPlayerPieces <= 2)
            {
                evaluationValue = -100000;
            }
        }
        return evaluationValue;
    }


}



