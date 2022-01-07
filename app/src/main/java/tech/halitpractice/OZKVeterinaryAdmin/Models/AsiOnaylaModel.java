package tech.halitpractice.OZKVeterinaryAdmin.Models;

import java.io.Serializable;

public class AsiOnaylaModel implements Serializable {
	private String text;
	private boolean tf;

	public void setText(String text){
		this.text = text;
	}

	public String getText(){
		return text;
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
			"AsiOnaylaModel{" + 
			"text = '" + text + '\'' + 
			",tf = '" + tf + '\'' + 
			"}";
		}
}