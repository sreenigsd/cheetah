package com.gsd.sreenidhi.cheetah.engine;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import com.gsd.sreenidhi.cheetah.exception.CheetahException;
import com.gsd.sreenidhi.forms.Constants;
import com.gsd.sreenidhi.utils.Base64Encoding;
import com.gsd.sreenidhi.utils.SSLUtilities;
import com.sun.deploy.net.proxy.BrowserProxyInfo;
import com.sun.deploy.net.proxy.ProxyConfigException;
import com.sun.deploy.net.proxy.ProxyInfo;
import com.sun.deploy.net.proxy.ProxyType;
import com.sun.deploy.net.proxy.ProxyUnavailableException;
import com.sun.deploy.net.proxy.*;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class FrameworkOps extends CheetahEngine {

	/**
	 * @param props Properties
	 * @return Boolean value validating the authenticity of the application URL
	 * @throws CheetahException Generic Exception Object that handles all exceptions
	 */
	public boolean validateLink(Properties props) throws CheetahException {

		logger.logMessage(null, this.getClass().getName(), "Implementing HTTP SSL Certificate Trusts",
				Constants.LOG_INFO, false);
		SSLUtilities.trustAllHostnames();
		SSLUtilities.trustAllHttpsCertificates();

		logger.logMessage(null, this.getClass().getName(), "Performing URL validation", Constants.LOG_INFO, false);
		String url = props.getProperty("app.env.url");
		HttpURLConnection huc = null;
		int respCode = 200;
		boolean validate = true;

		if (props.getProperty("skip.url.validation") != null
				&& "TRUE".equalsIgnoreCase(props.getProperty("skip.url.validation"))) {
			// DO NOT VALIDATE URL
			logger.logMessage(null, this.getClass().getName(), "URL Validation skipped.", Constants.LOG_INFO, false);
		} else {

			String requestMethod = "HEAD";
			if (props.getProperty("connection.auth.request.method") != null) {
				requestMethod = props.getProperty("connection.auth.request.method");
			}

			if ("WEB".equalsIgnoreCase(props.getProperty("test.type"))
					|| "WEB_SERVICE".equalsIgnoreCase(props.getProperty("test.type"))
					|| "WEBSERVICE".equalsIgnoreCase(props.getProperty("test.type"))) {

				try {
					logger.logMessage(null, this.getClass().getName(), "Attempting HTTP connection", Constants.LOG_INFO,
							false);
					huc = (HttpURLConnection) (new URL(url).openConnection());

					// HTTP URL Authentication
					if (props.getProperty("connection.auth.required") != null
							&& "true".equalsIgnoreCase(props.getProperty("connection.auth.required"))) {
						logger.logMessage(null, this.getClass().getName(), "Setting HTTP URL Authentication",
								Constants.LOG_INFO, false);
						String encoded = Base64Encoding.encode(props.getProperty("connection.auth.username"),
								props.getProperty("connection.auth.password"));
						logger.logMessage(null, this.getClass().getName(), "Encoded authentication: " + encoded,
								Constants.LOG_INFO, false);
						String basicAuth = "Basic " + encoded;
						huc.setRequestProperty("Authorization", basicAuth);
					}

					logger.logMessage(null, this.getClass().getName(), "Using Request Method: " + requestMethod,
							Constants.LOG_INFO, false);
					huc.setRequestMethod(requestMethod);
					logger.logMessage(null, this.getClass().getName(), "Attempting connection", Constants.LOG_INFO,
							false);
					huc.connect();

					respCode = huc.getResponseCode();
					logger.logMessage(null, this.getClass().getName(), "HTTP connection response: " + respCode,
							Constants.LOG_INFO, false);

					if (respCode >= 400) {
						logger.logMessage(null, this.getClass().getName(),
								"URL validation Failed. Either the application is down or the provided URL is invalid. Please verify and try again.",
								Constants.LOG_ERROR, false);
						validate = false;
					} else {
						logger.logMessage(null, this.getClass().getName(), "URL validation Successful.",
								Constants.LOG_INFO, false);
						validate = true;
					}

				} catch (MalformedURLException e) {
					logger.logMessage(e, this.getClass().getName(),
							"Malformed URl Exception: " + e.getMessage() + "\n" + getExceptionTrace(e),
							Constants.LOG_FATAL, true);
					validate = false;
				} catch (IOException e) {
					logger.logMessage(e, this.getClass().getName(),
							"IO Exception: " + e.getMessage() + "\n" + getExceptionTrace(e), Constants.LOG_FATAL, true);
					validate = false;

				}
			} else {
				// DO NOT VALIDATE URL
				logger.logMessage(null, this.getClass().getName(), "URL Validation will not be performed.",
						Constants.LOG_INFO, false);
				validate = false;
			}
		}
		logger.logMessage(null, this.getClass().getName(), "URL validation complete", Constants.LOG_INFO, false);
		logger.breakLine();
		return validate;
	}

	/**
	 * @param props Properties
	 * @throws CheetahException Generic Exception Object that handles all exceptions
	 */
	public void setSystemProxy(Properties props) throws CheetahException {

		CheetahEngine.logger.logMessage(null, this.getClass().getName(),
				"###########################################################################", Constants.LOG_INFO,
				false);
		CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Proxy settings Implementation.",
				Constants.LOG_INFO, false);
		CheetahEngine.logger.logMessage(null, this.getClass().getName(),
				"--------------------------------------------------------", Constants.LOG_INFO, false);

		if (validateProxyRequirement()) {
			if (props.getProperty("proxy.bypass") != null
					&& "TRUE".equalsIgnoreCase(props.getProperty("proxy.bypass"))) {
				// BYPASS PROXY IMPLEMENTATION
				CheetahEngine.logger.logMessage(null, this.getClass().getName(),
						"Proxy bypass requested. Proxy settings will not be implemented.", Constants.LOG_INFO, false);
				CheetahEngine.cheetahForm.setProxy_url(null);
				resetSystemProxy();
			} else {

				if (CheetahEngine.props.getProperty("application.network.zone") != null
						&& "internal".equalsIgnoreCase(CheetahEngine.props.getProperty("application.network.zone"))) {
					// Do not implement Proxy
					CheetahEngine.logger.logMessage(null, this.getClass().getName(),
							"Proxy will not be implemented for applications within firewalls.", Constants.LOG_INFO,
							false);
					CheetahEngine.cheetahForm.setProxy_url(null);
					resetSystemProxy();
				} else if (CheetahEngine.props.getProperty("application.network.zone") != null
						&& "external".equalsIgnoreCase(CheetahEngine.props.getProperty("application.network.zone"))) {
					CheetahEngine.logger.logMessage(null, this.getClass().getName(),
							"Proxy implementation attempt for applications external to firewalls.", Constants.LOG_INFO,
							false);
					attemptProxySetting();
				} else {
					CheetahEngine.logger.logMessage(null, this.getClass().getName(),
							"Proxy implementation - generic attempt.", Constants.LOG_INFO, false);
					attemptProxySetting();
				}
			}
		}else {
			CheetahEngine.logger.logMessage(null, this.getClass().getName(),
					"Proxy Requirement vaildated to false...",
					Constants.LOG_INFO);
		}
		CheetahEngine.logger.logMessage(null, this.getClass().getName(),
				"###########################################################################",
				Constants.LOG_INFO);

	}

	/**
	 * Check if a proxy needs to be implemented. Proxy implementation is required
	 * only for Web and Webservice testing.
	 * 
	 * @return Boolean
	 * @throws CheetahException Generic Exception Object that handles all exceptions
	 */
	private boolean validateProxyRequirement() throws CheetahException {
		boolean set_proxy = false;
		
		
		if(CheetahEngine.configurator.proxyConfigurator.useProxy
				|| CheetahEngine.configurator.proxyConfigurator.useProxyPac) {
			if ("WEB".equalsIgnoreCase(props.getProperty("test.type"))
					|| "WEBSERVICE".equalsIgnoreCase(props.getProperty("test.type"))
					|| "WEB_SERVICE".equalsIgnoreCase(props.getProperty("test.type"))) {
				set_proxy = true;
				CheetahEngine.logger.logMessage(null, this.getClass().getName(),
						"Proxy configurations needs to be set...",
						Constants.LOG_INFO);
			} else {
				CheetahEngine.logger.logMessage(null, this.getClass().getName(),
						"TEST_TYPE does not require proxy.Proxy will not be implemented for this test.",
						Constants.LOG_INFO);
				set_proxy = false;
			}
		}
		
		return set_proxy;
	}

	/**
	 * @param pacFile Pac File
	 * @throws CheetahException Generic Exception Object that handles all exceptions
	 */
	public void implementProxyWithPac(String pacFile) throws CheetahException {

		CheetahEngine.logger.logMessage(null, this.getClass().getName(),
				"Implementing Proxy according to PAC file settings: " + pacFile, Constants.LOG_INFO, false);
		String proxy = getProxyDetailsFromPac(pacFile);
		CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Setting System proxy from Pac File",
				Constants.LOG_INFO, false);
		if (!proxy.contains("DIRECT")) {
			String[] proxyArr = proxy.split(":");
			CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Proxy Host: " + proxyArr[0],
					Constants.LOG_INFO, false);
			CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Proxy Port: " + proxyArr[1],
					Constants.LOG_INFO, false);

			System.setProperty("http.proxyHost", proxyArr[0]);
			System.setProperty("http.proxyPort", proxyArr[1]);

			System.setProperty("https.proxyHost", proxyArr[0]);
			System.setProperty("https.proxyPort", proxyArr[1]);

			CheetahEngine.cheetahForm.setProxy_url(proxyArr[0]);
			CheetahEngine.cheetahForm.setProxy_port(proxyArr[1]);

		} else {
			CheetahEngine.cheetahForm.setProxy_url("DIRECT");
			resetSystemProxy();

		}

		CheetahEngine.cheetahForm.setProxy_pac(pacFile);
		CheetahEngine.logger.breakLine();

	}

	/**
	 * @param pacFile Pac File
	 * @return Proxy URL from PAC
	 * @throws CheetahException Generic Exception Object that handles all exceptions
	 */
	public String getProxyDetailsFromPac(String pacFile) throws CheetahException {
		String proxy = null;
		String pac_file = pacFile;
		BrowserProxyInfo b = new BrowserProxyInfo();

		try {
			b.setType(ProxyType.AUTO);
			b.setAutoConfigURL(pac_file);
			CheetahEngine.logger.breakLine();
			CheetahEngine.logger.logMessage(null, this.getClass().getName(),
					"Retrieving proxy from Pac File: " + pac_file, Constants.LOG_INFO, false);

			SunAutoProxyHandler handler = new SunAutoProxyHandler();
			handler.init(b);

			ProxyInfo[] ps;

			ps = handler.getProxyInfo(new URL(props.getProperty("app.env.url")));

			for (ProxyInfo p : ps) {
				CheetahEngine.logger.logMessage(null, this.getClass().getName(),
						"Proxy URL from Pac File: " + p.toString(), Constants.LOG_INFO, false);
				proxy = p.toString();
			}
		} catch (MalformedURLException | ProxyConfigException | ProxyUnavailableException e) {
			throw new CheetahException(e);
		}
		return proxy;

	}

	/**
	 * Reset Proxy
	 * 
	 * @throws CheetahException Generic Exception Object that handles all exceptions
	 */
	public void resetSystemProxy() throws CheetahException {
		CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Reset Proxy... ", Constants.LOG_INFO, false);
		System.setProperty("http.proxyHost", "");
		System.setProperty("https.proxyHost", "");

	}

	/**
	 * @throws CheetahException Generic Exception Object that handles all exceptions
	 */
	public void attemptProxySetting() throws CheetahException {
		CheetahEngine.logger.logMessage(null, this.getClass().getName(),
				"Use Procy Pac: " + Boolean.toString(CheetahEngine.configurator.proxyConfigurator.isUseProxyPac()),
				Constants.LOG_INFO, false);
		if (CheetahEngine.configurator.proxyConfigurator.isUseProxyPac()) {
			// Use Proxy Pac

			implementProxyWithPac(CheetahEngine.configurator.proxyConfigurator.getProxyPacUrl());

		} else {
			// Check Proxy host and port
			if (CheetahEngine.configurator.proxyConfigurator.isUseProxy()) {
				// Set Proxy with host and port
				// Implement Proxy
				CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Implementing Proxy settings.",
						Constants.LOG_INFO, false);

				CheetahEngine.logger.logMessage(null, this.getClass().getName(),
						"Proxy Host: " + CheetahEngine.configurator.proxyConfigurator.getHttpProxyHost(),
						Constants.LOG_INFO, false);
				CheetahEngine.logger.logMessage(null, this.getClass().getName(),
						"Proxy Port: " + CheetahEngine.configurator.proxyConfigurator.getHttpProxyPort(),
						Constants.LOG_INFO, false);

				System.setProperty("http.proxyHost", CheetahEngine.configurator.proxyConfigurator.getHttpProxyHost());
				System.setProperty("http.proxyPort",
						Integer.toString(CheetahEngine.configurator.proxyConfigurator.getHttpProxyPort()));

				System.setProperty("https.proxyHost", CheetahEngine.configurator.proxyConfigurator.getHttpsProxyHost());
				System.setProperty("https.proxyPort",
						Integer.toString(CheetahEngine.configurator.proxyConfigurator.getHttpProxyPort()));

				CheetahEngine.cheetahForm.setProxy_url(CheetahEngine.configurator.proxyConfigurator.getHttpProxyHost());
				CheetahEngine.cheetahForm.setProxy_port(
						Integer.toString(CheetahEngine.configurator.proxyConfigurator.getHttpProxyPort()));
			} else {
				CheetahEngine.logger.logMessage(null, this.getClass().getName(), "Proxy configuration not available !",
						Constants.LOG_INFO, false);
				// Proxy is not set
				CheetahEngine.cheetahForm.setProxy_url(null);
				resetSystemProxy();
			}
		}
	}

}
