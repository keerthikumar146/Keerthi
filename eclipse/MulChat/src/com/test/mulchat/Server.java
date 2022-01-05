package com.test.mulchat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws IOException {
		ServerSocket s=new ServerSocket(8080);
		
		Socket so;
		
		while(true) {
			 so=s.accept();
			 System.out.println("new client request");
			 
			 DataInputStream input=new DataInputStream(so.getInputStream());
			 DataOutputStream output=new DataOutputStream(so.getOutputStream());
			 
			 Thread t=new Thread();
		}
	}
}
