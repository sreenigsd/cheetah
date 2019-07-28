package com.gsd.sreenidhi.automation.config;

public class MailConfigurator {
	public Boolean sendMail = false;
	public String smtpHost;
	public String smtpPort;
	public String smtpUsername;
	public String smtpPassword;
	
	public String defaultFrom;
	public String defaultTo;
	public String defaultSubject;
	public String defaultTemplate;
	public String templatePlaceholder;
	
	public boolean retrieveSmtpCredentialFromFile;
	public String smtpCredentialFile;
	
	/**
	 * @return the smtpHost
	 */
	public String getSmtpHost() {
		return smtpHost;
	}
	/**
	 * @param smtpHost the smtpHost to set
	 */
	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}
	/**
	 * @return the smtpPort
	 */
	public String getSmtpPort() {
		return smtpPort;
	}
	/**
	 * @param smtpPort the smtpPort to set
	 */
	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}
	/**
	 * @return the smtpUsername
	 */
	public String getSmtpUsername() {
		return smtpUsername;
	}
	/**
	 * @param smtpUsername the smtpUsername to set
	 */
	public void setSmtpUsername(String smtpUsername) {
		this.smtpUsername = smtpUsername;
	}
	/**
	 * @return the smtpPassword
	 */
	public String getSmtpPassword() {
		return smtpPassword;
	}
	/**
	 * @param smtpPassword the smtpPassword to set
	 */
	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}
	/**
	 * @return the defaultFrom
	 */
	public String getDefaultFrom() {
		return defaultFrom;
	}
	/**
	 * @param defaultFrom the defaultFrom to set
	 */
	public void setDefaultFrom(String defaultFrom) {
		this.defaultFrom = defaultFrom;
	}
	/**
	 * @return the defaultTo
	 */
	public String getDefaultTo() {
		return defaultTo;
	}
	/**
	 * @param defaultTo the defaultTo to set
	 */
	public void setDefaultTo(String defaultTo) {
		this.defaultTo = defaultTo;
	}
	/**
	 * @return the defaultSubject
	 */
	public String getDefaultSubject() {
		return defaultSubject;
	}
	/**
	 * @param defaultSubject the defaultSubject to set
	 */
	public void setDefaultSubject(String defaultSubject) {
		this.defaultSubject = defaultSubject;
	}
	/**
	 * @return the defaultTemplate
	 */
	public String getDefaultTemplate() {
		return defaultTemplate;
	}
	/**
	 * @param defaultTemplate the defaultTemplate to set
	 */
	public void setDefaultTemplate(String defaultTemplate) {
		this.defaultTemplate = defaultTemplate;
	}
	/**
	 * @return the retrieveSmtpCredentialFromFile
	 */
	public boolean isRetrieveSmtpCredentialFromFile() {
		return retrieveSmtpCredentialFromFile;
	}
	/**
	 * @param retrieveSmtpCredentialFromFile the retrieveSmtpCredentialFromFile to set
	 */
	public void setRetrieveSmtpCredentialFromFile(boolean retrieveSmtpCredentialFromFile) {
		this.retrieveSmtpCredentialFromFile = retrieveSmtpCredentialFromFile;
	}
	/**
	 * @return the smtpCredentialFile
	 */
	public String getSmtpCredentialFile() {
		return smtpCredentialFile;
	}
	/**
	 * @param smtpCredentialFile the smtpCredentialFile to set
	 */
	public void setSmtpCredentialFile(String smtpCredentialFile) {
		this.smtpCredentialFile = smtpCredentialFile;
	}
	/**
	 * @return the templatePlaceholder
	 */
	public String getTemplatePlaceholder() {
		return templatePlaceholder;
	}
	/**
	 * @param templatePlaceholder the templatePlaceholder to set
	 */
	public void setTemplatePlaceholder(String templatePlaceholder) {
		this.templatePlaceholder = templatePlaceholder;
	}
	/**
	 * @return the sendMail
	 */
	public Boolean getSendMail() {
		return sendMail;
	}
	/**
	 * @param sendMail the sendMail to set
	 */
	public void setSendMail(Boolean sendMail) {
		this.sendMail = sendMail;
	}

}
