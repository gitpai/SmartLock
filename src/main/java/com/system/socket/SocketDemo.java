package com.system.socket;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.hibernate.engine.spi.ActionQueue.TransactionCompletionProcesses;

public class SocketDemo extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9080853382423607789L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		/*UDPServerThread server = UDPServerThread.getUDPServerThread();
		server.start();*/
		
		TCPServerThread server = TCPServerThread.getTCPServerThreadInstance();
		server.start();		
	}

}
