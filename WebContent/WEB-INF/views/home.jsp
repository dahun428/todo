<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>To Do</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
	<c:set value="home" var="position"/>
	<%@ include file="nav.jsp" %>
	<!-- Content -->
	<div class="row mb-3">
		<div class="col-12">
			<div class="card">
				<div class="card-header">전체 일정</div>
				<div class="card-body">
					<div class="row">
					<c:forEach items="${todoList }" var="todo">
						<div class="col-4 mb-2">
							<div class="card">
								<div class="card-header d-flex justify-content-between">
									<div><c:out value="${todo.title }"/> </div> 
									<div><span class="badge ${todo.statusClass }">${todo.status }</span></div>
								</div>
								<div class="card-body">
									<div class="row mb-3">
										<div class="col-9">
											<small><c:out value="${todo.content }"/> </small>
										</div>
										<div class="col-3">
											<button type="button" class="btn btn-outline-secondary btn-sm" onclick="openTodoDetailModal(${todo.no})">상세</button>
										</div>
									</div>
									<div class="d-flex justify-content-between">
										<span class="text-secondary font-weight-bold"><c:out value="${todo.userName }"/> </span>											
										<strong><fmt:formatDate value="${todo.createDate }"/></strong>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
					</div>
					<div class="row">
						<div class="col-12 text-center"><button class="btn btn-outline-dark btn"> 더보기 </button></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 상세정보 모달창 -->
	<div class="modal" id="modal-todo-detail">
		<div class="modal-dialog modal-lg">
 			<div class="modal-content">
   				<div class="modal-header">
     				<h4 class="modal-title">상세 정보</h4>
     				<button type="button" class="close" data-dismiss="modal">&times;</button>
   				</div>
   				<div class="modal-body">
     				<div class="row">
     					<div class="col-12">
     						<table class="table table-bordered table-sm">
     							<colgroup>
     								<col width="15%">
     								<col width="35%">
     								<col width="15%">
     								<col width="35%">
     							</colgroup>
     							<tbody>
     								<tr>
     									<th>제목</th>
     									<td colspan="3" id="todo-title"></td>
     								</tr>
     								<tr>
     									<th>작성자</th>
     									<td id="todo-username">홍길동</td>
     									<th>등록일</th>
     									<td id="todo-createdate">2020-06-12</td>
     								</tr>
     								<tr>
     									<th>상태</th>
     									<td id="todo-status">처리예정</td>
     									<th>예정일</th>
     									<td id="todo-day">2020-07-12</td>
     								</tr>
     								<tr>
     									<th style="vertical-align: middle;">내용</th>
     									<td colspan="3"><small><span id="todo-content"></span></small></td>
     								</tr>
     							</tbody>
     						</table>
     					</div>
     				</div>
   				</div>
   				<div class="modal-footer">
   					<span id="btn-todo-modify" style="display: none;">
     				<button type="button" class="btn btn-success btn-sm">처리완료</button>
     				<button type="button" class="btn btn-info btn-sm">처리중</button>
     				<button type="button" class="btn btn-secondary btn-sm">보류</button>
     				<button type="button" class="btn btn-danger btn-sm">취소</button>
     				</span>
     				<button type="button" class="btn btn-outline-dark btn-sm" data-dismiss="modal">닫기</button>
   				</div>
 			</div>
		</div>
	</div>
	
	<%@ include file="footer.jsp" %>
</div>
<script type="text/javascript">
	function openTodoDetailModal(no) {
		$("#modal-todo-detail").modal('show');
		$('#btn-todo-modify').css('display','none');
		var todoNo = no;
		
		var todoTitle = $('#todo-title');
		var todoName = $('#todo-username');
		var todoStatus = $('#todo-status');
		var todoDay = $('#todo-day');
		var todoContent = $('#todo-content');
		var todoCreateDate = $('#todo-createdate');
		var todoButton = $('#btn-todo-modify');
		
		$.ajax({
			type:"POST",
			url:"get.hta",
			data:{
				'todoNo':todoNo
			},
			success:function(data){
				console.log(data.no);
				console.log(data);
				console.log(data.isModifyCan);
				var no = data.no;
				var title = data.title;
				var createDate = data.createDate;
				var day = data.day;
				var userId = data.userId;
				var userName = data.userName;
				var content = data.content;
				var status = data.status;
				var isModifyCan = data.isModifyCan;
				if(isModifyCan){
					todoButton.css('display','block');
				}
				todoTitle.text(title);
				todoName.text(userName);
				todoStatus.text(status);
				todoDay.text(day);
				todoContent.text(content);
				todoCreateDate.text(createDate);
				
			}
		});
	}
	
	
</script>
</body>
</html>
