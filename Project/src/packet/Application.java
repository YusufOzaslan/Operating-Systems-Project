package packet;

import static packet.ColoredSystemOutPrintln.ANSI_RESET;
import static packet.ColoredSystemOutPrintln.COLORS;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;


public class Application {
	
	
	
	public static void main(String[] args) throws Exception{
		
		
		
		/*Dispatcher dispatcher = new Dispatcher(readProcess());
		dispatcher.execute();*/
		
		
		
		/*/<----------------TEST-------------------->
		
		
		dispatcher.split_sort(dispatcher.get_allProccesses());
		dispatcher.get_p0();	
		dispatcher.get_p1();
		dispatcher.get_p2();
		dispatcher.get_p3();
		
			
		<---------------TEST-------------------------->/*/
		
		// process test
		myProcess mp = new myProcess("5", "5", "5");
		mp.execute();
		myProcess mp2 = new myProcess("2", "2", "2");
		mp2.execute();
		
		
		// process test son
		
		
	}
	
	private static Queue readProcess()  throws Exception{
		// Prosesler dosyadan okunur
		BufferedReader br = new BufferedReader(new FileReader("giris.txt"));
	    String line = null, at, p, pt;
	    Queue processes = new Queue();
	    
	    while ((line = br.readLine()) != null) {
	    // split fonksiyonunun parametresindeki boşluk karakteri hocanın vereceği dosyaya göre değiştirilebilir
	      String[] values = line.split(", ");
	      processes.addProcess(new myProcess(values[0], values[1], values[2]));
	      
	    }
	    br.close();
	    
	    return processes;
	}
}
