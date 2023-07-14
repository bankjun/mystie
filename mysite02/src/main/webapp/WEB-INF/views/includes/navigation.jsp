<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="com.bitacademy.mysite.vo.UserVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 	UserVo authUser = (UserVo)session.getAttribute("authUser");
%>
<div id="navigation">
	<ul>
		<% 
			if(authUser != null) {
		%>
		<li><a href="${pageContext.request.contextPath }"><%=authUser.getName() %></a></li>
		<% 
			} else { 
		%>
    <li><a href="${pageContext.request.contextPath }/user?a=loginform">로그인</a></li>
		<% 
			} 
		%>
		<li><a href="${pageContext.request.contextPath }/guestbook">방명록</a></li>
		<li><a href="${pageContext.request.contextPath }/board">게시판</a></li>
	</ul>
</div>