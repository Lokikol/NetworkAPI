package eu.derloki.networkapi.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import eu.derloki.networkapi.network.NetworkConnection;
import eu.derloki.networkapi.network.ReceivedData;

public class UDPConnection extends NetworkConnection{
	private DatagramSocket socket = null;
	private DatagramPacket spacket = null;
	private DatagramPacket rpacket = null;
	
	private int port;
	
	
	public UDPConnection() throws SocketException{
		this(1024);
	}
	
	public UDPConnection(int port) throws SocketException {
		this(port,1024);
	}
	
	public UDPConnection(int port, int bufferSize) throws SocketException{
		super(bufferSize);
		this.port = port;
		initializeSocket(port);
	}

	private void initializeSocket(int port) throws SocketException {
		// TODO Auto-generated method stub
		if(port == -1)
			socket = new DatagramSocket();
		else
			socket = new DatagramSocket(port);
	}

	public void send(InetAddress addr, int toPort, byte[] data, int offset, int length){
		spacket = new DatagramPacket(data,offset,length,addr,toPort);
			if(spacket != null && socket != null){
				try {
					socket.send(spacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
	
	@Override
	public ReceivedData receive() {
		newBuffer();
		
		ReceivedData rec = new ReceivedData();
		
		rpacket = new DatagramPacket(buffer,bufferSize);
		try {
			socket.receive(rpacket);
			
			rec.address = rpacket.getAddress();
			rec.port = rpacket.getPort();
			rec.length = rpacket.getLength();
			rec.data = rpacket.getData();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return rec;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		if(spacket != null){
			spacket = null;
		}
		
		if(rpacket != null){
			rpacket = null;
		}
		
		if(socket != null){
			socket.close();
			socket = null;
		}
	}

	public int getPort(){
		return port;
	}
}
