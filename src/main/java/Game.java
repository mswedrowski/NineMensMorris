import model.GameState;
import model.PlayerState;
import model.Position;

import model.enums.Phase;
import model.enums.PlayerType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;


public class Game
{
    private GameState gameState;

    public Game(PlayerType playerBlackType,PlayerType playerWhiteType)
    {
        this.gameState = new GameState(playerBlackType,playerWhiteType);
    }

    public void preformNextMovePhasePlace(GameState gameState)
    {
        PlayerState currentPlayer = gameState.getTurnOfPlayer();

        int pickedPosition = -1;
        if(currentPlayer.getPlayerType() == PlayerType.HUMAN)
        {
            HumanIO humanIO = new HumanIO();

            try
            {
                pickedPosition = humanIO.placeNewPiece(gameState);

            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            //Temp random
           ArrayList<Position> positions= getAllNonOccupiedPositions(gameState);
           pickedPosition = new Random().nextInt(positions.size());

           Position positionToChange = positions.get(pickedPosition);

           currentPlayer.putPieceOnBoard(positionToChange);
        }

        int numOfMills = BoardInfo.getMills(gameState,pickedPosition);

        gameState.changePlayer();

    }

    public ArrayList<Position> getAllNonOccupiedPositions(GameState gameState)
    {
        return (ArrayList<Position>) gameState.getPositions().stream().filter(Position::isEmpty).collect(Collectors.toList());
    }

    public void updatePhase(GameState gameState)
    {

        if(gameState.getPlayerBlack().getPiecesInDrawer()==0 && gameState.getPlayerWhite().getPiecesInDrawer() == 0)
        {

            gameState.setPhase(Phase.MOVE_PIECES);
        }
        else
        {
            gameState.setPhase(Phase.PLACE_PIECES);
        }
    }

    public static void main(String[] args)
    {
        Game game = new Game(PlayerType.AI,PlayerType.HUMAN);

        System.out.println(game.gameState.getPositions().get(11));

        while (game.gameState.getPhase() == Phase.PLACE_PIECES)
        {
            game.preformNextMovePhasePlace(game.gameState);
            game.updatePhase(game.gameState);
        }

    }
}
