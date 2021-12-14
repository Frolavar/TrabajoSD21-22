package Servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {
	public static void main(String[] args) {
		ExecutorService pool=null;
		ServerSocket ss=null;
		try {
			pool=Executors.newCachedThreadPool();
			ss=new ServerSocket(9999);
			while(true) {
				Socket cliente=ss.accept();
				pool.execute(new ManejadorCliente(cliente));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(pool!=null) {
				pool.shutdown();
			}
			if(ss!=null) {
				try {
					ss.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
			
		
	}
}
