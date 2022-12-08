package packet;

public class Process {
	private int _arrivalTime;
	private int _priority;
	private int _processorTime;
	
	public Process(String arrivalTime, String priorty, String processorTime){
		_arrivalTime = Integer.parseInt(arrivalTime);
		_priority = Integer.parseInt(priorty);
		_processorTime = Integer.parseInt(processorTime);
	}
	
	public int get_arrivalTime() {
		return _arrivalTime;
	}
	public int get_priority() {
		return _priority;
	}
	public int get_processorTime() {
		return _processorTime;
	}
	public void set_arrivalTime(int _arrivalTime) {
		this._arrivalTime = _arrivalTime;
	}
	public void set_priority(int _priority) {
		this._priority = _priority;
	}
	public void set_processorTime(int _processorTime) {
		this._processorTime = _processorTime;
	}
}
