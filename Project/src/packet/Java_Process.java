package packet;

public class Java_Process {
	//Her proses için bu program çalıştırılır
	public static void main(String[] args) {
		long pid = ProcessHandle.current().pid();
		System.out.print("(id:" + pid + "  oncelik:" + args[0] + "  kalan sure:" + args[1] + " sn)");
	}
}
