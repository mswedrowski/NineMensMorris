import model.GameState;
import model.PlayerState;
import model.Position;

import model.enums.Color;
import model.enums.Phase;
import model.enums.PlayerType;

import java.io.IOException;
import java.util.ArrayList;

import java.util.stream.Collectors;


public class Game
{
    private GameState gameState;

    public Game(PlayerType playerBlackType,PlayerType playerWhiteType)
    {
        this.gameState = new GameState(playerBlackType,playerWhiteType);
    }

    public GameState preformNextMovePhasePlace(GameState gameState)
    {
        PlayerState currentPlayer = gameState.currentPlayer();



        if(currentPlayer.getPlayerType() == PlayerType.HUMAN)
        {
            HumanIO humanIO = new HumanIO();

            try
            {
                 gameState = humanIO.placeNewPieceIO(gameState);

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {

            gameState = MinMax.minMaxAlgorithm(gameState,3);

            //System.out.println(gameState);
            /*
            //Temp random
           ArrayList<Position> positions= getAllNonOccupiedPositions(gameState);
           pickedPosition = new Random().nextInt(positions.size());
           System.out.println(pickedPosition);

           Position positionToChange = positions.get(pickedPosition);
           System.out.println("AI_resources");
           System.out.println(positionToChange);
           currentPlayer.putPieceOnBoard(positionToChange);

            */
        }

        //int numOfMills = BoardInfo.getMills(gameState,pickedPosition);

        return gameState;
    }


    public GameState preformNextMovePhaseMove(GameState gameState)
    {
        System.out.println(gameState);
        PlayerState currentPlayer = gameState.currentPlayer();

        if(currentPlayer.getPlayerType() == PlayerType.HUMAN)
        {
            HumanIO humanIO = new HumanIO();

            try
            {
                gameState = humanIO.movePieceIO(gameState,currentPlayer);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            //Temp Currently not working
            gameState = MinMax.minMaxAlgorithm(gameState,3);
        }

        //int numOfMills = BoardInfo.getMills(gameState,pickedPosition);

        gameState.changePlayer();
        return gameState;
    }




    public ArrayList<Position> getAllNonOccupiedPositions(GameState gameState)
    {
        return (ArrayList<Position>) gameState.getAllPositions().stream().filter(Position::isEmpty).collect(Collectors.toList());
    }

    public void updatePhase(GameState gameState)
    {
        if(gameState.getPlayerBlack().getPiecesInDrawer()==0 && gameState.getPlayerWhite().getPiecesInDrawer() == 0)
        {
            gameState.setPhase(Phase.MOVE_PIECES);      //should be move_pieces
        }
        else if(gameState.getPhase() == Phase.MOVE_PIECES
                && gameState.getPlayerBlack().getPiecesOnBoard() < 3 && gameState.getPlayerWhite().getPiecesOnBoard() < 3)
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


        while (game.gameState.getPhase() == Phase.PLACE_PIECES)
        {
            System.out.println(game.gameState);
            game.gameState = game.preformNextMovePhasePlace(game.gameState);
            System.out.println("TERAZ KOLEJKA");


            System.out.println(game.gameState.currentPlayer());
            game.updatePhase(game.gameState);
        }


        while (game.gameState.getPhase() == Phase.MOVE_PIECES)
        {

            game.gameState = game.preformNextMovePhaseMove(game.gameState);
            //game.updatePhase(game.gameState);

        }




        /*

        GameState gs = new GameState(PlayerType.AI,PlayerType.AI);

        gs.getPosition(0).setPositionColor(Color.BLACK);
        gs.getPosition(1).setPositionColor(Color.BLACK);
        gs.getPosition(5).setPositionColor(Color.WHITE);
        gs.getPosition(6).setPositionColor(Color.WHITE);
        //gs.getPosition(0).setPositionColor(Color.BLACK);
        gs.setTurnOfPlayer(gs.getPlayerBlack());
        System.out.println(gs);


        GameState result = MinMax.minMaxAlgorithm(gs,1);
        System.out.println(result);

        */




    }
}
