package board.controller;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import attach.model.AttachDao;
import board.model.BoardVo;

public class ListControllerImpl implements IBoardController {
	AttachDao attachdao = AttachDao.getInstance();
	@Override
	public String doRequest(HttpServletRequest req, HttpServletResponse resp) throws Throwable {
		HttpSession session = req.getSession();
		Enumeration enu = session.getAttributeNames();
		session.removeAttribute("detail");
		session.removeAttribute("error");
		session.removeAttribute("files");

		String pageNum = req.getParameter("pageNum");
		
		if(pageNum==null){
			pageNum="1";
		}
		int pageSize = 10;
		int currentPage = Integer.parseInt(pageNum);
		
		int start = (currentPage-1)*pageSize+1;
		int end = currentPage*pageSize;
		int count = dao.boardCount();
		int number = 0;		
		
		List<BoardVo> li = null;
		if(count>0) {
			li = dao.selectAll(start, end);
		} else {
			li = Collections.emptyList();
		}
		number = count - (currentPage-1)*pageSize;
		Map<Object, Object> ansCount = dao.boardRef();
		req.setAttribute("ansCount", ansCount);
		
		req.setAttribute("currentPage", new Integer(currentPage));
		req.setAttribute("startRow", new Integer(start));
		req.setAttribute("endRow", new Integer(end));
		req.setAttribute("count", new Integer(count));
		req.setAttribute("pageSize", new Integer(pageSize));
		req.setAttribute("number", new Integer(number));
		req.setAttribute("list", li);
		
		Map<Object, Object> hsm = attachdao.countFileList();
		req.setAttribute("files", hsm);
		
		return "list.jsp";
	}

}
