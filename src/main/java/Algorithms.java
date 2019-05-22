import model.GameState;
import model.enums.Color;
import model.enums.HeuristicType;
import model.enums.Phase;

import javax.swing.plaf.synth.ColorType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Algorithms
{
    static int MIN = -9999;
    static int MAX = 9999;
    static int compCount = 0;


    public static Result alphaBeta(GameState gameState, int depth, int alpha, int beta, Phase phase, boolean maxPlayer, Color colorToEval, HeuristicType heuristicType)
    {
        Result result = new Result();
        if(depth != 0)
        {
            ArrayList<GameState> possibleMoves = null;

            if(phase==Phase.PLACE_PIECES)
            {
               possibleMoves = AI.addPiece(gameState);

                 Collections.shuffle(possibleMoves);
                //possibleMoves = (ArrayList<GameState>) possibleMoves.stream().sorted(Comparator.comparing(GameState::currentPlayerPieces)).collect(Collectors.toList());
            }

            if(gameState.getPhase() == Phase.MOVE_PIECES)
            {
                possibleMoves = AI.addPiecesMovingPhase(gameState);
                // All moves are blocked

                if(possibleMoves.isEmpty())
                {
                    gameState.setPhase(Phase.END_OF_GAME);
                    result.setBoard(gameState);
                    return result;
                }

                Collections.shuffle(possibleMoves);
            }
            if(gameState.getPhase() == Phase.END_OF_GAME)
            {
                result.setBoard(gameState);
                return result;
            }


            if(maxPlayer)
            {
                int best = MIN;

                for (GameState possibleGameState: possibleMoves)
                {
                    Result value = alphaBeta(possibleGameState, depth-1, alpha, beta, phase, false, colorToEval,heuristicType);

                    //best = Math.max(best,value.getEvaluationScore());

                    if(value.getEvaluationScore() >= best)
                    {

                        best = value.getEvaluationScore();
                        result.setBoard(possibleGameState);
                    }

                    alpha = Math.max(alpha,best);

                    if(beta <= alpha)
                    {
                        break;
                    }
                }
            }

            else
            {
                int best = MAX;

                for (GameState possibleGameState: possibleMoves)
                {
                    Result value = alphaBeta(possibleGameState, depth-1, alpha, beta, phase, true, colorToEval,heuristicType);

                    //best = Math.min(best,value.getEvaluationScore());

                    if(best >= value.getEvaluationScore())
                    {
                        best = value.getEvaluationScore();
                        result.setBoard(possibleGameState);
                    }
                    beta = Math.min(beta,best);


                    if(beta <= alpha)
                    {
                        break;
                    }
                }
            }
        }
        else
        {
            result.setEvaluationScore(AI.getEvaluation(heuristicType,gameState, colorToEval));
            compCount++;
        }

        return result;
    }


    public static Result miniMax(GameState gameState, int depth, Phase phase, boolean maxPlayer, Color colorToEval,HeuristicType heuristicType)
    {
        Result result = new Result();
        if(depth != 0)
        {
            ArrayList<GameState> possibleMoves = null;

            if(phase==Phase.PLACE_PIECES)
            {
                possibleMoves = AI.addPiece(gameState);
                Collections.shuffle(possibleMoves);
                //possibleMoves = (ArrayList<GameState>) possibleMoves.stream().sorted(Comparator.comparing(GameState::currentPlayerPieces)).collect(Collectors.toList());
            }

            if(gameState.getPhase() == Phase.MOVE_PIECES)
            {
                possibleMoves = AI.addPiecesMovingPhase(gameState);
                // All moves are blocked

                if(possibleMoves.isEmpty())
                {
                    gameState.setPhase(Phase.END_OF_GAME);
                    result.setBoard(gameState);
                    return result;
                }

                Collections.shuffle(possibleMoves);
            }

            if(gameState.getPhase() == Phase.END_OF_GAME)
            {
                result.setBoard(gameState);
                return result;
            }

            if(maxPlayer)
            {
                int best = MIN;

                for (GameState possibleGameState: possibleMoves)
                {
                    Result value = miniMax(possibleGameState, depth-1, phase, false, colorToEval,heuristicType);

                    //best = Math.max(best,value.getEvaluationScore());

                    if(value.getEvaluationScore() > best)
                    {
                        best = value.getEvaluationScore();
                        result.setBoard(possibleGameState);
                    }
                }
            }

            else
            {
                int best = MAX;

                for (GameState possibleGameState: possibleMoves)
                {
                    Result value = miniMax(possibleGameState, depth-1, phase, true, colorToEval,heuristicType);

                    best = Math.min(best,value.getEvaluationScore());
                    //beta = Math.min(alpha,best);

                    if(best > value.getEvaluationScore())
                    {
                        best = value.getEvaluationScore();
                        result.setBoard(possibleGameState);
                    }
                }
            }

        }

        else
        {
            compCount++;
            result.setEvaluationScore(AI.getEvaluation(heuristicType,gameState, colorToEval));
        }

        return result;
    }
}
