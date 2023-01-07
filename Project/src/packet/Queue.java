package packet;

import java.util.LinkedList;
import java.util.List;

public class Queue {
	
	private List<myProcess> processList = new LinkedList<myProcess>();
	
	public void addProcessByIndex(int index, myProcess process) {
		processList.add(index, process);
	}
	public void addProcess(myProcess process) {
		processList.add(process);
	}

	public List<myProcess> getProcessList() {
		return processList;
	}
	
	public Queue setProcessList(Queue queue, int index, myProcess process) {
		queue.getProcessList().remove(index);
		queue.getProcessList().add(index, process);
		return queue;
	}

	public Queue sort(Queue queue) {
		// Varış zamanına göre sıralanır		
		int n = queue.getProcessList().size();
		int tmpArrival_j;
		int tmpArrival_min;
		myProcess temp;
		//SelectionShort ile sıraladık 
		for (int i = 0; i < n-1; i++)
		{
			int min_idx = i;
			for (int j = i+1; j < n; j++) {
				tmpArrival_j=queue.getProcessList().get(j).get_arrivalTime();
				tmpArrival_min=queue.getProcessList().get(min_idx).get_arrivalTime();      	
				
				if ( tmpArrival_j < tmpArrival_min) {
					min_idx = j;
			    	}
				}
			temp = queue.getProcessList().get(min_idx);
			queue = queue.setProcessList(queue, min_idx, queue.getProcessList().get(i));
			queue = queue.setProcessList(queue, i, temp);
		}
		return queue;
	}
	//Kuyruk boş mu değil mi diye kontrol eder
	public boolean isEmpty() {
		return processList.isEmpty();
	}
}





