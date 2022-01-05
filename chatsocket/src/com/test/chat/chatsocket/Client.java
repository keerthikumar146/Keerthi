package com.test.chat.chatsocket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
	public static void main(String[] args) {
		
		try {
			Socket s=new Socket("127.0.0.1",8080);
			System.out.println("connected");
			DataInputStream input=new DataInputStream(s.getInputStream());
			DataOutputStream output=new DataOutputStream(s.getOutputStream());
			
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			
			String mesin="",mesout="";
			
			while(!mesin.equals("end")) {
				mesout=br.readLine();
				output.writeUTF(mesout);
				mesin=input.readUTF();
				System.out.println(mesin);
			}
			
		}catch(Exception e) {
			
		}
	}
}
