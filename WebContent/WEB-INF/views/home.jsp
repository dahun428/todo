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
<fmt:bundle basename="com.simple.resources.message">

<div class="container">
	<c:set value="home" var="position"/>
	<%@ include file="nav.jsp" %>
	<!-- Content -->
	<div class="row mb-3">
		<div class="col-12">
			<div class="card">
				<div class="card-header"><fmt:message key="home.title"/> </div>
				<div class="card-body">
					<div class="row" id="todo-list">
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
											<button type="button" class="btn btn-outline-secondary btn-sm" onclick="openTodoDetailModal(${todo.no})"><fmt:message key="button.label.detail" /> </button>
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
						<div class="col-12 text-center">
							<input type="hidden" name="plus" id="plusNumber" value="6" />
							<button class="btn btn-outline-dark btn" id="plus-btn"><fmt:message key="button.label.more"/> </button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 상세정보 모달창 -->
	<%@ include file="detailmodal.jsp" %>
	<%@ include file="footer.jsp" %>
</div>
</fmt:bundle>
<script type="text/javascript">

	$(document).ready(function(){
		//더보기
		var target = $('#todo-list');
		$('#plus-btn').click(function(){
		var plusNum = $('#plusNumber').val();
			$.ajax({
				type:'POST',
				url:'more.hta',
				data:{
					'plusNum':plusNum
				},
				success:function(data){
					console.log(data);
					console.log(data.beginIndex);
					console.log(data.lastIndex);
					$('#plusNumber').val(data.lastIndex);
					var todolist = data.todoList;
					
					for(var i = 0; i < todolist.length; i++){
						
						var no = todolist[i].no;
						var title = todolist[i].title;
						var content = todolist[i].content;
						var createDate = todolist[i].createDate;
						var day = todolist[i].day;
						var isModifyCan = todolist[i].isModifyCan;
						var status = todolist[i].status;
						var statusClass = todolist[i].statusClass;
						var userId = todolist[i].userId;
						var userName = todolist[i].userName;
					
						console.log(no, title, content, createDate, day, isModifyCan, status, statusClass, userId, userName);
						var innerhtml = '<div class="col-4 mb-2">'
							+'<div class="card">'
							+'<div class="card-header d-flex justify-content-between">'
								+'<div>'+title+'</div>' 
								+'<div><span class="badge '+statusClass+'">'+status+'</span></div>'
							+'</div>'
							+'<div class="card-body">'
								+'<div class="row mb-3">'
									+'<div class="col-9">'
										+'<small>'+content+'</small>'
									+'</div>'
									+'<div class="col-3">'
										+'<button type="button" class="btn btn-outline-secondary btn-sm" onclick="openTodoDetailModal('+no+')">상세</button>'
									+'</div>'
								+'</div>'
								+'<div class="d-flex justify-content-between">'
									+'<span class="text-secondary font-weight-bold">'+userName+'</span>'											
									+'<strong>'+createDate+'</strong>'
								+'</div>'
							+'</div>'
						+'</div>'
					+'</div>';
						target.append(innerhtml);
						
						
					}
				}
			});
		});
		
		
		//todoDetail Click Function
		var endBtn = $('#detail-btn-process-end');
		var progressBtn = $('#detail-btn-process-progress');
		var delayBtn = $('#detail-btn-process-delay');
		var cancleBtn = $('#detail-btn-process-cancle');
		
		endBtn.click(function(){
			endBtn.updateFuncWithAjax();
		});
		progressBtn.click(function(){
			progressBtn.updateFuncWithAjax();
		});
		delayBtn.click(function(){
			delayBtn.updateFuncWithAjax();
		});
		cancleBtn.click(function(){
			cancleBtn.updateFuncWithAjax();
		});
	});
	$.fn.updateFuncWithAjax = function(){
		var todoStatus = $('#todo-status');
		var todoNo = $('#todo-no').val();
		console.log('todoNo : ',todoNo);
		var id = $(this).attr('id');
		console.log('id : ', id);
		var target = id.split('-')[3];
		console.log(target);
		var statusName = '';
		if(target === 'end'){
			statusName = '처리완료';
		} else if (target === 'progress'){
			statusName = '처리중';
		} else if (target === 'delay'){
			statusName = '보류';
		} else if (target === 'cancle'){
			statusName = '취소';
		}
		todoStatus.text(statusName);
		console.log(statusName);
		$.ajax({
			type:'post',
			url:'update.hta',
			data:{
				'status':statusName,
				'todoNo':todoNo
			},
			success:function(){
				
			}
			
		});
	};

	function openTodoDetailModal(no) {
		$("#modal-todo-detail").modal('show');
		$('#btn-todo-modify').css('display','none');
		var todoNo = no;
		
		var todoTitle = $('#todo-title');
		var todoName = $('#todo-username');
		var todoStatus = $('#todo-status');
		var todoDay = $('#todo-day');
		var todoContent = $('#todo-content-detail');
		var todoCreateDate = $('#todo-createdate');
		var todoId = $('#todo-no');
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
				todoId.val(no);
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
