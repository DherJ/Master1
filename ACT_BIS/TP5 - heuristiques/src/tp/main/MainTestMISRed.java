package tp.main;

import java.util.ArrayList;
import tp.MIS.MISProblem;
import tp.io.FileOperation;
import tp.pb.Part;
import tp.pb.Pizza;

public class MainTestMISRed {

	static Pizza pizza;
	static ArrayList<Part> allParts = new ArrayList<Part> ();
	static FileOperation fo;
	
	public static void main(String[] args) {
		String pathFile = args[0];
		fo = new FileOperation();
		pizza = fo.readFile(pathFile);
		allParts = pizza.generateAllParts();
		
		/** Test réduction **/
		MISProblem mis = new MISProblem(pizza);
		mis = pizza.redToMIS(allParts);
		System.out.println(mis.getInstPizza().getHeight());
		mis.printGraphe();
		System.out.println(mis.getListOfIndepSommet().size());
		System.out.println(mis.getGraphe()[1][1].toString());
	}
}