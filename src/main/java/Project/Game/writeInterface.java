package Project.Game;

import java.io.FileNotFoundException;

public interface writeInterface {
    public void writeMovesToFile(String filename, String move);

    public String getMovesFromFile(String filename) throws FileNotFoundException;
}
