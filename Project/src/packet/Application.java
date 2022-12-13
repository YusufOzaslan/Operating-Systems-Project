package packet;

import java.io.BufferedReader;
import java.io.FileReader;

public class Application {
	public static void main(String[] args) throws Exception{
		Dispatcher dispatcher = new Dispatcher(readProcess());
		
		//<----------------TEST-------------------->
		
		
		
		dispatcher.split_sort(dispatcher.get_allProccesses());
		dispatcher.get_p0();	
		//dispatcher.get_p1();
		//dispatcher.get_p2();
		//dispatcher.get_p3();
		
		//<---------------TEST-------------------------->
		
		System.out.println(dispatcher.get_allProccesses().getProcessList().get(0).get_processorTime());// test için yazıldı
		System.out.println(dispatcher.get_allProccesses().getProcessList().get(0)._status);// test için yazıldı
		
	}
	
	private static Queue readProcess()  throws Exception{
		// Prosesler dosyadan okunur
		BufferedReader br = new BufferedReader(new FileReader("giris.txt"));
	    String line = null, at, p, pt;
	    Queue processes = new Queue();
	    
	    while ((line = br.readLine()) != null) {
	    // split fonksiyonunun parametresindeki boşluk karakteri hocanın vereceği dosyaya göre değiştirilebilir
	      String[] values = line.split(", ");
	      processes.addProcess(new Process(values[0], values[1], values[2]));
	      
	    }
	    br.close();
	    
	    return processes;
	}
}
