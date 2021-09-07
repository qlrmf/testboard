package fileservlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import attach.model.AttachVo;

@WebServlet(
		urlPatterns="/upload", 
		initParams= {
			@WebInitParam(
				name="uploadPath", 
				value="C:\\uploadfiletest\\upload\\"
			)
		}
	)
@MultipartConfig(maxFileSize = -1, maxRequestSize = -1, fileSizeThreshold = 1024)
public class UploadServlet extends HttpServlet {
	private static String uploadPath = "";
	private File uploadDir = null;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		uploadPath = config.getInitParameter("uploadPath");
		uploadDir = new File(uploadPath);
		if(!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String contentType = req.getContentType();
		if(contentType!=null && contentType.toLowerCase().startsWith("multipart/")) {
			fileUpload(req);
		}
		String[] header = req.getHeader("referer").split("/");
		String nextPath = "board/write.board";
		if(header[header.length-1].equals("updateForm.board")) {
			nextPath = "board/update.board";
		}
		req.getRequestDispatcher(nextPath).forward(req, resp);
	}
	
	//파일 업로드 기능 구현-----------------------------
		private void fileUpload(HttpServletRequest req) throws IOException, ServletException {
			HttpSession session = req.getSession(true);
			Collection<Part> parts = null;
			Map<Object, Object> boardInfo = new HashMap<Object, Object>();
			AttachVo vo = null;
			
			try {
				parts = req.getParts();
				for(Part part: parts) {
					String fileName = null;
					long fileSize = 0;
					String contentType = null;
					
					vo = new AttachVo();
					contentType = part.getContentType();
					if(contentType==null) {
						boardInfo.put(part.getName(), readParameterValue(part));
						part.delete();
					}else {
						fileName = getFileName(part);
						fileSize = part.getSize();
						if(!fileName.equals("")) {
							vo.setFileName(fileName);
							vo.setFileSize(fileSize);
							vo.setContentType(contentType);
							part.write(uploadPath + fileName);
						}
						part.delete();
					}
				}
				session.setAttribute("boardInfo", boardInfo);
				session.setAttribute("files", vo);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			}	
			
		}
		
		private String getFileName(Part part) {
			for(String cd : part.getHeader("Content-Disposition").split(";")) {
				if(cd.trim().startsWith("filename")) {
					String tmp = cd.substring(cd.indexOf('=')+1).trim().replace("\"", "");
					tmp = tmp.substring(tmp.indexOf(":")+1);
					return tmp;
				}
			}
			return null;
		}
		
		private String readParameterValue(Part part) throws IOException {
			InputStreamReader isr = new InputStreamReader(part.getInputStream(), "utf-8");
			char[] data = new char[512];
			int len = -1;
			StringBuilder builder = new StringBuilder();
			while((len=isr.read(data))!=-1) {
				builder.append(data,0,len);
			}
			return builder.toString();
		}
}
