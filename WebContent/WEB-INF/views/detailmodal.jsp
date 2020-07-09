<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<!-- 상세정보 모달창 -->
	<div class="modal" id="modal-todo-detail">
		<div class="modal-dialog modal-lg">
 			<div class="modal-content">
   				<div class="modal-header">
     				<h4 class="modal-title"><fmt:message key="button.label.detail"/> </h4>
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
     									<td id="todo-username"></td>
     									<th>등록일</th>
     									<td id="todo-createdate"></td>
     								</tr>
     								<tr>
     									<th>상태</th>
     									<td id="todo-status"></td>
     									<th>예정일</th>
     									<td id="todo-day"></td>
     								</tr>
     								<tr>
     									<th style="vertical-align: middle;">내용</th>
     									<td colspan="3"><small><span id="todo-content-detail"></span></small></td>
     								</tr>
     							</tbody>
     						</table>
     						<input type="hidden" name="todoNo" id="todo-no" value=""/>
     					</div>
     				</div>
   				</div>
   				<div class="modal-footer">
   				<div id="btn-todo-modify" style="display: none;">
     				<button type="button" class="btn btn-success btn-sm" id="detail-btn-process-end">처리완료</button>
     				<button type="button" class="btn btn-info btn-sm" id="detail-btn-process-progress">처리중</button>
     				<button type="button" class="btn btn-secondary btn-sm" id="detail-btn-process-delay">보류</button>
     				<button type="button" class="btn btn-danger btn-sm" id="detail-btn-process-cancle">취소</button>
   				</div>
     				<button type="button" onclick="history.go(0)" class="btn btn-outline-dark btn-sm" data-dismiss="modal">닫기</button>
   				</div>
 			</div>
		</div>
	</div>