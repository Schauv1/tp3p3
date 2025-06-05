package system;

import java.util.LinkedList;

public class BruteForce {
	LinkedList<InstructionSet> _soluciones;
	public BruteForce() {
		_soluciones = new LinkedList<InstructionSet>();
	}
	
	public boolean isVerifiable(MatrizValores matriz) {
		if ((minimumPath(matriz))%2 != 0)
			return false;
		return true;
	}
	
	public int minimumPath(MatrizValores matriz) {
		return (matriz._matriz.length + matriz._matriz[0].length - 1);
	}
	
	public void buildAllPaths(MatrizValores matriz) {
		if (!isVerifiable(matriz))
			throw new IllegalArgumentException("No existe camino minimo adecuado para esta matriz");
		for (int y = 0; y < matriz._matriz.length; y++) {
			_soluciones.addAll(buildAllLanePaths(matriz,y));
		}
	}
	private LinkedList<InstructionSet> buildAllLanePaths(MatrizValores matriz, int linea) {
		int movesLimit = minimumPath(matriz);
		LinkedList<InstructionSet> solucionesTemp = new LinkedList<InstructionSet>();
		InstructionSet solAct = new InstructionSet();
		for (int y = 0; y < linea; y ++) {
			solAct.addInstruction(Step.Down, matriz.getValue(0, y));
		}
		InstructionSet solTemp;
		for (int x = 0; x < matriz.sizeX(); x++) {
			solTemp = new InstructionSet(solAct);
			for (int temp = 0; temp < x; temp++) {
				solTemp.addInstruction(Step.Right, matriz.getValue(temp, linea));
			}
			for (int y = linea; y <matriz.sizeY(); y++) {
				if (x < matriz._matriz[0].length)
					for (InstructionSet in: crossroads(matriz,solTemp))
						if (!in.equals(solTemp))
							if (!existsInList(solucionesTemp,in)) 
								solucionesTemp.add(in);
				else
					for (int temp = 1; temp < y; temp++) {
						solTemp.addInstruction(Step.Down, matriz.getValue(x, temp));
					}
			}
			if (solTemp._instructions.size() == minimumPath(matriz))
				if(!existsInList(solucionesTemp, solTemp))
					solucionesTemp.add(solAct);
			solTemp = null;
		}

		return solucionesTemp;
	}
	
	private LinkedList<InstructionSet> crossroads(MatrizValores matriz, InstructionSet lastMoves) {
		LinkedList<InstructionSet> possibilities = new LinkedList<InstructionSet>();
		int x = 0, y = 0;
		InstructionSet actMove = new InstructionSet(lastMoves);
		if (actMove._instructions.size() >= minimumPath(matriz))
			return possibilities;
		for (Step s: lastMoves._instructions) {
			switch (s) {
			case Right:
				x ++;
				break;
			case Down:
				y ++;
				break;
			}
		}
		if (y >= matriz.sizeY() || x >= matriz.sizeX())
			return possibilities;
		for (int x1 = x; x1 < matriz.sizeX(); x1++) {
			if (x1 != 0 && x1 < matriz.sizeX())
			actMove.addInstruction(Step.Right, matriz.getValue(x1, y));
			for (int y1 = y; y1 < matriz.sizeY(); y1++) {
				if (y1 < matriz._matriz.length)
					actMove.addInstruction(Step.Down, matriz.getValue(x1, y1));
				if (x1 < matriz._matriz[x].length) {
					for (InstructionSet in: crossroads(matriz,actMove)) {
						if (!in.equals(actMove))
							if (!existsInList(possibilities,in)) 
								possibilities.add(in);
							
					}
				}
			}
			if (actMove._instructions.size() == minimumPath(matriz)) {
				if(!existsInList(possibilities,actMove))
					possibilities.add(actMove);
			}
			actMove = null;
			actMove = new InstructionSet(lastMoves);
		}
		return possibilities;
	}
	
	public void buildAllPaths_Lookback(MatrizValores matriz) {
		if (!isVerifiable(matriz))
			throw new IllegalArgumentException("No existe camino minimo adecuado para esta matriz");
		for (int y = 0; y < matriz._matriz.length; y++) {
			_soluciones.addAll(buildAllLanePaths_Lookback(matriz,y));
		}
	}
	private LinkedList<InstructionSet> buildAllLanePaths_Lookback(MatrizValores matriz, int linea) {
		int movesLimit = minimumPath(matriz);
		LinkedList<InstructionSet> solucionesTemp = new LinkedList<InstructionSet>();
		InstructionSet solAct = new InstructionSet();
		for (int y = 0; y < linea; y ++) {
			solAct.addInstruction(Step.Down, matriz.getValue(0, y));
		}
		if (!canStillBeNeutral(matriz, solAct))
			return solucionesTemp;
		InstructionSet solTemp;
		for (int x = 0; x < matriz.sizeX(); x++) {
			solTemp = new InstructionSet(solAct);
			for (int temp = 0; temp < x; temp++) {
				solTemp.addInstruction(Step.Right, matriz.getValue(temp, linea));
			}
			if (!canStillBeNeutral(matriz, solTemp))
				return solucionesTemp;
			for (int y = linea; y <matriz.sizeY(); y++) {
				if (x < matriz._matriz[0].length)
					for (InstructionSet in: crossroadsLookback(matriz,solTemp))
						if (!in.equals(solTemp))
							if (!existsInList(solucionesTemp,in)) 
								solucionesTemp.add(in);
				else
					for (int temp = 1; temp < y; temp++) {
						solTemp.addInstruction(Step.Down, matriz.getValue(x, temp));
					}
				if (!canStillBeNeutral(matriz, solTemp))
					return solucionesTemp;
			}
			if (solTemp._instructions.size() == minimumPath(matriz) && solTemp._finalValue == 0)
				if(!existsInList(solucionesTemp, solTemp))
					solucionesTemp.add(solAct);
			solTemp = null;
		}

		return solucionesTemp;
	}
	
	private LinkedList<InstructionSet> crossroadsLookback(MatrizValores matriz, InstructionSet lastMoves) {
		LinkedList<InstructionSet> possibilities = new LinkedList<InstructionSet>();
		int x = 0, y = 0;
		InstructionSet actMove = new InstructionSet(lastMoves);
		if (actMove._instructions.size() >= minimumPath(matriz))
			return possibilities;
		if (!canStillBeNeutral(matriz, actMove))
			return possibilities;
		for (Step s: lastMoves._instructions) {
			switch (s) {
			case Right:
				x ++;
				break;
			case Down:
				y ++;
				break;
			}
		}
		if (y >= matriz.sizeY() || x >= matriz.sizeX())
			return possibilities;
		for (int x1 = x; x1 < matriz.sizeX(); x1++) {
			if (x1 != 0 && x1 < matriz.sizeX())
			actMove.addInstruction(Step.Right, matriz.getValue(x1, y));
			if (!canStillBeNeutral(matriz, actMove))
				return possibilities;
			for (int y1 = y; y1 < matriz.sizeY(); y1++) {
				if (y1 < matriz._matriz.length) {
					actMove.addInstruction(Step.Down, matriz.getValue(x1, y1));
					if (!canStillBeNeutral(matriz, actMove))
						return possibilities;
				}
				if (x1 < matriz._matriz[x].length) {
					for (InstructionSet in: crossroads(matriz,actMove)) {
						if (!in.equals(actMove))
							if (!existsInList(possibilities,in)) 
								possibilities.add(in);
					}
				}
			}
			if (actMove._instructions.size() == minimumPath(matriz) && actMove._finalValue == 0) {
				if(!existsInList(possibilities,actMove))
					possibilities.add(actMove);
			}
			actMove = null;
			actMove = new InstructionSet(lastMoves);
		}
		return possibilities;
	}
	
	private boolean existsInList(LinkedList<InstructionSet> sets, InstructionSet in) {
		for (InstructionSet set: sets)
			if(set.equals(in))
				return true;
		return false;
	}
	
	private boolean canStillBeNeutral(MatrizValores matriz, InstructionSet in) {
		int remainingSteps = (in._instructions.size() - minimumPath(matriz))*-1;
		int valueDiff;
		if (in._finalValue < 0)
			valueDiff = (in._finalValue*-1);
		else
			valueDiff = in._finalValue;
		if ((remainingSteps - valueDiff) > 0)
			return true;
		return false;
	}
}
