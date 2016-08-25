/* low level HTTP requests in Java
 * jason.albalah@yahoo.com
 * adapted from http://www.drdobbs.com/jvm/making-http-requests-from-java/240160966
 */

import java.net.Socket;
import java.net.InetAddress;
import java.net.URI;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RequestHTTP {

	public static void main(String args[]){
		long start = System.nanoTime();
		getRequest("http://www.nasdaq.com/symbol/googl/real-time", "./new.html");
		double time = (System.nanoTime() - start) / 1.e9;
		System.out.println("Runtime, " + time + "s");
	}

	/* GET request (low level TCP application layer protocol)
	 * @ param urlStr, (URL with parameters)
	 * @ param 
	 */
	public static void getRequest(String urlStr, String filePath){
		try{
			String line = "";
			int c = 0; // misc counter
			FileWriter fw = new FileWriter(new File(filePath));
			URI uri = new URI(urlStr); 
			String host = uri.getHost( ); 
			String path = uri.getRawPath( ); 
			if (path == null || path.length( ) == 0) {
				path = "/";
			} 
 
			// adapted from drdobbs.com
			String query = uri.getRawQuery( ); 
			if (query != null && query.length( ) > 0) {
				path += "?" + query;
			} 
			String protocol = uri.getScheme( ); 
			int port = uri.getPort( ); 
			if (port == -1) {
				if (protocol.equals("http")) { 
					port = 80; // http port 
				}
				else if (protocol.equals("https")) {
					port = 443; // https port 
				}
				else {
					port = 0;
				}
			}

			Socket socket = new Socket(host, port);
			PrintWriter request = new PrintWriter(socket.getOutputStream());
			request.print( "GET " + path + " HTTP/1.1\r\n" + 
						   "Host: " + host + "\r\n" + 
						   "Connection: close\r\n" + 
						   "\r\n"); 
			request.flush(); // write buffered request
			// efficient decoding of stream to chars
			BufferedReader br = new BufferedReader(
									new InputStreamReader(
										socket.getInputStream()
									)
								);
			System.out.println(br.readLine());
			System.out.println(br.readLine());

			// read chars and write to file
			while((line = br.readLine()) != null){
				c += 1;
				fw.write(line+'\n');
			}

			fw.flush(); // write all buffered chars
			fw.close();
			br.close();

			System.out.println("HTML file written, "+c+" lines");
		} catch(Exception e){ e.printStackTrace(); }
	}
}