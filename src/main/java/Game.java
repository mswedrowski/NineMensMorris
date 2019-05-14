import model.GameState;
import model.PlayerState;
import model.Position;

import model.enums.Color;
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
        PlayerState currentPlayer = gameState.currentPlayer();

        int pickedPosition = -1;

        if(currentPlayer.getPlayerType() == PlayerType.HUMAN)
        {
            HumanIO humanIO = new HumanIO();

            try
            {
                pickedPosition = humanIO.placeNewPieceIO(gameState);

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            AI bot = new AI();
            //Temp random
           ArrayList<Position> positions= getAllNonOccupiedPositions(gameState);
           pickedPosition = new Random().nextInt(positions.size());
           System.out.println(pickedPosition);

           Position positionToChange = positions.get(pickedPosition);
           System.out.println("AI_resources");
           System.out.println(positionToChange);
           currentPlayer.putPieceOnBoard(positionToChange);
        }

        //int numOfMills = BoardInfo.getMills(gameState,pickedPosition);

        gameState.changePlayer();
    }


    public void preformNextMovePhaseMove(GameState gameState)
    {
        PlayerState currentPlayer = gameState.currentPlayer();

        if(currentPlayer.getPlayerType() == PlayerType.HUMAN)
        {
            HumanIO humanIO = new HumanIO();

            try
            {
                humanIO.movePieceIO(gameState,currentPlayer);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            //Temp Currently not working
            System.out.println("Not moving");
        }

        //int numOfMills = BoardInfo.getMills(gameState,pickedPosition);

        gameState.changePlayer();
    }




    public ArrayList<Position> getAllNonOccupiedPositions(GameState gameState)
    {
        return (ArrayList<Position>) gameState.getAllPositions().stream().filter(Position::isEmpty).collect(Collectors.toList());
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

    public void isEndOfGame()
    {
        if(gameState.getPlayerWhite().getPiecesOnBoard() < 3 || gameState.getPlayerBlack().getPiecesOnBoard() < 3)
        {
        gameState.setPhase(Phase.END_OF_GAME);
        }
    }

    public static void main(String[] args)
    {
        Game game = new Game(PlayerType.AI,PlayerType.HUMAN);

       /* while (game.gameState.getPhase() == Phase.PLACE_PIECES)
        {
            game.preformNextMovePhasePlace(game.gameState);
            game.updatePhase(game.gameState);
        }

        */
       game.gameState.setPhase(Phase.MOVE_PIECES);
        while (game.gameState.getPhase() == Phase.MOVE_PIECES)
        {
            game.gameState.getPosition(3).setPositionColor(Color.WHITE);
            game.gameState.getPosition(4).setPositionColor(Color.BLACK);
            game.preformNextMovePhaseMove(game.gameState);

        }

    }
}
