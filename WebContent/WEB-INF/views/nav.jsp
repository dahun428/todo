<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- Header -->
	<nav class="navbar navbar-expand-sm navbar-dark bg-dark mt-3 mb-3">
		<ul class="navbar-nav mr-auto">
      		<li class="nav-item ${'home' eq position ? 'active' : ''}">
        		<a class="nav-link" href="/home.hta"><fmt:message key="menu.label.home"/> </a>
      		</li>
      		<li class="nav-item ${'todo' eq position ? 'active' : ''}">
        		<a class="nav-link" href="/todos.hta"><fmt:message key="menu.label.todo"/></a>
      		</li>
    	</ul>
    	<ul class="navbar-nav navbar-dark bg-dark justify-content-end">
      		<li class="nav-item">
        		<a class="nav-link" href="#" onclick="openRegisterformModal(event)"><fmt:message key="menu.label.signin"/></a>
      		</li>
      		
      	<c:choose>
      		<c:when test="${not empty loginedUser }">
	      		<li class="nav-item">
	        		<a class="nav-link" href="/logout.hta">로그아웃</a>
	      		</li>
	      	</c:when>
      		<c:otherwise>
	      		<li class="nav-item">
	        		<a class="nav-link" href="#" onclick="openLoginformModal(event)"><fmt:message key="menu.label.signup"/></a>
	      		</li>
      		</c:otherwise>
      	</c:choose>	
      		
      
    	</ul>
	</nav>