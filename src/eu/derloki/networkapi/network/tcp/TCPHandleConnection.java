package eu.derloki.networkapi.network.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public abstract class TCPHandleConnection implements Runnable{

	protected Socket client = null;
	protected OutputStream os = null;
	protected InputStream is = null;
	
	public TCPHandleConnection(Socket pClient) {
		client = pClient;
		try {
			os = client.getOutputStream();
			is = client.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public abstract void run();

	public void close(){
		System.out.println("inner close");
		if(is != null){
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			is = null;
		}
		
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
		
		if(client != null){
			try {
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			client = null;
		}
	}
	
}
