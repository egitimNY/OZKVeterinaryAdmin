package tech.halitpractice.OZKVeterinaryAdmin.Models;

import java.io.Serializable;

public class KampanyaEkleModel implements Serializable {
	private String sonuc;
	private boolean tf;

	public void setSonuc(String sonuc){
		this.sonuc = sonuc;
	}

	public String getSonuc(){
		return sonuc;
	}

	public void setTf(boolean tf){
		this.tf = tf;
	}

	public boolean isTf(){
		return tf;
	}

	@Override
 	public String toString(){
		return 
			"KampanyaEkleModel{" + 
			"sonuc = '" + sonuc + '\'' + 
			",tf = '" + tf + '\'' + 
			"}";
		}
}