package system;

import java.util.Random;

public class TestBase {

	public static void main(String[] args) {
		Random rand = new Random();
		MatrizValores a = new MatrizValores(3,4);
//		for (int y = 0; y < a._matriz.length; y++) {
//			for (int x = 0; x < a._matriz[y].length; x++) {
//				if (rand.nextInt(2) == 0)
//					a.addNegative(x, y);
//				else
//					a.addPositive(x, y);
//			}
//		}
		BruteForce tester = new BruteForce();
		System.out.println(tester.isVerifiable(a));
		tester.buildAllPaths_Backtrack(a);
		System.out.println(tester.getSoluciones().size());
		
		for (InstructionSet i: tester.getSoluciones()) {
			System.out.println(i.getInstructions() + " " + i.get_finalValue());
		}
		
	}

}
