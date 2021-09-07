<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
   
<jsp:include page="boardHeader.jsp" flush="false"/>
	
<article>
	<div>
		<h4>DeleteArticle</h4>
	</div>
	<div>
	<form action="delete.board" method="post">
		<table>
		<caption><b>해당 게시글을 삭제합니다.</b></caption>
		<tr>
			<td>작성일: <fmt:formatDate value="${delete.regdate }" type="both" pattern="yyyy-MM-dd"/> | 작성자: ${delete.writer }</td>
		</tr>
		<tr>
			<td>제목: ${delete.title }</td>
		</tr>
		<tr>
			<td>내용: ${delete.content }</td>
		</tr>
		<tr>
			<td>비밀번호: </td>
			<td><input id="inputbox" type="password" name="password" placeholder="게시글 비밀번호 입력" required></td>
		</tr>
		</table>
		<br>
		<input type="submit" value="삭제">
	</form>
	</div>
</article>


<jsp:include page="boardFooter.jsp" flush="false"/>