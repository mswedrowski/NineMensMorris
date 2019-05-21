import model.GameState;

public class Result
{
    private int evaluationScore = 0;
    private int numberOfEvaluations = 0;
    private GameState board;

    public int getEvaluationScore() {
        return evaluationScore;
    }

    public void setEvaluationScore(int evaluationScore) {
        this.evaluationScore = evaluationScore;
    }

    public int getNumberOfEvaluations() {
        return numberOfEvaluations;
    }

    public void setNumberOfEvaluations(int numberOfEvaluations) {
        this.numberOfEvaluations = numberOfEvaluations;
    }

    public GameState getBoard() {
        return board;
    }

    public void setBoard(GameState board) {
        this.board = board;
    }
}
