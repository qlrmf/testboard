package board.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import attach.model.AttachVo;
import attach.model.AttachDao;
import board.model.BoardVo;

public class WriteControllerImpl implements IBoardController {
	private AttachDao attachdao = AttachDao.getInstance();
	
	@Override
	public String doRequest(HttpServletRequest req, HttpServletResponse resp) throws Throwable {
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession(true);
		AttachVo file = null;

		Map<Object, Object> boardInfo = (Map<Object, Object>) session.getAttribute("boardInfo");

		String writer = (String)boardInfo.get("writer");
		String title = (String)boardInfo.get("title");
		String content = (String)boardInfo.get("content");
		int depth = Integer.parseInt((String) boardInfo.get("depth"));
		int ref = Integer.parseInt((String) boardInfo.get("ref"));
		String password = (String)boardInfo.get("password");
		
		BoardVo vo = new BoardVo();
		vo.setWriter(writer);
		if(ref!=0) {
			vo.setDepth(depth+1);
		}else {
			vo.setDepth(depth);
		}
		vo.setTitle(title);
		vo.setContent(content);
		vo.setRef(ref);
		vo.setPassword(password);
		if(dao.insert(vo)!=-1) {
			file = (AttachVo)session.getAttribute("files");
			if(file!=null) {
				attachdao.insert(file);
			}
			session.removeAttribute("files");
			session.removeAttribute("boardInfo");
			return "pro/tmp.jsp";
		}
		session.setAttribute("error", "Writing");
		return "pro/error.jsp";
	}
	
	
}
