import AI_resources.Evaluation;
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

        if(gameState.getPhase() == Phase.PLACE_PIECES)
        {
            ArrayList<GameState> possibleMoves = AI.addPiece(gameState);
            return (possibleMoves.stream().max(Comparator.comparing(move -> min(move,depth-1,colorToEvaluate))).get());

        }
        return null; //za chwile
        }


    public static int min(GameState gameState,int depth,Color colorToEval)
    {
        if(depth == 1)
        {

        }

        if(depth == 0 )
        {
            return AI.getEvaluation(gameState,colorToEval);
        }

        return AI.addPiece(gameState).stream().map(move -> max(move,depth-1,colorToEval))
                .min(Comparator.comparing(Integer::valueOf)).get();
    }
    public static int max(GameState gameState,int depth,Color colorToEval)
    {
        if(depth == 1)
        {

        }
        if(depth == 0)
        {
            return AI.getEvaluation(gameState,colorToEval);
        }

        return AI.addPiece(gameState).stream().map(move -> min(move,depth -1,colorToEval))
                .max(Comparator.comparing(Integer::valueOf)).get();
    }
}
