package system;

import java.util.Random;

import javax.swing.JButton;

public class MatrizValores {
	private Value[][] _matriz;
	
	public MatrizValores(int sizeX, int sizeY) {
		_matriz = new Value[sizeY][sizeX];
		crearMatriz();
	}
	
	private void crearMatriz() {
		Random rand = new Random();
		for (int y = 0; y < _matriz.length; y++) {
			for (int x = 0; x < _matriz[y].length; x++) {
				if (rand.nextInt(2) == 0)
					addNegative(x, y);
				else
					addPositive(x, y);
			}
		}
	}
	
	public void addNegative(int X, int Y) {
		_matriz[Y][X] = Value.minusOne;
	}
	
	public void addPositive(int X, int Y) {
		_matriz[Y][X] = Value.plusOne;
	}
	
	public Value getValue(int x, int y) {
		return _matriz[y][x];
	}

	public int sizeY() {
		return _matriz.length;
	}
	
	public int sizeX() {
		return _matriz[0].length;
	}

	public Value[][] devolverMatriz() {	
		return _matriz.clone();
	}
}
