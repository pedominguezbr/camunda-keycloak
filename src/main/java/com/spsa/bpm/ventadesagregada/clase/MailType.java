package com.spsa.bpm.ventadesagregada.clase;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement
public class MailType implements Serializable{
	
	public MailType()
    {
    }
	
	private String subject;
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return the template
	 */
	public String getTemplate() {
		return template;
	}
	/**
	 * @param template the template to set
	 */
	public void setTemplate(String template) {
		this.template = template;
	}
	/**
	 * @return the keys
	 */
	public keysTypes[] getKeys() {
		return keys;
	}
	/**
	 * @param keys the keys to set
	 */
	public void setKeys(keysTypes[] keys) {
		this.keys = keys;
	}
	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}
	/**
	 * @param to the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}
	private String template;
	private keysTypes[] keys;
	private String to = null;
}
