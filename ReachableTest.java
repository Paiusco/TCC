import java.io.*;
import java.net.*;

public class ReachableTest {
	public static void main(String args[]) {
     		try {
       			InetAddress address = InetAddress.getByName("usp.br");
       			System.out.println("Name: " + address.getHostName());
       			System.out.println("Addr: " + address.getHostAddress());
      			System.out.println("Reach: " + address.isReachable(3000));
     		}
     		catch (UnknownHostException e) {
       			System.err.println("Unable to lookup web.mit.edu");
     		}
     		catch (IOException e) {
       			System.err.println("Unable to reach web.mit.edu");
     		}
   	}
}
