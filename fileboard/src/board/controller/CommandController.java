package board.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import attach.model.AttachVo;

public class CommandController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	IBoardController board;
	private Map<String, Object> reqMap = new HashMap<String, Object>();
		
	@Override
	public void init(ServletConfig config) throws ServletException {
		String propConfig = config.getInitParameter("propConfig");
		
		Properties prop = new Properties();
		FileInputStream fis = null;
		String path = config.getServletContext().getRealPath("/WEB-INF");

		try {
			fis = new FileInputStream(new File(path, propConfig));
			prop.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		Iterator<Object> it = prop.keySet().iterator();
		while(it.hasNext()) {
			String command = (String) it.next();
			String className = prop.getProperty(command);
			
			try {
				Class<?> commandClass = Class.forName(className);
				Object commandInstance = commandClass.newInstance();
				reqMap.put(command, commandInstance);		
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doRequest(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doRequest(req,resp);
	}

	private void doRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String page = null;
		String uri = req.getRequestURI();
		if(uri.indexOf(req.getContextPath())==0) {
			uri = uri.substring(req.getContextPath().length());
		}
		try {
			board = (IBoardController) reqMap.get(uri);
			System.out.println("uri: "+uri);
			System.out.println("board: "+board);
			if(board==null) {
				board = new NullControllerImpl();
			}
			page = "/WEB-INF/board/" + board.doRequest(req, resp);
		} catch (Throwable e) {
			throw new ServletException(e);
		} 
		req.getRequestDispatcher(page).forward(req, resp);
	}
	
	
}
