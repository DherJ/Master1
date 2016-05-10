package tp.main;

/**
 * Represent a configuration of a Tablet with main values:
 * 	- m: width of the table
 * 	- n: height of the table
 *  - (i,j): index of the trap case in the table
 */
public class Quadruple {
	private int m, n, i, j;
	
	public Quadruple(int _m, int _n, int _i, int _j){
		m = _m;
		n = _n; 
		i = _i;
		j = _j;
	}

	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}
	
	public String toString() {
		return "[ " + m + ", " + n + ", " + i + ", " + j + " ]";
	}
}
