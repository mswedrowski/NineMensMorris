import model.GameState;
import model.enums.Color;
import model.enums.Phase;

import java.util.ArrayList;
import java.util.Comparator;

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

            return (possibleMoves.stream().max(Comparator.comparing(move -> minAdd(move,depth-1,colorToEvaluate))).get());

        }
        if(gameState.getPhase() == Phase.MOVE_PIECES)
        {
            ArrayList<GameState> possibleMoves = AI.addPiecesMovingPhase(gameState);

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

        return AI.addPiecesMovingPhase(gameState).stream().map(move -> maxMove(move,depth-1,colorToEval))
                .min(Comparator.comparing(Integer::valueOf)).get();
    }
    public static int maxMove(GameState gameState, int depth, Color colorToEval)
    {

        if(depth == 0)
        {
            return AI.getEvaluation(gameState,colorToEval);
        }

        return AI.addPiecesMovingPhase(gameState).stream().map(move -> minMove(move,depth -1,colorToEval))
                .max(Comparator.comparing(Integer::valueOf)).get();
    }


}
