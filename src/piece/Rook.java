package piece;

/**
 * 
 * @author Kshitij Bafna
 * @author Ting Yao
 *
 */
public class Rook extends Piece
{
	/**
	 * boolean variable to determine if the king moved already during the game
	 */
	public boolean moved = false;
	
	/**
	 * 3-arg constructor to initialize a new Queen object
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @param c - color
	 */
	public Rook(int x, int y, boolean c)
	{
		xpos = x;
		ypos = y;
		color = c;
	}
	
	/**
	 * check if the Rook's move is legal
	 * @param b - the chess board
	 * @param oldx - the original x coordinate for the piece that is going to be moved
	 * @param oldy - the original y coordinate for the piece that is going to be moved
	 * @param newx - the x coordinate the piece is moving to
	 * @param newy - the y coordinate the piece is moving to
	 * @return true if the move is legal, otherwise return false
	 */
	public boolean isValidMove(Box[][] b, int oldx, int oldy, int newx, int newy)
	{
		if(((oldx != newx) && (oldy != newy)))
		{
			return false;
		}
		
		if(oldx == newx)
		{
			if(oldy > newy)
			{
				for(int i = oldy - 1; i > newy; i--)
				{
					if(b[oldx][i].getPiece() != null)
						return false;
				}
			}else
			{
				for(int i = oldy + 1; i < newy; i++)
				{
					if(b[oldx][i].getPiece() != null)
						return false;
				}
			}
		}else
		{
			if(oldx > newx)
			{
				for(int i = oldx - 1; i > newx; i--)
				{
					if(b[i][oldy].getPiece() != null)
						return false;
				}
			}else
			{
				for(int i = oldx + 1; i < newx; i++)
				{
					if(b[i][oldy].getPiece() != null)
						return false;
				}
			}
		}
		moved = true;
		return true;
	}
	
	/**
	 * Check if the Rook's move put opponent under check
	 * @param b - the chess board
	 * @param newx - the x coordinate the piece is moving to
	 * @param newy - the y coordinate the piece is moving to
	 * @param p - which player's turn
	 * @return true if opponent under check, otherwise return false
	 */
	public boolean check(Box[][] b, int newx, int newy, boolean p)
	{
		for(int i = newx - 1; i >= 0; i--)
		{
			if(b[i][newy].getPiece() != null)
			{
				if(b[i][newy].getPiece() instanceof King && b[i][newy].getPiece().getColor() != p)
				{
					return true;
				}else
				{
					break;
				}
			}
		}
		
		for(int i = newx + 1; i < 8; i++)
		{
			if(b[i][newy].getPiece() != null)
			{
				if(b[i][newy].getPiece() instanceof King && b[i][newy].getPiece().getColor() != p)
				{
					return true;
				}else
				{
					break;
				}
			}
		}
		
		for(int i = newy - 1; i >= 0; i--)
		{
			if(b[newx][i].getPiece() != null)
			{
				if(b[newx][i].getPiece() instanceof King && b[newx][i].getPiece().getColor() != p)
				{
					return true;
				}else
				{
					break;
				}
			}
		}
		
		for(int i = newy + 1; i < 8; i++)
		{
			if(b[newx][i].getPiece() != null)
			{
				if(b[newx][i].getPiece() instanceof King && b[newx][i].getPiece().getColor() != p)
				{
					return true;
				}else
				{
					break;
				}
			}
		}
		
		return false;
	}
	
}