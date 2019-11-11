package ar.com.cse.proxy.app.bean;

import java.util.List;
import java.util.Map;

public class Response {
	/*
	 *
	 */
	//  "headers": {
    //	"Accept": "*/*", 
    //  "Accept-Encoding": "gzip, deflate", 
    //  "Host": "httpbin.org", 
    //  "User-Agent": "curl/7.35.0"
  	//  }, 
    // "origin": "99.250.201.200", 
    //  "url": "http://httpbin.org/get"
	// 
	private List<String> args;
	private Map<String, String> headers;
	private String origin;
	private String url;
	private String resultado;

	
	/**
	 * @return the args
	 */
	public List<String> getArgs() {
		return args;
	}
	/**
	 * @param args the args to set
	 */
	public void setArgs(List<String> args) {
		this.args = args;
	}
	/**
	 * @return the headers
	 */
	public Map<String, String> getHeaders() {
		return headers;
	}
	/**
	 * @param headers the headers to set
	 */
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}
	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the resultado
	 */
	public String getResultado() {
		return resultado;
	}
	/**
	 * @param resultado the resultado to set
	 */
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	
	

}
