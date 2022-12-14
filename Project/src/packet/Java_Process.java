package packet;
import static packet.ColoredSystemOutPrintln.*;

public class Java_Process {


	//Her proses için bu program çalıştırılır
	public static void main(String[] args) {
		int colorId = Integer.parseInt(args[2]);
		long pid = ProcessHandle.current().pid();
		System.out.println(COLORS[colorId] + "(id:" + pid + "  oncelik:" + args[0] + "  kalan sure:" + args[1] + " sn)" + ANSI_RESET);
	}
}
