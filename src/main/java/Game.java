import model.GameState;
import model.PlayerState;
import model.Position;

import model.enums.AlgorithmType;
import model.enums.HeuristicType;
import model.enums.Phase;
import model.enums.PlayerType;

import java.io.IOException;
import java.util.ArrayList;

import java.util.stream.Collectors;


public class Game
{

    public static final int alpha = Integer.MIN_VALUE;
    public static final int beta  = Integer.MAX_VALUE;
    private GameState gameState;

    public Game(PlayerType playerBlackType,PlayerType playerWhiteType)
    {
        this.gameState = new GameState(playerBlackType,playerWhiteType);
    }

    public GameState preformNextMovePhasePlace(GameState gameState)
    {
        PlayerState currentPlayer = gameState.currentPlayer();

        currentPlayer.setNumberOfMoves(currentPlayer.getNumberOfMoves()+1);

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
            gameState = AIgetMove(currentPlayer);
        }
        return gameState;
    }


    public GameState preformNextMovePhaseMove(GameState gameState)
    {
        PlayerState currentPlayer = gameState.currentPlayer();

        currentPlayer.setNumberOfMoves(currentPlayer.getNumberOfMoves()+1);

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
            gameState = AIgetMove(currentPlayer);
        }

        return gameState;
    }



    public GameState AIgetMove(PlayerState currentPlayer)
    {
        Result result = null;
        HeuristicType heuristicType = currentPlayer.getHeuristicType();
        if(currentPlayer.getAlgorithmType() == AlgorithmType.MINIMAX)
        {
            result = Algorithms.miniMax(gameState,4,gameState.getPhase(),true,currentPlayer.getColorOfPlayer(),heuristicType);
        }
        else if(currentPlayer.getAlgorithmType() == AlgorithmType.ALPHABETA)
        {
            result = Algorithms.alphaBeta(gameState,4,alpha,beta,gameState.getPhase(),true,currentPlayer.getColorOfPlayer(),heuristicType);
        }
        return result.getBoard();
    }


    public ArrayList<Position> getAllNonOccupiedPositions(GameState gameState)
    {
        return (ArrayList<Position>) gameState.getAllPositions().stream().filter(Position::isEmpty).collect(Collectors.toList());
    }

    public void updatePhasePlace(GameState gameState)
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

    public void updatePhaseMove(GameState gameState)
    {
        if (gameState.getPhase() == Phase.END_OF_GAME || (gameState.getPhase() == Phase.MOVE_PIECES
                && gameState.getPlayerBlack().getPiecesOnBoard() < 3 || gameState.getPlayerWhite().getPiecesOnBoard() < 3))
        {
            gameState.setPhase(Phase.END_OF_GAME);
            System.out.println(gameState);
            System.out.println("Black");
            System.out.println(gameState.getPlayerBlack().getEvalnumber());
            System.out.println(gameState.getPlayerBlack().getTime());
            System.out.println(gameState.getPlayerBlack().getNumberOfMoves());
            System.out.println("White");
            System.out.println(gameState.getPlayerWhite().getEvalnumber());
            System.out.println(gameState.getPlayerWhite().getTime());
            System.out.println(gameState.getPlayerWhite().getNumberOfMoves());
            System.out.println("Koniec");

        }

    }


    public static void main(String[] args)
    {
        Game game = new Game(PlayerType.AI,PlayerType.AI);
        //game.gameState.getPlayerWhite().setHeuristicType(HeuristicType.FieldScore);
        //game.gameState.getPlayerBlack().setHeuristicType(HeuristicType.FieldScore);
        //game.gameState.getPlayerWhite().setAlgorithmType(AlgorithmType.MINIMAX);

        while (game.gameState.getPhase() == Phase.PLACE_PIECES)
        {
            System.out.println(game.gameState);
            int startTime = (int) System.currentTimeMillis();
            int startNumberComp = Algorithms.compCount;
            game.gameState = game.preformNextMovePhasePlace(game.gameState);

            int endNumberComp = Algorithms.compCount;
            int endTime = (int) System.currentTimeMillis();
            game.gameState.getEnemy().setTime( game.gameState.getEnemy().getTime() + endTime - startTime);
            game.gameState.getEnemy().setEvalnumber(game.gameState.getEnemy().getEvalnumber() + endNumberComp - startNumberComp );

            System.out.println("TURN OF PLAYER: ");
            System.out.println(game.gameState.currentPlayer());
            game.updatePhasePlace(game.gameState);
        }

        while (game.gameState.getPhase() == Phase.MOVE_PIECES)
        {
            System.out.println(game.gameState);
            int startTime = (int) System.currentTimeMillis();
            int startNumberComp = Algorithms.compCount;

            game.gameState = game.preformNextMovePhaseMove(game.gameState);

            int endNumberComp = Algorithms.compCount;
            int endTime = (int) System.currentTimeMillis();
            game.gameState.getEnemy().setTime( game.gameState.getEnemy().getTime() + endTime - startTime);
            game.gameState.getEnemy().setEvalnumber( game.gameState.getEnemy().getEvalnumber() + endNumberComp - startNumberComp);

            System.out.println("TURN OF PLAYER: ");
            System.out.println(game.gameState.currentPlayer());
            game.updatePhaseMove(game.gameState);
        }

    }
}
