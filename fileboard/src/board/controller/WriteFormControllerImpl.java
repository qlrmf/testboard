package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.BoardVo;

public class WriteFormControllerImpl implements IBoardController {

	@Override
	public String doRequest(HttpServletRequest req, HttpServletResponse resp) throws Throwable {
		int num=0;
		int ref=0;
		int depth=0;
		try {
			if(req.getParameter("num")!=null) {
				num = Integer.parseInt(req.getParameter("num"));
				ref = Integer.parseInt(req.getParameter("ref"));
				depth = Integer.parseInt(req.getParameter("depth"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		req.setAttribute("num", num);
		req.setAttribute("ref", ref);
		req.setAttribute("depth", depth);
		
		return "writeForm.jsp";
	}

}
