package tp.main;

import java.util.ArrayList;
import java.util.List;

/**
 * The Tablet class correspond at a table mxn in the game (chocolate table),
 * It contains the main information about this table:
 * 	- m: width of the table
 * 	- n: height of the table
 *  - (i,j): index of the trap case in the table
 * It has 3 main functions that allow the user to get the current score of the table:
 * 	- f(int,int,int,int): a naive recursive function to calculate the score of the table
 *  - noSym(int,int,int,int): a dynamic function to calculate the score of the table (faster)
 *  - withSym(int,int,int,int): a dynamic function to calculate the score of the table with using
 *  							the symmetries in the table (faster+++)
 */
public class Tablet {
	
	private int maxNeg = 0, maxPos = 0, cmpSym = 0, cmpNoSym = 0, cmp = 0;
	private boolean initMaxPos = false;
	private boolean initMaxNeg = false;
	private int[][][][] tab;
	
	/** Constructor **/
	public Tablet(int m, int n, int i, int j, int[][][][] _tab){
		tab = _tab;
	}
	

	/**
	 * Naive recursive function to calculate the score of the table
	 * @param m width of the table
	 * @param n height of the table
	 * @param i x_index of the trap case in the table
	 * @param j y_index of the trap case in the table
	 * @return the value of the current configuration
	 */
	int f(int m, int n, int i, int j) {
		int val = 0, result = 0;
		
		// Need to save the previous maxNeg & maxPos of the calling function
		final int previousMaxNeg = maxNeg, previousMaxPos = maxPos;
		boolean previousInitMaxPos = initMaxPos;
		boolean previousInitMaxNeg = initMaxNeg;
		initMaxPos = false;
		initMaxNeg = false;
		
		// Stop condition, there is only the trap case left
		if((m == 1) && (n == 1))
			return 0;
		
		//* Vertical cut of the table *//
		for(int colonne=1; colonne<m; colonne++) {
			
			// When the trap case is on the right of the cut
			if(i >= colonne) {
				val = f(m-colonne, n, i-colonne, j);
				cmp++;
			}
			
			// When the trap case is on the left of the cut
			else if(i < colonne){
				val = f(colonne, n, i, j);
				cmp++;
			}

			// Now with save the maximum of the negative (or positive)
			// values returned to calculate the score of the current Tablet
			// at the end of the function
			if( (val <= 0) && ( (maxNeg < val) || !initMaxNeg) ) {
				maxNeg = val;
				initMaxNeg = true;
			}
			else if( (maxPos < val) || !initMaxPos ) {
				maxPos = val;
				initMaxPos = true;
			}
		}
		
		//* Horizontal cut of the table *//
		for(int ligne=1; ligne<n; ligne++) {
			
			// When the trap case is below the cut
			if(j >= ligne) {
				val = f(m, n-ligne, i, j-ligne);
				cmp++;
			}

			// When the trap case is above the cut
			else if(j < ligne) {
				val = f(m, ligne, i, j);
				cmp++;
			}
			
			// Now with save the maximum of the negative (or positive)
			// values returned to calculate the score of the current Tablet
			// at the end of the function
			if( (val <= 0) && ( (maxNeg < val) || !initMaxNeg) ) {
				maxNeg = val;
				initMaxNeg = true;
			}
			else if( (val > 0) && ( (maxPos < val) || !initMaxPos) ) {
				maxPos = val;
				initMaxPos = true;
			}
		}
		
		// Calculate the final score of this configuration
		if(initMaxNeg) {
			result = (Math.abs(maxNeg) + 1);
		}
		else if( initMaxPos ){
			result = (maxPos*(-1)) - 1;
			
		}
		
		// Set back the previous values of the calling function
		initMaxNeg = previousInitMaxNeg;
		initMaxPos = previousInitMaxPos;
		maxPos = previousMaxPos;
		maxNeg = previousMaxNeg;
		
		// Return the final result of this configuration
		return result;
		
	}
	
	/**
	 * Dynamic function without symmetries to calculate the score of the table
	 * @param m width of the table
	 * @param n height of the table
	 * @param i x_index of the trap case in the table
	 * @param j y_index of the trap case in the table
	 * @return the value of the current configuration
	 */
	int noSym(int m, int n, int i, int j) {
		int val = 0, result = 0;
		
		// Need to save the previous maxNeg & maxPos of the calling function
		final int previousMaxNeg = maxNeg, previousMaxPos = maxPos;
		boolean previousInitMaxPos = initMaxPos;
		boolean previousInitMaxNeg = initMaxNeg;
		initMaxPos = false;
		initMaxNeg = false;
		
		// Stop condition, there is only the trap case left
		if((m == 1) && (n == 1))
			return 0;
		
		//* Vertical cut of the table *//
		for(int colonne=1; colonne<m; colonne++) {
			
			// When the trap case is on the right of the cut
			if(i >= colonne) {
				// Check if we have already calculate this case in the table
				// if not, (==0), we do the recursive call to calculate it
				if(tab[m-colonne][n][i-colonne][j] == 0){
					cmpNoSym++;
					val = noSym(m-colonne, n, i-colonne, j);
					tab[m-colonne][n][i-colonne][j] = val;
				}
				// if we already calculate this configuration, we just put the
				// value in var val
				val = tab[m-colonne][n][i-colonne][j];
			}

			// When the trap case is on the left of the cut
			else if(i < colonne){
				// Check if we have already calculate this case in the table
				// if not, (==0), we do the recursive call to calculate it
				if(tab[colonne][n][i][j] == 0){
					cmpNoSym++;
					val = noSym(colonne, n, i, j);
					tab[colonne][n][i][j] = val;
				}
				// if we already calculate this configuration, we just put the
				// value in var val
				val = tab[colonne][n][i][j];
			}

			// Now with save the maximum of the negative (or positive)
			// values returned to calculate the score of the current Tablet
			// at the end of the function
			if( (val <= 0) && ( (maxNeg < val) || !initMaxNeg) ) {
				maxNeg = val;
				initMaxNeg = true;
			}
			else if( (maxPos < val) || !initMaxPos ) {
				maxPos = val;
				initMaxPos = true;
			}
		}
		
		//* Horizontal cut of the table *//
		for(int ligne=1; ligne<n; ligne++) {

			// When the trap case is below the cut
			if(j >= ligne) {
				// Check if we have already calculate this case in the table
				// if not, (==0), we do the recursive call to calculate it
				if(tab[m][n-ligne][i][j-ligne] == 0){
					cmpNoSym++;
					val = noSym(m, n-ligne, i, j-ligne);
					tab[m][n-ligne][i][j-ligne] = val;
				}
				// if we already calculate this configuration, we just put the
				// value in var val
				val = tab[m][n-ligne][i][j-ligne];
			}

			// When the trap case is above the cut
			else if(j < ligne) {
				// Check if we have already calculate this case in the table
				// if not, (==0), we do the recursive call to calculate it
				if(tab[m][ligne][i][j] == 0){
					cmpNoSym++;
					val = noSym(m, ligne, i, j);
					tab[m][ligne][i][j] = val;
				}
				// if we already calculate this configuration, we just put the
				// value in var val
				val = tab[m][ligne][i][j];
			}
			
			// Now with save the maximum of the negative (or positive)
			// values returned to calculate the score of the current Tablet
			// at the end of the function
			if( (val <= 0) && ( (maxNeg < val) || !initMaxNeg) ) {
				maxNeg = val;
				initMaxNeg = true;
			}
			else if( (val > 0) && ( (maxPos < val) || !initMaxPos) ) {
				maxPos = val;
				initMaxPos = true;
			}
		}
		
		// Calculate the final score of this configuration
		if(initMaxNeg) 
			result = (Math.abs(maxNeg) + 1);
		else if( initMaxPos )
			result = (maxPos*(-1)) - 1;
		
		// Set back the previous values of the calling function
		initMaxNeg = previousInitMaxNeg;
		initMaxPos = previousInitMaxPos;
		maxPos = previousMaxPos;
		maxNeg = previousMaxNeg;
		
		// Return the final result of this configuration
		return result;
		
	}
	
	/**
	 * Dynamic function with symmetries to calculate the score of the table
	 * @param m width of the table
	 * @param n height of the table
	 * @param i x_index of the trap case in the table
	 * @param j y_index of the trap case in the table
	 * @return the value of the current configuration
	 */
	int withSym(int m, int n, int i, int j) {
		int val = 0, result = 0, newM=0, newN=0, newI=0, newJ=0, Isym=0, Jsym=0;
		
		// Need to save the previous maxNeg & maxPos of the calling function
		final int previousMaxNeg = maxNeg, previousMaxPos = maxPos;
		boolean previousInitMaxPos = initMaxPos;
		boolean previousInitMaxNeg = initMaxNeg;
		initMaxPos = false;
		initMaxNeg = false;
		
		// Stop condition, there is only the trap case left
		if((m == 1) && (n == 1))
			return 0;
		
		//* Vertical cut of the table *//
		for(int colonne=1; colonne<m; colonne++) {
			
			// When the trap case is on the right of the cut
			if(i >= colonne) {
				newM = m-colonne;
				newN = n;
				newI = i-colonne;
				newJ = j;
				Isym = (newM-1)-newI;
				Jsym = (newN-1)-newJ;
			}
			
			// When the trap case is on the left of the cut
			else if(i < colonne){
				newM = colonne;
				newN = n;
				newI = i;
				newJ = j;
				Isym = (newM-1)-newI;
				Jsym = (newN-1)-newJ;
			}
			
			// Check if we have already calculate this case in the table
			// if not, (==0), we do the recursive call to calculate it
			// and we put the value calculated in the table at all the 
			// index including the symmetries cases
			if(tab[newM][newN][newI][newJ] == 0){
				cmpSym++;
				val = withSym(newM, newN, newI, newJ);
				tab[newM][newN][newI][newJ] = val;
				tab[newM][newN][newI][Jsym] = val;
				tab[newM][newN][Isym][newJ] = val;
				tab[newM][newN][Isym][Jsym] = val;
				
				tab[newN][newM][newJ][newI] = val;
				tab[newN][newM][newJ][Isym] = val;
				tab[newN][newM][Jsym][newI] = val;
				tab[newN][newM][Jsym][Isym] = val;
			}
			// if we already calculate this configuration, we just put the
			// value in var val
			val = tab[newM][newN][newI][newJ];

			// Now with save the maximum of the negative (or positive)
			// values returned to calculate the score of the current Tablet
			// at the end of the function
			if( (val <= 0) && ( (maxNeg < val) || !initMaxNeg) ) {
				maxNeg = val;
				initMaxNeg = true;
			}
			else if( (maxPos < val) || !initMaxPos ) {
				maxPos = val;
				initMaxPos = true;
			}
		}
		
		//* Horizontal cut *//
		for(int ligne=1; ligne<n; ligne++) {
			
			// When the trap case is below the cut
			if(j >= ligne) {
				newM = m;
				newN = n-ligne;
				newI = i;
				newJ = j-ligne;
				Isym = (newM-1)-newI;
				Jsym = (newN-1)-newJ;
			}
			
			// When the trap case is above the cut
			else if(j < ligne) {
				newM = m;
				newN = ligne;
				newI = i;
				newJ = j;
				Isym = (newM-1)-newI;
				Jsym = (newN-1)-newJ;
			}
			
			// Check if we have already calculate this case in the table
			// if not, (==0), we do the recursive call to calculate it
			// and we put the value calculated in the table at all the 
			// index including the symmetries cases
			if(tab[newM][newN][newI][newJ] == 0){
				cmpSym++;
				val = withSym(newM, newN, newI, newJ);
				tab[newM][newN][newI][newJ] = val;
				tab[newM][newN][newI][Jsym] = val;
				tab[newM][newN][Isym][newJ] = val;
				tab[newM][newN][Isym][Jsym] = val;
				
				tab[newN][newM][newJ][newI] = val;
				tab[newN][newM][newJ][Isym] = val;
				tab[newN][newM][Jsym][newI] = val;
				tab[newN][newM][Jsym][Isym] = val;
			}
			// if we already calculate this configuration, we just put the
			// value in var val
			val = tab[newM][newN][newI][newJ];
			
			// Now with save the maximum of the negative (or positive)
			// values returned to calculate the score of the current Tablet
			// at the end of the function
			if( (val <= 0) && ( (maxNeg < val) || !initMaxNeg) ) {
				maxNeg = val;
				initMaxNeg = true;
			}
			else if( (val > 0) && ( (maxPos < val) || !initMaxPos) ) {
				maxPos = val;
				initMaxPos = true;
			}
		}
		
		// Calculate the final score of this configuration
		if(initMaxNeg) 
			result = (Math.abs(maxNeg) + 1);
		else if( initMaxPos )
			result = (maxPos*(-1)) - 1;
		
		// Set back the previous values of the calling function
		initMaxNeg = previousInitMaxNeg;
		initMaxPos = previousInitMaxPos;
		maxPos = previousMaxPos;
		maxNeg = previousMaxNeg;
		
		// Return the final result of this configuration
		return result;
	}
	
	/**
	 * Dynamic function with symmetries which return the best move that the 
	 * bot can do.
	 * @param m width of the table
	 * @param n height of the table
	 * @param i x_index of the trap case in the table
	 * @param j y_index of the trap case in the table
	 * @return a Quadruple that contains the move that the bot has done
	 */
	Quadruple getBestMoveWithSym(int m, int n, int i, int j) {
		int val = 0, newM=0, newN=0, newI=0, newJ=0, Isym=0, Jsym=0;
		
		// Need to save the previous maxNeg & maxPos of the calling function
		final int previousMaxNeg = maxNeg, previousMaxPos = maxPos;
		boolean previousInitMaxPos = initMaxPos;
		boolean previousInitMaxNeg = initMaxNeg;
		initMaxPos = false;
		initMaxNeg = false;
		Quadruple bestMove = null, move = null;
		
		// Stop condition, there is only the trap case left
		if((m == 1) && (n == 1)){
			return new Quadruple(1,1,0,0);
		}
		
		//* Vertical cut *//
		for(int colonne=1; colonne<m; colonne++) {
			
			// When the trap case is on the right of the cut
			if(i >= colonne) {
				newM = m-colonne;
				newN = n;
				newI = i-colonne;
				newJ = j;
				Isym = (newM-1)-newI;
				Jsym = (newN-1)-newJ;
			}

			// When the trap case is on the left of the cut
			else if(i < colonne){
				newM = colonne;
				newN = n;
				newI = i;
				newJ = j;
				Isym = (newM-1)-newI;
				Jsym = (newN-1)-newJ;
			}
			
			// Check if we have already calculate this case in the table
			// if not, (==0), we do the recursive call to calculate it
			// and we put the value calculated in the table at all the 
			// index including the symmetries cases
			if(tab[newM][newN][newI][newJ] == 0){
				cmpSym++;
				val = withSym(newM, newN, newI, newJ);
				tab[newM][newN][newI][newJ] = val;
				tab[newM][newN][newI][Jsym] = val;
				tab[newM][newN][Isym][newJ] = val;
				tab[newM][newN][Isym][Jsym] = val;
				
				tab[newN][newM][newJ][newI] = val;
				tab[newN][newM][newJ][Isym] = val;
				tab[newN][newM][Jsym][newI] = val;
				tab[newN][newM][Jsym][Isym] = val;
			}
			// if we already calculate this configuration, we just put the
			// value in var val
			val = tab[newM][newN][newI][newJ];

			// Now with save the maximum of the negative (or positive)
			// values returned to calculate the score of the current Tablet
			// at the end of the function, and we save the (m,n,i,j) in a Quadruple
			// to return the best move that the bot can done
			if( (val <= 0) && ( (maxNeg < val) || !initMaxNeg) ) {
				bestMove = new Quadruple(newM, newN, newI, newJ);
				maxNeg = val;
				initMaxNeg = true;
			}
			else if( (maxPos < val) || !initMaxPos ) {
				move = new Quadruple(newM, newN, newI, newJ);
				maxPos = val;
				initMaxPos = true;
			}
		}
		
		//* Horizontal cut *//
		for(int ligne=1; ligne<n; ligne++) {

			// When the trap case in below the cut
			if(j >= ligne) {
				newM = m;
				newN = n-ligne;
				newI = i;
				newJ = j-ligne;
				Isym = (newM-1)-newI;
				Jsym = (newN-1)-newJ;
			}

			// When the trap case is above the cut
			else if(j < ligne) {
				newM = m;
				newN = ligne;
				newI = i;
				newJ = j;
				Isym = (newM-1)-newI;
				Jsym = (newN-1)-newJ;
			}
			
			// if not, (==0), we do the recursive call to calculate it
			// and we put the value calculated in the table at all the 
			// index including the symmetries cases
			if(tab[newM][newN][newI][newJ] == 0){
				cmpSym++;
				val = withSym(newM, newN, newI, newJ);
				tab[newM][newN][newI][newJ] = val;
				tab[newM][newN][newI][Jsym] = val;
				tab[newM][newN][Isym][newJ] = val;
				tab[newM][newN][Isym][Jsym] = val;
				
				tab[newN][newM][newJ][newI] = val;
				tab[newN][newM][newJ][Isym] = val;
				tab[newN][newM][Jsym][newI] = val;
				tab[newN][newM][Jsym][Isym] = val;
			}
			// if we already calculate this configuration, we just put the
			// value in var val
			val = tab[newM][newN][newI][newJ];
			
			// Now with save the maximum of the negative (or positive)
			// values returned to calculate the score of the current Tablet
			// at the end of the function, and we save the (m,n,i,j) in a Quadruple
			// to return the best move that the bot can done
			if( (val <= 0) && ( (maxNeg < val) || !initMaxNeg) ) {
				bestMove = new Quadruple(newM, newN, newI, newJ);
				maxNeg = val;
				initMaxNeg = true;
			}
			else if( (val > 0) && ( (maxPos < val) || !initMaxPos) ) {
				move = new Quadruple(newM, newN, newI, newJ);
				maxPos = val;
				initMaxPos = true;
			}
		}
		
		// Set back the previous value of the calling function
		initMaxNeg = previousInitMaxNeg;
		initMaxPos = previousInitMaxPos;
		maxPos = previousMaxPos;
		maxNeg = previousMaxNeg;
		
		// Return the bestMove if it is initialized (negative result)
		if(bestMove != null)
			return bestMove;
		// Else return a positive move, ie a move that will let a chance
		// to the player to win
		else if(move != null)
			return move;
		// Else if any of the previous move are initialized, return null
		else return null;
	}
	
	/**
	 * Function that calculate all the possibilities (i,j) in this configuration
	 * which will return the score 127
	 * @param val
	 * @return a List of Quadruple which had a score of 127
	 */
	public List<Quadruple> getQuadraEgalVal(int val) {
		List<Quadruple> result = new ArrayList <Quadruple> ();
		for(int i = 0; i < 127; i++) {
			for(int j = 0; j < 127; j++) {
				if(tab[127][127][i][j] == 0) {
					val = withSym(127,127,i,j);
				} else val = tab[127][127][i][j];
				if(val == 127) {
					result.add(new Quadruple(127, 127, i, j));
				}
			}
		}
		return result;
	}
	
	
	
	
	/** Getters & Setters **/ 
	public int getCmpSym(){
		return cmpSym;
	}
	
	public int getCmp(){
		return cmp;
	}
	
	public int getCmpNoSym(){
		return cmpNoSym;
	}
}