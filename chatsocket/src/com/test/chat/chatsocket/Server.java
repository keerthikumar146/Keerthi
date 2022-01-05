package com.test.chat.chatsocket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) {
		int port=8080;
		
		try {
			ServerSocket s=new ServerSocket(port);
			System.out.println("server started");
			
			System.out.println("wating for client");
			Socket so=s.accept();
			
			DataInputStream input=new DataInputStream(so.getInputStream());
			DataOutputStream output=new DataOutputStream(so.getOutputStream());
			
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			
			String mesInput="",mesOutput="";
			
			while(!mesInput.equals("end")) {
				mesInput=input.readUTF();
				System.out.println(mesInput);
				mesOutput=br.readLine();
				output.writeUTF(mesOutput);
				output.flush();
			}
			so.close();
			
		} catch (Exception e) {

		}
	}
}