package board.model;

import java.util.List;
import java.util.Map;

public interface IBoardDao {
	public int insert(BoardVo vo);
	public void update(BoardVo vo);
	public void delete(int num);
	public List<BoardVo> selectAll(int start, int end);
	public List<BoardVo> search(String type, String str, int start, int end);	// type: writer, title	str: 검색내용
	public BoardVo detail(int num);
	
	public List<BoardVo> detailAnswer(int num);
	public void hitIt(int num);
	public Map<Object, Object> boardRef();
	public int boardCount();
	public int searchBoardCount(String type, String str);
}
