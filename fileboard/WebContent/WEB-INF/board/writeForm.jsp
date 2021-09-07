<%@page import="board.model.BoardVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
   
<jsp:include page="boardHeader.jsp" flush="false"/>
	
<article>
	<div>
		<h4>Write</h4>
	</div>
	<div>
	<form action="<%= request.getContextPath() %>/upload" method="post" enctype="multipart/form-data">
		<input type="hidden" name="ref" value=${num }>
		<input type="hidden" name="depth" value=${depth }>
		<table border="1" >
			<tr>
				<td id="head">작성자: </td>
				<td><input id="inputbox" type="text" name="writer" required></td>
				<td id="head">비밀번호: </td>
				<td><input id="inputbox" type="password" name="password" required></td>
			</tr>
			<tr>
				<td id="head">제목: </td>
				<td colspan="4">
					<c:if test="${num==0 }">
						<input id="inputbox" type="text" name="title" required>
					</c:if>
					<c:if test="${num!=0 }">
						<input id="inputbox" type="text" name="title" placeholder="RE:${detail.title }" required>
					</c:if>
				</td>
			</tr>
			<tr>
				<td colspan="4"><textarea style="width: 99%; font-size: 12" rows="20" name="content" required></textarea></td>
			</tr>
			<tr>
			<c:if test="${num==0 }">
				<td id="head">첨부파일</td>
				<td colspan="3"><input id="inputbox" type="file" name="file"></td>
			</c:if>
			</tr>
		</table>
		<input type="button" onclick="location.href='list.board'" value="취소">
		<input type="submit" value="저장">
	</form>
	</div>
</article>


<jsp:include page="boardFooter.jsp" flush="false"/></html>