package com.test.mulchat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

//import org.graalvm.compiler.nodes.debug.StringToBytesNode;

public class ClientHandler implements Runnable {
	Scanner sc = new Scanner(System.in);
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
		String mes;
		while (true) {
			try {
				mes=dis.readUTF();
				System.out.println(mes);
				
				if(mes.equals("logout")) {
					this.login=false;
					this.s.close();
					break;
				}
				
				StringTokenizer stoken=new StringTokenizer(mes,"#");
				String mestosend=stoken.nextToken();
				String recp=stoken.nextToken();
				
				for(ClientHandler ch : Server.vector) {
					if(ch.name.equals(recp) && ch.login==true) {
						ch.dout.writeUTF(this.name+ " : "+mestosend);
						break;
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
		try {
			dis.close();
			dout.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
