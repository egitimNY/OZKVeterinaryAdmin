package tech.halitpractice.OZKVeterinaryAdmin.Models;

import java.io.Serializable;

public class KampanyaModel implements Serializable {
	private String resim;
	private boolean tf;
	private String text;
	private String id;
	private String baslik;

	public void setResim(String resim){
		this.resim = resim;
	}

	public String getResim(){
		return resim;
	}

	public void setTf(boolean tf){
		this.tf = tf;
	}

	public boolean isTf(){
		return tf;
	}

	public void setText(String text){
		this.text = text;
	}

	public String getText(){
		return text;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setBaslik(String baslik){
		this.baslik = baslik;
	}

	public String getBaslik(){
		return baslik;
	}

	@Override
 	public String toString(){
		return 
			"KampanyaModel{" + 
			"resim = '" + resim + '\'' + 
			",tf = '" + tf + '\'' + 
			",text = '" + text + '\'' + 
			",id = '" + id + '\'' + 
			",baslik = '" + baslik + '\'' + 
			"}";
		}
}