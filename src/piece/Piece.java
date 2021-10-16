package piece;

/**
 * 
 * @author Kshitij Bafna
 * @author Ting Yao
 *
 */

/**
 * Abstract class to be extended by every piece class (Rook, Knight, Bishop, Quuen, King, and Pawn)
 */
public abstract class Piece
{
	/**
	 * variables that every piece class will have
	 * x coordinate
	 * y coordinate
	 * color of the piece
	 */
	public int xpos;
	public int ypos;
	public boolean color;
	
	/**
	 * get the color of the current Piece
	 * @return the color of the Piece
	 */
	public boolean getColor()
	{
		return color;
	}
	
	/**
	 * abstract method that needs to be implemented in every class that extends Piece
	 * Check if the move by the Piece is legal
	 * @param b - the chess board
	 * @param oldx - the original x coordinate for the piece that is going to be moved
	 * @param oldy - the original y coordinate for the piece that is going to be moved
	 * @param newx - the x coordinate the piece is moving to
	 * @param newy - the y coordinate the piece is moving to
	 * @return true if valid, false otherwise
	 */
	public abstract boolean isValidMove(Box[][] b, int oldx, int oldy, int newx, int newy);
	
	/**
	 * abstract method that needs to be implemented in every class that extends Piece
	 * Check if opponent under check
	 * @param b - the chess board
	 * @param newx - the x coordinate the piece is moving to
	 * @param newy - the y coordinate the piece is moving to
	 * @param p - which player's turn
	 * @return true if opponent under check, false otherwise
	 */
	public abstract boolean check(Box[][] b, int newx, int newy, boolean p);
}