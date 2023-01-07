package packet;

import java.io.BufferedReader;
import java.io.FileReader;

public class Application {
	public static void main(String[] args) throws Exception{
		Dispatcher dispatcher = new Dispatcher(readProcess());
		dispatcher.runDispatcher();
	}
	
	private static Queue readProcess()  throws Exception{
		// Prosesler dosyadan okunur
		BufferedReader br = new BufferedReader(new FileReader("giris.txt"));
	    String line = null;
	    Queue processes = new Queue();
	    while ((line = br.readLine()) != null) {
	      String[] values = line.split(", ");
	      processes.addProcess(new myProcess(values[0], values[1], values[2]));
	    }
	    br.close();
	    return processes;
	}
}
