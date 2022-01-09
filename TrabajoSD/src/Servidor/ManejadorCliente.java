package Servidor;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;

public class ManejadorCliente implements Runnable{
	
	private Socket cliente=null;
	
	public ManejadorCliente(Socket cliente) {
		super();
		this.cliente = cliente;
	}
	
	@Override
	public void run() {
		try(DataInputStream dis=new DataInputStream(cliente.getInputStream());
			DataOutputStream dos=new DataOutputStream(cliente.getOutputStream());
		){
			//Recibe la búsqueda del cliente
			String busqueda=dis.readLine();
			//Creamos la url para la petición usando la búsqueda que hemos recibido del cliente
			String url="https://newsapi.org/v2/everything?q="+ busqueda +"&language=en&sortBy=popularity&apiKey=8410a71068704edb88f7fd215ab64f08";
			URL u = new URL(url);
			//Abrimos la conexión y leemos la respuesta de la API
			HttpURLConnection con = (HttpURLConnection) u.openConnection();
			con.setRequestMethod("GET");
			try(DataInputStream disCon=new DataInputStream(con.getInputStream());){
				String linea=disCon.readLine();
				//Le mandamos al cliente el json que hemos recibido de la API
				dos.writeBytes(linea);
			}catch(IOException e) {
				e.printStackTrace();
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
