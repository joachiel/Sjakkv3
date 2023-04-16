package Project.Game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class moveWrite implements writeInterface{
    
    public void writeMovesToFile(String filename, String move){
        try {
            PrintWriter writer = new PrintWriter(filename);
            writer.println(move);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
        
    }

    public String getMovesFromFile(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        String line = scanner.nextLine();
        return line;
    }
}
