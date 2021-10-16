package chess;
import java.util.*;
import piece.*;

/**
 * 
 * @author Kshitij Bafna
 * @author Ting Yao
 *
 */
public class Chess
{
	/**
	 * Used to find the coordinates of the inputs
	 */
	public static int oldx, oldy, newx, newy;
	public static String xaxis = "abcdefgh";
	
	/**
	 * Infinite loop that conitunues until a king is dead, checkmate happens, a player resigns,
	 * or both players agree on a draw.
	 * Checks input for Pawn's speical move promotion
	 * White always start the game first
	 * @param args
	 */
	public static void main(String[] args)
	{
		Board board = new Board();
		Box[][] pboard = board.initialize();
		board.printboard(pboard);
		boolean playerTurn = true;
		boolean draw = false;
		Scanner sc = new Scanner(System.in);
		String input;
		String inputToken[];
		
		
		while(true)
		{
			if(playerTurn)
			{
				System.out.print("White's move: ");
			}else
			{
				System.out.print("Black's move: ");
			}
			input = sc.nextLine();
			if((input.compareTo("resign") == 0))
			{
				if(playerTurn)
				{
					System.out.println("Black wins");
					break;
				}else
				{
					System.out.println("White wins");
					break;
				}
			}
			if(draw)
			{
				if((input.compareTo("draw")) == 0)
				{
					break;
				}else
				{
					draw = false;
				}
			}
			inputToken = input.split(" ");
			if(inputToken.length < 2)
				System.out.println("Illegal move, try again");
			else if(inputToken[0].length() != 2 || inputToken[1].length() != 2)
			{
				System.out.println("Illegal move, try again");
			}else if(inputToken.length > 3 || inputToken.length < 2)
			{
				System.out.println("Illegal move, try again");
			}else if(inputToken.length == 3)
			{
				if((inputToken[2].compareTo("draw?") == 0))
				{
					stringtoInt(inputToken[0], inputToken[1]);
					int success = board.move(pboard, oldx, oldy, newx, newy, playerTurn);
					if(success == 0)
					{
						playerTurn = !playerTurn;
						draw = true;
					}else if(success == 4)
					{
						if(playerTurn)
						{
							System.out.println("White wins");
							break;
						}else
						{
							System.out.println("Black wins");
							break;
						}
					}
				}else
				{
					stringtoInt(inputToken[0], inputToken[1]);
					if(!(pboard[oldx][oldy].getPiece() instanceof Pawn))
					{
						System.out.println("Illegal move, try again");
					}else if((playerTurn == true && newx == 0) || (playerTurn == false && newx == 7))
					{
						if((inputToken[2].compareTo("Q") == 0) || (inputToken[2].compareTo("N") == 0) || (inputToken[2].compareTo("B") == 0) || (inputToken[2].compareTo("R") == 0))
						{
							int success = board.move(pboard, oldx, oldy, newx, newy, playerTurn);
							if(success == 0)
							{
								boolean check = false;
								if(inputToken[2].compareTo("Q") == 0)
								{
									pboard[newx][newy].setPiece(new Queen(newx, newy, playerTurn));
									if(pboard[newx][newy].getPiece().check(pboard, newx, newy, playerTurn))
									{
										check = true;
									}
								}else if(inputToken[2].compareTo("N") == 0)
								{
									pboard[newx][newy].setPiece(new Knight(newx, newy, playerTurn));
									if(pboard[newx][newy].getPiece().check(pboard, newx, newy, playerTurn))
									{
										check = true;
									}
								}else if(inputToken[2].compareTo("B") == 0)
								{
									pboard[newx][newy].setPiece(new Bishop(newx, newy, playerTurn));
									if(pboard[newx][newy].getPiece().check(pboard, newx, newy, playerTurn))
									{
										check = true;
									}
								}else if(inputToken[2].compareTo("R") == 0)
								{
									pboard[newx][newy].setPiece(new Rook(newx, newy, playerTurn));
									if(pboard[newx][newy].getPiece().check(pboard, newx, newy, playerTurn))
									{
										check = true;
									}
								}
								System.out.println();
								board.printboard(pboard);
								if(check == true)
								{
									System.out.println("check" + "\n");
								}
								playerTurn = !playerTurn;
							}else if(success == 4)
							{
								if(playerTurn)
								{
									System.out.println("White wins");
									break;
								}else
								{
									System.out.println("Black wins");
									break;
								}
							}
						}
					}else
					{
						System.out.println("Illegal move, try again");
					}
					
				}
			}else if(inputToken[0].length() == 2 && inputToken[1].length() == 2)
			{
				stringtoInt(inputToken[0], inputToken[1]);
				int success = board.move(pboard, oldx, oldy, newx, newy, playerTurn);
				if(success == 0)
				{
					System.out.println();
					board.printboard(pboard);
					playerTurn = !playerTurn;
				}else if(success == 11)
				{
					System.out.println();
					board.printboard(pboard);
					System.out.println("check" + "\n");
					playerTurn = !playerTurn;
				}else if(success == 1)
				{
					System.out.println();
					board.printboard(pboard);
					System.out.println("Checkmate");
					if(playerTurn)
					{
						System.out.println("White wins");
						break;
					}else
					{
						System.out.println("Black wins");
						break;
					}
				}else if(success == 4)
				{
					if(playerTurn)
					{
						System.out.println("White wins");
						break;
					}else
					{
						System.out.println("Black wins");
						break;
					}
				}else
				{
					System.out.println("Illegal move, try again");
				}
			}
			
		}
	}
	
	/**
	 * Translate the RankFile input into coordinates of the 2D array
	 * @param src - first RankFile input
	 * @param dest - second RankFile input
	 */
	public static void stringtoInt(String src, String dest)
	{
		oldy = xaxis.indexOf(src.charAt(0));
		oldx = 8 - Character.getNumericValue(src.charAt(1));
		newy = xaxis.indexOf(dest.charAt(0));
		newx = 8 - Character.getNumericValue(dest.charAt(1));
	}
}