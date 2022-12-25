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
	private static Timer chronometer = new Timer();
	
	private Queue _allProccesses = new Queue();

	// Öncelikli kuyruklar
	private Queue processList0 = new Queue();
	private Queue processList1 = new Queue();
	private Queue processList2 = new Queue();
	private Queue processList3 = new Queue();
	
	//temel zamanlama kuantumu, feedback sıralayıcısı için kullanılır
	private final int quantum = 1;
	
	public static int timer;
	public static int idCounter = 0;// yeni prosesler üretildikçe değeri arttırılır(myProcess sınıfında kullanılır)
	
	
	public Dispatcher(Queue allProccesses){
		_allProccesses = allProccesses;
		
		
	}
	
	//fonksiyon tamamlanmadı
	public void run() {
		
		//dispatcher çalıştırıldığında zamanlayıcı da çalıştırılır, her saniye timer'ı 1 arttırır
		timer = 0;
		chronometer.scheduleAtFixedRate(new TimerTask() {
		      public void run() {
		    	  //Her saniye ekrana timer'ın değerini göstermek için aşağıdaki satır kullanılır
		    	  //System.out.println("Timer: " + timer);
		    	  timer++;		
		      }
		    }, 0, 1000);
		
		
		for (myProcess process : get_allProccesses().getProcessList()) {
			
			//process gelene kadar beklenir
			wait(process.get_arrivalTime() - timer);
			
			//0 öncelikli processler FCFS ile çalışır
			if (process.get_priority() == 0) {
								
				get_p0().addProcess(process);
									
				process.execute(); //burada process çalıştırıldıktan sonra process bitene kadar geçen sürede loop'un devam edip zamanı gelen diğer processlerin de uygun kuyrukları yerleşmesi
				//mantıklı gibi ama tam yapamadım
				//thread kullanılabilir process.start() ile
				
					
					
			}
			//1, 2 veya 3 öncelikli processlere feedback sistemine gider
			else {
				feedback(process);
			}
		}
		
	}
	
	//fonksiyon tamamlanmadı
	private void feedback(myProcess process) {
		
		switch(process.get_priority()) {
		
		case 1:
			processList1.addProcess(process);
			break;
		
		case 2: 
			processList2.addProcess(process);
			break;
			
		case 3:
			processList3.addProcess(process);
			break;
			
		default:
			System.out.println("Gecersiz priority degeri");
	        break;
		
		
		
		
		}
		
		
		
		
		
	}
	
		
	
	public Queue get_allProccesses() {// test
		return _allProccesses;
	}
	
	//process listesini önceden sortlayıp öncelik kuyruklarına koymak iyi bir fikir olmayabilir
	//processler'in zamanı gelince uygun kuyruklarına göndermek daha mantıklı olur gibi geliyor
	public void split_sort(Queue processList) {
		
		// _allProccesse'de bulunan prosesler öncelikli kuyruklara eklenir(_priority değişkenine göre)
		System.out.println("Spilit Cagirildi");	
		for(int i=0;i<processList.getProcessList().size() ; i++)
		{
			
			int tempPriority=processList.getProcessList().get(i).get_priority();
		
			if( tempPriority == 0) {
				processList0.addProcess(processList.getProcessList().get(i));
			}
			
			else if( tempPriority == 1) {
				processList1.addProcess(processList.getProcessList().get(i));
			}
			else if( tempPriority == 2) {
				processList2.addProcess(processList.getProcessList().get(i));
			}
			else if( tempPriority == 3) {
				processList3.addProcess(processList.getProcessList().get(i));
			}
			else {
				System.out.println("...Gecersiz priority degiskeni...");
			}
			
		}
		
		//Yukarıda priortiy'e gore sıraladık ardından kendi iclerinde arrivalTime'a gore sıralamak icin bunları kullandık --->
		/*--->*/processList0=processList0.sort(processList0);
		/*--->*/processList1=processList1.sort(processList1);
		/*--->*/processList2=processList2.sort(processList2);
		/*--->*/processList3=processList3.sort(processList3);
		
		
	}	
	

	// Assagıda bulunun fonksiyonlar kuyrukları ayrı ayrı getirmek icin yazıldı
	// İclerindeki return harici hersey debug icindir
	
	
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
		
		return processList0;
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

		return processList1;

	}
	
	public Queue get_p2() {
		
		/* <---------------------------------------TEST--------------------------------------->
		System.out.println("P2 Cagirildi");

		System.out.println("ArrivalT     "+"   Prio  "+"     ProsesT" );
		for(int i=0;i<processList2.getProcessList().size();i++) {
			
				System.out.print(processList2.getProcessList().get(i).get_arrivalTime()+"  		  " );
				System.out.print(processList2.getProcessList().get(i).get_priority()+"        " );
				System.out.println(processList2.getProcessList().get(i).get_processorTime());
				
		}
		
		*/
		
		return processList2;
	
	}
	
	public Queue get_p3() {
		
		/* <---------------------------------------TEST--------------------------------------->
		System.out.println("P3 Cagirildi");

		System.out.println("ArrivalT     "+"   Prio  "+"     ProsesT" );
		for(int i=0;i<processList3.getProcessList().size();i++) {
			
				System.out.print(processList3.getProcessList().get(i).get_arrivalTime()+"  		  " );
				System.out.print(processList3.getProcessList().get(i).get_priority()+"        " );
				System.out.println(processList3.getProcessList().get(i).get_processorTime());
				
		}
		
		*/
		
		return processList3;
	}
	
	public static void wait(int seconds) {
	    try {
	    	TimeUnit.SECONDS.sleep(seconds);
	    } catch (InterruptedException e) {
	        // handle exception
	    }
	}
	
	
}
