package system;

import java.util.LinkedList;

public class InstructionSet {
	LinkedList<Step> _instructions;
	int _finalValue;
	
	public InstructionSet() {
		_instructions = new LinkedList<Step>();
		_finalValue = 0;
	}
	
	@SuppressWarnings("unchecked")
	public InstructionSet(InstructionSet lastMoves) {
		_finalValue = lastMoves._finalValue;
		_instructions = (LinkedList<Step>) lastMoves._instructions.clone();
	}

	public void addInstruction(Step direction, Value value) {
		_instructions.add(direction);
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
	
	public boolean equals(InstructionSet set) {
		if (_instructions.size() != set._instructions.size())
			return false;
		for (int i = 0; i < _instructions.size(); i++) {
			if (set._instructions.get(i) != _instructions.get(i))
				return false;
		}
		return true;
	}
}
