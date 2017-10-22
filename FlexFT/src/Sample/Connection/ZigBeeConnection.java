package Sample.Connection;

import FlexFT.State;
import OpenCOM.ILifeCycle;
import OpenCOM.IMetaInterface;
import OpenCOM.IUnknown;
import com.digi.xbee.api.RemoteXBeeDevice;
import com.digi.xbee.api.XBeeDevice;
import com.digi.xbee.api.XBeeNetwork;
import com.digi.xbee.api.exceptions.XBeeException;
import com.digi.xbee.api.utils.HexUtils;
import java.io.*;

public class ZigBeeConnection extends State<IConnection> implements
		IUnknown, IConnection, IMetaInterface, ILifeCycle {

        private static final String PORT = "/dev/ttyUSB0";
       	private static final int BAUD_RATE = 9600;
        private static final String DATA_TO_SEND = "TCC nota 10!";
        private static final String REMOTE_NODE_IDENTIFIER = "REMOTE";

	public ZigBeeConnection(IUnknown pRuntime) {
		super(pRuntime);
	}

	public void ZigBeeCommunication(){

                XBeeDevice myDevice = new XBeeDevice(PORT, BAUD_RATE);
                byte[] dataToSend = DATA_TO_SEND.getBytes();

		 try {
                        myDevice.open();

                        // Obtain the remote XBee device from the XBee network.
                        XBeeNetwork xbeeNetwork = myDevice.getNetwork();
                        RemoteXBeeDevice remoteDevice = xbeeNetwork.discoverDevice(REMOTE_NODE_IDENTIFIER);
                        if (remoteDevice == null) {
                                System.out.println("Couldn't find the remote XBee device with '" + REMOTE_NODE_IDENTIFIER + "' Node Identifier.");
                                System.exit(1);
                        }

                        System.out.format("Sending data to %s >> %s | %s... ", remoteDevice.get64BitAddress(),
                                        HexUtils.prettyHexString(HexUtils.byteArrayToHexString(dataToSend)),
                                        new String(dataToSend));

                        myDevice.sendData(remoteDevice, dataToSend);

                        System.out.println("Success");

                } catch (XBeeException e) {
                        System.out.println("Error");
                        e.printStackTrace();
			//Colocar algum tipo de return
                        System.exit(1);
                } finally {
                        myDevice.close();
                }
	}

	public boolean send(String message) {
		File file = new File("/home/pi/inicio.txt");
		try {
			if (file.createNewFile()) {
				System.out.println("File named created successfully !");
  			} else {
				System.out.println("File with name already exixts !");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("[" + this + "] executando send => " + message);

		ZigBeeCommunication();

		try {
			if (file.delete()) {  
				System.out.println("File deleted successfully !");  
			} else {  
				System.out.println("File delete operation failed !");  
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
  	}
}

