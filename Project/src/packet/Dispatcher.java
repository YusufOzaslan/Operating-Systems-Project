package packet;

public class Dispatcher {
	private Queue _allProccesses = new Queue();
	
	public Dispatcher(Queue allProccesses){
		_allProccesses = allProccesses;
	}
	
	public Queue get_allProccesses() {// test
		return _allProccesses;
	}
}
