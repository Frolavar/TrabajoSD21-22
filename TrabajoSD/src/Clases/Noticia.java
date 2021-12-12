package Clases;

public class Noticia {
	private String author;
	private String title;
	private String description;
	private String url;
	private String content;
	
	public Noticia(String author, String title, String description, String url, String content) {
		super();
		this.author = author;
		this.title = title;
		this.description = description;
		this.url = url;
		this.content = content;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
