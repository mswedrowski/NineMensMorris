package AI_resources;
import model.GameState;

public class Evaluation {
    /* Private Variable Declarations */
    private int evaluation = 0;
    private int positionsEvaluated = 0;
    private GameState board;

    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    public int getPositionsEvaluated() {
        return positionsEvaluated;
    }

    public void setPositionsEvaluated(int positionsEvaluated) {
        this.positionsEvaluated = positionsEvaluated;
    }

    public GameState getGameState()
    {
        return board;
    }

    public void setNineMensMorrisBoard(GameState inputBoard) {
        board = inputBoard;
    }
}
