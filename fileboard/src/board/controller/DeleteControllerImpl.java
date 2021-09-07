package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.BoardVo;

public class DeleteControllerImpl implements IBoardController {

	@Override
	public String doRequest(HttpServletRequest req, HttpServletResponse resp) throws Throwable {
		HttpSession session = req.getSession(true);
		BoardVo delete = (BoardVo)session.getAttribute("delete");
		
		String password = req.getParameter("password");
		if(!password.equals(delete.getPassword())) {
			req.setAttribute("error", "delete");
			return "pro/error.jsp";
		}
		dao.delete(delete.getNum());
		session.removeAttribute("delete");
		return "tmp.jsp";
	}

}
