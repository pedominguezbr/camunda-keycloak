package com.spsa.bpm.ventadesagregada.clase;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement
public class SendMailReq implements Serializable{
	private static final long serialVersionUID = 1346732878L;
	
	public SendMailReq()
    {
    }
	
	private String applicationCode;
	/**
	 * @return the applicationCode
	 */
	public String getApplicationCode() {
		return applicationCode;
	}
	/**
	 * @param applicationCode the applicationCode to set
	 */
	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}
	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}
	/**
	 * @param company the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}
	/**
	 * @return the email
	 */
	public MailType getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(MailType email) {
		this.email = email;
	}
	private String company;
	private MailType email;
}
