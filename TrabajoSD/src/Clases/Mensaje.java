package Clases;

import java.io.Serializable;
import java.util.ArrayList;

public class Mensaje implements Serializable{
	private String status;
	private int totalResults;
	private ArrayList<Noticia> articles;
	public Mensaje(String status, int totalResults, ArrayList<Noticia> articles) {
		super();
		this.status = status;
		this.totalResults = totalResults;
		articles = articles;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getTotalResults() {
		return totalResults;
	}
	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}
	public ArrayList<Noticia> getNoticias() {
		return articles;
	}
	public void setNoticias(ArrayList<Noticia> articles) {
		articles = articles;
	}
}
