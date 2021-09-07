package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDao;
import board.model.IBoardDao;

public interface IBoardController {
	IBoardDao dao = BoardDao.getInstance();
	public String doRequest(HttpServletRequest req, HttpServletResponse resp) throws Throwable;
}
