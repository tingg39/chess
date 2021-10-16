package chess;
import piece.*;

/**
 * 
 * @author Kshitij Bafna
 * @author Ting Yao
 *
 */
public class Board
{	
	/**
	 * records the coordinates of both white king and black king
	 */
	int wkingx, wkingy, bkingx, bkingy;
	
	/**
	 * Initialize the chess board to get ready for the game to start
	 * @return the initialized chess board
	 */
	public Box[][] initialize()
	{
		Box[][] board = new Box[8][8];
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				board[i][j] = new Box(null);
			}
		}
		int col;
		for(col = 1; col < 8; col += 2)
		{
			board[0][col].setColor(Color.BLACK);
			board[2][col].setColor(Color.BLACK);
			board[4][col].setColor(Color.BLACK);
			board[6][col].setColor(Color.BLACK);
			board[1][col].setColor(Color.WHITE);
			board[3][col].setColor(Color.WHITE);
			board[5][col].setColor(Color.WHITE);
			board[7][col].setColor(Color.WHITE);
		}
		for(col = 0; col < 8; col += 2)
		{
			board[0][col].setColor(Color.WHITE);
			board[2][col].setColor(Color.WHITE);
			board[4][col].setColor(Color.WHITE);
			board[6][col].setColor(Color.WHITE);
			board[1][col].setColor(Color.BLACK);
			board[3][col].setColor(Color.BLACK);
			board[5][col].setColor(Color.BLACK);
			board[7][col].setColor(Color.BLACK);
		}
		board[0][0].setPiece(new Rook(0, 0, false));
		board[0][1].setPiece(new Knight(0, 1, false));
		board[0][2].setPiece(new Bishop(0, 2, false));
		board[0][3].setPiece(new Queen(0, 3, false));
		board[0][4].setPiece(new King(0, 4, false));
		bkingx = 0;
		bkingy = 4;
		board[0][5].setPiece(new Bishop(0, 5, false));
		board[0][6].setPiece(new Knight(0, 6, false));
		board[0][7].setPiece(new Rook(0, 7, false));
		for(col = 0; col < 8; col++)
		{
			board[1][col].setPiece(new Pawn(1, col, false));
			board[6][col].setPiece(new Pawn(6, col, true));
		}
		board[7][0].setPiece(new Rook(7, 0, true));
		board[7][1].setPiece(new Knight(7, 1, true));
		board[7][2].setPiece(new Bishop(7, 2, true));
		board[7][3].setPiece(new Queen(7, 3, true));
		board[7][4].setPiece(new King(7, 4, true));
		wkingx = 7;
		wkingy = 4;
		board[7][5].setPiece(new Bishop(7, 5, true));
		board[7][6].setPiece(new Knight(7, 6, true));
		board[7][7].setPiece(new Rook(7, 7, true));
		
		
		return board;
	}
	
	/**
	 * Print out the chess board at the start of the game and every time a piece is moved
	 * @param board - the chess board
	 */
	public void printboard(Box[][] board)
	{
		int row;
		int col;
		int count = 8;
		char xaxis = 'a';
		for(row = 0; row < 8; row++)
		{
			for(col = 0; col < 8; col++)
			{
				Piece p = board[row][col].getPiece();
				if(p == null)
				{
					Color c = board[row][col].getColor();
					if(c == Color.BLACK)
					{
						System.out.print("## ");
					}else
					{
						System.out.print("   ");
					}
				}else 
				{
					boolean c = p.getColor();
					if(p instanceof Rook)
					{
						if(c == true)
						{
							System.out.print("wR ");
						}else
						{
							System.out.print("bR ");
						}
					}else if(p instanceof Knight)
					{
						if(c == true)
						{
							System.out.print("wN ");
						}else
						{
							System.out.print("bN ");
						}
					}else if(p instanceof Bishop)
					{
						if(c == true)
						{
							System.out.print("wB ");
						}else
						{
							System.out.print("bB ");
						}
					}else if(p instanceof Queen)
					{
						if(c == true)
						{
							System.out.print("wQ ");
						}else
						{
							System.out.print("bQ ");
						}
					}else if(p instanceof King)
					{
						if(c == true)
						{
							System.out.print("wK ");
						}else
						{
							System.out.print("bK ");
						}
					}else if(p instanceof Pawn)
					{
						if(c == true)
						{
							System.out.print("wP ");
						}else
						{
							System.out.print("bP ");
						}
					}
				}
			}
			System.out.println(count);
			count--;
		}
		for(col = 0; col < 8; col++)
		{
			System.out.print(" " + xaxis + " ");
			xaxis = (char)(xaxis + 1);
		}
		System.out.println();
		System.out.println();
	}
	
	/**
	 * Check for invalid inputs
	 * Check for special move castling
	 * Check if the movement is legal
	 * If the movement is legal, check if current player's king is under check. If it is under check
	 * then it is an illegal move
	 * If the move is legal, check if the current player put the opponent under check or it is checkmate after the move
	 * Also checked here if the speical move enpassant is legal
	 * @param board - the chess board
	 * @param oldx - the original x coordinate for the piece that is going to be moved
	 * @param oldy - the original y coordinate for the piece that is going to be moved
	 * @param newx - the x coordinate the piece is moving to
	 * @param newy - the y coordinate the piece is moving to
	 * @param p - player's turn
	 * @return 0 if it is just a normal move, 1 if checkmate, 4 if a king is dead, and 11 if opponent under check
	 */
	public int move(Box[][] board, int oldx, int oldy, int newx, int newy, boolean p)
	{
		if(oldx == newx && oldy == newy)
		{
			return -1;
		}
		if(oldx > 7 || oldy > 7 || newx > 7 || newy > 7 || oldx < 0 || oldy < 0 || newx < 0 || newy < 0)
		{
			return -1;
		}
		Piece oldp = board[oldx][oldy].getPiece();
		if(oldp == null)
		{
			return -1;
		}else
		{
			if(oldp.getColor() != p)
			{
				return -1;
			}
		}
		Piece newp = board[newx][newy].getPiece();
		if(newp != null)
		{
			if(oldp.getColor() == newp.getColor())
			{
				return -1;
			}
		}
		if(oldp instanceof King)
		{
			if(isCastlingMove(board, oldx, oldy, newx, newy))
			{
				board[newx][newy].setPiece(oldp);
				if(newy - oldy > 0)
				{
					// is a right side castle, rook to be placed one block left
					board[newx][newy-1].setPiece(board[newx][7].getPiece());
					board[newx][7].setPiece(null);
				}
				else
				{
					// is a left castle, rook to be placed one block right
					board[newx][newy+1].setPiece(board[newx][0].getPiece());
					board[newx][0].setPiece(null);
				}
				board[oldx][oldy].setPiece(null);

				//
				if(p)
				{
					for(int i = 0; i < 8; i++)
					{
						if(board[3][i].getPiece() != null && board[3][i].getPiece() instanceof Pawn)
							((Pawn)board[3][i].getPiece()).enpassant = false;
					}
				}
				else
				{
					for(int i = 0; i < 8; i++)
					{
						if(board[4][i].getPiece() != null && board[4][i].getPiece() instanceof Pawn)
							((Pawn)board[4][i].getPiece()).enpassant = false;
					}
				}
				//
				return 0;
			}
		}

		boolean valid = oldp.isValidMove(board, oldx, oldy, newx, newy);
		if(valid == false)
		{
			return -1;
		}

		int checked = opponentUnderCheck(board, oldx, oldy, newx, newy, p);
		if(checked == -1)
		{
			return -1;
		}else if(checked == 1)
		{
			if(checkmate(board, oldx, oldy, newx, newy, p) == true)
			{
				if(board[oldx][oldy].getPiece() instanceof Pawn)
				{
					if(p == true)
					{
						if(newx == 0)
						{
							board[newx][newy].setPiece(new Queen(newx, newy, p));
							board[oldx][oldy].setPiece(null);
							return 1;
						}

					}else
					{
						if(newx == 7)
						{
							board[newx][newy].setPiece(new Queen(newx, newy, p));
							board[oldx][oldy].setPiece(null);
							return 1;
						}
					}
				}else
				{
					board[newx][newy].setPiece(oldp);
					board[oldx][oldy].setPiece(null);
					return 1;
				}	
			}
			
			
			if(board[oldx][oldy].getPiece() instanceof Pawn)
			{
				if(isEnpassant(board, oldx, oldy, newx, newy, p))
				{
					if(p)
					{
						board[newx][newy].setPiece(oldp);
						board[oldx][oldy].setPiece(null);
						board[newx+1][newy].setPiece(null);
					}
					else
					{
						board[newx][newy].setPiece(oldp);
						board[oldx][oldy].setPiece(null);
						board[newx-1][newy].setPiece(null);
					}
					//
					if(p)
					{
						for(int i = 0; i < 8; i++)
						{
							if(board[3][i].getPiece() != null && board[3][i].getPiece() instanceof Pawn)
								((Pawn)board[3][i].getPiece()).enpassant = false;
						}
					}
					else
					{
						for(int i = 0; i < 8; i++)
						{
							if(board[4][i].getPiece() != null && board[4][i].getPiece() instanceof Pawn)
								((Pawn)board[4][i].getPiece()).enpassant = false;
						}
					}
					//
					return 0;
				}
				if(p == true)
				{
					if(newx == 0)
					{
						board[newx][newy].setPiece(new Queen(newx, newy, p));
						board[oldx][oldy].setPiece(null);
						return 11;
					}else
					{
						board[newx][newy].setPiece(oldp);
						board[oldx][oldy].setPiece(null);
						return 11;
					}
				}else
				{
					if(newx == 7)
					{
						board[newx][newy].setPiece(new Queen(newx, newy, p));
						board[oldx][oldy].setPiece(null);
						return 11;
					}else
					{
						board[newx][newy].setPiece(oldp);
						board[oldx][oldy].setPiece(null);
						return 11;
					}
				}
				
			}else
			{
				board[newx][newy].setPiece(oldp);
				if(oldp instanceof King)
				{
					if(p == true)
					{
						wkingx = newx;
						wkingy = newy;
					}else
					{
						bkingx = newx;
						bkingy = newy;
					}
				}
				board[oldx][oldy].setPiece(null);
				//
				if(p)
				{
					for(int i = 0; i < 8; i++)
					{
						if(board[3][i].getPiece() != null && board[3][i].getPiece() instanceof Pawn)
							((Pawn)board[3][i].getPiece()).enpassant = false;
					}
				}
				else
				{
					for(int i = 0; i < 8; i++)
					{
						if(board[4][i].getPiece() != null && board[4][i].getPiece() instanceof Pawn)
							((Pawn)board[4][i].getPiece()).enpassant = false;
					}
				}
				//
				return 11;
			}
		}else if(checked == 0)
		{
			if(isEnpassant(board, oldx, oldy, newx, newy, p))
			{
				if(p)
				{
					board[newx][newy].setPiece(oldp);
					board[oldx][oldy].setPiece(null);
					board[newx+1][newy].setPiece(null);
				}
				else
				{
					board[newx][newy].setPiece(oldp);
					board[oldx][oldy].setPiece(null);
					board[newx-1][newy].setPiece(null);
				}
				//
				if(p)
				{
					for(int i = 0; i < 8; i++)
					{
						if(board[3][i].getPiece() != null && board[3][i].getPiece() instanceof Pawn)
							((Pawn)board[3][i].getPiece()).enpassant = false;
					}
				}
				else
				{
					for(int i = 0; i < 8; i++)
					{
						if(board[4][i].getPiece() != null && board[4][i].getPiece() instanceof Pawn)
							((Pawn)board[4][i].getPiece()).enpassant = false;
					}
				}
				//
				return 0;
			}
			if(board[oldx][oldy].getPiece() instanceof Pawn)
			{
				if(p == true)
				{
					if(newx == 0)
					{
						if(board[newx][newy].getPiece() instanceof King)
						{
							return 4;
						}else
						{
							board[newx][newy].setPiece(new Queen(newx, newy, p));
							board[oldx][oldy].setPiece(null);
							//
							if(p)
							{
								for(int i = 0; i < 8; i++)
								{
									if(board[3][i].getPiece() != null && board[3][i].getPiece() instanceof Pawn)
										((Pawn)board[3][i].getPiece()).enpassant = false;
								}
							}
							else
							{
								for(int i = 0; i < 8; i++)
								{
									if(board[4][i].getPiece() != null && board[4][i].getPiece() instanceof Pawn)
										((Pawn)board[4][i].getPiece()).enpassant = false;
								}
							}
							//
							return 0;
						}
					}else
					{
						if(board[newx][newy].getPiece() instanceof King)
						{
							return 4;
						}else
						{
							board[newx][newy].setPiece(oldp);
							board[oldx][oldy].setPiece(null);
							//
							if(p)
							{
								for(int i = 0; i < 8; i++)
								{
									if(board[3][i].getPiece() != null && board[3][i].getPiece() instanceof Pawn)
										((Pawn)board[3][i].getPiece()).enpassant = false;
								}
							}
							else
							{
								for(int i = 0; i < 8; i++)
								{
									if(board[4][i].getPiece() != null && board[4][i].getPiece() instanceof Pawn)
										((Pawn)board[4][i].getPiece()).enpassant = false;
								}
							}
							//
							return 0;
						}
					}
				}else
				{
					if(newx == 7)
					{
						if(board[newx][newy].getPiece() instanceof King)
						{
							return 4;
						}else
						{
							board[newx][newy].setPiece(new Queen(newx, newy, p));
							board[oldx][oldy].setPiece(null);
							//
							if(p)
							{
								for(int i = 0; i < 8; i++)
								{
									if(board[3][i].getPiece() != null && board[3][i].getPiece() instanceof Pawn)
										((Pawn)board[3][i].getPiece()).enpassant = false;
								}
							}
							else
							{
								for(int i = 0; i < 8; i++)
								{
									if(board[4][i].getPiece() != null && board[4][i].getPiece() instanceof Pawn)
										((Pawn)board[4][i].getPiece()).enpassant = false;
								}
							}
							//
							return 0;
						}
					}else
					{
						if(board[newx][newy].getPiece() instanceof King)
						{
							return 4;
						}else
						{
							board[newx][newy].setPiece(oldp);
							board[oldx][oldy].setPiece(null);
							//
							if(p)
							{
								for(int i = 0; i < 8; i++)
								{
									if(board[3][i].getPiece() != null && board[3][i].getPiece() instanceof Pawn)
										((Pawn)board[3][i].getPiece()).enpassant = false;
								}
							}
							else
							{
								for(int i = 0; i < 8; i++)
								{
									if(board[4][i].getPiece() != null && board[4][i].getPiece() instanceof Pawn)
										((Pawn)board[4][i].getPiece()).enpassant = false;
								}
							}
							//
							return 0;
						}
					}
				}
				
			}else
			{
				if(board[newx][newy].getPiece() instanceof King)
				{
					return 4;
				}else
				{
					board[newx][newy].setPiece(oldp);
					if(oldp instanceof King)
					{
						if(p == true)
						{
							wkingx = newx;
							wkingy = newy;
						}else
						{
							bkingx = newx;
							bkingy = newy;
						}
					}
					board[oldx][oldy].setPiece(null);
					//
					if(p)
					{
						for(int i = 0; i < 8; i++)
						{
							if(board[3][i].getPiece() != null && board[3][i].getPiece() instanceof Pawn)
								((Pawn)board[3][i].getPiece()).enpassant = false;
						}
					}
					else
					{
						for(int i = 0; i < 8; i++)
						{
							if(board[4][i].getPiece() != null && board[4][i].getPiece() instanceof Pawn)
								((Pawn)board[4][i].getPiece()).enpassant = false;
						}
					}
					//
					return 0;
				}
			}	
		}
		
		return 0;
	}
	
	/**
	 * check if the special move enpassant is valid
	 * @param b - the chess board
	 * @param oldx - the orignal x coordinate of the piece that is going to be moved
	 * @param oldy - the orignal y coordinate of the piece that is going to be moved
	 * @param newx - the new x coordinate the piece is moving to
	 * @param newy - the new y coordinate the piece is moving to
	 * @param color - which player's turn
	 * @return true if the move is legal, otherwise return false
	 */
	private boolean isEnpassant(Box[][] b, int oldx, int oldy, int newx, int newy, boolean color) {
		int xdistance = newx - oldx;
		int ydistance = newy - oldy;
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
			}
		}
		return false;
	}

	/**
	 * If both the king and the rook did not move during the game, castling is allowed if it does not put
	 * the current player's king under check
	 * @param b - the chess board
	 * @param oldx2 - the original x coordinate for the piece that is going to be moved
	 * @param oldy2 - the original y coordinate for the piece that is going to be moved
	 * @param newx2 - the x coordinate the piece is moving to
	 * @param newy2 - the y coordinate the piece is moving to
	 * @return true if successful, otherwise return false
	 */

	private boolean isCastlingMove(Box[][] b, int oldx2, int oldy2, int newx2, int newy2)
	{
		boolean leftside_castle = false;
		if(oldy2 > newy2)
			leftside_castle = true;
		if(oldx2 != newx2)
			return false;
		
		//verify king move
		int ydistance = Math.abs(newy2 - oldy2);		
		if(ydistance != 2)
			return false;
		if(((King)b[oldx2][oldy2].getPiece()).moved)
			return false;
		if(leftside_castle)
		{
			for(int i = oldy2; i >= newy2; i--)
			{
				if(b[oldx2][i].getPiece() != null)
				{	
					if(i != oldy2)
						return false;
				}
				int check = opponentUnderCheck(b, oldx2, oldy2, newx2, i, b[oldx2][oldy2].getPiece().getColor());
				if(check == -1)
					return false;
			}
		}
		else
		{
			for(int i = oldy2; i <= newy2; i++)
			{
				if(b[oldx2][i].getPiece() != null)
				{	
					if(i != oldy2)
						return false;
				}
				int check = opponentUnderCheck(b, oldx2, oldy2, newx2, i, b[oldx2][oldy2].getPiece().getColor());
				if(check == -1)
					return false;
			}
		}		
		
		//verify rook
		if(leftside_castle)
		{
			if(b[oldx2][0].getPiece() != null && b[oldx2][0].getPiece() instanceof Rook)
			{
				if(((Rook)b[oldx2][0].getPiece()).moved)
					return false;
			}
			else
				return false;
		}
		else
		{
			if(b[oldx2][7].getPiece() != null && b[oldx2][7].getPiece() instanceof Rook)
			{
				if(((Rook)b[oldx2][7].getPiece()).moved)
					return false;
			}
			else
				return false;
		}
		
		return true;
	}

	/**
	 * After finding out your movement is legal, check if the move put the current player's king under check.
	 * If the move puts the current player's king under check, return -1 and it will be an illegal move.
	 * Then we check if the current player puts his opponent under check, if that is the case return 1.
	 * If nothing happens in the move, return 0 and indicate it is a normal move.
	 * @param b - the chess board
	 * @param oldx - the original x coordinate for the piece that is going to be moved
	 * @param oldy - the original y coordinate for the piece that is going to be moved
	 * @param newx - the x coordinate the piece is moving to
	 * @param newy - the y coordinate the piece is moving to
	 * @param p - player's turn
	 * @return -1 if current player's king under check, 0 if nothing happens, 1 if opponent under check
	 */
	public int opponentUnderCheck(Box[][] b, int oldx, int oldy, int newx, int newy, boolean p)
	{
		Box[][] tempboard = tempBoard(b, oldx, oldy, newx, newy);
		if(b[oldx][oldy].getPiece() instanceof Pawn)
		{
			if(p == true)
			{
				if(newx == 0)
				{
					tempboard[newx][newy].setPiece(new Queen(newx, newy, p));
				}
			}else
			{
				if(newx == 7)
				{
					tempboard[newx][newy].setPiece(new Queen(newx, newy, p));
				}
			}
		}
		int col, row;
		boolean checked;
		for(row = 0; row < 8; row++)
		{
			for(col = 0; col < 8; col++)
			{
				if(tempboard[row][col].getPiece() != null && tempboard[row][col].getPiece().getColor() != p)
				{
					checked = tempboard[row][col].getPiece().check(tempboard, row, col, !p);
					if(checked == true)
					{
						return -1;
					}
				}
			}
		}
				
		for(row = 0; row < 8; row++)
		{
			for(col = 0; col < 8; col++)
			{
				if(tempboard[row][col].getPiece() != null && tempboard[row][col].getPiece().getColor() == p)
				{
					checked = tempboard[row][col].getPiece().check(tempboard, row, col, p);
					if(checked == true)
					{
						return 1;
					}
				}
			}
		}
		
		return 0;
	}
	
	/**
	 * For every valid move the current player's king can make, check if the king is still under check after the move.
	 * Return false if a move removes the check status for the current player.
	 * Return true if checkmate applies.
	 * @param b - the chess board
	 * @param oldx - the original x coordinate for the piece that is going to be moved
	 * @param oldy - the original y coordinate for the piece that is going to be moved
	 * @param newx - the x coordinate the piece is moving to
	 * @param newy - the y coordinate the piece is moving to
	 * @param p - player's turn
	 * @return true if checkmate, otherwise return false
	 */
	public boolean checkmate(Box[][] b, int oldx, int oldy, int newx, int newy, boolean p)
	{
		Box[][] newboard = tempBoard(b, oldx, oldy, newx, newy);
		if(p == true)
		{
			if(bkingx - 1 >= 0)
			{
				if(bkingy - 1 >= 0)
				{
					if(newboard[bkingx-1][bkingy-1].getPiece() == null)
					{
						if(opponentKingDead(newboard, bkingx, bkingy, bkingx-1, bkingy-1, p) == -1)
						{
							return false;
						}
					}else
					{
						if(newboard[bkingx-1][bkingy-1].getPiece().getColor() == false)
						{
							if(opponentKingDead(newboard, bkingx, bkingy, bkingx-1, bkingy-1, p) == -1)
							{
								return false;
							}
						}
					}
				}
				
				if(newboard[bkingx-1][bkingy].getPiece() == null)
				{
					if(opponentKingDead(newboard, bkingx, bkingy, bkingx-1, bkingy, p) == -1)
					{
						return false;
					}
				}else
				{
					if(newboard[bkingx-1][bkingy].getPiece().getColor() == false)
					{
						if(opponentKingDead(newboard, bkingx, bkingy, bkingx-1, bkingy, p) == -1)
						{
							return false;
						}
					}
				}
				
				if(bkingy + 1 < 8)
				{
					if(newboard[bkingx-1][bkingy+1].getPiece() == null)
					{
						if(opponentKingDead(newboard, bkingx, bkingy, bkingx-1, bkingy+1, p) == -1)
						{
							return false;
						}
					}else
					{
						if(newboard[bkingx-1][bkingy+1].getPiece().getColor() == false)
						{
							if(opponentKingDead(newboard, bkingx, bkingy, bkingx-1, bkingy+1, p) == -1)
							{
								return false;
							}
						}
					}
				}
			}
			
			if(bkingx + 1 < 8)
			{
				if(bkingy - 1 >= 0)
				{
					if(newboard[bkingx+1][bkingy-1].getPiece() == null)
					{
						if(opponentKingDead(newboard, bkingx, bkingy, bkingx+1, bkingy-1, p) == -1)
						{
							return false;
						}
					}else
					{
						if(newboard[bkingx+1][bkingy-1].getPiece().getColor() == false)
						{
							if(opponentKingDead(newboard, bkingx, bkingy, bkingx+1, bkingy-1, p) == -1)
							{
								return false;
							}
						}
					}
				}
				
				if(newboard[bkingx+1][bkingy].getPiece() == null)
				{
					if(opponentKingDead(newboard, bkingx, bkingy, bkingx+1, bkingy, p) == -1)
					{
						return false;
					}
				}else
				{
					if(newboard[bkingx+1][bkingy].getPiece().getColor() == false)
					{
						if(opponentKingDead(newboard, bkingx, bkingy, bkingx+1, bkingy, p) == -1)
						{
							return false;
						}
					}
				}
				
				if(bkingy + 1 < 8)
				{
					if(newboard[bkingx+1][bkingy+1].getPiece() == null)
					{
						if(opponentKingDead(newboard, bkingx, bkingy, bkingx+1, bkingy+1, p) == -1)
						{
							return false;
						}
					}else
					{
						if(newboard[bkingx+1][bkingy+1].getPiece().getColor() == false)
						{
							if(opponentKingDead(newboard, bkingx, bkingy, bkingx+1, bkingy+1, p) == -1)
							{
								return false;
							}
						}
					}
				}
			}
			
			if(bkingy - 1 >= 0)
			{
				if(newboard[bkingx][bkingy-1].getPiece() == null)
				{
					if(opponentKingDead(newboard, bkingx, bkingy, bkingx, bkingy-1, p) == -1)
					{
						return false;
					}
				}else
				{
					if(newboard[bkingx][bkingy-1].getPiece().getColor() == false)
					{
						if(opponentKingDead(newboard, bkingx, bkingy, bkingx, bkingy-1, p) == -1)
						{
							return false;
						}
					}
				}
			}
			
			if(bkingy + 1 < 8)
			{
				if(newboard[bkingx][bkingy+1].getPiece() == null)
				{
					if(opponentKingDead(newboard, bkingx, bkingy, bkingx, bkingy+1, p) == -1)
					{
						return false;
					}
				}else
				{
					if(newboard[bkingx][bkingy+1].getPiece().getColor() == false)
					{
						if(opponentKingDead(newboard, bkingx, bkingy, bkingx, bkingy+1, p) == -1)
						{
							return false;
						}
					}
				}
			}
		}else
		{
			if(wkingx - 1 >= 0)
			{
				if(wkingy - 1 >= 0)
				{
					if(newboard[wkingx-1][wkingy-1].getPiece() == null)
					{
						if(opponentKingDead(newboard, wkingx, wkingy, wkingx-1, wkingy-1, p) == -1)
						{
							return false;
						}
					}else
					{
						if(newboard[wkingx-1][wkingy-1].getPiece().getColor() == false)
						{
							if(opponentKingDead(newboard, wkingx, wkingy, wkingx-1, wkingy-1, p) == -1)
							{
								return false;
							}
						}
					}
				}
				
				if(newboard[wkingx-1][wkingy].getPiece() == null)
				{
					if(opponentKingDead(newboard, wkingx, wkingy, wkingx-1, wkingy, p) == -1)
					{
						return false;
					}
				}else
				{
					if(newboard[wkingx-1][wkingy].getPiece().getColor() == false)
					{
						if(opponentKingDead(newboard, wkingx, wkingy, wkingx-1, wkingy, p) == -1)
						{
							return false;
						}
					}
				}
				
				if(wkingy + 1 < 8)
				{
					if(newboard[wkingx-1][wkingy+1].getPiece() == null)
					{
						if(opponentKingDead(newboard, wkingx, wkingy, wkingx-1, wkingy+1, p) == -1)
						{
							return false;
						}
					}else
					{
						if(newboard[wkingx-1][wkingy+1].getPiece().getColor() == false)
						{
							if(opponentKingDead(newboard, wkingx, wkingy, wkingx-1, wkingy+1, p) == -1)
							{
								return false;
							}
						}
					}
				}
			}
			
			if(wkingx + 1 < 8)
			{
				if(wkingy - 1 >= 0)
				{
					if(newboard[wkingx+1][wkingy-1].getPiece() == null)
					{
						if(opponentKingDead(newboard, wkingx, wkingy, wkingx+1, wkingy-1, p) == -1)
						{
							return false;
						}
					}else
					{
						if(newboard[wkingx+1][wkingy-1].getPiece().getColor() == false)
						{
							if(opponentKingDead(newboard, wkingx, wkingy, wkingx+1, wkingy-1, p) == -1)
							{
								return false;
							}
						}
					}
				}
				
				if(newboard[wkingx+1][wkingy].getPiece() == null)
				{
					if(opponentKingDead(newboard, wkingx, wkingy, wkingx+1, wkingy, p) == -1)
					{
						return false;
					}
				}else
				{
					if(newboard[wkingx+1][wkingy].getPiece().getColor() == false)
					{
						if(opponentKingDead(newboard, wkingx, wkingy, wkingx+1, wkingy, p) == -1)
						{
							return false;
						}
					}
				}
				
				if(wkingy + 1 < 8)
				{
					if(newboard[wkingx+1][wkingy+1].getPiece() == null)
					{
						if(opponentKingDead(newboard, wkingx, wkingy, wkingx+1, wkingy+1, p) == -1)
						{
							return false;
						}
					}else
					{
						if(newboard[wkingx+1][wkingy+1].getPiece().getColor() == false)
						{
							if(opponentKingDead(newboard, wkingx, wkingy, wkingx+1, wkingy+1, p) == -1)
							{
								return false;
							}
						}
					}
				}
			}
			
			if(wkingy - 1 >= 0)
			{
				if(newboard[wkingx][wkingy-1].getPiece() == null)
				{
					if(opponentKingDead(newboard, wkingx, wkingy, wkingx, wkingy-1, p) == -1)
					{
						return false;
					}
				}else
				{
					if(newboard[wkingx][wkingy-1].getPiece().getColor() == false)
					{
						if(opponentKingDead(newboard, wkingx, wkingy, wkingx, wkingy-1, p) == -1)
						{
							return false;
						}
					}
				}
			}
			
			if(wkingy + 1 < 8)
			{
				if(newboard[wkingx][wkingy+1].getPiece() == null)
				{
					if(opponentKingDead(newboard, wkingx, wkingy, wkingx, wkingy+1, p) == -1)
					{
						return false;
					}
				}else
				{
					if(newboard[wkingx][wkingy+1].getPiece().getColor() == false)
					{
						if(opponentKingDead(newboard, wkingx, wkingy, wkingx, wkingy+1, p) == -1)
						{
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * This method is used in the checkmate method.
	 * Check if opponent's king is under check for this particular move.
	 * @param newboard - the chess board
	 * @param kingx - king's x coordinate
	 * @param kingy - king's y coordinate
	 * @param newx - the x coordinate the king is moving to
	 * @param newy - the y coordinate the king is moving to
	 * @param p - which player's turn
	 * @return 0 if nothing happens, -1 if the move is legal but opponent not under check, 1 if the move puts opponent under check
	 */
	public int opponentKingDead(Box[][] newboard, int kingx, int kingy, int newx, int newy, boolean p)
	{
		int checked = 0;
		if(newboard[kingx][kingy].getPiece().isValidMove(newboard, kingx, kingy, newx, newy) == true)
		{
			checked = -1;
			Box[][] tb = tempBoard(newboard, kingx, kingy, newx, newy);
			int col, row;
			for(row = 0; row < 8; row++)
			{
				for(col = 0; col < 8; col++)
				{
					if(tb[row][col].getPiece() != null && tb[row][col].getPiece().getColor() == p)
					{
						if(tb[row][col].getPiece().check(tb, row, col, p) == true)
						{
							checked = 1;
						}
					}
				}
			}
		}
		return checked;
	}
	
	/**
	 * Creates a temporary chess board in order to test if a new move is legal.
	 * @param b - the chess board
	 * @param oldx - the original x coordinate for the piece that is going to be moved
	 * @param oldy - the original y coordinate for the piece that is going to be moved
	 * @param newx - the x coordinate the piece is moving to
	 * @param newy - the y coordinate the piece is moving to
	 * @return the new chess board for testing
	 */
	public Box[][] tempBoard(Box[][] b, int oldx, int oldy, int newx, int newy)
	{
		Box[][] board = new Box[8][8];
		int row,col;
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				board[i][j] = new Box(null);
			}
		}
		for(col = 1; col < 8; col += 2)
		{
			board[0][col].setColor(Color.BLACK);
			board[2][col].setColor(Color.BLACK);
			board[4][col].setColor(Color.BLACK);
			board[6][col].setColor(Color.BLACK);
			board[1][col].setColor(Color.WHITE);
			board[3][col].setColor(Color.WHITE);
			board[5][col].setColor(Color.WHITE);
			board[7][col].setColor(Color.WHITE);
		}
		for(col = 0; col < 8; col += 2)
		{
			board[0][col].setColor(Color.WHITE);
			board[2][col].setColor(Color.WHITE);
			board[4][col].setColor(Color.WHITE);
			board[6][col].setColor(Color.WHITE);
			board[1][col].setColor(Color.BLACK);
			board[3][col].setColor(Color.BLACK);
			board[5][col].setColor(Color.BLACK);
			board[7][col].setColor(Color.BLACK);
		}
		for(row = 0; row < 8; row++)
		{
			for(col = 0; col < 8; col++)
			{
				if(b[row][col].getPiece() != null)
					board[row][col].setPiece(b[row][col].getPiece());
			}
		}
		board[newx][newy].setPiece(b[oldx][oldy].getPiece());
		board[oldx][oldy].setPiece(null);
		return board;
	}
}