package packet;
public class Dispatcher {
	
	private Queue _allProccesses = new Queue();

	// Öncelikli kuyruklar
	private Queue processQueue0 = new Queue();
	private Queue processQueue1 = new Queue();
	private Queue processQueue2 = new Queue();
	private Queue processQueue3 = new Queue();
	
	private Queue suspendQueue2 = new Queue();
	private Queue suspendQueue3 = new Queue();
	
	public static int timer = 0;
	// yeni prosesler üretildikçe değeri arttırılır(myProcess sınıfında kullanılır)
	public static int idCounter = 0;

	private int temp;
	private boolean startCheck = true;
	
	public Dispatcher(Queue allProccesses){
		_allProccesses = allProccesses;
	}
	
	public void runDispatcher() {
		split_sort(_allProccesses);
		myProcess process;
		
		if(processQueue0.getProcessList().size()>0) {
	
			for (int i = 0; i < processQueue0.getProcessList().size(); i++) {
				startCheck = true;
				//q0 kuyruğunda sıradaki proses seçilir
					process = processQueue0.getProcessList().get(i);
					//0 öncelikli processler FCFS ile çalışır
					if (process.get_arrivalTime() <= timer) {
						for (int j = process.get_processorTime(); j > 0; j--) {					
							if(startCheck) {
								// ilk önce proses başladı mesajı yazılır
								process.executeMessage();
								startCheck = false;
							}
							else{
								process.runningMessage();
							}
							// proses zamanı azaltılır
							temp = process.get_processorTime();
							temp--;
							process.execute();
							process.set_processorTime(temp);
							// Askı kontrolü yapılır
							timeoutCheck();
							timer++;
						}
						process.endMessage();
						process.execute();
						// prosesin en son çalıştığı zaman kaydedilir
						// max değer verilerek kuyruktan çıkarıldığı varsayıldı
						process.set_lastCallTime(2147483647);
					}
					//1, 2 veya 3 öncelikli prosesler için feedback fonksiyonuna gidilir
					else {
						feedback();
						timeoutCheck();
						i--;
					}
			}			
		}
		while(processQueue1.getProcessList().size()>0 ||
			  processQueue2.getProcessList().size()>0 ||
			  processQueue3.getProcessList().size()>0 ||
			  suspendQueue2.getProcessList().size()>0 ||
			  suspendQueue3.getProcessList().size()>0)
		{
			feedback();
			timeoutCheck();
		}
		
	}
	
	private void feedback() {// bu fonksiyon daha tamamlanmadı
		myProcess process;		
		myProcess process1;
		myProcess process2;
		myProcess process3;
		
		// proses seçimi yapılır
		if(!processQueue1.isEmpty()) {
			process1 = processQueue1.getProcessList().get(0);
		}
		else process1 = null;
		
		if(!processQueue2.isEmpty()) {
			process2 = processQueue2.getProcessList().get(0);
		}
		else if(!suspendQueue2.isEmpty()) {
			process2 = suspendQueue2.getProcessList().get(0);
		}
		else process2 = null;
		
		if(!processQueue3.isEmpty()) {
			process3 = processQueue3.getProcessList().get(0);
		}
		else if(!suspendQueue3.isEmpty()) {
			process3 = suspendQueue3.getProcessList().get(0);
		}
		else process3 = null;
		
		
		if (process1 != null && process1.get_arrivalTime() <= timer) {
			process = process1;
		}
		else if(process2 != null && process2.get_arrivalTime() <= timer) {
			process = process2;
		}
		else if(process3 != null && process3.get_arrivalTime() <= timer) {
			process = process3;
		}
		else {// hiçbir prosesin zamanı gelmediyse fonksiyondan çıkılır
			timer++;
			return;
		}
		switch(process.get_priority()) {
		
		case 1:
			process.executeMessage();
			process.execute(); 
			// proses zamanı azaltılır
			temp = process.get_processorTime();
			temp--;
			process.set_processorTime(temp);   
			// öncelik değeri arttırılır
			temp = process.get_priority();
			temp++;
			process.set_priority(temp);
			timer++;
			// prosesin en son çalıştığı zaman kaydedilir
			process.set_lastCallTime(timer);
			if (process.get_processorTime() == 0) {
				//süresi biten proses sonlanır
				process.endMessage();
				process.execute();
				processQueue1.getProcessList().remove(0);
			} 
			else {// 1 saniye çalıştıktan sonra askıya alınır
				process.suspendedMessage();
				suspendQueue2.addProcess(process);
				process.execute();
				processQueue1.getProcessList().remove(0);
			}
			break;		
		case 2:
			process.executeMessage();
			process.execute();
			// proses zamanı azaltılır
			temp = process.get_processorTime();
			temp--;
			process.set_processorTime(temp);
			// öncelik değeri arttırılır
			temp = process.get_priority();
			temp++;
			process.set_priority(temp);
			timer++;
			// prosesin en son çalıştığı zaman kaydedilir
			process.set_lastCallTime(timer);
			if(process.get_processorTime() == 0) {// proses süresi biterse
				if (suspendQueue2.getProcessList().contains(process)) {// proses askıya alınan kuyruktan çalışıyor ise
					process.endMessage();
					process.execute();
					suspendQueue2.getProcessList().remove(0);
				} else {// proses ilk kez çalışıyor ise
					process.endMessage();
					process.execute();
					processQueue2.getProcessList().remove(0);
				}
			}
			else {
				if (suspendQueue2.getProcessList().contains(process)) {
					// proses askıya alınan kuyruktan çalışıyor ise
					process.suspendedMessage();
					suspendQueue3.addProcess(process);
					process.execute();
					suspendQueue2.getProcessList().remove(0);
				} else {// proses ilk kez çalışıyor ise
					process.suspendedMessage();
					suspendQueue3.addProcess(process);
					process.execute();
					processQueue2.getProcessList().remove(0);
				}
			}
			break;			
		case 3:
			process.executeMessage();
			process.execute();
			// proses zamanı azaltılır
			temp = process.get_processorTime();
			temp--;
			process.set_processorTime(temp);
			timer++;
			// prosesin en son çalıştığı zaman kaydedilir
			process.set_lastCallTime(timer);			
			if(process.get_processorTime() == 0) {// proses süresi biterse
				if(processQueue3.getProcessList().contains(process)) {// proses processQueue3 kuyruğunda ise
					process.endMessage();
					process.execute();
					processQueue3.getProcessList().remove(0);
				}
				else {// process suspendQueue3 kuyruğunda ise
					process.endMessage();
					process.execute();
					suspendQueue3.getProcessList().remove(0);

				}
			}
			else {
				if (suspendQueue3.getProcessList().contains(process)) {
					// proses askıya alınan kuyruktan çalışıyor ise
					process.suspendedMessage();
					suspendQueue3.addProcess(process);
					process.execute();
					suspendQueue3.getProcessList().remove(0);
					
				} else {// proses ilk kez çalışıyor ise

					process.suspendedMessage();
					suspendQueue3.addProcess(process);
					process.execute();
					processQueue3.getProcessList().remove(0);
				}
			}
			break;			
		default:
			System.out.println("Gecersiz priority degeri");
	        break;		
		}			
	}
	
	public void timeoutCheck() {
		myProcess process;
		int dif = 0;
		if(!suspendQueue2.isEmpty()) {
			process = suspendQueue2.getProcessList().get(0);
			dif = timer - process.get_lastCallTime();
			if (dif == 20) {
				// askıya alınan proses 20 saniye beklerse zaman aşımına uğrar
				process.overTimeMessage();
				process.execute();
				suspendQueue2.getProcessList().remove(0);
			}
		}
		if(!suspendQueue3.isEmpty()) {
			process = suspendQueue3.getProcessList().get(0);
			dif = timer - process.get_lastCallTime();
			if (dif == 20) {
				// askıya alınan proses 20 saniye beklerse zaman aşımına uğrar
				process.overTimeMessage();
				process.execute();
				suspendQueue3.getProcessList().remove(0);
			}
		}
		if(!processQueue0.isEmpty()) {
			process = processQueue0.getProcessList().get(0);
			dif = timer - process.get_lastCallTime();
			if (dif == 20) {
				// askıya alınan proses 20 saniye beklerse zaman aşımına uğrar
				process.overTimeMessage();
				process.execute();
				processQueue0.getProcessList().remove(0);
			}
		}
		if(!processQueue1.isEmpty()) {
			process = processQueue1.getProcessList().get(0);
			dif = timer - process.get_lastCallTime();
			if (dif == 20) {
				// askıya alınan proses 20 saniye beklerse zaman aşımına uğrar
				process.overTimeMessage();
				process.execute();
				processQueue1.getProcessList().remove(0);
			}
		}
		if(!processQueue2.isEmpty()) {
			process = processQueue2.getProcessList().get(0);
			dif = timer - process.get_lastCallTime();
			if (dif == 20) {
				// askıya alınan proses 20 saniye beklerse zaman aşımına uğrar
				process.overTimeMessage();
				process.execute();
				processQueue2.getProcessList().remove(0);
			}
		}
		if(!processQueue3.isEmpty()) {
			process = processQueue3.getProcessList().get(0);
			dif = timer - process.get_lastCallTime();
			if (dif == 20) {
				// askıya alınan proses 20 saniye beklerse zaman aşımına uğrar
				process.overTimeMessage();
				process.execute();
				processQueue3.getProcessList().remove(0);
			}
		}
		else {
			return;
		}
	}
	
	public void split_sort(Queue processList) {		
		// _allProccesse'de bulunan prosesler öncelikli kuyruklara eklenir(_priority değişkenine göre)	
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
		/*--->*/if(!processQueue0.isEmpty()) {processQueue0=processQueue0.sort(processQueue0);}
		/*--->*/if(!processQueue1.isEmpty()) {processQueue1=processQueue1.sort(processQueue1);}
		/*--->*/if(!processQueue2.isEmpty()) {processQueue2=processQueue2.sort(processQueue2);}
		/*--->*/if(!processQueue3.isEmpty()) {processQueue3=processQueue3.sort(processQueue3);}
	}	
	
	public Queue get_allProccesses() {// test
		return _allProccesses;
	}
}
