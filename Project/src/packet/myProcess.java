package packet;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

enum status {
      Hazir,
	  Basladi,
	  Askida,
	  yurutuluyor,
	  zamanasimi,
	  sonlandi
	}

public class myProcess {
	
	private int _arrivalTime;
	private int _priority;
	private int _processorTime;
	private int _id;// gerek kalmayabilir
	
	public status _status;

	
	public myProcess(String arrivalTime, String priorty, String processorTime){
		
		_arrivalTime = Integer.parseInt(arrivalTime);
		_priority = Integer.parseInt(priorty);
		_processorTime = Integer.parseInt(processorTime);
		_status=status.Hazir;
		
	}
	
	public void execute() {
		// çalıştırılmak istenen proses için bu fonksiyon kullanılır
		// proses oluşturduğumuz jar dosyasını çalıştırır
		
		
		String priorty = String.valueOf(this._priority);
		String remainingTime = String.valueOf(this._arrivalTime - Dispatcher.timer);
		String jar = "java -jar Java_Process.jar";
		String parameter = jar + " " + priorty + " " + remainingTime;
		
		try {
	           runProcess(parameter);
	    } 
		catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	private static void runProcess(String command) throws Exception {
		// yeni bir proses oluşturulur ve çalıştırılır
        Process pro = Runtime.getRuntime().exec(command);
        printLines(pro.getInputStream());
        printLines(pro.getErrorStream());
        pro.waitFor();// proses bitene kadar beklenir
      }
	
    private static void printLines(InputStream ins) throws Exception {
    	// Ana programdan ayrı çalıştırılan proseslerin bilgileri ekrana yazdırılır
        String line = null;
        BufferedReader in = new BufferedReader(
            new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
      }

	private void executeMessage() {
		System.out.print(Dispatcher.timer + " sn proses basladi 	 ");
	}
	private void runningMessage() {
		System.out.print(Dispatcher.timer + " sn proses yürütülüyor 	 ");
	}	
	private void suspendedMessage() {
		System.out.print(Dispatcher.timer + " sn proses askıda 	 ");
	
	}
	private void endMessage() {
		System.out.println(Dispatcher.timer + " sn proses sonlandı 	 ");
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
	

