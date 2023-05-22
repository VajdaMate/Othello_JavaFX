import model.Colors;
import model.Disk;
import model.GameModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameModelTest {
    private GameModel gameModel;

    @BeforeEach
    public void setup(){
        gameModel = new GameModel();
    }

    @Test
    public void testPutDisk() {
        firstMove(2,3);
        Disk disk = gameModel.getDisk(2, 3);
        Assertions.assertEquals(Colors.BLACK, disk.getColor());
    }

    @Test
    public void testUndoRemove() {
        firstMove(5,4);
        gameModel.undoLast();
        Disk disk = gameModel.getDisk(5, 4);
        Assertions.assertEquals(Colors.VALID, disk.getColor());
    }
    @Test
    public void testUndoTrue(){
        firstMove(4,5);
        var value=gameModel.undoLast();
        Assertions.assertTrue(value);
    }

    @Test
    public void testUndoFalse(){
        var value=gameModel.undoLast();
        Assertions.assertFalse(value);
    }

    @Test
    public void testIsGameOver() {
        boolean gameOver = gameModel.isGameOver();
        Assertions.assertFalse(gameOver);
    }

    private void firstMove(int row,int col){
        gameModel.putDisk(row, col);
    }
}
