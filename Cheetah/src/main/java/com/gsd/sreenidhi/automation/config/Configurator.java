package com.gsd.sreenidhi.automation.config;

import java.io.File;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import com.gsd.sreenidhi.forms.Constants;
import com.gsd.sreenidhi.cheetah.engine.CheetahEngine;
import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.utils.FileUtils;

/**
 * @author Gundlupet Sreenidhi - sreenidhi.gsd@gmail.com
 *
 */
public class Configurator {

	public DriverConfigurator driverConfigurator = null;
	public MailConfigurator mailConfigurator = null;
	public ProxyConfigurator proxyConfigurator = null;
	public ExecutionConfigurator executionConfigurator = null;
	public DBConfigurator dbConfigurator = null;

	public Configurator() {
		dbConfigurator = new DBConfigurator();
		driverConfigurator = new DriverConfigurator();
		mailConfigurator = new MailConfigurator();
		proxyConfigurator = new ProxyConfigurator();
		executionConfigurator = new ExecutionConfigurator();
		
	}

	/**
	 * @throws CheetahException Generic Exception Object that handles all exceptions
	 */
	public void loadConfigurators() throws CheetahException {
		CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Attempting to load configurations", Constants.LOG_INFO);
		FileUtils fu = new FileUtils();

		loadConfig(dbConfigurator, fu.getConfiguration("dbconfig.xml"));
		loadConfig(driverConfigurator, fu.getConfiguration("drivers.xml"));
		loadConfig(mailConfigurator, fu.getConfiguration("mailer.xml"));
		loadConfig(proxyConfigurator, fu.getConfiguration("proxy.xml"));
		loadConfig(executionConfigurator, fu.getConfiguration("execution_conf.xml"));
		
	}

	/**
	 * @param configurator
	 * @param configurationFile
	 * @throws CheetahException Generic Exception Object that handles all exceptions
	 */
	private void loadConfig(Object configurator, File configurationFile) throws CheetahException {
		XMLConfiguration configRead;
		boolean valid = true;
		
		if (configurationFile.exists()) {
			// XML File exists
			CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Loading configuration: " +configurationFile.toString() , Constants.LOG_INFO);
			try {
				configRead = new XMLConfiguration(configurationFile);
				if (configurator.getClass().getTypeName().trim().equalsIgnoreCase("com.gsd.sreenidhi.automation.config.DBConfigurator")) {
					dbConfigurator.setUseDB(configRead.getBoolean("use-db"));
					if(dbConfigurator.isUseDB()) {
						try {
							dbConfigurator.setName(configRead.getString("name"));
							dbConfigurator.setDelegator(configRead.getString("delegator-name"));
							dbConfigurator.setDatabaseType(configRead.getString("database-type"));
							dbConfigurator.setDatabaseName(configRead.getString("database-name"));
							dbConfigurator.setDriverClass(configRead.getString("driver-class"));
							dbConfigurator.setJdbcUrl(configRead.getString("url"));
							dbConfigurator.setUsername(configRead.getString("username"));
							dbConfigurator.setPassword(configRead.getString("password"));
							dbConfigurator.setSchemaName(configRead.getString("schema-name"));
							valid = true;
						}catch(NullPointerException ex) {
							valid = false;
							Exception e = new NullPointerException("Missing required configurations. Please update your configurations and try again.");
							throw new CheetahException(e);
						}catch(Exception ex) {
							valid = false;
							Exception e = new NullPointerException("Missing required configurations. Please update your configurations and try again.");
							throw new CheetahException(e);
						}
					}
				}else if (configurator.getClass().getTypeName().trim().equalsIgnoreCase("com.gsd.sreenidhi.automation.config.DriverConfigurator")) {
					driverConfigurator.setIeDriverPath(configRead.getString("ie-driver-path")!=null?configRead.getString("ie-driver-path"):Constants.IEDriverName);
					driverConfigurator.setChromeDriverPath(configRead.getString("chrome-driver-path")!=null?configRead.getString("chrome-driver-path"):Constants.ChromeDriverName);
					driverConfigurator.setFirefoxDriverPath(configRead.getString("firefox-driver-path")!=null?configRead.getString("firefox-driver-path"):Constants.FirefoxDriverName);
					driverConfigurator.setOperaDriverPath(configRead.getString("opera-driver-path")!=null?configRead.getString("opera-driver-path"):Constants.OperaDriverName);
					driverConfigurator.setEdgeDriverPath(configRead.getString("edge-driver-path")!=null?configRead.getString("edge-driver-path"):Constants.EdgeDriverName);
					valid = true;
				}else if (configurator.getClass().getTypeName().trim().equalsIgnoreCase("com.gsd.sreenidhi.automation.config.MailConfigurator")) {
					mailConfigurator.setSendMail(configRead.getBoolean("send-mail"));
					if(mailConfigurator.getSendMail()) {
						try {
							mailConfigurator.setSmtpHost(configRead.getString("smtp-host"));
							mailConfigurator.setSmtpPort(configRead.getString("smtp-port"));
							mailConfigurator.setSmtpUsername(configRead.getString("smtp-username"));
							mailConfigurator.setSmtpPassword(configRead.getString("smtp-password"));
							mailConfigurator.setDefaultFrom(configRead.getString("default-from-email"));
							mailConfigurator.setDefaultTo(configRead.getString("default-to-email"));
							mailConfigurator.setDefaultSubject(configRead.getString("default-email-subject"));
							
							FileUtils fu = new FileUtils();
							File templateFile = new File(configRead.getString("email-template"));
							String template = fu.getFileContent(templateFile);
							mailConfigurator.setDefaultTemplate(template);
							
							mailConfigurator.setTemplatePlaceholder(configRead.getString("email-template-message-placeholder"));
							mailConfigurator.setRetrieveSmtpCredentialFromFile(configRead.getBoolean("retrieve-credential-from-file"));
							if(mailConfigurator.isRetrieveSmtpCredentialFromFile()) {
								mailConfigurator.setSmtpCredentialFile(configRead.getString("smtp-credential-file"));
								validateCredFile(mailConfigurator.getSmtpCredentialFile());
							}
							valid = true;
						}catch(NullPointerException ex) {
							valid = false;
							Exception e = new NullPointerException("Missing required configurations. Please update your configurations and try again.");
							throw new CheetahException(e);
						}catch(Exception ex) {
							valid = false;
							Exception e = new NullPointerException("Missing required configurations. Please update your configurations and try again.");
							throw new CheetahException(e);
						}
					}
					
					
				}else if (configurator.getClass().getTypeName().trim().equalsIgnoreCase("com.gsd.sreenidhi.automation.config.ProxyConfigurator")) {
					proxyConfigurator.setUseProxy(configRead.getBoolean("use-proxy"));
					if(proxyConfigurator.isUseProxy()) {
						proxyConfigurator.setHttpProxyHost(configRead.getString("proxy-host")!=null?configRead.getString("proxy-host"):null);
						proxyConfigurator.setHttpProxyPort(configRead.getInt("proxy-port"));
						proxyConfigurator.setHttpsProxyHost(configRead.getString("proxy-host")!=null?configRead.getString("proxy-host"):null);
						proxyConfigurator.setHttpsProxyPort(configRead.getInt("proxy-port"));
						proxyConfigurator.setProxyAuthUserName(configRead.getString("proxy-auth-username")!=null?configRead.getString("proxy-auth-username"):"");
						proxyConfigurator.setProxyAuthPassword(configRead.getString("proxy-auth-password")!=null?configRead.getString("proxy-auth-password"):"");
					}
				
					
					proxyConfigurator.setUseProxyPac(configRead.getBoolean("use-proxy-pac"));
					if(proxyConfigurator.isUseProxyPac()){
						proxyConfigurator.setProxyPacUrl(configRead.getString("proxy-pac-url"));
						proxyConfigurator.setProxyAuthUserName(configRead.getString("proxy-auth-username")!=null?configRead.getString("proxy-auth-username"):"");
						proxyConfigurator.setProxyAuthPassword(configRead.getString("proxy-auth-password")!=null?configRead.getString("proxy-auth-password"):"");
					}
					valid = true;
					
				}else if (configurator.getClass().getTypeName().trim().equalsIgnoreCase("com.gsd.sreenidhi.automation.config.ExecutionConfigurator")) {
					executionConfigurator.setExecutionEnv(configRead.getString("execution_env")==null?"localhost":configRead.getString("execution_env"));
					executionConfigurator.setTerrainLink(configRead.getString("terrain-link"));
					executionConfigurator.setTerrainPort(configRead.getString("terrain-port"));
					executionConfigurator.setHubPort(configRead.getString("hub-port"));
					executionConfigurator.setSaucelabsUsername(configRead.getString("saucelabs_username"));
					executionConfigurator.setSaucelabsAccessKey(configRead.getString("saucelabs_access-key"));
					executionConfigurator.setAppiumHub(configRead.getString("appium-remote-url"));
					executionConfigurator.setAppiumPort(configRead.getString("appium-remote-port"));
					valid = true;
				}else {
					valid = true;
				}

			} catch (ConfigurationException e) {
				throw new CheetahException(e);
			}
			
			if(!valid) {
				System.exit(1);
			}
			
		} else {
			Exception e = new NullPointerException("XML Config file not found.");
			CheetahEngine.logger.logMessage(null, this.getClass().getName(), e.getMessage(), Constants.LOG_WARN);
			
			if (configurator.getClass().getTypeName().equalsIgnoreCase("com.gsd.sreenidhi.automation.config.DBConfigurator")) {
				//Data will not be stored in Database
				//Executions will continue
				dbConfigurator.setUseDB(false);
			}else if (configurator.getClass().getTypeName().equalsIgnoreCase("com.gsd.sreenidhi.automation.config.DriverConfigurator")) {
				//Error
				CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Cannot continue without driver configuration", Constants.LOG_ERROR);
				CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Please fix your configuration and try again...", Constants.LOG_ERROR);
				System.exit(1);
			}else if (configurator.getClass().getTypeName().equalsIgnoreCase("com.gsd.sreenidhi.automation.config.ExecutionConfigurator")) {
				//Error
				CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Cannot continue without execution configuration", Constants.LOG_ERROR);
				CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Please fix your configuration and try again...", Constants.LOG_ERROR);
				System.exit(1);
			}else if (configurator.getClass().getTypeName().equalsIgnoreCase("com.gsd.sreenidhi.automation.config.MailConfigurator")) {
				//Mail messages will not be sent
				//Executions can continue
				mailConfigurator.setSendMail(false);
				mailConfigurator.setSmtpHost(null);
				mailConfigurator.setSmtpPort(null);
				mailConfigurator.setSmtpUsername(null);
				mailConfigurator.setSmtpPassword(null);
				mailConfigurator.setDefaultFrom(null);
				mailConfigurator.setDefaultTo(null);
				mailConfigurator.setDefaultSubject(null);
				mailConfigurator.setDefaultTemplate(null);
				mailConfigurator.setTemplatePlaceholder(null);
				mailConfigurator.setRetrieveSmtpCredentialFromFile(false);
				mailConfigurator.setSmtpCredentialFile(null);
				
			}else if (configurator.getClass().getTypeName().equalsIgnoreCase("com.gsd.sreenidhi.automation.config.ProxyConfigurator")) {
				//Proxy configuration is not available
				//Execution will continue without using proxy
				proxyConfigurator.setUseProxy(false);
				proxyConfigurator.setUseProxyPac(false);
			}else {
				//Executions will continue
			}
		}

	}

	/**
	 * @param smtpCredentialFile Credential File
	 * @throws CheetahException Generic Exception Object that handles all exceptions
	 */
	private void validateCredFile(String smtpCredentialFile) throws CheetahException {
		FileUtils fu = new FileUtils();
		File f = fu.getConfiguration(smtpCredentialFile);
		if(!f.exists()) {
			Exception e = new NullPointerException("Failed to identify exception file is specified location.");
			CheetahEngine.logger.logMessage(e, this.getClass().getName(), e.getMessage(), Constants.LOG_ERROR);
			CheetahEngine.logger.logMessage(e, this.getClass().getName(), "Email notifications will be disabled.", Constants.LOG_ERROR);
			mailConfigurator.setSendMail(false);
		}
		
	}

}
