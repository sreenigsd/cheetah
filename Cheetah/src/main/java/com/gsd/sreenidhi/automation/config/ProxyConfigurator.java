package com.gsd.sreenidhi.automation.config;

public class ProxyConfigurator {
	public boolean useProxyPac = false;
	public String proxyPacUrl;
	public boolean useProxy = false;
	public String httpProxyHost;
	public int httpProxyPort;
	public String httpsProxyHost;
	public int httpsProxyPort;
	public String proxyAuthUserName;
	public String proxyAuthPassword;
	
	public boolean isUseProxyPac() {
		return useProxyPac;
	}
	public void setUseProxyPac(boolean useProxyPac) {
		this.useProxyPac = useProxyPac;
	}
	public String getProxyPacUrl() {
		return proxyPacUrl;
	}
	public void setProxyPacUrl(String proxyPacUrl) {
		this.proxyPacUrl = proxyPacUrl;
	}
	public boolean isUseProxy() {
		return useProxy;
	}
	public void setUseProxy(boolean useProxy) {
		this.useProxy = useProxy;
	}
	public String getHttpProxyHost() {
		return httpProxyHost;
	}
	public void setHttpProxyHost(String httpProxyHost) {
		this.httpProxyHost = httpProxyHost;
	}
	public int getHttpProxyPort() {
		return httpProxyPort;
	}
	public void setHttpProxyPort(int httpProxyPort) {
		this.httpProxyPort = httpProxyPort;
	}
	public String getHttpsProxyHost() {
		return httpsProxyHost;
	}
	public void setHttpsProxyHost(String httpsProxyHost) {
		this.httpsProxyHost = httpsProxyHost;
	}
	public int getHttpsProxyPort() {
		return httpsProxyPort;
	}
	public void setHttpsProxyPort(int httpsProxyPort) {
		this.httpsProxyPort = httpsProxyPort;
	}
	public String getProxyAuthUserName() {
		return proxyAuthUserName;
	}
	public void setProxyAuthUserName(String proxyAuthUserName) {
		this.proxyAuthUserName = proxyAuthUserName;
	}
	public String getProxyAuthPassword() {
		return proxyAuthPassword;
	}
	public void setProxyAuthPassword(String proxyAuthPassword) {
		this.proxyAuthPassword = proxyAuthPassword;
	}
	
}
