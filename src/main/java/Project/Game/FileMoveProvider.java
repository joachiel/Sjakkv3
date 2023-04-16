package Project.Game;

import java.io.IOException;
import java.util.List;

import Project.Util.FileHelper;

public class FileMoveProvider implements MoveProvider{
    
    private List<String> moves;

    public FileMoveProvider() throws IOException {
        this("/Game/moves.txt", true);
    }


    public FileMoveProvider(String string, boolean b) throws IOException {
        this.moves = FileHelper.readLines(string, b);
    }


    @Override
    public String getMove() {
        return moves.get(moves.size()-1);
    }
    
}
