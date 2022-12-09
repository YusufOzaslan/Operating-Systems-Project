package packet;

public class Process {
	private int _arrivalTime;
	private int _priority;
	private int _processorTime;
	private int _id;
	
	public Process(String arrivalTime, String priorty, String processorTime){
		_arrivalTime = Integer.parseInt(arrivalTime);
		_priority = Integer.parseInt(priorty);
		_processorTime = Integer.parseInt(processorTime);
	}
	
	public void execute() {// proses askıya alındığında önceliği düşürülür vs.
		
		/*...
		 * 
		 */
		executeMessage();
	}
	private void suspendedMessage() {
		System.out.println("proses askıya alındı " + _id);
	}	private void endMessage() {
		System.out.println();
	}	
	private void executeMessage() {
		System.out.println();
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
