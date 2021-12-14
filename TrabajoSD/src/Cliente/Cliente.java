package Cliente;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import com.google.gson.Gson;

import Clases.Mensaje;
import Clases.Noticia;

public class Cliente {
	public static void main(String[] args) {
		try(BufferedReader bf=new BufferedReader(new InputStreamReader(System.in))){
			System.out.println("BUSCADOR DE NOTICIAS:");
			boolean buscar=true;
			while(buscar) {
				System.out.println("Introduce el topic a buscar:");
				String busqueda=bf.readLine();
				String mensaje=realizarPeticion(busqueda);
				if(mensaje!=null) {
					Gson g=new Gson();
					Mensaje m=g.fromJson(mensaje,Mensaje.class);
					int inicio=0,fin=9;
					List<Noticia> noticias=m.getNoticias();
					MenuBusquedas(inicio,fin,noticias);
					System.out.println("Introduce la noticia a buscar en la web o '0' para salir:");
					String eleccion=bf.readLine();
					int elec=Integer.parseInt(eleccion);
					while(elec!=0) {
						if(elec<0 || elec>fin+1) {
							System.out.println("Introduce un numero válido");
						}else {
							BuscarNoticia(noticias.get(elec-1));
						}
						eleccion=bf.readLine();
						elec=Integer.parseInt(eleccion);
					}
					
				}else {
					System.out.println("Fallo");
				}
				System.out.println("¿Quieres seguir buscando?");
			
			
			
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String realizarPeticion(String busqueda) {
		String mensaje=null;
		try (Socket s=new Socket("localhost",9999);
			DataOutputStream dos=new DataOutputStream(s.getOutputStream());
			DataInputStream dis=new DataInputStream(s.getInputStream());
		){
			dos.writeBytes(busqueda+"\r\n");
			dos.flush();
			mensaje=dis.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			return mensaje;
		}
	}
	
	public static void MenuBusquedas(int inicio, int fin, List<Noticia> noticias) {
		for(int i=inicio;i<fin;i++) {
			Noticia n=noticias.get(i);
			int x=i+1;
			System.out.println(x+":");
			System.out.println(n.getTitle());
			System.out.println(n.getDescription());
			System.out.println(" ");
		}
	}
	
	public static void BuscarNoticia(Noticia n) {
		try {
			Desktop.getDesktop().browse(new URI(n.getUrl()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
