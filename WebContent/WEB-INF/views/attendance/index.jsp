<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}" />
            </div>
        </c:if>
        <div class="report_menu_wrapper">
            <h2>勤怠 一覧</h2>
         <a href="<c:url value='/search'/>">検索画面へ</a><br><br>

        </div>
        <table id="attendance_list">
            <tbody>
                <tr>
                    <th class="attendance_name">氏名</th>
                    <th class="attendance_workday">日付</th>

                    <th class="attendance_begin">出勤時間</th>
                    <th class="attendance_finish">退勤時間</th>

                </tr>
                <c:forEach var="attendance" items="${AttendancesByMonth}"
                    varStatus="status">
                    <tr class="row${status.count%2}">
                        <td class="attendance_name"><c:out
                                value="${attendance.employee.name}" /></td>
                        <td class="attendance_workday"><fmt:formatDate
                                value='${attendance.workday}' pattern='yyyy-MM-dd' /></td>
                        <td class="attendance_begin"><fmt:formatDate
                                value='${attendance.begin}' pattern='yyyy年MM月dd日 HH時mm分' /></td>
                        <td class="attendance_finish"><fmt:formatDate
                                value='${attendance.finish}' pattern='yyyy年MM月dd日 HH時mm分' /></td>

                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${attendances_count} 件)<br>
            <c:forEach var="i" begin="1" end="${((attendances_count-1)/15)+1}">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
   </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/attendance/index?page=${i}'/>"><c:out
                                value="${i}" /></a>&nbsp;
   </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>



    </c:param>

</c:import>