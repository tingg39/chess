package piece;

/**
 * 
 * @author Kshitij Bafna
 * @author Ting Yao
 *
 */
public class Box
{
	/**
	 * for every box it has to have a color (BLACK if checkers and WHITE if blank) and a piece
	 */
	private Color bcolor;
	private Piece piece = null;
	
	/**
	 * One arg constructor for box to assign a piece to the box
	 * @param p - Piece
	 */
	public Box(Piece p)
	{
		piece = p;
	}
	
	/**
	 * Sets the color of the box for the chess board to be printed
	 * @param c - Color
	 */
	public void setColor(Color c)
	{
		bcolor = c;
	}
	
	/**
	 * Return the color of the box
	 * @return
	 */
	public Color getColor()
	{
		return bcolor;
	}
	
	/**
	 * Set a new piece to the box
	 * @param p
	 */
	public void setPiece(Piece p)
	{
		piece = p;
	}
	
	/**
	 * Get the piece inside of the box
	 * @return
	 */
	public Piece getPiece()
	{
		if(piece == null)
			return null;
		return piece;
	}
}