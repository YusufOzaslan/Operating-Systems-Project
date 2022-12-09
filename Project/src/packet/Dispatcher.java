package packet;

import java.util.LinkedList;
import java.util.List;

public class Dispatcher {
	private Queue _allProccesses = new Queue();

	// Öncelikli kuyruklar
	private Queue processList0 = new Queue();
	private Queue processList1 = new Queue();
	private Queue processList2 = new Queue();
	private Queue processList3 = new Queue();
	public static int timer;
	
	
	public Dispatcher(Queue allProccesses){
		_allProccesses = allProccesses;
	}
	
	public Queue get_allProccesses() {// test
		return _allProccesses;
	}
	public void split() {
	// _allProccesse'de bulunna prosesler öncelikli kuyruklara eklenir(_priority değişkenine göre)
		
	}
	public void feedBack() {
		
	}
}
