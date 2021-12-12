package Pruebas;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.*;

import Clases.Mensaje;
import Clases.Noticia;

import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;

public class Prueba {
	public static void main(String[] args) {
		String url="https://newsapi.org/v2/everything?q=tesla&from=2021-11-09&sortBy=publishedAt&apiKey=8410a71068704edb88f7fd215ab64f08";
		URL u;
		HttpURLConnection con;
		DataInputStream dis=null;
		try {
			u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			con.setRequestMethod("GET");
			dis=new DataInputStream(con.getInputStream());
			String linea=dis.readLine();
			System.out.println(linea);
			Gson g=new Gson();
			Mensaje m=g.fromJson(linea, Mensaje.class);
			System.out.println(m.getNoticias().get(0).getTitle());
			Desktop.getDesktop().browse(new URI(m.getNoticias().get(0).getUrl()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
