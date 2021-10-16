package piece;

/**
 * 
 * @author Kshitij Bafna
 * @author Ting Yao
 *
 */
public class Bishop extends Piece
{
	/**
	 * Constructor for the bishop class.
	 * Assigns the x coordinate, y coordinate and the color of the Bishop piece
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @param c - color
	 */
	public Bishop(int x, int y, boolean c)
	{
		xpos = x;
		ypos = y;
		color = c;
	}
	
	/**
	 * Check if the movement for bishop is legal
	 * @param b - the chess board
	 * @param oldx - the original x coordinate for the piece that is going to be moved
	 * @param oldy - the original y coordinate for the piece that is going to be moved
	 * @param newx - the x coordinate the piece is moving to
	 * @param newy - the y coordinate the piece is moving to
	 * @return true if the move is valid, otherwise return false
	 */
	public boolean isValidMove(Box[][] b, int oldx, int oldy, int newx, int newy)
	{
		int xdistance = Math.abs(newx - oldx);
		int ydistance = Math.abs(newy - oldy);
		
		if(xdistance != ydistance)
			return false;
		
		int xdirection = newx - oldx;
		int ydirection = newy - oldy;
		if(xdirection > 0 && ydirection > 0)
		{
			int xpos = oldx + 1;
			int ypos = oldy + 1;
			while(xpos < newx && ypos < newy)
			{
				if(b[xpos][ypos].getPiece() != null)
				{
					return false;
				}
				xpos += 1;
				ypos += 1;
			}
		}else if(xdirection > 0 && ydirection < 0)
		{
			int xpos = oldx + 1;
			int ypos = oldy - 1;
			while(xpos < newx && ypos > 0)
			{
				if(b[xpos][ypos].getPiece() != null)
				{
					return false;
				}
				xpos += 1;
				ypos -= 1;
			}
		}else if(xdirection < 0 && ydirection > 0)
		{
			int xpos = oldx - 1;
			int ypos = oldy + 1;
			while(xpos < newx && ypos > 0)
			{
				if(b[xpos][ypos].getPiece() != null)
				{
					return false;
				}
				xpos -= 1;
				ypos += 1;
			}
		}else if(xdirection < 0 && ydirection < 0)
		{
			int xpos = oldx - 1;
			int ypos = oldy - 1;
			while(xpos < newx && ypos > 0)
			{
				if(b[xpos][ypos].getPiece() != null)
				{
					return false;
				}
				xpos -= 1;
				ypos -= 1;
			}
		}
		
		return true;
	}
	
	/**
	 * Check if your move put the opponent under check
	 * @param b - the chess board
	 * @param newx - the x coordinate the piece is moving to
	 * @param newy - the y coordinate the piece is moving to
	 * @param p - which player's turn
	 * @return true if opponent under check, otherwise return false
	 */
	public boolean check(Box[][] b, int newx, int newy, boolean p)
	{
		for(int i = 0; i < 4; i++)
		{
			if(i == 0)
			{
				//right up
				int dx = 1; int dy = 1;
				while(newx+dx <= 7 && newy-dy >= 0)
				{
					if(b[newx+dx][newy-dy].getPiece() != null)
					{
						if(b[newx+dx][newy-dy].getPiece() instanceof King && b[newx+dx][newy-dy].getPiece().color != p)
							return true;
						else
							break;
					}
					dx++;
					dy++;
				}
			}
			else if(i == 1)
			{
				//right down
				int dx = 1; int dy = 1;
					while(newx+dx <= 7 && newy+dy <= 7)
					{
						if(b[newx+dx][newy+dy].getPiece() != null)
						{
							if(b[newx+dx][newy+dy].getPiece() instanceof King && b[newx+dx][newy+dy].getPiece().color != p)
								return true;
							else
								break;
						}
						dx++; dy++;
					}
			}
			else if(i == 2)
			{
				//left down
				int dx = 1; int dy = 1;
				while( newx-dx >= 0 && newy+dy <= 7)
				{
					if(b[newx-dx][newy+dy].getPiece() != null)
					{
						if(b[newx-dx][newy+dy].getPiece() instanceof King && b[newx-dx][newy+dy].getPiece().color != p)
							return true;
						else
							break;
					}
					dx++; dy++;
				}
			}
			else
			{
				//left up
				int dx = 1; int dy = 1;
				while( newy-dy >= 0 && newx-dx >= 0)
				{
					if(b[newx-dx][newy-dy].getPiece() != null)
					{
						if(b[newx-dx][newy-dy].getPiece() instanceof King && b[newx-dx][newy-dy].getPiece().color != p)
							return true;
						else
							break;
					}
					dx++; dy++;
				}
			}
		}
		return false;
	}
}