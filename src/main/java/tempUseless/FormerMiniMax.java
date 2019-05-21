package tempUseless;

import model.GameState;
import model.enums.Color;
import model.enums.Phase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


// NOW IM NOT USING IT BUT I LEFT IT BECAUSE I LIKE THIS WAY OF IMPLEMENTING MINIMAX AND I WOULD LIKE TO MAKE ALPHABETA THE SAME WAY

/*
public class FormerMiniMax
{
    // Evaluation
    public static GameState miniMaxAlgorithm(GameState gameState, int depth)
    {
        Color colorToEvaluate = gameState.currentPlayer().getColorOfPlayer();
        if(gameState.getPhase() == Phase.PLACE_PIECES)
        {
            ArrayList<GameState> possibleMoves = AI.addPiece(gameState);
            Collections.shuffle(possibleMoves);
            return (possibleMoves.stream().max(Comparator.comparing(move -> min(move,depth-1,colorToEvaluate,gameState.getPhase()))).get());
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

            return (possibleMoves.stream().max(Comparator.comparing(move -> min(move,depth-1,colorToEvaluate,gameState.getPhase()))).get());
        }
        return null;
    }

    public static int min(GameState gameState, int depth, Color colorToEval,Phase phase)
    {
        if(phase == Phase.PLACE_PIECES)
        {
            if(depth == 0 )
            {
                return AI.getEvaluation(gameState, colorToEval);
            }
            return AI.addPiece(gameState).stream().map(move -> max(move,depth-1,colorToEval,phase))
                    .min(Comparator.comparing(Integer::valueOf)).get();
        }
        else //Phase moving
        {
            if(depth == 0 )
            {
                return AI.getEvaluation(gameState,colorToEval);
            }
            ArrayList<GameState> possibleMoves = AI.addPiecesMovingPhase(gameState);
            return possibleMoves.stream().map(move -> max(move,depth-1,colorToEval,phase))
                    .min(Comparator.comparing(Integer::valueOf)).get();
        }
    }

    public static int max(GameState gameState, int depth, Color colorToEval,Phase phase)
    {
        if(phase == Phase.PLACE_PIECES)
        {
            if (depth == 0) {
                return AI.getEvaluation(gameState, colorToEval);
            }
            return AI.addPiece(gameState).stream().map(move -> max(move, depth - 1, colorToEval,phase))
                    .max(Comparator.comparing(Integer::valueOf)).get();
        }
        else //Phase moving
        {
            if(depth == 0)
            {
                return AI.getEvaluation(gameState,colorToEval);
            }
            ArrayList<GameState> possibleMoves = AI.addPiecesMovingPhase(gameState);
            return possibleMoves.stream().map(move -> min(move,depth -1,colorToEval,phase))
                    .max(Comparator.comparing(Integer::valueOf)).get();
        }
    }
}

 */
