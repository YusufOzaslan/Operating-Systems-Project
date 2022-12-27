package pkt;
import static pkt.ColoredSystemOutPrintln.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static pkt.ColoredSystemOutPrintln.COLORS;

enum status {
      Hazir,
	  Basladi,
	  Askida,
	  yurutuluyor,
	  zamanasimi,
	  sonlandi
	}

public class myProcess extends Thread{
	
	private int _arrivalTime;
	private int _priority;
	private int _processorTime;
	private int _remainingTime;
	private int _id;// gerek kalmayabilir
	
	private int _colorId;
	static int colorNum = -1;

	public status _status;

	public myProcess(String arrivalTime, String priority, String processorTime){		
		_arrivalTime = Integer.parseInt(arrivalTime);
		_priority = Integer.parseInt(priority);
		_processorTime = Integer.parseInt(processorTime);
		_remainingTime = Integer.parseInt(processorTime);
		_status=status.Hazir;
		
		_id = Dispatcher.idCounter;
		Dispatcher.idCounter++;
		
		//her proses oluşturulduğunda farklı bir renk atanır
		colorNum++;
		if (colorNum > COLORS.length) { colorNum = 0; }
		_colorId = colorNum;

	}
	
	public void execute() {
		// çalıştırılmak istenen proses için bu fonksiyon kullanılır
		// proses oluşturduğumuz jar dosyasını çalıştırır

		String priority = String.valueOf(this._priority);
		
		//burdaki remainingTime'ın hesaplanması doğru olmaz çünkü askıya alınmış bir processin çalışmadığı zamanlarda da remainingTime'ı azalıyormuş gib oluyor
		//onun yerine _remainingTime diye bir property yaptım, her çalıştığı saniye decreaseRemainingTime() fonksiyonunu çalıştırcak 
		String remainingTime = String.valueOf(this._processorTime);
		
		String colorId = String.valueOf((this._colorId));
		String processId = String.valueOf((this._id));
		String jar = "java -jar Java_Process.jar";
		String parameter = jar + " " + processId + " " + priority + " " + remainingTime + " " + colorId;
		//String parameter = jar + " " + processId + " " + priority + " " + get_RemainingTime() + " " + colorId;
		
		try {

	           runProcess(parameter); 
	    } 
		catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	//bu fonksiyon static'ti ama şimdilik değiştirdim deneme için
	private void runProcess(String command) throws Exception {
		
		// yeni bir proses oluşturulur ve çalıştırılır
        Process pro = Runtime.getRuntime().exec(command);
    	
        printLines(pro.getInputStream());
        printLines(pro.getErrorStream());
    
        pro.waitFor();// proses bitene kadar beklenir
      }
	
    private void printLines(InputStream ins) throws Exception {
    	// Ana programdan ayrı çalıştırılan proseslerin bilgileri ekrana yazdırılır
        String line = null;
        BufferedReader in = new BufferedReader(
            new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
      }

	private void executeMessage() {
		System.out.print(COLORS[get_colorId()] + Dispatcher.timer + " sn proses basladi 	 " + ANSI_RESET);
	}
	private void runningMessage() {
		System.out.print(COLORS[get_colorId()] + Dispatcher.timer + " sn proses yurutuluyor 	 " + ANSI_RESET);
	}	
	private void suspendedMessage() { 
		System.out.print(COLORS[get_colorId()] + Dispatcher.timer + " sn proses askida 	 " + ANSI_RESET); }
	private void endMessage() {
		System.out.print(COLORS[get_colorId()] + Dispatcher.timer + " sn proses sonlandi 	 " + ANSI_RESET);
	}
	private void overTimeMessage() {
		System.out.print(COLORS[get_colorId()] + Dispatcher.timer + " sn proses zaman asimi 	 " + ANSI_RESET);
	}	
	
	public boolean isComplete() {
	    return _remainingTime == 0;
	  }
	
	public int get_arrivalTime() { return _arrivalTime; }
	public int get_priority() { return _priority; }
	public int get_processorTime() { return _processorTime; }
	public int get_RemainingTime() { return _remainingTime; }
	public int get_colorId() { return this._colorId; }
	
	public void set_arrivalTime(int _arrivalTime) {
		this._arrivalTime = _arrivalTime;
	}
	public void set_priority(int _priority) {
		this._priority = _priority;
	}
	public void set_processorTime(int _processorTime) {
		this._processorTime = _processorTime;
	}
	
	public void set_status(status _status) { this._status = _status; }
	
	public void decreaseRemainingTime() { this._remainingTime--; }	
}
	

