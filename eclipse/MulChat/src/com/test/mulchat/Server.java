package com.test.mulchat;

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
		}
	}
}
