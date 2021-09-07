package board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import attach.model.AttachDao;
import attach.model.AttachVo;
import board.model.BoardVo;

public class DetailControllerImpl implements IBoardController {
	private AttachDao attach = AttachDao.getInstance();
	
	@Override
	public String doRequest(HttpServletRequest req, HttpServletResponse resp) throws Throwable {
		int num = Integer.parseInt(req.getParameter("num"));
		dao.hitIt(num);
		BoardVo vo = dao.detail(num);
		HttpSession session = req.getSession(true);
		session.setAttribute("detail", vo);
		req.setAttribute("num", num);
		
		List<BoardVo> li = dao.detailAnswer(num);
		req.setAttribute("answer", li);
		
		AttachVo files = attach.files(num);
		if(files!=null) {
			session.setAttribute("files", files);
		}
		return "detail.jsp";
	}

}
