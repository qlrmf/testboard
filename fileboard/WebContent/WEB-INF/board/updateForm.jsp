<%@page import="board.model.BoardVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
   
<jsp:include page="boardHeader.jsp" flush="false"/>
	
<article>
	<div>
		<h4>Update</h4>
	</div>
	<div>
	<form action="<%= request.getContextPath() %>/upload" method="post" enctype="multipart/form-data">
		<table border="1" >
			<tr>
				<td id="head">작성자: </td>
				<td>${update.writer }</td>
				<td id="head">비밀번호: </td>
				<td><input id="inputbox" type="password" name="password" placeholder="기존 입력한 비밀번호 입력"></td>
			</tr>
			<tr>
				<td id="head">제목: </td>
				<td colspan="5"><input id="inputbox" type="text" name="title" value="${update.title }"></td>
			</tr>
			<tr>
				<td colspan="6"><textarea style="width: 99%; font-size: 12" rows="20" name="content">${update.content }</textarea></td>
			</tr>
			<c:if test="${update.num eq update.ref}">
				<td id="head">첨부파일</td>
				<td colspan="3">
					기존 파일 : ${files.fileName } <br>
					<input id="inputbox" type="file" name="file">
				</td>
			</c:if>
		</table>
		<input type="button" onclick="location.href='list.board'" value="취소">
		<input type="hidden" value="${update.writer }"/>
		<input type="submit" value="저장">
	</form>
	</div>
</article>


<jsp:include page="boardFooter.jsp" flush="false"/></html>