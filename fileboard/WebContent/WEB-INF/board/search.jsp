<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="board.model.BoardVo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<jsp:include page="boardHeader.jsp" flush="false"/>
	
<article>
	<div>
		<h4>BoardList</h4>
	</div>
	<div>
	<form action="search.board">
	<table>
		<tr>
			<td style="width: 15%">
				<select id="searchSelect" name="type">
					<option value="Writer">작성자</option>
					<option value="Title">제목</option>
				</select>
			</td>
			<td style="width: 70%">
				<input id="search" type="text" name="str" placeholder="검색내용">
			</td>
			<td style="width: 15%">
				<input id="search" type="submit" value="검색">
			</td>
		</tr>
	</table>
	</form>
	</div>
	<div>
	<form action="writeForm.board">
		<table>
			<tr style="background:#FCD8D4; text-align: center;">
				<th id="num">번호</th>
				<th id="title">제목</th>
				<th id="writer">작성자</th>
				<th id="regdate">작성일</th>
				<th id="file">파일</th>
				<th id="read">조회수</th>
			</tr>
			<c:forEach var="vo" items="${search }">
				<tr>  
					<td align="center">
						<c:out value="${number }"/>
						<c:set var="number" value="${number-1 }"/>
					</td>
					<td>
						<c:if test="${vo.depth!=0 }">
							<c:out value="└"/>
							<input type="hidden" name="ref" value="${vo.ref }">
						</c:if>
						<a href="detail.board?num=${vo.num }&pageNum=${currentPage }">${vo.title}</a>
					</td>
					<td>${vo.writer }</td>
					<td><fmt:formatDate value="${vo.regdate }" type="both" pattern="yyyy-MM-dd"/></td>
					<td align="center"></td>
					<td align="center">${vo.readCount }</td>
				</tr>
			</c:forEach>
		</table>

		<c:if test="${count>0 }">	
			<c:set var="tmp" value="${count%pageSize==0? 0:1}"/>
			<c:set var="pageCount" value="${count/pageSize + tmp }"/>
			<fmt:parseNumber var="pageCount" value="${pageCount }" integerOnly="true"/>
			
			<c:set var="pageBlock" value="${5 }"/>
			
			<fmt:parseNumber var="result" value="${(currentPage-1)/pageBlock }" integerOnly="true"/>
			<c:set var="startPage" value="${result*pageBlock + 1 }"></c:set>
			<c:set var="endPage" value="${startPage + pageBlock -1 }"></c:set>
			
			
		
			<c:if test="${endPage>pageCount }">
				<c:set var="endPage" value="${pageCount }"/>
			</c:if>
			<c:if test="${startPage>pageBlock }">
				<a href="search.board?type=${type}&str=${str}&pageNum=${startPage-pageBlock }">[이전]</a>
			</c:if>
			<c:forEach var="i" begin="${startPage }" end="${endPage }">
				<a href="search.board?type=${type}&str=${str}&pageNum=${i }">[${i }]</a>
			</c:forEach>
			<c:if test="${endPage<pageCount }">
				<a href="search.board?type=${type}&str=${str}&pageNum=${startPage+pageBlock }">[다음]</a>
			</c:if>
		</c:if>
		<input type="submit" value="글쓰기">
	</form>
	</div>
</article>


<jsp:include page="boardFooter.jsp" flush="false"/>