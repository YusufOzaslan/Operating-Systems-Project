package packet;

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
	
	public Dispatcher(Queue allProccesses){
		_allProccesses = allProccesses;
	}
	
	public void runDispatcher() {
		//split_sort(_allProccesses);
		this._allProccesses=this._allProccesses.sort(this._allProccesses);
			
		for (myProcess process : _allProccesses.getProcessList()) {
			//0 öncelikli processler FCFS ile çalışır
			if (process.get_arrivalTime() <= timer && process.get_priority() == 0) {
				for (int i = process.get_processorTime(); i >= 0; i--) {
					// proses zamanı azaltılır
					temp = process.get_processorTime();
					temp--;
					process.execute();
					process.set_processorTime(temp);
					timer++;
				}
			}
			//1, 2 veya 3 öncelikli processlere feedback sistemine gider
			else {
				feedback(process);
				timer++;
			}
		}
		
		
		/*
		//dispatcher çalıştırıldığında zamanlayıcı da çalıştırılır, her saniye timer'ı 1 arttırır
		timer = 0;
		chronometer.scheduleAtFixedRate(new TimerTask() {
		      public void run() {
		    	  //Her saniye ekrana timer'ın değerini göstermek için aşağıdaki satır kullanılır
		    	  //System.out.println("Timer: " + timer);
		    	  timer++;		
		      }
		    }, 0, 1000);*/
		
		/*
		for (myProcess process : _allProccesses.getProcessList()) {
			
			//process gelene kadar beklenir
			wait(process.get_arrivalTime() - timer);
			
			//0 öncelikli processler FCFS ile çalışır
			if (process.get_priority() == 0) {
								
				get_p0().addProcess(process);
									
				process.execute(); //burada process çalıştırıldıktan sonra process
				//bitene kadar geçen sürede loop'un devam edip 
				//zamanı gelen diğer processlerin de uygun kuyrukları yerleşmesi
				//mantıklı gibi ama tam yapamadım
				//thread kullanılabilir process.start() ile
					
			}
			//1, 2 veya 3 öncelikli processlere feedback sistemine gider
			else {
				feedback(process);
			}
		}*/
		
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
