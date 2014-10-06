package eu.derloki.networkapi.network.tcp;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer{

	private ServerSocket server = null;
	private boolean running = false;
	private int port;
	private Class<? extends TCPHandleConnection> connectionHandlerClass;
	
	public TCPServer(Class<? extends TCPHandleConnection> connectionHandlerClass, int port){
		this.port = port;
		this.connectionHandlerClass = connectionHandlerClass;
		try {
			server = new ServerSocket(this.port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
	
	public void start(){
		new Thread(accept).start();
	}
	
	public void stop(){
		running = false;
	}
	
	Runnable accept = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			running = true;
			while(running){
				try {
					Socket client = server.accept();
					Constructor<? extends TCPHandleConnection> ctor = connectionHandlerClass.getConstructor(Socket.class);
					new Thread(ctor.newInstance(new Object[]{client})).start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			try {
				close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	
	
	public void close() throws Exception {
		// TODO Auto-generated method stub
		if(server != null){
			server.close();
			server = null;
		}
	}
	
}
