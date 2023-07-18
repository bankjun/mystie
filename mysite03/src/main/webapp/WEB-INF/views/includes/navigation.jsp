<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="navigation">
	<ul>
		<c:if test=${not empty authUser }>
			<li><a href="${pageContext.request.contextPath }">${authUser.name }</a></li>
		</c:if>
		
		<c:otherwise>
   			 <li><a href="${pageContext.request.contextPath }/user?a=loginform">로그인</a></li>		
		</c:otherwise>

		<li><a href="${pageContext.request.contextPath }/guestbook">방명록</a></li>
		<li><a href="${pageContext.request.contextPath }/board">게시판</a></li>
	</ul>
</div>