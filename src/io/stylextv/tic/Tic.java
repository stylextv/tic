package io.stylextv.tic;

public class Tic {
	
	private static final int FIRST_PLAYER = 1;
	private static final int SECOND_PLAYER = 2;
	private static final int UNKNOWN_PLAYER = 0;
	
	private static final int WIN_SCORE = 1;
	private static final int LOSE_SCORE = -1;
	private static final int DRAW_SCORE = 0;
	private static final int UNKNOWN_SCORE = -2;
	
	public static void main(String[] args) {
		for(int boardLength = 1; boardLength <= 5; boardLength++) {
			int[][] board = new int[boardLength][boardLength];
			
			int score = evaluate(board, boardLength, 0, FIRST_PLAYER, LOSE_SCORE, WIN_SCORE);
			System.out.println(score);
		}
	}
	
	private static int evaluate(int[][] board, int boardLength, int stonesPlayed, int player, int minScore, int maxScore) {
		int score = minScore;
		if(score >= maxScore) return score;
		
		for(int x = 0; x < boardLength; x++) {
			for(int y = 0; y < boardLength; y++) {
				if(board[x][y] != UNKNOWN_PLAYER) continue;
				
				board[x][y] = player;
				stonesPlayed++;
				
				int s = evaluateStatically(board, boardLength, stonesPlayed, player, x, y);
				if(s == UNKNOWN_SCORE) s = -evaluate(board, boardLength, stonesPlayed, oppositePlayer(player), -maxScore, -score);
				
				board[x][y] = UNKNOWN_PLAYER;
				stonesPlayed--;
				
				if(s > score) {
					score = s;
					if(score >= maxScore) return score;
				}
			}
		}
		
		return score;
	}
	
	private static int evaluateStatically(int[][] board, int boardLength, int stonesPlayed, int player, int x, int y) {
		if(
				   containsLine(board, boardLength, player, x, y, 1, 0)
				|| containsLine(board, boardLength, player, x, y, 0, 1)
				|| containsLine(board, boardLength, player, x, y, 1, 1)
				|| containsLine(board, boardLength, player, x, y, 1, -1)
				
		) return WIN_SCORE;
		
		if(stonesPlayed == boardLength * boardLength) return DRAW_SCORE;
		return UNKNOWN_SCORE;
	}
	
	private static boolean containsLine(int[][] board, int boardLength, int player, int startX, int startY, int dx, int dy) {
		int length = 1;
		
		int x = startX + dx;
		int y = startY + dy;
		
		while(x >= 0 && y >= 0 && x < boardLength && y < boardLength && board[x][y] == player) {
			
			length++;
			x += dx;
			y += dy;
		}
		
		x = startX - dx;
		y = startY - dy;
		
		while(x >= 0 && y >= 0 && x < boardLength && y < boardLength && board[x][y] == player) {
			
			length++;
			x -= dx;
			y -= dy;
		}
		
		return length >= boardLength;
	}
	
	private static int oppositePlayer(int player) {
		return player == FIRST_PLAYER ? SECOND_PLAYER : FIRST_PLAYER;
	}
	
}
