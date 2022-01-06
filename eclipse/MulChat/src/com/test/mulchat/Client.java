package com.test.mulchat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		
		
		try {
			//InetAddress ip = InetAddress.getByName("localhost");
			Socket s=new Socket("127.0.0.1",8080);
			DataInputStream dis=new DataInputStream(s.getInputStream());
			DataOutputStream dos=new DataOutputStream(s.getOutputStream());
			
			Thread sendmes=new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					while(true) {
						String mes=sc.nextLine();
						
						try {
							dos.writeUTF(mes);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}
			});
			
			Thread readmes=new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					while(true) {
						String mes=sc.nextLine();
						
						try {
							dis.readUTF();
							System.out.println(mes);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
					}
					
				}
			});
			
			sendmes.start();
			readmes.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
