package system;

public class MatrizValores {
	Value[][] _matriz;
	
	public MatrizValores(int sizeX, int sizeY) {
		_matriz = new Value[sizeY][sizeX];
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
}
