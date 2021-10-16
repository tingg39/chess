package piece;

/**
 * 
 * @author Kshitij Bafna
 * @author Ting Yao
 *
 */
public class Pawn extends Piece
{
	public boolean enpassant = false;
	/**
	 * 3-arg constructor to initialize a new Pawn object
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @param c - color
	 */

	public Pawn(int x, int y, boolean c)
	{
		xpos = x;
		ypos = y;
		color = c;
	}
	
	/**
	 * check if the Pawn's move is legal
	 * check if the speical move enpassant is legal
	 * @param b - the chess board
	 * @param oldx - the original x coordinate for the piece that is going to be moved
	 * @param oldy - the original y coordinate for the piece that is going to be moved
	 * @param newx - the x coordinate the piece is moving to
	 * @param newy - the y coordinate the piece is moving to
	 * @return true if the move is legal, otherwise return false
	 */
	public boolean isValidMove(Box[][] b, int oldx, int oldy, int newx, int newy)
	{
		int xdistance = newx - oldx;
		int ydistance = newy - oldy;
		boolean c = b[oldx][oldy].getPiece().getColor();
		
		if((Math.abs(xdistance) + Math.abs(ydistance)) > 2)
			return false;
		
		if(c == false)
		{
			if(xdistance <= 0)
				return false;
		}else if(c == true)
		{
			if(xdistance >= 0)
				return false;
		}
		
		if(Math.abs(ydistance) == 1 && Math.abs(xdistance) == 1)
		{
			if(b[newx][newy].getPiece() == null)
			{
				if(!color)
				{
					if(b[newx-1][newy].getPiece() != null && b[newx-1][newy].getPiece().color != color && b[newx-1][newy].getPiece() instanceof Pawn && ((Pawn)b[newx-1][newy].getPiece()).enpassant)
					{
						return true;
					}
				}
				else if(color)
				{
					if(b[newx+1][newy].getPiece() != null && b[newx+1][newy].getPiece().color != color && b[newx+1][newy].getPiece() instanceof Pawn && ((Pawn)b[newx+1][newy].getPiece()).enpassant)
					{
						return true;
					}
				}
				return false;
			}
		}
		
		if(Math.abs(xdistance) == 2)
		{
			if(b[newx][newy].getPiece() != null)
				return false;
			if(c == true)
			{
				if(oldx != 6)
					return false;
				else
				{
					if(newy-1 >=0 && b[newx][newy-1].getPiece() != null && b[newx][newy-1].getPiece().color != color && b[newx][newy-1].getPiece() instanceof Pawn)
						enpassant = true;
					else if(newy+1 <= 7 && b[newx][newy+1].getPiece() != null && b[newx][newy+1].getPiece().color != color && b[newx][newy+1].getPiece() instanceof Pawn)
						enpassant = true;
				}
			}else
			{
				if(oldx != 1)
					return false;
				else
				{
					if(newy-1 >=0 && b[newx][newy-1].getPiece() != null && b[newx][newy-1].getPiece().color != color && b[newx][newy-1].getPiece() instanceof Pawn)
						enpassant = true;
					else if(newy+1 <= 7 && b[newx][newy+1].getPiece() != null && b[newx][newy+1].getPiece().color != color && b[newx][newy+1].getPiece() instanceof Pawn)
						enpassant = true;					
				}
			}
		}
		
		if(Math.abs(xdistance) == 1 && Math.abs(ydistance) == 0)
		{
			if(b[newx][newy].getPiece() != null)
			{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * check if opponent is under check after the move
	 * @param b - the chess board
	 * @param newx - the x coordinate the piece is moving to
	 * @param newy - the y coordinate the piece is moving to
	 * @param p - which player's turn
	 * @return true if opponent under check, otherwise return false
	 */
	public boolean check(Box[][] b, int newx, int newy, boolean p)
	{
		if(p == true)
		{
			if(newx -1  >= 0 && newy -1 >= 0)
			{
				if(b[newx-1][newy-1].getPiece() != null)
				{
					if(b[newx-1][newy-1].getPiece() instanceof King && b[newx-1][newy-1].getPiece().getColor() == false)
					{
						return true;
					}
				}
			}
			
			if(newx - 1 >= 0 && newy + 1 < 8)
			{
				if(b[newx-1][newy+1].getPiece() != null)
				{
					if(b[newx-1][newy+1].getPiece() instanceof King && b[newx-1][newy+1].getPiece().getColor() == false)
					{
						return true;
					}
				}
			}
		}else
		{
			if(newx + 1< 8 && newy -1 >= 0)
			{
				if(b[newx+1][newy-1].getPiece() != null)
				{
					if(b[newx+1][newy-1].getPiece() instanceof King && b[newx+1][newy-1].getPiece().getColor() == true)
					{
						return true;
					}
				}
			}
			
			if(newx + 1 < 8 && newy + 1 < 8)
			{
				if(b[newx+1][newy+1].getPiece() != null)
				{
					if(b[newx+1][newy+1].getPiece() instanceof King && b[newx+1][newy+1].getPiece().getColor() == true)
					{
						return true;
					}
				}
			}
		}
		return false;
	}
}