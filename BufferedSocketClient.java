import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
//import java.net.InetAddress;
//import java.net.Socket;
import java.net.*;


public class BufferedSocketClient {

  public static void main(String args[]) throws Exception {
    Socket socket1;
    int portNumber = 1234;
    String str = "Esse é um TCC nota 10 mesmo!\n";

    socket1 = new Socket();
	socket1.connect(new InetSocketAddress("192.168.0.14", portNumber),5000);
	System.out.println(str);

    BufferedReader br = new BufferedReader(new InputStreamReader(socket1.getInputStream()));

    PrintWriter pw = new PrintWriter(socket1.getOutputStream(), true);

    pw.println(str);

    while ((str = br.readLine()) != null) {
      	System.out.println(str);
	//Thread.sleep(5000);
      	pw.println("bye");

      	if (str.equals("bye")){
		System.out.println("Acertouuu");
        	break;
	}
    }

    br.close();
    pw.close();
    socket1.close();
  }

}
