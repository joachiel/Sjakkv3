package Project.Game;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

public class CheckAndMateDetector {
/**
 * Component of the Chess game that detects check mates in the game.
 * 
 * @author Jussi Lundstedt
 *
 */
    private Board b;
    private LinkedList<Piece> wPieces;
    private LinkedList<Piece> bPieces;
    private LinkedList<Square> movableSquares;
    private final LinkedList<Square> squares;
    private Piece bk;
    private Piece wk;
    private HashMap<Square,List<Piece>> wMoves;
    private HashMap<Square,List<Piece>> bMoves;
    
    /**
     * Constructs a new instance of CheckmateDetector on a given board. By
     * convention should be called when the board is in its initial state.
     * 
     * @param b The board which the detector monitors
     * @param wPieces White pieces on the board.
     * @param bPieces Black pieces on the board.
     * @param wk Piece object representing the white king
     * @param bk Piece object representing the black king
     */
    public CheckAndMateDetector(Board b, LinkedList<Piece> whitePieces, 
            LinkedList<Piece> blackPieces, Piece wk, Piece bk) {
        this.b = b;
        this.wPieces = whitePieces;
        this.bPieces = blackPieces;
        this.bk = bk;
        this.wk = wk;
        
        // Initialize other fields
        squares = new LinkedList<Square>();
        movableSquares = new LinkedList<Square>();
        wMoves = new HashMap<Square,List<Piece>>();
        bMoves = new HashMap<Square,List<Piece>>();
        
        Square[][] brd = b.getBoard();
        
        // add all squares to squares list and as hashmap keys
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                squares.add(brd[x][y]);
                wMoves.put(brd[x][y], new LinkedList<Piece>());
                bMoves.put(brd[x][y], new LinkedList<Piece>());
            }
        }
        
        // update situation
        update();
    }
    
    /**
     * Updates the object with the current situation of the game.
     */
    public void update() {
        // Iterators through pieces
        Iterator<Piece> wIter = wPieces.iterator();
        Iterator<Piece> bIter = bPieces.iterator();
        
        // empty moves and movable squares at each update
        for (List<Piece> pieces : wMoves.values()) {
            pieces.removeAll(pieces);
        }
        
        for (List<Piece> pieces : bMoves.values()) {
            pieces.removeAll(pieces);
        }
        
        movableSquares.removeAll(movableSquares);
        
        // Add each move white and black can make to map
        while (wIter.hasNext()) {
            Piece p = wIter.next();

            if (!p.getClass().equals(King.class)) {
                if (p.getPosition() == null) {
                    wIter.remove();
                    continue;
                }

                List<Square> mvs = p.getLegalMoves(b);
                Iterator<Square> iter = mvs.iterator();
                while (iter.hasNext()) {
                    List<Piece> pieces = wMoves.get(iter.next());
                    pieces.add(p);
                }
            }
        }
        
        while (bIter.hasNext()) {
            Piece p = bIter.next();
            
            if (!p.getClass().equals(King.class)) {
                if (p.getPosition() == null) {
                    wIter.remove();
                    continue;
                }
                
                List<Square> mvs = p.getLegalMoves(b);
                Iterator<Square> iter = mvs.iterator();
                while (iter.hasNext()) {
                    List<Piece> pieces = bMoves.get(iter.next());
                    pieces.add(p);
                }
            }
        }
    }
    
    /**
     * Checks if the black king is threatened
     * @return boolean representing whether the black king is in check.
     */
    public boolean blackInCheck() {
        update();
        Square sq = bk.getPosition();
        if (wMoves.get(sq).isEmpty()) {
            movableSquares.addAll(squares);
            return false;
        } else System.out.println("Black in check"); return true;
    }
    
    /**
     * Checks if the white king is threatened
     * @return boolean representing whether the white king is in check.
     */
    public boolean whiteInCheck() {
        update();
        Square sq = wk.getPosition();
        if (bMoves.get(sq).isEmpty()) {
            movableSquares.addAll(squares);
            return false;
        } else return true;
    }
    
    /**
     * Checks whether black is in checkmate.
     * @return boolean representing if black player is checkmated.
     */
    public boolean blackCheckMated() {
        boolean checkmate = true;
        // Check if black is in check
        if (!this.blackInCheck()) return false;
        
        // If yes, check if king can evade
        if (canEvade(wMoves, bk)) return false;
        
        // If no, check if threat can be captured
        List<Piece> threats = wMoves.get(bk.getPosition());
        if (canCapture(bMoves, threats, bk)) checkmate = false;
        
        // If no, check if threat can be blocked
        if (canBlock(threats, bMoves, bk)) checkmate = false;
        
        // If no possible ways of removing check, checkmate occurred
        return checkmate;
    }
    
    /**
     * Checks whether white is in checkmate.
     * @return boolean representing if white player is checkmated.
     */
    public boolean whiteCheckMated() {
        boolean checkmate = true;
        // Check if white is in check
        if (!this.whiteInCheck()) return false;
        
        // If yes, check if king can evade
        if (canEvade(bMoves, wk)) return false;
        
        // If no, check if threat can be captured
        List<Piece> threats = bMoves.get(wk.getPosition());
        if (canCapture(wMoves, threats, wk)) return false;
        
        // If no, check if threat can be blocked
        if (canBlock(threats, wMoves, wk)) return false;
        
        // If no possible ways of removing check, checkmate occurred
        return checkmate;
    }
    
    /*
     * Helper method to determine if the king can evade the check.
     * Gives a false positive if the king can capture the checking piece.
     */
    private boolean canEvade(Map<Square,List<Piece>> tMoves, Piece tKing) {
        boolean evade = false;
        List<Square> kingsMoves = tKing.getLegalMoves(b);
        Iterator<Square> iterator = kingsMoves.iterator();
        
        // If king is not threatened at some square, it can evade
        while (iterator.hasNext()) {
            Square sq = iterator.next();
            if (!testMove(tKing, sq)) continue;
            if (tMoves.get(sq).isEmpty()) {
                movableSquares.add(sq);
                evade = true;
                System.out.println(sq.getX());
                System.out.println(sq.getY());
            }
            for (Map.Entry<Square, List<Piece>> entry : tMoves.entrySet()){
                System.out.println(entry.getKey().getX() + ":" + entry.getKey().getY());
                System.out.println(entry.getValue());
            }
        }
        return evade;
    }
    
    /*
     * Helper method to determine if the threatening piece can be captured.
     */
    private boolean canCapture(Map<Square,List<Piece>> poss, 
            List<Piece> threats, Piece k) {
        
        boolean capture = false;
        if (threats.size() == 1) {
            Square sq = threats.get(0).getPosition();
            
            if (k.getLegalMoves(b).contains(sq)) {
                movableSquares.add(sq);
                if (testMove(k, sq)) {
                    capture = true;
                }
            }
            
            List<Piece> caps = poss.get(sq);
            ConcurrentLinkedDeque<Piece> capturers = new ConcurrentLinkedDeque<Piece>();
            capturers.addAll(caps);
            
            if (!capturers.isEmpty()) {
                movableSquares.add(sq);
                for (Piece p : capturers) {
                    if (testMove(p, sq)) {
                        capture = true;
                    }
                }
            }
        }
        
        return capture;
    }
    
    /*
     * Helper method to determine if check can be blocked by a piece.
     */
    private boolean canBlock(List<Piece> threats, 
            Map <Square,List<Piece>> blockMoves, Piece k) {
        boolean blockable = false;
        
        if (threats.size() == 1) {
            Square ts = threats.get(0).getPosition();
            Square ks = k.getPosition();
            Square[][] brdArray = b.getBoard();
            
            if (ks.getX() == ts.getX()) {
                int max = Math.max(ks.getY(), ts.getY());
                int min = Math.min(ks.getY(), ts.getY());
                
                for (int i = min + 1; i < max; i++) {
                    List<Piece> blks = 
                            blockMoves.get(brdArray[ks.getX()][i]);
                    ConcurrentLinkedDeque<Piece> blockers = 
                            new ConcurrentLinkedDeque<Piece>();
                    blockers.addAll(blks);
                    
                    if (!blockers.isEmpty()) {
                        movableSquares.add(brdArray[ks.getX()][i]);
                        
                        for (Piece p : blockers) {
                            if (testMove(p,brdArray[ks.getX()][i])) {
                                blockable = true;
                            }
                        }
                        
                    }
                }
            }
            
            if (ks.getY() == ts.getY()) {
                int max = Math.max(ks.getX(), ts.getX());
                int min = Math.min(ks.getX(), ts.getX());
                
                for (int i = min + 1; i < max; i++) {
                    List<Piece> blks = 
                            blockMoves.get(brdArray[i][ks.getY()]);
                    ConcurrentLinkedDeque<Piece> blockers = 
                            new ConcurrentLinkedDeque<Piece>();
                    blockers.addAll(blks);
                    
                    if (!blockers.isEmpty()) {
                        
                        movableSquares.add(brdArray[i][ks.getY()]);
                        
                        for (Piece p : blockers) {
                            if (testMove(p, brdArray[i][ks.getY()])) {
                                blockable = true;
                            }
                        }
                        
                    }
                }
            }
            
            Class<? extends Piece> tC = threats.get(0).getClass();
            
            if (tC.equals(Queen.class) || tC.equals(Bishop.class)) {
                int kX = ks.getX();
                int kY = ks.getY();
                int tX = ts.getX();
                int tY = ts.getY();
                
                if (kX > tX && kY > tY) {
                    for (int i = tX + 1; i < kX; i++) {
                        tY++;
                        List<Piece> blks = 
                                blockMoves.get(brdArray[i][tY]);
                        ConcurrentLinkedDeque<Piece> blockers = 
                                new ConcurrentLinkedDeque<Piece>();
                        blockers.addAll(blks);
                        
                        if (!blockers.isEmpty()) {
                            movableSquares.add(brdArray[i][tY]);
                            
                            for (Piece p : blockers) {
                                if (testMove(p, brdArray[i][tY])) {
                                    blockable = true;
                                }
                            }
                        }
                    }
                }
                
                if (kX > tX && tY > kY) {
                    for (int i = tX + 1; i < kX; i++) {
                        tY--;
                        List<Piece> blks = 
                                blockMoves.get(brdArray[i][tY]);
                        ConcurrentLinkedDeque<Piece> blockers = 
                                new ConcurrentLinkedDeque<Piece>();
                        blockers.addAll(blks);
                        
                        if (!blockers.isEmpty()) {
                            movableSquares.add(brdArray[i][tY]);
                            
                            for (Piece p : blockers) {
                                if (testMove(p, brdArray[i][tY])) {
                                    blockable = true;
                                }
                            }
                        }
                    }
                }
                
                if (tX > kX && kY > tY) {
                    for (int i = tX - 1; i > kX; i--) {
                        tY++;
                        List<Piece> blks = 
                                blockMoves.get(brdArray[i][tY]);
                        ConcurrentLinkedDeque<Piece> blockers = 
                                new ConcurrentLinkedDeque<Piece>();
                        blockers.addAll(blks);
                        
                        if (!blockers.isEmpty()) {
                            movableSquares.add(brdArray[i][tY]);
                            
                            for (Piece p : blockers) {
                                if (testMove(p, brdArray[i][tY])) {
                                    blockable = true;
                                }
                            }
                        }
                    }
                }
                
                if (tX > kX && tY > kY) {
                    for (int i = tX - 1; i > kX; i--) {
                        tY--;
                        List<Piece> blks = 
                                blockMoves.get(brdArray[i][tY]);
                        ConcurrentLinkedDeque<Piece> blockers = 
                                new ConcurrentLinkedDeque<Piece>();
                        blockers.addAll(blks);
                        
                        if (!blockers.isEmpty()) {
                            movableSquares.add(brdArray[i][tY]);
                            
                            for (Piece p : blockers) {
                                if (testMove(p, brdArray[i][tY])) {
                                    blockable = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return blockable;
    }
    
    /**
     * Method to get a list of allowable squares that the player can move.
     * Defaults to all squares, but limits available squares if player is in
     * check.
     * @param b boolean representing whether it's white player's turn (if yes,
     * true)
     * @return List of squares that the player can move into.
     */
    public List<Square> getAllowableSquares(boolean b) {
        movableSquares.removeAll(movableSquares);
        if (whiteInCheck()) {
            whiteCheckMated();
        } else if (blackInCheck()) {
            blackCheckMated();
        }
        return movableSquares;
    }
    
    /**
     * Tests a move a player is about to make to prevent making an illegal move
     * that puts the player in check.
     * @param p Piece moved
     * @param sq Square to which p is about to move
     * @return false if move would cause a check
     */
    public boolean testMove(Piece p, Square sq) {
        Piece c = sq.getOccupyingPiece();
        
        boolean movetest = true;
        Square init = p.getPosition();
        
        p.move(sq);
        update();
        
        if (p.getColor() == 1 && blackInCheck()) movetest = false;
        else if (p.getColor() == 0 && whiteInCheck()) movetest = false;
        
        p.move(init);
        if (c != null) sq.setOccupyingPiece(c);;
        
        update();
        
        movableSquares.addAll(squares);
        return movetest;
    }

}

