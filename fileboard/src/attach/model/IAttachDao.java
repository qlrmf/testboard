package attach.model;

import java.util.List;
import java.util.Map;

public interface IAttachDao {
	public int insert(AttachVo vo);
	public void update(AttachVo vo);
	public AttachVo files(int bNum);
	public int countFile(int bNum);
	public Map<Object, Object> countFileList();
}
