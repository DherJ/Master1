package tp.main;

import java.util.List;

public class Main {
	
	static int m, n, i, j;
	static boolean game = false;
	static boolean f127 = false;
	static boolean naive = false;
	static boolean dynamic = false;
	static boolean withSym = false;
	static int MAX;
	
	/**
	 * Function that check the arguments of the program and initialize
	 * the variables with it.
	 * @param args -> args of the program
	 */
	public static void parseCommandLine(String[] args) {
		int result [] = new int [4];
		String[] parts;
		for(String s : args) {
			parts = s.split("=");
			switch(parts[0]) {
			case "-naive":
				naive = true;
				break;
			case "-dynamic":
				dynamic = true;
				break;
			case "-sym":
				withSym = true;
				break;
			case "-f127":
				f127 = true;
				break;
			case "-game" :
				game = true;
				break;
			case "-m" :
				m = Integer.parseInt(parts[1]);
				break;
			case "-n" :
				n = Integer.parseInt(parts[1]);
				break;
			case "-i" :
				i = Integer.parseInt(parts[1]);
				break;
			case "-j" :
				j = Integer.parseInt(parts[1]);
				break;
			}
		}
	}

	
	public static void main(String[] args) {
		System.out.print("Args: ");
		for(String s : args) System.out.print(s + " ");
		parseCommandLine(args);
		
		MAX = Math.max(m, n) + 1;
		
		if(naive)
			testNaive();
		else if(dynamic)
			testDynamic();
		else if(withSym)
			testDynamicWithSym();
		else if(f127)
			testFunctionFindValue127();
		else
			launchGraphicGame();
	}
	
	/**
	 * Function that create an object Game with the value m, n, i, j
	 * initially in the program's arguments, and launch the game.
	 */
	static public void launchGraphicGame(){
		Game game = new Game(m, n, i, j);
		game.beginGame();
	}
	
	/**
	 * Function that looking for all the (i,j) in a table 127x127 which
	 * have the value 127, and display the results
	 */
	static public void testFunctionFindValue127(){
		System.out.println("\nStart looking for value 127 in 127x127 table...");
		int[][][][] tab = new int [128][128][128][128];
		Tablet t = new Tablet(m,n,i,j,tab);
		long begin = System.nanoTime();
		List<Quadruple> list = t.getQuadraEgalVal(127);
		long end = System.nanoTime();
		for(Quadruple q : list) {
			System.out.println(q.toString());
		}
		System.out.println("Time: " + ((end-begin)*Math.pow(10, -9)) + "s");
	}
	
	/**
	 * Function which test the naive function with the value of m, n, i, j
	 */
	static public void testNaive(){
		System.out.println("\nStart naive function with ("+m+","+n+","+i+","+j+")...");
		int[][][][] tab = new int [MAX][MAX][MAX][MAX];
		Tablet test = new Tablet(m, n, i, j, tab);
		long begin = System.nanoTime();
		int naive = test.f(m, n, i, j);
		long end = System.nanoTime();
		System.out.println("Naive function: " + naive + "\ttime: " + ((end-begin)*Math.pow(10, -9)) + "s" + " \tcmpt:" + test.getCmp());
	}
	
	/**
	 * Function which test the dynamic function with the value of m, n, i, j
	 */
	static public void testDynamic(){
		System.out.println("\nStart dynamic function without symmetries: ("+m+","+n+","+i+","+j+")...");
		int[][][][] tab = new int [MAX][MAX][MAX][MAX];
		Tablet test = new Tablet(m, n, i, j, tab);
		long begin = System.nanoTime();
		int dynamic = test.noSym(m, n, i, j);
		long end = System.nanoTime();
		System.out.println("Dynamic function: " + dynamic + "\ttime: " + ((end-begin)*Math.pow(10, -9)) + "s" + " \tcmpt:" + test.getCmpNoSym());
	}
	
	/**
	 * Function which test the dynamic function (with symmetries) with the value m, n, i, j
	 */
	static public void testDynamicWithSym(){
		System.out.println("\nStart dynamic function with symmetries: ("+m+","+n+","+i+","+j+")...");
		int[][][][] tab = new int [MAX][MAX][MAX][MAX];
		Tablet test = new Tablet(m, n, i, j, tab);
		long begin = System.nanoTime();
		int withSym = test.withSym(m, n, i, j);
		long end = System.nanoTime();
		System.out.println("Dynamic function with sym: " + withSym + "\ttime: " + ((end-begin)*Math.pow(10, -9)) + "s" + " \tcmpt:" + test.getCmpSym());
	}
}