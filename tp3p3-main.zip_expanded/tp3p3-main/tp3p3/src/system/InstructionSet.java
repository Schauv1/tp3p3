package system;

import java.util.LinkedList;

public class InstructionSet {
	private LinkedList<Step> _instructions;
	private int _finalValue, _rightMoves, _downMoves;
	
	public InstructionSet() {
		_instructions = new LinkedList<Step>();
		_finalValue = 0;
		_rightMoves = 0;
		_downMoves = 0;
	}
	
	@SuppressWarnings("unchecked")
	public InstructionSet(InstructionSet lastMoves) {
		_finalValue = lastMoves._finalValue;
		_downMoves = lastMoves._downMoves;
		_rightMoves = lastMoves._rightMoves;
		_instructions = (LinkedList<Step>) lastMoves._instructions.clone();
	}

	public void addInstruction(Step direction, Value value) {
		_instructions.add(direction);
		switch(direction) {
		case Right:
			_rightMoves ++;
			break;
		case Down:
			_downMoves ++;
			break;
		}
		switch(value) {
		case minusOne:
			_finalValue --;
			break;
		case plusOne:
			_finalValue ++;
			break;
		}
	}
	
	public void setStart(Value value) {
		switch(value) {
		case minusOne:
			_finalValue --;
			break;
		case plusOne:
			_finalValue ++;
			break;
		}
	}
	
	@SuppressWarnings("unchecked")
	public LinkedList<Step> getInstructions() {
		return (LinkedList<Step>) _instructions.clone();
	}
	
	public int get_finalValue() {
		return _finalValue;
	}

	public int get_rightMoves() {
		return _rightMoves;
	}

	public int get_downMoves() {
		return _downMoves;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != InstructionSet.class)
			return false;
		InstructionSet set = (InstructionSet) obj;
		if (_instructions.size() != set._instructions.size())
			return false;
		for (int x = 0; x < _instructions.size(); x++) {
			if (set._instructions.get(x) != this._instructions.get(x))
				return false;
		}
		return true;
	}
	
	public InstructionSet clone() {
		return this.clone();
	}
	
	public int nextDown() {
		return _downMoves+1;
	}
	
	public int nextRight() {
		return _rightMoves+1;
	}
	
}
