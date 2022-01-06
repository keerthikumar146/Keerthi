package com.test.mulchat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server {
	
	static Vector<ClientHandler> vector=new Vector<>();
	static int i=0;
	public static void main(String[] args) throws IOException {
		ServerSocket s=new ServerSocket(8080);
		
		Socket so;
		
		while(true) {
			 so=s.accept();
			 System.out.println("new client request" 	+ s);
			 
			 DataInputStream input=new DataInputStream(so.getInputStream());
			 DataOutputStream output=new DataOutputStream(so.getOutputStream());
			 
			 ClientHandler clienthandler=new ClientHandler("Client num"+i,input,output,so);
			 
			 Thread t=new Thread(clienthandler);
			 
			 vector.add(clienthandler);
			 
			 t.start();
			 
			 i++;
		}
	}
}
