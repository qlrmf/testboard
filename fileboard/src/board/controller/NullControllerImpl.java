package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NullControllerImpl implements IBoardController {

	@Override
	public String doRequest(HttpServletRequest req, HttpServletResponse resp) throws Throwable {
		return "pro/error.jsp";
	}

}
