package com.system.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import com.global.dao.CycLockDAO;
import com.global.daoimpl.CycLockDAOImpl;
import com.global.model.CycLock;

public class TCPServerThread extends Thread{
	private ServerSocket servSocket;
	private static Socket clientSocket;
	private static OutputStream out;
	private static final int SERVER_PORT=9999;
	private static final int BUFFER_SIZE = 512;				//接发数组大小
	
	private static TCPServerThread tcpServerThread;
	private TCPServerThread() {
		try {
			this.servSocket = new ServerSocket(SERVER_PORT);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("��ʼ��ʧ��");
		}
	}
	
	public static TCPServerThread getTCPServerThreadInstance(){
		if(tcpServerThread==null){
			tcpServerThread = new TCPServerThread();
		}
		return tcpServerThread;		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		byte[] receivBuf=new byte[BUFFER_SIZE];  
		int recvMsgSize;
		double lat=0,lon=0,asl=0;
		boolean state=true;
        try {  
            while(true){  
                clientSocket=servSocket.accept();  
                SocketAddress clientAddress = clientSocket.getRemoteSocketAddress();  
                System.out.println("Handling client at "+ clientAddress);  
                InputStream in =clientSocket.getInputStream(); 
                out= clientSocket.getOutputStream();
                
                while((recvMsgSize=in.read(receivBuf))!=-1){  
                	String received=new String(receivBuf,0,recvMsgSize);
                	if(received.charAt(0)=='{'){
                		try {
                            JSONObject  dataJson=new JSONObject(received);
                            lat = dataJson.getDouble("lat");
                            lon = dataJson.getDouble("lon");
                            asl = dataJson.getDouble("asl");
                            state = dataJson.getBoolean("locksta");
                            System.out.println(received);  
	   					} catch (Exception e) {
	   						System.out.println("ϵͳ�������ݷ�������");
	   					}
                		CycLockDAO cdao = new CycLockDAOImpl();
                        CycLock cyc = new CycLock();
                        cyc.setAsl(asl);
                        cyc.setLat(lat);
                        cyc.setLon(lon);
                        cyc.setState(state);
                        cyc.setTime(new Date());
                        cdao.save(cyc);     
                	}
                }  
                in.close();
                //clientSocket.close();               
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}
	
	
	public void send(String msg){
		try {
			//OutputStream out= clientSocket.getOutputStream();  
			byte[] sendByte = msg.getBytes();
			out.write(sendByte, 0, sendByte.length); 
			
			//out.close();
			//clientSocket.close();
		} catch (Exception e) {
			System.out.println("����ʧ��");
		}
	}
	
	public static void main(String[] args) {
		TCPServerThread server = TCPServerThread.getTCPServerThreadInstance();
		server.start();
	}
	
	
}
