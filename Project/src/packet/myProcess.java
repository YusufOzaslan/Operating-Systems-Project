package packet;
import static packet.ColoredSystemOutPrintln.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static packet.ColoredSystemOutPrintln.COLORS;

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

	private int _colorId;
	static int colorNum = -1;

	public status _status;


	public myProcess(String arrivalTime, String priority, String processorTime){
		
		_arrivalTime = Integer.parseInt(arrivalTime);
		_priority = Integer.parseInt(priority);
		_processorTime = Integer.parseInt(processorTime);
		_status=status.Hazir;

		//her proses oluşturulduğunda farklı bir renk atanır
		colorNum++;
		if (colorNum > COLORS.length) { colorNum = 0; }
		_colorId = colorNum;


	}
	
	public void execute() {
		// çalıştırılmak istenen proses için bu fonksiyon kullanılır
		// proses oluşturduğumuz jar dosyasını çalıştırır

		
		String priority = String.valueOf(this._priority);
		String remainingTime = String.valueOf(this._arrivalTime - Dispatcher.timer);
		String colorId = String.valueOf((this._colorId));
		String jar = "java -jar Java_Process.jar";
		String parameter = jar + " " + priority + " " + remainingTime + " " + colorId;
		
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
		System.out.print(COLORS[get_colorId()] + Dispatcher.timer + " sn proses basladi 	 " + ANSI_RESET);
	}
	private void runningMessage() {
		System.out.print(COLORS[get_colorId()] + Dispatcher.timer + " sn proses yürütülüyor 	 " + ANSI_RESET);
	}	
	private void suspendedMessage() { System.out.print(COLORS[get_colorId()] + Dispatcher.timer + " sn proses askıda 	 " + ANSI_RESET); }
	private void endMessage() {
		System.out.println(COLORS[get_colorId()] + Dispatcher.timer + " sn proses sonlandı 	 " + ANSI_RESET);
	}	
	
	
	public int get_arrivalTime() { return _arrivalTime; }
	public int get_priority() { return _priority; }
	public int get_processorTime() { return _processorTime; }
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
	
}
	

