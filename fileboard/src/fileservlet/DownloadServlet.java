package fileservlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import attach.model.AttachVo;

public class DownloadServlet extends HttpServlet {
	private static String uploadPath = "";
	private File uploadDir = null;

	@Override
	public void init() throws ServletException {
		uploadPath = getServletContext().getInitParameter("uploadPath");
		uploadDir = new File(uploadPath);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String fileName = req.getParameter("fileName");
		String file = uploadPath + fileName;
		System.out.println(file);
		String mimeType = getServletContext().getMimeType(file);
		
		if(mimeType == null) {
			mimeType = "application/octet-stream";
		}
		fileName = URLEncoder.encode(fileName, "utf-8").replaceAll("\\+", "%20");

		resp.setContentType(mimeType + "; charset=utf-8");
		resp.setHeader("Content-Disposition", "attatchment; filename="+fileName);

		byte[] buffer = new byte[1024];
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));

		ServletOutputStream out = resp.getOutputStream();
		
		int readCnt = 0;
		while((readCnt = in.read(buffer, 0, buffer.length)) != -1) {
			out.write(buffer, 0, readCnt);
		}
		
		out.flush();
		out.close();
		in.close();
	}
}
