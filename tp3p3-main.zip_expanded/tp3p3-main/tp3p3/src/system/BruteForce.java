package system;

import java.util.LinkedList;
import java.util.Random;

public class BruteForce {
	private LinkedList<InstructionSet> _soluciones;
	public BruteForce() {
		_soluciones = new LinkedList<InstructionSet>();
	}
	
	public boolean isVerifiable(MatrizValores matriz) {
		if ((minimumPath(matriz)+1)%2 != 0)
			return false;
		return true;
	}
	
	public int minimumPath(MatrizValores matriz) {
		return (matriz.sizeY() + matriz.sizeX() - 2);
	}
	
	public void buildAllPaths(MatrizValores matriz) {
		if (!isVerifiable(matriz))
			throw new IllegalArgumentException("No existe camino minimo adecuado para esta matriz");
		for (int y = 0; y < matriz.sizeY(); y++) {
			_soluciones.addAll(buildAllLanePaths(matriz,y));
		}
	}
	
	private LinkedList<InstructionSet> buildAllLanePaths(MatrizValores matriz, int linea) {
		int movesLimit = minimumPath(matriz);
		LinkedList<InstructionSet> solucionesTemp = new LinkedList<InstructionSet>();
		InstructionSet solAct = new InstructionSet();
		solAct.setStart(matriz.getValue(0, 0));
		for (int y = 1; y < linea+1; y ++) {
			solAct.addInstruction(Step.Down, matriz.getValue(0, solAct.nextDown()));
		}
		InstructionSet solTemp;
		for (int x = 1; x < matriz.sizeX()+1; x++) {
			solTemp = new InstructionSet(solAct);
			for (int temp = 1; temp < x; temp++) {
				solTemp.addInstruction(Step.Right, matriz.getValue(temp, linea));
			}
			for (int y = linea; y < matriz.sizeY(); y++) {
				if (y > linea && x < matriz.sizeX()-1)
					_soluciones.addAll(crossroadsX(matriz,solTemp));
			}
		}
		return solucionesTemp;
	}
	
	private LinkedList<InstructionSet> crossroadsX(MatrizValores matriz, InstructionSet lastMoves) {
		int y = lastMoves.get_downMoves();
		int x = lastMoves.get_rightMoves();
		if (x == 0 && y == 0)
			x++;
		int cap = matriz.sizeX();
		if (y!= 0 || x != 0)
			cap --;
		LinkedList<InstructionSet> soluciones = new LinkedList<InstructionSet>();
		InstructionSet solSupp = new InstructionSet(lastMoves);
		if (y >= matriz.sizeY() || x >= matriz.sizeX())
			return soluciones;
		for (x = x ; x < cap; x++) {
			InstructionSet solAct = new InstructionSet(solSupp);
			solAct.addInstruction(Step.Right, matriz.getValue(solAct.nextRight(), solAct.get_downMoves()));
			solSupp.addInstruction(Step.Right, matriz.getValue(solSupp.nextRight(), solSupp.get_downMoves()));
			for (y = y; y < matriz.sizeY()-1; y++) {
				solAct.addInstruction(Step.Down, matriz.getValue(solAct.get_rightMoves(), solAct.nextDown()));
				_soluciones.addAll(crossroadsY(matriz, solAct));
			}
			if (solAct.getInstructions().size() == minimumPath(matriz) && !existsInList(_soluciones, solAct) && solAct.get_finalValue() == 0)
				_soluciones.add(new InstructionSet(solAct));
		}
		return soluciones;
	}
	
	private LinkedList<InstructionSet> crossroadsY(MatrizValores matriz, InstructionSet lastMoves) {
		int y = lastMoves.get_downMoves();
		int x = lastMoves.get_rightMoves();
		InstructionSet solSupp = new InstructionSet(lastMoves);
		LinkedList<InstructionSet> soluciones = new LinkedList<InstructionSet>();
		if (y >= matriz.sizeY() || x >= matriz.sizeX())
			return soluciones;
		for (y = y ; y < matriz.sizeY()-1; y++) {
			InstructionSet solAct = new InstructionSet(solSupp);
			solAct.addInstruction(Step.Down, matriz.getValue(solAct.get_rightMoves(), solAct.nextDown()));
			solSupp.addInstruction(Step.Down, matriz.getValue(solSupp.get_rightMoves(), solSupp.nextDown()));
			if (x < matriz.sizeX()) {
				_soluciones.addAll(crossroadsX_Backtrack(matriz, solAct));
			}
			if (solAct.getInstructions().size() == minimumPath(matriz) && !existsInList(_soluciones, solAct) && solAct.get_finalValue() == 0) 
				_soluciones.add(new InstructionSet(solAct));
		}
		return soluciones;
	}
	
	public void buildAllPaths_Backtrack(MatrizValores matriz) {
		if (!isVerifiable(matriz))
			throw new IllegalArgumentException("No existe camino minimo adecuado para esta matriz");
		for (int y = 0; y < matriz.sizeY(); y++) {
			_soluciones.addAll(buildAllLanePaths_Backtrack(matriz,y));
		}
	}
	
	private LinkedList<InstructionSet> buildAllLanePaths_Backtrack(MatrizValores matriz, int linea) {
		int movesLimit = minimumPath(matriz);
		LinkedList<InstructionSet> solucionesTemp = new LinkedList<InstructionSet>();
		InstructionSet solAct = new InstructionSet();
		solAct.setStart(matriz.getValue(0, 0));
		for (int y = 1; y < linea+1; y ++) {
			solAct.addInstruction(Step.Down, matriz.getValue(0, solAct.nextDown()));
		}
		if (!canStillBeNeutral(matriz, solAct))
			return solucionesTemp;
		InstructionSet solTemp;
		for (int x = 1; x < matriz.sizeX()+1; x++) {
			solTemp = new InstructionSet(solAct);
			for (int temp = 1; temp < x; temp++) {
				solTemp.addInstruction(Step.Right, matriz.getValue(temp, linea));
			}
			if (!canStillBeNeutral(matriz, solTemp))
				continue;
			for (int y = linea; y < matriz.sizeY(); y++) {
				if (y > linea && x < matriz.sizeX()-1)
					_soluciones.addAll(crossroadsX_Backtrack(matriz,solTemp));
			}
		}
		return solucionesTemp;
	}
	
	private LinkedList<InstructionSet> crossroadsX_Backtrack(MatrizValores matriz, InstructionSet lastMoves) {
		int y = lastMoves.get_downMoves();
		int x = lastMoves.get_rightMoves();
		if (x == 0 && y == 0)
			x++;
		int cap = matriz.sizeX();
		if (y!= 0 || x != 0)
			cap --;
		LinkedList<InstructionSet> soluciones = new LinkedList<InstructionSet>();
		InstructionSet solSupp = new InstructionSet(lastMoves);
		if (y >= matriz.sizeY() || x >= matriz.sizeX())
			return soluciones;
		for (x = x ; x < cap; x++) {
			InstructionSet solAct = new InstructionSet(solSupp);
			solAct.addInstruction(Step.Right, matriz.getValue(solAct.nextRight(), solAct.get_downMoves()));
			solSupp.addInstruction(Step.Right, matriz.getValue(solSupp.nextRight(), solSupp.get_downMoves()));
			if (!canStillBeNeutral(matriz, solAct)) 
				return soluciones;
			for (y = y; y < matriz.sizeY()-1; y++) {
				solAct.addInstruction(Step.Down, matriz.getValue(solAct.get_rightMoves(), solAct.nextDown()));
				_soluciones.addAll(crossroadsY_Backtrack(matriz, solAct));
			}
			if (solAct.getInstructions().size() == minimumPath(matriz) && !existsInList(_soluciones, solAct))
				_soluciones.add(new InstructionSet(solAct));
		}
		return soluciones;
	}
	
	private LinkedList<InstructionSet> crossroadsY_Backtrack(MatrizValores matriz, InstructionSet lastMoves) {
		int y = lastMoves.get_downMoves();
		int x = lastMoves.get_rightMoves();
		InstructionSet solSupp = new InstructionSet(lastMoves);
		LinkedList<InstructionSet> soluciones = new LinkedList<InstructionSet>();
		if (y >= matriz.sizeY() || x >= matriz.sizeX())
			return soluciones;
		for (y = y ; y < matriz.sizeY()-1; y++) {
			InstructionSet solAct = new InstructionSet(solSupp);
			solAct.addInstruction(Step.Down, matriz.getValue(solAct.get_rightMoves(), solAct.nextDown()));
			solSupp.addInstruction(Step.Down, matriz.getValue(solSupp.get_rightMoves(), solSupp.nextDown()));
			if (!canStillBeNeutral(matriz, solAct)) 
				return soluciones;
			if (x < matriz.sizeX()) {
				_soluciones.addAll(crossroadsX_Backtrack(matriz, solAct));
			}
			if (solAct.getInstructions().size() == minimumPath(matriz) && !existsInList(_soluciones, solAct)) 
				_soluciones.add(new InstructionSet(solAct));
		}
		return soluciones;
	}
	
	private boolean existsInList(LinkedList<InstructionSet> sets, InstructionSet in) {
		for (InstructionSet set: sets) 
			if(set.equals(in)) 
				return true;
		return false;
	}
	
	private boolean canStillBeNeutral(MatrizValores matriz, InstructionSet in) {
		int remainingSteps = (in.getInstructions().size() - minimumPath(matriz))*-1;
		int valueDiff;
		if (in.get_finalValue() < 0)
			valueDiff = (in.get_finalValue()*-1);
		else
			valueDiff = in.get_finalValue();
		if ((remainingSteps - valueDiff) >= 0)
			return true;
		return false;
	}
	
	public LinkedList<Step> returnRandomSolution() {
		Random rand = new Random();
		return _soluciones.get(rand.nextInt(_soluciones.size())).getInstructions();
	}
	
	@SuppressWarnings("unchecked")
	public LinkedList<InstructionSet> getSoluciones() {
		return (LinkedList<InstructionSet>) _soluciones.clone();
	}
	
	public int caminosExplorados() {
		return _soluciones.size();
	}
}
