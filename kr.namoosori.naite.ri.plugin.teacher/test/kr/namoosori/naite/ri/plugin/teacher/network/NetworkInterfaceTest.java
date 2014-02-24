package kr.namoosori.naite.ri.plugin.teacher.network;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

public class NetworkInterfaceTest {
	public static void main(String[] args) throws Exception {
		//
		//viewNetworkInterfaces();
		viewNetworkInterfacesInformation();
		//viewNetworkInterface("net5");//(Intel(R) Centrino(R) Advanced-N 6205)
		//viewNetworkInterfacesWithSub();
	}
	
	public static void viewNetworkInterfacesInformation() throws Exception {
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface netint : Collections.list(nets))
            displayInterfaceInformation(netint);
    }

    static void displayInterfaceInformation(NetworkInterface netint) throws Exception {
        System.out.printf("Display name: %s\n", netint.getDisplayName());
        System.out.printf("Name: %s\n", netint.getName());
        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
        
        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
        	System.out.printf("InetAddress: %s\n", inetAddress);
        }
       
        System.out.printf("Up? %s\n", netint.isUp());
        System.out.printf("Loopback? %s\n", netint.isLoopback());
        System.out.printf("PointToPoint? %s\n", netint.isPointToPoint());
        System.out.printf("Supports multicast? %s\n", netint.supportsMulticast());
        System.out.printf("Virtual? %s\n", netint.isVirtual());
        System.out.printf("Hardware address: %s\n",
                    Arrays.toString(netint.getHardwareAddress()));
        System.out.printf("MTU: %s\n", netint.getMTU());
        System.out.printf("\n");
     }
	
	static void viewNetworkInterfacesWithSub() throws Exception {
		//
		Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        
        for (NetworkInterface netIf : Collections.list(nets)) {
            System.out.printf("Display name: %s\n", netIf.getDisplayName());
            System.out.printf("Name: %s\n", netIf.getName());
            displaySubInterfaces(netIf);
            System.out.printf("\n");
        }
	}
	
	static void displaySubInterfaces(NetworkInterface netIf) throws Exception {
        Enumeration<NetworkInterface> subIfs = netIf.getSubInterfaces();
        
        for (NetworkInterface subIf : Collections.list(subIfs)) {
        	System.out.printf("\tSub Interface Display name: %s\n", subIf.getDisplayName());
        	System.out.printf("\tSub Interface Name: %s\n", subIf.getName());
        }
     }

	static void viewNetworkInterfaces() throws Exception {
		//
		Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
		while (nis.hasMoreElements()) {
			NetworkInterface ni = nis.nextElement();
			System.out.println(ni);
			viewNetworkInterface(ni.getName());
		}
	}
	private static void viewNetworkInterface(String name) throws Exception {
		//
		NetworkInterface nif = NetworkInterface.getByName(name);
		Enumeration<InetAddress> nifAddresses = nif.getInetAddresses();
		while (nifAddresses.hasMoreElements()) {
			InetAddress addr = nifAddresses.nextElement();
			System.out.println(addr);
		}
	}

	@SuppressWarnings("resource")
	void example() throws Exception {
		String address = "127.0.0.1";
		int port = 4447;
		String hostname = "";
		
		// create socket to send data to server
		Socket soc = new java.net.Socket();
		soc.connect(new InetSocketAddress(address, port));
		
		// if you have a preference or otherwise need to specify which NIC to use
		// When you create the socket and bind it to that address, the system uses the associated interface
		NetworkInterface nif = NetworkInterface.getByName("bge0");
		Enumeration<InetAddress> nifAddresses = nif.getInetAddresses();

		Socket soc1 = new java.net.Socket();
		soc1.bind(new InetSocketAddress(nifAddresses.nextElement(), 0));
		soc1.connect(new InetSocketAddress(address, port));
		
		// You can also use NetworkInterface to identify the local interface on which a multicast group is to be joined.
		NetworkInterface nif2 = NetworkInterface.getByName("bge0");
		MulticastSocket ms = new MulticastSocket();
		ms.joinGroup(new InetSocketAddress(hostname, port), nif2);
	}
}
