package eu.derloki.networkapi.network.tcp;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;

import eu.derloki.networkapi.network.NetworkConnection;
import eu.derloki.networkapi.network.ReceivedData;

public class TCPConnection extends NetworkConnection{
	private Socket socket = null;
	private InputStream is = null;
	private OutputStream os = null;
	
	private InetAddress addr;
	private int port;
	
	private boolean connected = false;
	
	public TCPConnection(){
		this(1024);
	}
		
	public TCPConnection(int bufferSize){
		super(bufferSize);
	}
	
	public TCPConnection(InetAddress addr, int port) throws IOException{
		this(addr,port,1024);
	}
	
	public TCPConnection(InetAddress addr, int port, int bufferSize) throws IOException{
		super(bufferSize);
		connect(addr, port);
	}
	
	public void connect(InetAddress addr, int port) throws IOException{
	
		this.addr = addr;
		this.port = port;
		
		initializeConnection();
	}
	
	private void initializeConnection() throws IOException{
			socket = new Socket(addr,port);
			is = socket.getInputStream();
			os = socket.getOutputStream();
			connected = true;
	}
	
	public void send(byte[] data, int offset, int length) {
		// TODO Auto-generated method stub
		if(socket != null && os != null){
			try {
				os.write(data, offset, length);
				os.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	@Override
	public ReceivedData receive() {
		
		newBuffer();
		
		ReceivedData out = new ReceivedData();
		// TODO Auto-generated method stub
		if(socket != null && is != null){
			try {
				int len = is.read(buffer);
				if(len != -1){
					out.data = Arrays.copyOf(buffer,buffer.length);
					out.length = len;
					out.address = addr;
					out.port = port;
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return out;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		if(os != null){
			
			try {
				os.flush();
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			os = null;
		}

		if(is != null){
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			is = null;
		}
		
		if(socket != null){
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			socket = null;
		}
		
		connected = false;
	}
	
	public boolean isConnected(){
		return connected;
	}
}
