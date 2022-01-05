package com.test.mulchat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

abstract class ClientHandler implements Runnable {
	Scanner sc=new Scanner(System.in);
	private String name;
	final DataInputStream dis;
	final DataOutputStream dout;
	Socket s;
	boolean login;
	public ClientHandler(String name, DataInputStream dis, DataOutputStream dout, Socket s) {
		super();
		this.name = name;
		this.dis = dis;
		this.dout = dout;
		this.s = s;
		this.login = true;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
