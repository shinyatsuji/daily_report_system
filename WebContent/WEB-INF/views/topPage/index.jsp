<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="../layout/app.jsp">
<c:param name="content">
<c:if test="${flush !=null}">
<div id="flush_success">
<c:out value="${flush}"/>
</div>

</c:if>
<h2>日報管理システムへようこそ</h2>
<h3>【タイムカード登録】</h3>
<br>
<c:choose>
<c:when test = "${!(attendance.workday_begin_flag == 1)}">
<a href="<c:url value='/begin'/>">出勤する</a>&nbsp;
</c:when>
<c:otherwise>
-出勤済-
</c:otherwise>
</c:choose>
<c:choose>
<c:when test ="${!(attendance.workday_finish_flag == 1)}">
<a href="<c:url value='/finish'/>">退勤する</a>&nbsp;
</c:when>
<c:otherwise>
-退勤済-
</c:otherwise>
</c:choose>
<br><br>
<table>
<thead>
<tr>
 <th>出勤時間</th>
 <th>退勤時間</th>
</tr>
</thead>
<tbody>
<tr>
<td>
<fmt:formatDate value="${attendance.begin}" pattern="yyyy年MM月dd日 HH時mm分"/>
</td>
<td>
<fmt:formatDate value="${attendance.finish}" pattern="yyyy年MM月dd日　HH時mm分"/>
</td>
</tr>
</tbody>
</table>
<br><br>
<h3>【自分の日報　一覧】</h3>
 <table id="report_list">
 <tbody>
 <tr>
 <th class="report_name">氏名</th>
 <th class="report_date">日付</th>
 <th class="report_title">タイトル</th>
 <th class="report_action">操作</th>
 </tr>
 <c:forEach var="report" items="${reports}" varStatus="status">
 <tr class="row${status.count % 2}">
 <td class="report_name"><c:out value="${report.employee.name}"/></td>
  <td><fmt:formatDate value="${report.report_date}" pattern="yyyy-MM-dd"/></td>
  <td><c:out value="${report.title}"/></td>
  <td><a href="<c:url value='/reports/show?id=${report.id}'/>">詳細を見る</a></td>
 </tr>
 </c:forEach>
 </tbody>
 </table>

 <div id="pagination">
 (全　${reports_count}　件) <br>
 <c:forEach var="i" begin="1" end="${((reports_count-1)/15)+1}" step="1">
 <c:choose>
 <c:when test="${i == page}">
 <c:out value="${i}"/>
 </c:when>
 <c:otherwise>
 <a href="<c:url value='/?page=${i}'/>"><c:out value="${i}"/></a>&nbsp;
 </c:otherwise>
 </c:choose>
 </c:forEach>
 </div>
 <p><a href="<c:url value='/reports/new'/>">新規日報の登録</a></p>
</c:param>
</c:import>