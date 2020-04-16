package controllers.attendance;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Attendance;
import models.Employee;
import utils.DBUtil;

/**
 * Servlet implementation class AttendanceIndexServlet
 */
@WebServlet("/attendance/index")
public class AttendanceIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AttendanceIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Employee login_employee = (Employee) request.getSession().getAttribute("login_employee");

        //        ページ設定。
        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException e) {
        }

        //     ログインユーザーの情報からAttendance一覧を取得。
        List<Attendance> attendances = em.createNamedQuery("getAttendancesByEmployee", Attendance.class)
                .setParameter("employee", login_employee)
                .setFirstResult(15 * (page - 1))
                .setMaxResults(15)
                .getResultList();

        //      ログインユーザーの情報からAttendance一覧の要素数を取得。
        long attendances_count = (long) em.createNamedQuery("getAttendancesCountByEmployee", Long.class)
                .setParameter("employee", login_employee)
                .getSingleResult();

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime truncatedDays = localDateTime.truncatedTo(ChronoUnit.DAYS);

        Timestamp begin = Timestamp.valueOf(truncatedDays.with(TemporalAdjusters.firstDayOfMonth()));
        Timestamp end = Timestamp.valueOf(truncatedDays.with(TemporalAdjusters.lastDayOfMonth()));

        List<Attendance> AttendancesByMonth = em.createNamedQuery("getAttendancesByMonth", Attendance.class)
                .setParameter("begin", begin)
                .setParameter("end", end)
                .setParameter("employee", login_employee)
                .getResultList();
        System.out.println("４月１日" + begin);
        System.out.println("４月３０日" + end);

        System.out.println("ログインuの勤怠一覧は" + AttendancesByMonth);
        System.out.println("ログインuの勤怠一覧は" + attendances);
        System.out.println("ログインuの勤怠数は" + attendances_count);

        em.close();
        request.setAttribute("attendances_count", attendances_count);
        request.setAttribute("attendances", attendances);
        request.setAttribute("AttendancesByMonth", AttendancesByMonth);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/attendance/index.jsp");
        rd.forward(request, response);
    }

}
