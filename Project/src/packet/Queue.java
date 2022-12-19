package packet;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Queue {
	
	private List<myProcess> processList = new LinkedList<myProcess>();
	
	public Queue(){
	}
	
	public void addProcess(myProcess process) {
		processList.add(process);
	}
	
	public List<myProcess> getProcessList() {//test
		return processList;
	}
	
	
	public Queue sort(Queue queue) {// Varış zamanına göre sıralanır
		
		Dispatcher d1=new Dispatcher(queue);
					    
		int n = queue.getProcessList().size();
 
		//SelectionShort ile sıraladık 
		for (int i = 0; i < n-1; i++)
		{
	        
			int min_idx = i;
			for (int j = i+1; j < n; j++) {
				int tmpArrival_j=queue.getProcessList().get(j).get_arrivalTime();
				int tmpArrival_min=queue.getProcessList().get(min_idx).get_arrivalTime();      	
				
				if ( tmpArrival_j < tmpArrival_min)
					min_idx = j;
			    }
			            
			    int temp = queue.getProcessList().get(min_idx).get_arrivalTime(); ;
			    queue.getProcessList().get(min_idx).set_arrivalTime(queue.getProcessList().get(i).get_arrivalTime());
			    queue.getProcessList().get(i).set_arrivalTime(temp);
		
		}

		return queue;
	}

	//Kuyruk boş mu değil mi diye kontrol eder
	public boolean isEmpty() {
		
		return processList.isEmpty();
	}
	
}
