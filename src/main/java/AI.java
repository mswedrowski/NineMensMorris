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

    public static ArrayList<Integer> getAllNonOccupiedPositions(GameState gameState)
    {
        return (ArrayList<Integer>) gameState.getAllPositions().stream().filter(Position::isEmpty)
                .map(Position::getPositionBoardName).collect(Collectors.toList());
    }

    public static ArrayList<Integer> getAllNonOccupiedPositionArrayInx(GameState gameState)
    {
        ArrayList<Integer> freePositions = getAllNonOccupiedPositions(gameState);
        for(int i=0;i<freePositions.size();i++)
        {
            freePositions.set(i,BoardInfo.boardPositionToArrayInx(freePositions.get(i)));
        }
        return freePositions;
    }


    public static void playerPuttingPiece(GameState gameState,int indexOfPosition)
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
        ArrayList<Position> positions = gameState.getAllPositions();

        for(int i=0; i<positions.size(); i++ )
        {
            if(positions.get(i).getPositionColor() == Color.NONE)
            {
                GameState newPossibleMove = (GameState) AI.deepClone(gameState);
                playerPuttingPiece(newPossibleMove,i);

                int numberOfMills = BoardInfo.getPotentialMills(newPossibleMove,i);

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
        }
        return possibleMoves;
    }


    public static ArrayList<GameState> addPiecesMovingPhase(GameState gameState)
    {
        ArrayList<GameState> possibleMovesArray =new ArrayList<>();

        PlayerState player = gameState.currentPlayer();

        if(gameState.getPhase() == Phase.END_OF_GAME)
        {
            possibleMovesArray.add(gameState);
            return possibleMovesArray;
        }

        for(int moveFromIndex=0; moveFromIndex< gameState.getAllPositions().size();moveFromIndex++)
        {
            if(gameState.getPosition(moveFromIndex).getPositionColor() == gameState.currentPlayer().getColorOfPlayer())
            {
                ArrayList<Integer> allowedMoves = BoardInfo.getNeighboursOfPosition(moveFromIndex);

                if(gameState.currentPlayer().getPiecesOnBoard() == 3)
                {
                    allowedMoves = getAllNonOccupiedPositionArrayInx(gameState);
                }

                    for(Integer moveToIndex : allowedMoves)
                    {   GameState possibleMove = (GameState) AI.deepClone(gameState);

                        Position positionToMove = possibleMove.getPosition(moveToIndex);
                        Position positionMoveFrom = possibleMove.getPosition(moveFromIndex);

                        if(gameState.getPosition(moveToIndex).isEmpty() && BoardInfo.isPreviousPiecePositionValid(positionMoveFrom,positionToMove))
                        {
                            positionMoveFrom.setPositionColor(Color.NONE);
                            positionToMove.setPositionColor(gameState.currentPlayer().getColorOfPlayer());
                            positionToMove.setPreviousPiecePosition(positionMoveFrom);

                            if (BoardInfo.getMills(possibleMove, moveToIndex) > 0)
                            {
                                possibleMovesArray = removePiece(possibleMove, possibleMovesArray);
                            }
                            else
                                {
                                    possibleMove.changePlayer();
                                    possibleMovesArray.add(possibleMove);
                            }
                        }
                    }
                }
            }

        // all moves blocked
        if (possibleMovesArray.isEmpty())
        {
            gameState.setPhase(Phase.END_OF_GAME);
            possibleMovesArray.add(gameState);
        }
        return possibleMovesArray;
    }


    public static ArrayList<GameState> removePiece(GameState newGameState,ArrayList<GameState> possibleMoves)
    {
        for(int i = 0; i< newGameState.getAllPositions().size(); i++)
        {
            if(BoardInfo.canRemove(newGameState,i))
            {
                if (BoardInfo.getMills(newGameState, i) == 0)
                {
                    GameState possibleRemovalState = (GameState) AI.deepClone(newGameState);
                    playerGetsPieceRemoved(possibleRemovalState,i);

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


    public static int getPotentialMillCount(GameState gameState,Color colorToCheck)
    {
        int millCounter =0;

        for(int i=0;i<gameState.getAllPositions().size();i++)
        {
            if(gameState.getPosition(i).getPositionColor() == colorToCheck)
            {
                millCounter += BoardInfo.getMills(gameState,i);
            }
        }
        return millCounter;
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
        int mills = getPotentialMillCount(gameState,colorToEvaluate);
        int evaluationValue = mills + (currentPlayerPieces - enemyPieces)*100;

        // TO FIX
        if( gameState.getPhase() != Phase.PLACE_PIECES)
        {
            if(enemyPieces <= 2)
            {
                evaluationValue = 100000;
            }

            else if(currentPlayerPieces <= 2)
            {
                evaluationValue = -100000;
            }
        }

        // due to block
        else if( gameState.getPhase() == Phase.END_OF_GAME)
        {
            if( currentPlayer.getColorOfPlayer() == colorToEvaluate)
            {
                return -100000;
            }
            else
            {
                return  100000;
            }
        }
        return evaluationValue;
    }


/*
    public static int getEvaluationPotentialMills(GameState gameState, Color colorToEvaluate)
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
/*
            else if(currentPlayerPieces <= 2)
            {
                evaluationValue = -100000;
            }
        }
        return evaluationValue;
    }
    */


}



