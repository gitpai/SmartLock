package com.socket.tolower;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;

import com.global.dao.CookInfoDao;
import com.global.dao.EquipDao;
import com.global.dao.ExceptDao;
import com.global.daoimpl.CookInfoDaoImpl;
import com.global.daoimpl.EquipDaoImpl;
import com.global.daoimpl.ExcepDaoImpl;
import com.global.model.CookInfo;
import com.global.model.EquipInfo;
import com.global.model.ExcepInfo;

/**
 * 设置成单例类
 * @author XiaoXiang
 *
 */
public class UDPServerThread extends Thread{
	private DatagramSocket serverSocket;					//申明数据报套接字
	private DatagramPacket recvPacket;						//申明接收数据的数据包
	private DatagramPacket sendPacket;						//申明发送信息的数据包
	private int serverPort = 8899;							//接收数据的端口
	public static final int BUFFER_SIZE = 512;				//接发数组大小
	
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
					/*---------接收数据，进行解析--------*/
					byte[] recvByte = recvPacket.getData();
					if(recvByte[1]==0x10){				//上传设备信息
						EquipDao edao = new EquipDaoImpl();
						EquipInfo ei = new EquipInfo();
						ei.setEquipIp(recvPacket.getAddress().getHostAddress()).setEquipId(recvPacket.getPort()).setCreatTime(new Date());
						edao.save(ei);
					}else if(recvByte[1]==0x20){		//上传设备故障		
						ExceptDao etdao = new ExcepDaoImpl();
						ExcepInfo exi = new ExcepInfo();
						exi.setCreatTime(new Date()).setEquipId(recvPacket.getPort()).setEquipIp(recvPacket.getAddress().getHostAddress());
						exi.setSolve(false);
						switch (recvByte[3]) {
						case (byte)0x00:
							exi.setEtype(ExcepInfo.TYPE_NOECLCTRIC);
							break;
						case (byte)0x10:
							exi.setEtype(ExcepInfo.TYPE_NOWATER);
							break;
						case (byte)0x20:
							exi.setEtype(ExcepInfo.TYPE_NORICE);
							break;
						case (byte)0x30:
							exi.setEtype(ExcepInfo.TYPE_EQUIP);
							break;
						default:
							exi.setEtype(ExcepInfo.TYPE_EQUIP);
							break;
						}
						etdao.save(exi);
					}else if(recvByte[1]==0x30){
						CookInfoDao cdao = new CookInfoDaoImpl();
						CookInfo ci = cdao.findLastet();
						switch (recvByte[3]) {
						case (byte)0x00:
							ci.setState(CookInfo.STATE_CONFRIM);
							break;
						case (byte)0x10:
							ci.setState(CookInfo.STATE_COOKING);
							break;
						case (byte)0x20:
							ci.setState(CookInfo.STATE_COOKED);
							break;
						case (byte)0x30:
							ci.setState(CookInfo.STATE_END);
							break;
						default:
							ci.setState(CookInfo.STATE_ERROR);
							break;
						}
						cdao.save(ci);
					}
					String rcvd = "客户端IP地址 " + recvPacket.getAddress() + ",端口号： " + recvPacket.getPort();
					
					System.out.println("下位机设备信息"+rcvd);
					System.err.println("下位机设备发送消息为"+recvStr);
					sendData("The Server receive the Data:"+recvStr);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				break;				
			}
		}
	}
	
	
	//对外提供的方法
	
	/**
	 * 发送数据
	 * 根据recvPacket得到需要发送的IP地址
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
	 * 发送数据
	 * @param sendIp   需要发送的地址
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
	 * 测试发送数据
	 * @param sendIp   需要发送的地址
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
	 * 关闭套接字
	 */
	public void closeSocket(){
		serverSocket.close();
	 }
	
	
	/**
	 * 对象在被销毁时执行的方法
	 */
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		closeSocket();
	}
	
}
