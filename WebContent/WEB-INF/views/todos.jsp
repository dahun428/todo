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
	<c:set value="todo" var="position"/>
	<%@ include file="nav.jsp" %>
	
	<!-- Content -->
	<div class="row mb-3">
		<div class="col-12">
			<div class="card">
				<div class="card-header">나의 일정 <button class="btn btn-primary btn-sm float-right" onclick="openTodoFormModal()">새 일정</button></div>
				<div class="card-body">
					<form method="get" action="todos.hta" id="my-form">
					<div class="row mb-3">
						<div class="col-6 pt-2">
						
							<strong class="mr-3">처리완료 : <span class="bg-success text-white py-1 px-3"><fmt:formatNumber value="${clearTodo }"/></span></strong>
							<strong>미처리 : <span class="bg-primary text-white py-1 px-3"><fmt:formatNumber value="${noClearTodo }"/></span></strong>
						</div>
						<div class="col-6 d-flex justify-content-end">
							<select class="form-control mr-3" style="width: 100px; " name="rows">
								<option value="5" ${rows eq 5 ? 'selected' : '' }> 5개씩</option>
								<option value="10" ${rows eq 10 ? 'selected' : '' } > 10개씩</option>
								<option value="20" ${rows eq 20 ? 'selected' : '' }> 20개씩</option>
							</select>
							<select class="form-control " style="width: 120px;" name="status">
								<option value=""> 전체</option>
								<option value="처리예정" ${status eq '처리예정' ? 'selected' : '' }> 처리예정</option>
								<option value="처리중" ${status eq '처리중' ? 'selected' : '' }> 처리중</option>
								<option value="보류" ${status eq '보류' ? 'selected' : '' }> 보류</option>
								<option value="삭제" ${status eq '삭제' ? 'selected' : '' }> 삭제</option>
								<option value="처리완료" ${status eq '처리완료' ? 'selected' : '' }> 처리완료</option>
							</select>
						</div>
					</div>
					<div class="row">
						<div class="col-12">
							<table class='table'>
								<colgroup>
									<col width="10%">
									<col width="*">
									<col width="15%">
									<col width="15%">
								</colgroup>
								<thead>
									<tr>
										<th>순번</th>
										<th>제목</th>
										<th>날짜</th>
										<th>처리상태</th>
									</tr>
								</thead>
								<tbody>
							<c:choose>
								<c:when test="${not empty todoList }">
									<c:forEach items="${todoList }" var="todo" varStatus="status">
										<tr>
											<td>${(pagenation.pageNo - 1) * pagenation.rowsPerPage + status.count }</td>
											<td><a href="#" onclick="openTodoDetailModal(${todo.no})"><c:out value="${todo.title }"/></a></td>
											<td><fmt:formatDate value="${todo.createdDate }"/> </td>
											<td><c:out value="${todo.status }"/> </td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
										<tr>
											<td colspan="4"><span>내역이 없습니다.</span></td>
										</tr>								
								</c:otherwise>
							</c:choose>	
								</tbody>
							</table>
						</div>
					</div>
					<div class="row">
						<div class="col-6 ">
						<input type="hidden" name="page" value=${pagenation.pageNo } />
							<ul class="pagination">
							<c:if test="${pagenation.pageNo > 1 }">
								<li class="page-item"><a class="page-link" href="../todos.hta?page=${pagenation.pageNo - 1 }">이전</a></li>
							</c:if>	
							<c:forEach var="page" begin="${pagenation.beginPage }" end="${pagenation.endPage }">
								<li class="page-item ${page eq pagenation.pageNo ? 'active' : '' }"><a class="page-link" href="../todos.hta?rows=${rows }&status=${status }&page=${page }&keyword=${keyword }">${page }</a></li>
							</c:forEach>
							<c:if test="${pagenation.pageNo < pagenation.totalPages }">
								<li class="page-item"><a class="page-link" href="../todos.hta?page=${pagenation.pageNo + 1 }">다음</a></li>
							</c:if>
							</ul>
							
						</div>
						<div class="col-6 text-right">
							<input type="text" class="form-control" style="width: 250px; display: inline-block;" name="keyword" value="${keyword }">
							<button type="button" id="search-btn" class="btn btn-dark" style="margin-top: -5px;">검색</button> 
						</div>
					</div>
				</form>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 등록 모달창 -->
	<div class="modal" id="modal-todo-form">
		<div class="modal-dialog modal-lg">
		<form method="post" action="add.hta">
 			<div class="modal-content">
   				<div class="modal-header">
     				<h4 class="modal-title">새 일정 등록폼</h4>
     				<button type="button" class="close" data-dismiss="modal">&times;</button>
   				</div>
   				<div class="modal-body">
   					<div class="row" id="fail-todo-notitle" style="display: none">
   						<div class="col-12">
   							<div class="alert alert-danger">
	   								<strong>실패</strong> 제목을 입력해주세요
	   						</div>
   						</div>
   					</div>
   					<div class="row" id="fail-todo-noday" style="display: none">
   						<div class="col-12">
   							<div class="alert alert-danger">
	   								<strong>실패</strong> 처리 예정일을 입력해주세요
	   						</div>
   						</div>
   					</div>
   					<div class="row" id="fail-todo-nocontent" style="display: none">
   						<div class="col-12">
   							<div class="alert alert-danger">
	   								<strong>실패</strong> 내용을 입력해주세요
	   						</div>
   						</div>
   					</div>
   					<div class="row" id="fail-todo-noserver" style="display: none">
   						<div class="col-12">
   							<div class="alert alert-danger">
	   								<strong>실패</strong> 로그인이 필요합니다.
	   						</div>
   						</div>
   					</div>
     				<div class="row">
     					<div class="col-12">
     						<div class="card">
     							<div class="card-body">
     								<div class="form-group">
     									<label>제목</label>
     									<input type="text" class="form-control" name="title" />
     								</div>
     								<div class="form-group">
     									<label>처리 예정일</label>
     									<input type="date" class="form-control" name="day" />
     								</div>
     								<div class="form-group">
     									<label>내용</label>
     									<textarea id="todo-content" rows="3" class="form-control" name="content"></textarea>
     								</div>
     							</div>
     						</div>
     					</div>
     				</div>
   				</div>
   				<div class="modal-footer">
     				<button type="button" class="btn btn-success btn-sm" onclick="addTodo()">등록</button>
     				<button type="button" class="btn btn-outline-dark btn-sm" data-dismiss="modal">닫기</button>
   				</div>
 			</div>
 		</form>
		</div>
	</div>
	
	<!-- 상세정보 모달창 -->
	<%@ include file="detailmodal.jsp" %>
	
	<%@ include file="footer.jsp" %>	
</div>
</fmt:bundle>
<script>
	$(document).ready(function(){
	
		//todoList Search Function
		var form = $('#my-form');
		var searchBtn = $('#search-btn');
		var rows = $('select[name=rows]');
		var status = $('select[name=status]');
		
		console.log(rows.val(), status.val());
		rows.change(function(){
			console.log(rows.val());
			form.submit();
		});
		status.change(function(){
			console.log(status.val());
			console.log(rows.val(), status.val());
			form.submit();
		});
		searchBtn.click(function(){
			form.submit();
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
				todoStatus.text(statusName);
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
			url:"get.hta",
			type:"POST",
			data:{
				'todoNo':todoNo
			},
			success:function(data){
				console.log(data)
				console.log(data.content)
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
	function openTodoFormModal() {
		$("#modal-todo-form").modal('show');
	}
	
	function addTodo() {
		
		var title = $('input[name=title]').val();
		var day = $('input[name=day]').val();
		var content = $('#todo-content').val();
		
		var notitle = $('#fail-todo-notitle');
		var noday = $('#fail-todo-noday');
		var nocontent = $('#fail-todo-nocontent');
		var noserver = $('#fail-todo-noserver');
		
		if(!title){
		
			notitle.css('display', 'block');
			noday.css('display', 'none');
			nocontent.css('display', 'none');
			
			return;
		}
		if(!day){

			notitle.css('display', 'none');
			noday.css('display', 'block');
			nocontent.css('display', 'none');
			return;
		}
		if(!content){
			
			notitle.css('display', 'none');
			noday.css('display', 'none');
			nocontent.css('display', 'block');
			return;
		}
		
		console.log(title, day, content);
		
		$.ajax({
			type:"POST",
			url:"add.hta",
			data:{
				'title':title,
				'day':day,
				'content':content
			},
			success:function(data){
				console.log(data.result);
				if(data.result == "noId"){
					notitle.css('display', 'none');
					noday.css('display', 'none');
					nocontent.css('display', 'none');
					noserver.css('display','block');
					return;
				}
				if(data.result){
					alert('글 작성이 완료되었습니다.');
					location.href = 'todos.hta';
				}
			}
		});
		
	}
</script>
</body>
</html>
