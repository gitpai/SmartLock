package com.socket.tolower;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class SocketDemo extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9080853382423607789L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		/*System.out.println("与下位机的通信程序正在启动");
		SocketServer socketServer=new SocketServer();
		socketServer.start();
		System.out.println("与下位机的通信程序启动成功");*/
		UDPServerThread server = UDPServerThread.getUDPServerThread();
		server.start();
	}

}
