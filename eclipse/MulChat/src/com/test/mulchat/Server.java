package com.test.mulchat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

public class Server {
	
	static Vector<ClientHandler> vector=new Vector<>();
	static int i=0;
	public static void main(String[] args) throws IOException {
		ServerSocket s=new ServerSocket(8080);
		
		Socket so;
		
		while(true) {
			 so=s.accept();
			 System.out.println("new client request " 	+ so);
			 
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

class ClientHandler implements Runnable {
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
			this.dis.close();
			this.dout.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}

