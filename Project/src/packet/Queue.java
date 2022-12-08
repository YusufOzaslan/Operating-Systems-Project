package packet;

import java.util.LinkedList;
import java.util.List;

public class Queue {
	private List processList = new LinkedList();
	
	public Queue(){
	}
	
	public void addProcess(Process process) {
		processList.add(process);
	}
	
	public List getProcessList() {//test
		return processList;
	}
}
