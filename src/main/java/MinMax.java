import model.GameState;
import model.enums.Color;
import model.enums.Phase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

public class MinMax
{

    // Evaluation
    public static GameState minMaxAlgorithm(GameState gameState,int depth)
    {

        Color colorToEvaluate = gameState.currentPlayer().getColorOfPlayer();
        /*
        if(gameState.getPhase() == Phase.MOVE_PIECES)
        {
            System.out.println("MOVE");
        }

         */

        if(gameState.getPhase() == Phase.PLACE_PIECES)
        {
            ArrayList<GameState> possibleMoves = AI.addPiece(gameState);
            Collections.shuffle(possibleMoves);

            return (possibleMoves.stream().max(Comparator.comparing(move -> minAdd(move,depth-1,colorToEvaluate))).get());

        }
        if(gameState.getPhase() == Phase.MOVE_PIECES)
        {
            ArrayList<GameState> possibleMoves = AI.addPiecesMovingPhase(gameState);
            // All moves are blocked
            if(possibleMoves.isEmpty())
            {
                gameState.setPhase(Phase.END_OF_GAME);
                return gameState;
            }
            Collections.shuffle(possibleMoves);

            return (possibleMoves.stream().max(Comparator.comparing(move -> minMove(move,depth-1,colorToEvaluate))).get());
        }

        return null;
    }


    public static int minAdd(GameState gameState, int depth, Color colorToEval)
    {

        if(depth == 0 )
        {
            int eval = AI.getEvaluation(gameState,colorToEval);
           // System.out.println(gameState);
          //  System.out.println(eval);
            return eval;
        }

        return AI.addPiece(gameState).stream().map(move -> maxAdd(move,depth-1,colorToEval))
                .min(Comparator.comparing(Integer::valueOf)).get();
    }
    public static int maxAdd(GameState gameState, int depth, Color colorToEval)
    {

        if(depth == 0)
        {

            int eval = AI.getEvaluation(gameState,colorToEval);
           // System.out.println(gameState);
           // System.out.println(eval);
            return eval;
        }

        return AI.addPiece(gameState).stream().map(move -> minAdd(move,depth -1,colorToEval))
                .max(Comparator.comparing(Integer::valueOf)).get();
    }





    public static int minMove(GameState gameState, int depth, Color colorToEval)
    {

        if(depth == 0 )
        {
            return AI.getEvaluation(gameState,colorToEval);
        }

        ArrayList<GameState> possibleMoves = AI.addPiecesMovingPhase(gameState);
        return possibleMoves.stream().map(move -> maxMove(move,depth-1,colorToEval))
                .min(Comparator.comparing(Integer::valueOf)).get();
    }
    public static int maxMove(GameState gameState, int depth, Color colorToEval)
    {

        if(depth == 0)
        {
            return AI.getEvaluation(gameState,colorToEval);
        }

        ArrayList<GameState> possibleMoves = AI.addPiecesMovingPhase(gameState);
        return possibleMoves.stream().map(move -> minMove(move,depth -1,colorToEval))
                .max(Comparator.comparing(Integer::valueOf)).get();
    }


}
