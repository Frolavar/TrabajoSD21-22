package Cliente;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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
			System.out.println("--> BUSCADOR DE NOTICIAS:");
			boolean buscar=true;
			//El cliente indicará cuando quiere dejar de buscar
			while(buscar) {
				System.out.println("--> Introduce el topic a buscar:");
				String busqueda=bf.readLine();
				//Nos conectamos al servidor para hacer la petición y guardamos la respuesta
				String mensaje=realizarPeticion(busqueda);
				//Comprobamos que el mensaje es válido
				if(mensaje!=null) {
					Gson g=new Gson();
					//Deserializamos el mensaje
					Mensaje m=g.fromJson(mensaje,Mensaje.class);
					int inicio=0,fin=9;
					//Obtenemos las noticias del mensaje
					List<Noticia> noticias=m.getNoticias();
					//Mostramos el menu de noticias
					MenuBusquedas(inicio,fin,noticias);
					inicio=fin+1;
					fin=fin+10;
					System.out.println("--> Introduce el número de la noticia a buscar en la web, '0' para salir o '+' para mostrar más noticias:");
					//Leemos la petición del cliente
					String eleccion=bf.readLine();
					int elec;
					if(eleccion.equals("+")) {
						elec=-1;
					}else {
						elec=Integer.parseInt(eleccion);
					}
					while(elec!=0) {
						if(elec<-1 || elec>fin+1) {
							System.out.println("--> Introduce una opción válida");
						}else if(elec==-1){
							if(inicio<noticias.size()&&fin<noticias.size()) {
								MenuBusquedas(inicio,fin,noticias);
								inicio=fin+1;
								fin=fin+10;
							}else if(inicio<noticias.size()) {
								fin=noticias.size()-1;
								MenuBusquedas(inicio,fin,noticias);
								inicio=fin+1;
								fin=fin+10;
							}else {
								System.out.println("--> No hay mas noticias");
							}
							
						} else {
							BuscarNoticia(noticias.get(elec-1));
						}
						System.out.println("--> Introduce el número de la noticia a buscar en la web, '0' para salir o '+' para mostrar más noticias:");
						eleccion=bf.readLine();
						if(eleccion.equals("+")) {
							elec=-1;
						}else {
							elec=Integer.parseInt(eleccion);
						}
					}
					
				}else {
					System.out.println("Fallo");
				}
				//Preguntamos al cliente si desea buscar otro topic
				System.out.println("--> ¿Quieres seguir buscando otro topic? SI/NO");
				String seguir=bf.readLine();
				while(!seguir.equalsIgnoreCase("SI")||!seguir.equalsIgnoreCase("NO")) {
					System.out.println("--> Introduce SI o NO");
					seguir=bf.readLine();
				}
				if(seguir.equalsIgnoreCase("NO"))
					buscar=false;
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
		for(int i=inicio;i<=fin;i++) {
			Noticia n=noticias.get(i);
			int x=i+1;
			System.out.println(x+":");
			try {
				System.out.println(new String(n.getTitle().getBytes(),"utf-8"));
				System.out.println(new String(n.getDescription().getBytes(),"utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
