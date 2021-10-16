package piece;

/**
 * 
 * @author Kshitij Bafna
 * @author Ting Yao
 *
 */
public class Queen extends Piece
{
	/**
	 * 3-arg constructor to initialize a new Queen object
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @param c - color
	 */
	public Queen(int x, int y, boolean c)
	{
		xpos = x;
		ypos = y;
		color = c;
	}
	
	/**
	 * check if the Queen's move is legal
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
		if(xdistance != ydistance)
		{
			if(xdistance != 0 && ydistance != 0)
			{
				return false;
			}
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
		}else if(oldy == newy)
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
	 * Check if the Queen's move put opponent under check
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
		
		int xpos = newx + 1;
		int ypos = newy + 1;
		while(xpos < 8 && ypos < 8)
		{
			if(b[xpos][ypos].getPiece() != null)
			{
				if(b[xpos][ypos].getPiece() instanceof King && b[xpos][ypos].getPiece().getColor() != p)
				{
					return true;
				}else 
				{
					break;
				}
			}
			xpos += 1;
			ypos += 1;
		}
		xpos = newx + 1;
		ypos = newy - 1;
		while(xpos < 8 && ypos >= 0)
		{
			if(b[xpos][ypos].getPiece() != null)
			{
				if(b[xpos][ypos].getPiece() instanceof King && b[xpos][ypos].getPiece().getColor() != p)
				{
					return true;
				}else 
				{
					break;
				}
			}
			xpos += 1;
			ypos -= 1;
		}
		xpos = newx - 1;
		ypos = newy + 1;
		while(xpos >= 0 && ypos < 8)
		{
			if(b[xpos][ypos].getPiece() != null)
			{
				if(b[xpos][ypos].getPiece() instanceof King && b[xpos][ypos].getPiece().getColor() != p)
				{
					return true;
				}else 
				{
					break;
				}
			}
			xpos -= 1;
			ypos += 1;
		}
		
		xpos = newx - 1;
		ypos = newy - 1;
		while(xpos >= 0 && ypos >= 0)
		{
			if(b[xpos][ypos].getPiece() != null)
			{
				if(b[xpos][ypos].getPiece() instanceof King && b[xpos][ypos].getPiece().getColor() != p)
				{
					return true;
				}else 
				{
					break;
				}
			}
			xpos -= 1;
			ypos -= 1;
		}
		return false;
	}
}