import model.GameState;
import model.PlayerState;
import model.Position;
import model.enums.Color;
import model.enums.HeuristicType;
import model.enums.Phase;

import java.util.ArrayList;

public class Heuristics {

    public static int getPotentialMillCount(GameState gameState, Color colorToCheck) {
        int millCounter = 0;

        for (int i = 0; i < gameState.getAllPositions().size(); i++) {
            if (gameState.getPosition(i).getPositionColor() == colorToCheck) {
                millCounter += BoardInfo.getMills(gameState, i);
            }
        }
        return millCounter;
    }

    public static int getNumberOfNeighbours(int positionInArray) {
        return BoardInfo.getNeighboursOfPosition(positionInArray).size();
    }


    public static int getEvaluation(HeuristicType heuristicType, GameState gameState, Color colorToEvaluate) {
        int evaluation = 0;

        if (heuristicType == HeuristicType.PieceCount) {
            PlayerState currentPlayer = null;

            if (colorToEvaluate == Color.WHITE) {
                currentPlayer = gameState.getPlayerWhite();
            }
            else {
                currentPlayer = gameState.getPlayerBlack();
            }
            int currentPlayerPieces = currentPlayer.getPiecesOnBoard();
            int enemyPieces = gameState.getOtherPlayer(currentPlayer).getPiecesOnBoard();
            int mills = getPotentialMillCount(gameState, colorToEvaluate);
            int evaluationValue = mills + (currentPlayerPieces - enemyPieces) * 100;


            if (gameState.getPhase() != Phase.PLACE_PIECES) {
                if (enemyPieces <= 2) {
                    evaluationValue = 100000;
                } else if (currentPlayerPieces <= 2) {
                    evaluationValue = -100000;
                }
            }

            // due to block
            else if (gameState.getPhase() == Phase.END_OF_GAME) {
                if (currentPlayer.getColorOfPlayer() == colorToEvaluate) {
                    return -100000;
                } else {
                    return 100000;
                }
            }
            evaluation = evaluationValue;
        }


        else if (heuristicType == HeuristicType.NeighboursCount) {
            int evaluationValue = 0;

            PlayerState currentPlayer = null;

            if (colorToEvaluate == Color.WHITE) {
                currentPlayer = gameState.getPlayerWhite();
            } else {
                currentPlayer = gameState.getPlayerBlack();
            }

            Color oppositeColor = BoardInfo.colorOfEnemy(currentPlayer);

            ArrayList<Position> positions = gameState.getAllPositions();

            for (int i = 0; i < positions.size(); i++) {
                if (positions.get(i).getPositionColor() == colorToEvaluate) {
                    evaluationValue += getNumberOfNeighbours(i);
                } else if (positions.get(i).getPositionColor() == oppositeColor) {
                    evaluationValue -= getNumberOfNeighbours(i);
                }
            }

            if (gameState.getPhase() == Phase.END_OF_GAME) {
                if (currentPlayer.getColorOfPlayer() == colorToEvaluate) {
                    return -100000;
                } else {
                    return 100000;
                }
            }

            evaluation = evaluationValue;
        }

        else if (heuristicType == HeuristicType.FieldScore) {
            int evaluationValue = 0;
            Color oppositeColor = null;
            PlayerState currentPlayer = null;


            if (colorToEvaluate == Color.WHITE) {
                currentPlayer = gameState.getPlayerWhite();
            } else {
                currentPlayer = gameState.getPlayerBlack();
            }

            oppositeColor = BoardInfo.colorOfEnemy(currentPlayer);

            ArrayList<Position> positions = gameState.getAllPositions();

            int currentPlayerPieces = currentPlayer.getPiecesOnBoard();
            int enemyPieces = gameState.getOtherPlayer(currentPlayer).getPiecesOnBoard();
            int mills = getPotentialMillCount(gameState, colorToEvaluate);
            evaluationValue = mills + (currentPlayerPieces - enemyPieces) * 10;

            for (int i = 0; i < positions.size(); i++) {
                if (positions.get(i).getPositionColor() == colorToEvaluate) {
                    evaluationValue += BoardInfo.fieldScoreHeuristic(i);
                } else if (positions.get(i).getPositionColor() == oppositeColor) {
                    evaluationValue -= BoardInfo.fieldScoreHeuristic(i);
                }
            }

            if (gameState.getPhase() == Phase.END_OF_GAME) {
                if (currentPlayer.getColorOfPlayer() == colorToEvaluate) {
                    return -100000;
                } else {
                    return 100000;
                }
            }

            evaluation = evaluationValue;
        }
        return evaluation;
    }
}
