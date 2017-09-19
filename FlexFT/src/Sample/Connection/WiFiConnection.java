package Sample.Connection;

import FlexFT.State;
import OpenCOM.ILifeCycle;
import OpenCOM.IMetaInterface;
import OpenCOM.IUnknown;
import java.io.*;
import java.net.*;

public class WiFiConnection extends State<IConnection> implements IUnknown, IConnection,
		IMetaInterface, ILifeCycle {

	public WiFiConnection(IUnknown pRuntime) {
		super(pRuntime);
	}

	public boolean pingTester(String host) {
		try {
        		InetAddress address = InetAddress.getByName(host);
	                System.out.println("Name: " + address.getHostName());
        	        System.out.println("Addr: " + address.getHostAddress());
                	System.out.println("Reach: " + address.isReachable(3000));
		}
	        catch (UnknownHostException e) {
        	        System.err.println("Unable to lookup usp.br");
			return false;
	        }
        	catch (IOException e) {
                	System.err.println("Unable to reach usp.br");
			return false;
		}
	return true;
	}



	public void socketCommunication() {
		try {
			Socket socket1;
    			int portNumber = 1234;
    			String str = "TCC nota 10!\n";

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
					System.out.println("Final da conexao");
        				break;
				}
    			}

	   		br.close();
    			pw.close();
	    		socket1.close();
		}
		catch(Exception e) {
			System.out.println("Exception catched");
		}
	}



	public void send(String message) {

			boolean result;
			if (pingTester("usp.br") != true)
				if (pingTester("web.mit.edu") != true)
					if (pingTester("google.com") != true)
						System.out.println("Colocar aqui retorno de erro Wifi");
						//colocar aqui o retorno de erro da WiFi
		/*
		try {

	                Socket socket1;
                        int portNumber = 1234;
                        String str = "TCC nota 10 mesmo!\n";

                        socket1 = new Socket();
                        socket1.connect(new InetSocketAddress("192.168.0.14", portNumber),10000);
                        System.out.println(str);

                        BufferedReader br = new BufferedReader(new InputStreamReader(socket1.getInputStream()));

                        PrintWriter pw = new PrintWriter(socket1.getOutputStream(), true);

                        pw.println(str);

                        while ((str = br.readLine()) != null) {
                                System.out.println(str);
                                //Thread.sleep(5000);
                                pw.println("bye");

                                if (str.equals("bye")){
                                        System.out.println("Errou");
                                        break;
                                }
                        }

                        br.close();
                        pw.close();
                        socket1.close();
	                System.out.println("[" + this + "] executando send => " + message);
        	        System.out.println("[" + this + "] executando send => " + message);
                }
                catch(Exception e) {
                        System.out.println("Exception catched");
                } */

		socketCommunication();
		System.out.println("[" + this + "] executando send => " + message);
		System.out.println("[" + this + "] executando send => " + message);
	}
}
