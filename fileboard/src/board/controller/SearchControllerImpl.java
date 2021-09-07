package board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.BoardVo;

public class SearchControllerImpl implements IBoardController{

	@Override
	public String doRequest(HttpServletRequest req, HttpServletResponse resp) throws Throwable {
		String type = req.getParameter("type");
		String str = req.getParameter("str");
		int pageSize = 10;

		String pageNum = req.getParameter("pageNum");
		if(pageNum==null){
			pageNum="1";
			
		}
		if(str==null || str.equals("")) {
			return "pro/tmp.jsp";
		}
		int currentPage = Integer.parseInt(pageNum);
		int start = (currentPage-1)*pageSize+1;
		int end = currentPage*pageSize;
		int count = dao.searchBoardCount(type, str);
		int number = 0;		
		
		List li = dao.search(type, str, start, end);
		number = count - (currentPage-1)*pageSize;

		req.setAttribute("currentPage", new Integer(currentPage));
		req.setAttribute("startRow", new Integer(start));
		req.setAttribute("endRow", new Integer(end));
		req.setAttribute("count", new Integer(count));
		req.setAttribute("pageSize", new Integer(pageSize));
		req.setAttribute("number", new Integer(number));
		req.setAttribute("type", type);
		req.setAttribute("str", str);
		req.setAttribute("search", li);
		return "search.jsp";
	}

}
