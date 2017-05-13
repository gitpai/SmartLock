package com.system.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServerThread extends Thread{
	private DatagramSocket serverSocket;					//�������ݱ��׽���
	private DatagramPacket recvPacket;						//�����������ݵ����ݰ�
	private DatagramPacket sendPacket;						//����������Ϣ�����ݰ�
	private int serverPort = 8899;							//�������ݵĶ˿�
	public static final int BUFFER_SIZE = 512;				//�ӷ������С
	
	private static UDPServerThread udpServerThread;
	
	private UDPServerThread(){
		try {
			this.serverSocket = new DatagramSocket(serverPort);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public static UDPServerThread getUDPServerThread(){
		if(udpServerThread==null){
			udpServerThread = new UDPServerThread();
		}
		return udpServerThread;
	}

	
	@Override
	public void run() {
		byte[] recvBuf = new byte[BUFFER_SIZE];
		recvPacket = new DatagramPacket(recvBuf, recvBuf.length);
		while(true){
			if(serverSocket!=null){
				try {
					serverSocket.receive(recvPacket);
					String recvStr = new String(recvPacket.getData(), 0, recvPacket.getLength());
					/*---------�������ݣ����н���--------*/
					byte[] recvByte = recvPacket.getData();
					
					String rcvd = "�ͻ���IP��ַ " + recvPacket.getAddress() + ",�˿ںţ� " + recvPacket.getPort();
					
					System.out.println("��λ���豸��Ϣ"+rcvd);
					System.err.println("��λ���豸������ϢΪ"+recvStr);
					sendData("The Server receive the Data:"+recvStr);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				break;				
			}
		}
	}
	
	
	//�����ṩ�ķ���
	
	/**
	 * ��������
	 * ����recvPacket�õ���Ҫ���͵�IP��ַ
	 */
	public void sendData(String sendStr){
		try {
			int port = recvPacket.getPort();
			InetAddress addr = recvPacket.getAddress();
			byte[] sendBuf = sendStr.getBytes();
			sendPacket = new DatagramPacket(sendBuf,sendBuf.length,addr,port);
			serverSocket.send(sendPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * ��������
	 * @param sendIp   ��Ҫ���͵ĵ�ַ
	 * @param sendStr
	 */
	public void sendData(String sendIp,int sendPort,byte[] sendByte){
		try {
			InetAddress addr = InetAddress.getByName(sendIp);
			//byte[] sendBuf = sendStr.getBytes();
			sendPacket = new DatagramPacket(sendByte,sendByte.length,addr,sendPort);
			serverSocket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * ���Է�������
	 * @param sendIp   ��Ҫ���͵ĵ�ַ
	 * @param sendStr
	 */
	public void sendDataTest(String sendIp,int sendPort,String sendStr){
		try {
			InetAddress addr = InetAddress.getByName(sendIp);
			byte[] sendBuf = sendStr.getBytes();
			sendPacket = new DatagramPacket(sendBuf,sendBuf.length,addr,sendPort);
			serverSocket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * �ر��׽���
	 */
	public void closeSocket(){
		serverSocket.close();
	 }
	
	
	/**
	 * �����ڱ�����ʱִ�еķ���
	 */
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		closeSocket();
	}
	
}
