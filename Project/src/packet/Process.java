package packet;

enum status {
      Hazir,
	  Basladi,
	  Askida,
	  yurutuluyor,
	  zamanasimi,
	  sonlandi
	}

public class Process implements Runnable {
	
	private int _arrivalTime;
	private int _priority;
	private int _processorTime;
	private int _id;
	
	public status _status;

	
	public Process(String arrivalTime, String priorty, String processorTime){
		
		_arrivalTime = Integer.parseInt(arrivalTime);
		_priority = Integer.parseInt(priorty);
		_processorTime = Integer.parseInt(processorTime);
		_status=status.Hazir;
		
	}
	
	public void execute(Process P) {

		
		
		

		executeMessage(P._id);
	}
	
	private void suspendedMessage() {
		System.out.println("proses basladi : " + _id);
	
	}
	private void endMessage() {
		System.out.println();
	}	
	private void executeMessage(int id) {
		System.out.println("Baslatilan Process id = "+ _id);
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

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	
}
	

