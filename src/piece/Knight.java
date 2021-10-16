package piece;

/**
 * 
 * @author Kshitij Bafna
 * @author Ting Yao
 *
 */
public class Knight extends Piece
{
	
	/**
	 * 3-arg constructor to initialize a new Knight object
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @param c - color
	 */
	public Knight(int x, int y, boolean c)
	{
		xpos = x;
		ypos = y;
		color = c;
	}
	
	
	/**
	 * check if the Knight's move is legal
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
		if(xdistance != 1 && xdistance != 2)
		{
			return false;
		}
		int ydistance = Math.abs(newy - oldy);
		if(ydistance != 1 && ydistance != 2)
		{
			return false;
		}
		if((xdistance + ydistance)  != 3)
		{
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * Check if the Knight's move put opponent under check
	 * @param b - the chess board
	 * @param newx - the x coordinate the piece is moving to
	 * @param newy - the y coordinate the piece is moving to
	 * @param p - which player's turn
	 * @return true if opponent under check, otherwise return false
	 */
	public boolean check(Box[][] b, int newx, int newy, boolean p)
	{
		if(newx - 2 >= 0)
		{
			if(newy - 1 >= 0)
			{
					if(b[newx-2][newy-1].getPiece() != null)
					{
						if(b[newx-2][newy-1].getPiece() instanceof King && b[newx-2][newy-1].getPiece().getColor() != p)
						{
							return true;
						}
					}
			}
			
			if(newy + 1 < 8)
			{
				if(b[newx-2][newy+1].getPiece() != null)
				{
					if(b[newx-2][newy+1].getPiece() instanceof King && b[newx-2][newy+1].getPiece().getColor() != p)
					{
						return true;
					}
				}
			}
		}
		
		if(newy + 2 < 8)
		{
			if(newx - 1 >= 0)
			{
				if(b[newx-1][newy+2].getPiece() != null)
				{
					if(b[newx-1][newy+2].getPiece() instanceof King && b[newx-1][newy+2].getPiece().getColor() != p)
					{
						return true;
					}
				}
			}
			
			if(newx + 1 < 8)
			{
				if(b[newx+1][newy+2].getPiece() != null)
				{
					if(b[newx+1][newy+2].getPiece() instanceof King && b[newx+1][newy+2].getPiece().getColor() != p)
					{
						return true;
					}
				}
			}
		}
		
		if(newx + 2 < 8)
		{
			if(newy - 1 >= 0)
			{
					if(b[newx+2][newy-1].getPiece() != null)
					{
						if(b[newx+2][newy-1].getPiece() instanceof King && b[newx+2][newy-1].getPiece().getColor() != p)
						{
							return true;
						}
					}
			}
			
			if(newy + 1 < 8)
			{
				if(b[newx+2][newy+1].getPiece() != null)
				{
					if(b[newx+2][newy+1].getPiece() instanceof King && b[newx+2][newy+1].getPiece().getColor() != p)
					{
						return true;
					}
				}
			}
		}
		
		if(newy - 2 >= 0)
		{
			if(newx - 1 >= 0)
			{
				if(b[newx-1][newy-2].getPiece() != null)
				{
					if(b[newx-1][newy-2].getPiece() instanceof King && b[newx-1][newy-2].getPiece().getColor() != p)
					{
						return true;
					}
				}
			}
			
			if(newx + 1 < 8)
			{
				if(b[newx+1][newy-2].getPiece() != null)
				{
					if(b[newx+1][newy-2].getPiece() instanceof King && b[newx+1][newy-2].getPiece().getColor() != p)
					{
						return true;
					}
				}
			}
		}
		return false;
	}
}