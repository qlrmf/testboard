package board.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import attach.model.AttachDao;
import attach.model.AttachVo;
import board.model.BoardVo;

public class UpdateControllerImpl implements IBoardController {
	private AttachDao attachdao = AttachDao.getInstance();

	@Override
	public String doRequest(HttpServletRequest req, HttpServletResponse resp) throws Throwable {
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession(true);
		
		Map<Object, Object> boardInfo = (Map<Object, Object>) session.getAttribute("boardInfo");

		String password = (String)boardInfo.get("password");
		BoardVo update = (BoardVo)session.getAttribute("update");
		if(!password.equals(update.getPassword())) {
			session.setAttribute("error", "update");
			return "pro/error.jsp";
		}

		String title = (String)boardInfo.get("title");
		String content = (String)boardInfo.get("content");
		
		BoardVo vo = new BoardVo();
		vo.setNum(update.getNum());
		vo.setTitle(title);
		vo.setContent(content);
		dao.update(vo);
		
		AttachVo file = (AttachVo)session.getAttribute("files");
		System.out.println(file);
		int bNum = update.getNum();
		if(file.getFileName()!=null) {
			file.setbNum(bNum);
			if(!attachdao.countFileList().containsKey(bNum)) {
				attachdao.insert(file);
			}else {
				attachdao.update(file);
			}
		}
		session.removeAttribute("update");
		return "pro/tmp.jsp";
	}

}
