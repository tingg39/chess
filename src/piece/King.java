package piece;

/**
 * 
 * @author Kshitij Bafna
 * @author Ting Yao
 *
 */
public class King extends Piece
{
	/**
	 * boolean variable to determine if the king moved already during the game
	 */
	public boolean moved = false;
	
	/**
	 * 3-arg constructor to assign the king its x coordinate, y coordinate and color
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @param c - color
	 */
	public King(int x, int y, boolean c)
	{
		xpos = x;
		ypos = y;
		color = c;
	}
	
	/**
	 * Check is the movement is legal for a king
	 * @param b - the chess board
	 * @param oldx - the original x coordinate for the piece that is going to be moved
	 * @param oldy - the original y coordinate for the piece that is going to be moved
	 * @param newx - the x coordinate the piece is moving to
	 * @param newy - the y coordinate the piece is moving to
	 * @return true if the move is legal, otherwise return false
	 */
	public boolean isValidMove(Box[][] b, int oldx, int oldy, int newx, int newy)
	{
		int xdistance = Math.abs(newx - oldx);
		int ydistance = Math.abs(newy - oldy);
		
		if((xdistance + ydistance) > 2 || xdistance == 2 || ydistance == 2)
		{
			return false;

		}
		moved = true;
		return true;
	}
	
	/**
	 * check if the king's move puts opponent under check
	 * @param b - the chess board
	 * @param newx - the x coordinate the piece is moving to
	 * @param newy - the y coordinate the piece is moving to
	 * @param p - which player's turn
	 * @return true if opponent under check, otherwise return false
	 */
	public boolean check(Box[][] b, int newx, int newy, boolean p)
	{
		if(newx - 1 >= 0)
		{
			if(b[newx-1][newy].getPiece() != null)
			{
				if(b[newx-1][newy].getPiece() instanceof King && b[newx-1][newy].getPiece().getColor() != p)
				{
					return true;
				}
			}
			if(newy - 1 >= 0)
			{
				if(b[newx-1][newy-1].getPiece() != null)
				{
					if(b[newx-1][newy-1].getPiece() instanceof King && b[newx-1][newy-1].getPiece().getColor() != p)
					{
						return true;
					}
				}
			}
			if(newy + 1 < 8)
			{
				if(b[newx-1][newy+1].getPiece() != null)
				{
					if(b[newx-1][newy+1].getPiece() instanceof King && b[newx-1][newy+1].getPiece().getColor() != p)
					{
						return true;
					}
				}
			}
		}
		
		if(newx + 1 < 8)
		{
			if(b[newx+1][newy].getPiece() != null)
			{
				if(b[newx+1][newy].getPiece() instanceof King && b[newx+1][newy].getPiece().getColor() != p)
				{
					return true;
				}
			}
			if(newy - 1 >= 0)
			{
				if(b[newx+1][newy-1].getPiece() != null)
				{
					if(b[newx+1][newy-1].getPiece() instanceof King && b[newx+1][newy-1].getPiece().getColor() != p)
					{
						return true;
					}
				}
			}
			if(newy + 1 < 8)
			{
				if(b[newx+1][newy+1].getPiece() != null)
				{
					if(b[newx+1][newy+1].getPiece() instanceof King && b[newx+1][newy+1].getPiece().getColor() != p)
					{
						return true;
					}
				}
			}
		}
		
		if(newy - 1 >= 0)
		{
			if(b[newx][newy-1].getPiece() != null)
			{
				if(b[newx][newy-1].getPiece() instanceof King && b[newx][newy-1].getPiece().getColor() != p)
				{
					return true;
				}
			}
		}
		
		if(newy + 1 < 8)
		{
			if(b[newx][newy+1].getPiece() != null)
			{
				if(b[newx][newy+1].getPiece() instanceof King && b[newx][newy+1].getPiece().getColor() != p)
				{
					return true;
				}
			}
		}
		return false;
	}
}