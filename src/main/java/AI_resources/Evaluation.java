package AI_resources;
import model.GameState;

public class Evaluation {

    private int evaluation = 0;
    private int positionsEvaluated = 0;
    private GameState gameState;

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
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
