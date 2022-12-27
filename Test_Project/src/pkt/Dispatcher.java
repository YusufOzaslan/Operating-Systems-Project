package pkt;

import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.stream.Collectors;
import java.util.stream.*;  
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.Timer;
import java.util.TimerTask;


public class Dispatcher {
	
	//timer'ı çalıştırmak için gereken nesne
	//private static Timer chronometer = new Timer();
	
	private Queue _allProccesses = new Queue();

	// Öncelikli kuyruklar
	private Queue processQueue0 = new Queue();
	private Queue processQueue1 = new Queue();
	private Queue processQueue2 = new Queue();
	private Queue processQueue3 = new Queue();
	
	//temel zamanlama kuantumu, feedback sıralayıcısı için kullanılır
	private final int quantum = 1;
	
	public static int timer = 0;
	public static int idCounter = 0;// yeni prosesler üretildikçe değeri arttırılır(myProcess sınıfında kullanılır)
	
	private int temp;
	private Time t = new Time();
	
	public Dispatcher(Queue allProccesses){
		_allProccesses = allProccesses;
		this.t.timer = 0;
	}
	
	public void runDispatcher() {
		split_sort(_allProccesses);
		this._allProccesses=this._allProccesses.sort(this._allProccesses);
		
		threadq0 tq0 = new threadq0(processQueue0, t);
		threadq1 tq1 = new threadq1(processQueue1, processQueue2, t);
		threadq2 tq2 = new threadq2(processQueue2, processQueue3, t);
		threadq3 tq3 = new threadq3(processQueue3, t);
		tq0.start();
		tq1.start();
		tq2.start();
		tq3.start();		
	}
	
	//fonksiyon tamamlanmadı
	private void feedback(myProcess process) {
		
		switch(process.get_priority()) {
		
		case 1:
			if (process.get_arrivalTime() <= timer) {
				process.execute();
				// proses zamanı azaltılır
				temp = process.get_processorTime();
				temp--;
				process.set_processorTime(temp);
				// öncelik değeri arttırılır
				temp = process.get_priority();
				temp++;
				process.set_priority(temp);		
				processQueue2.addProcess(process);
			}
			break;		
		case 2:
			if (process.get_arrivalTime() <= timer) {
				process.execute();
				// proses zamanı azaltılır
				temp = process.get_processorTime();
				temp--;
				process.set_processorTime(temp);
				// öncelik değeri arttırılır
				temp = process.get_priority();
				temp++;
				process.set_priority(temp);			
				processQueue3.addProcess(process);
			}
			break;			
		case 3:
			if (process.get_arrivalTime() <= timer) {
				process.execute();
				// proses zamanı azaltılır
				temp = process.get_processorTime();
				temp--;
				process.set_processorTime(temp);
				// öncelik değeri arttırılır
				temp = process.get_priority();
				temp++;
				process.set_priority(temp);
			}
			break;			
		default:
			System.out.println("Gecersiz priority degeri");
	        break;		
		}			
	}
	
	//process listesini önceden sortlayıp öncelik kuyruklarına koymak iyi bir fikir olmayabilir
	//processler'in zamanı gelince uygun kuyruklarına göndermek daha mantıklı olur gibi geliyor
	public void split_sort(Queue processList) {
		
		// _allProccesse'de bulunan prosesler öncelikli kuyruklara eklenir(_priority değişkenine göre)
		System.out.println("Split Cagirildi");	
		for(int i=0;i<processList.getProcessList().size() ; i++)
		{
			
			int tempPriority=processList.getProcessList().get(i).get_priority();
		
			if( tempPriority == 0) {
				processQueue0.addProcess(processList.getProcessList().get(i));
			}
			
			else if( tempPriority == 1) {
				processQueue1.addProcess(processList.getProcessList().get(i));
			}
			else if( tempPriority == 2) {
				processQueue2.addProcess(processList.getProcessList().get(i));
			}
			else if( tempPriority == 3) {
				processQueue3.addProcess(processList.getProcessList().get(i));
			}
			else {
				System.out.println("...Gecersiz priority degiskeni...");
			}
			
		}
		
		//Yukarıda priortiy'e gore sıraladık ardından kendi iclerinde arrivalTime'a gore sıralamak icin bunları kullandık --->
		/*--->*/processQueue0=processQueue0.sort(processQueue0);
		/*--->*/processQueue1=processQueue1.sort(processQueue1);
		/*--->*/processQueue2=processQueue2.sort(processQueue2);
		/*--->*/processQueue3=processQueue3.sort(processQueue3);
	}	
	

	// Assagıda bulunun fonksiyonlar kuyrukları ayrı ayrı getirmek icin yazıldı
	// İclerindeki return harici her sey debug icindir
	
	
	public Queue get_p0() {
		
		/*/ <---------------------------------------DEBUG--------------------------------------->
		System.out.println("P0 Cagirildi");

		System.out.println("ArrivalT     "+"   Prio  "+"     ProsesT" );
		for(int i=0;i<processList0.getProcessList().size();i++) {
			
				System.out.print(processList0.getProcessList().get(i).get_arrivalTime()+"  		  " );
				System.out.print(processList0.getProcessList().get(i).get_priority()+"        " );
				System.out.println(processList0.getProcessList().get(i).get_processorTime());
				
		}
		
		//<---------------------------------------DEBUG--------------------------------------->*/
		
		return processQueue0;
	}
	
	public Queue get_p1() {
		
		/*/ <---------------------------------------DEBUG--------------------------------------->
		System.out.println("P1 Cagirildi");
		System.out.println("ArrivalT     "+"   Prio  "+"     ProsesT" );
		for(int i=0;i<processList1.getProcessList().size();i++) {
		
				System.out.print(processList1.getProcessList().get(i).get_arrivalTime()+"  		  " );
				System.out.print(processList1.getProcessList().get(i).get_priority()+"        " );
				System.out.println(processList1.getProcessList().get(i).get_processorTime());
				
		}
		
		//<---------------------------------------DEBUG--------------------------------------->*/

		return processQueue1;

	}
	
	public Queue get_p2() {
		
		/*/ <---------------------------------------TEST--------------------------------------->
		System.out.println("P2 Cagirildi");

		System.out.println("ArrivalT     "+"   Prio  "+"     ProsesT" );
		for(int i=0;i<processList2.getProcessList().size();i++) {
			
				System.out.print(processList2.getProcessList().get(i).get_arrivalTime()+"  		  " );
				System.out.print(processList2.getProcessList().get(i).get_priority()+"        " );
				System.out.println(processList2.getProcessList().get(i).get_processorTime());
				
		}
		
		/*/
		
		return processQueue2;
	
	}
	
	public Queue get_p3() {
		
		/*/ <---------------------------------------TEST--------------------------------------->
		System.out.println("P3 Cagirildi");

		System.out.println("ArrivalT     "+"   Prio  "+"     ProsesT" );
		for(int i=0;i<processList3.getProcessList().size();i++) {
			
				System.out.print(processList3.getProcessList().get(i).get_arrivalTime()+"  		  " );
				System.out.print(processList3.getProcessList().get(i).get_priority()+"        " );
				System.out.println(processList3.getProcessList().get(i).get_processorTime());
				
		}
		
		/*/
		
		return processQueue3;
	}
	
	public static void wait(int seconds) {
	    try {
	    	TimeUnit.SECONDS.sleep(seconds);
	    } catch (InterruptedException e) {
	        // handle exception
	    }
	}
	
	public Queue get_allProccesses() {// test
		return _allProccesses;
	}
}

class threadq0 extends Thread{
	Queue _processQueue0;
	Time t;
	int temp;
	public threadq0(Queue processQueue0, Time timer){
		_processQueue0 = processQueue0;
		this.t = timer;
	}
	public void run() {
		for (myProcess process : _processQueue0.getProcessList()) {
			synchronized (t) {
				if (process.get_arrivalTime() <= t.timer) {
					for (int i = process.get_processorTime(); i >= 0; i--) {
						// proses zamanı azaltılır
						temp = process.get_processorTime();
						temp--;
						process.execute();
						process.set_processorTime(temp);
						t.timer++;
					}
				}
				else {
					//feedback(process);
					t.timer++;
				}
			}
		}
	}
}
class threadq1 extends Thread{
	Queue _processQueue1;
	Queue _processQueue2;
	Time t;
	int temp;
	public threadq1(Queue processQueue1, Queue processQueue2, Time timer){
		_processQueue1 = processQueue1;
		_processQueue2 = processQueue2;
		this.t = timer;
	}
	public void run() {
		for (myProcess process : _processQueue1.getProcessList()) {
			synchronized (t) {
				if (process.get_arrivalTime() <= t.timer) {
					process.execute();
					// proses zamanı azaltılır
					temp = process.get_processorTime();
					temp--;
					process.set_processorTime(temp);
					// öncelik değeri arttırılır
					temp = process.get_priority();
					temp++;
					process.set_priority(temp);		
					_processQueue2.addProcess(process);
				}
			}
		}
	}
}
class threadq2 extends Thread{
	Queue _processQueue2;
	Queue _processQueue3;
	Time t;
	int temp;
	public threadq2(Queue processQueue2, Queue processQueue3, Time timer){
		_processQueue2 = processQueue2;
		_processQueue3 = processQueue3;
		this.t = timer;
	}
	public void run() {
		for (myProcess process : _processQueue2.getProcessList()) {
			synchronized (t) {
				if (process.get_arrivalTime() <= t.timer) {
					process.execute();
					// proses zamanı azaltılır
					temp = process.get_processorTime();
					temp--;
					process.set_processorTime(temp);
					// öncelik değeri arttırılır
					temp = process.get_priority();
					temp++;
					process.set_priority(temp);		
					_processQueue3.addProcess(process);
				}
			}
		}
	}
}
class threadq3 extends Thread{
	Queue _processQueue3;
	Time t;
	int temp;
	public threadq3(Queue processQueue3, Time timer){
		_processQueue3 = processQueue3;
		this.t = timer;
	}
	public void run() {
		for (myProcess process : _processQueue3.getProcessList()) {
			synchronized (t) {
				if (process.get_arrivalTime() <= t.timer) {
					process.execute();
					// proses zamanı azaltılır
					temp = process.get_processorTime();
					temp--;
					process.set_processorTime(temp);
				}
			}
		}
	}
}


