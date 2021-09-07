package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.BoardVo;

public class DeleteFormControllerImpl implements IBoardController {

	@Override
	public String doRequest(HttpServletRequest req, HttpServletResponse resp) throws Throwable {
		int num = Integer.parseInt(req.getParameter("num"));
		BoardVo tmp = dao.detail(num);
		
		HttpSession session = req.getSession(true);
		session.setAttribute("delete", tmp);
		return "deleteForm.jsp";
	}

}
