package controllers.toppage;

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
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class TopPageIndexServlet
 */
@WebServlet("/index.html")
public class TopPageIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TopPageIndexServlet() {
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

        //        現在時間取得
        LocalDateTime localDateTime = LocalDateTime.now();
        //        時間以下を切り捨て
        LocalDateTime truncatedDays = localDateTime.truncatedTo(ChronoUnit.DAYS);

        LocalDateTime beginningMonth = truncatedDays.with(TemporalAdjusters.firstDayOfMonth());
        //        Timestamp型に変換
        Timestamp workday = Timestamp.valueOf(truncatedDays);

        //        Timestamp（”今日の年月日　00:00:00”)で検索をかける
        List<Attendance> workdays = em.createNamedQuery("getAttendanceByWorkday", Attendance.class)
                .setParameter("workday", workday)
                .setParameter("employee", login_employee)
                .getResultList();

        Attendance attendance = new Attendance();

        //        登録があれば呼び出してリクエストスコープに登録
        if (!(workdays.size() == 0)) {

            attendance = workdays.get(0);
            attendance.setWorkday_begin_flag(1);
            Timestamp finish = attendance.getFinish();

            if (finish != null) {
                attendance.setWorkday_finish_flag(1);
            } else {
                attendance.setWorkday_finish_flag(0);
            }
        }

        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        List<Report> reports = em.createNamedQuery("getMyAllReports", Report.class)
                .setParameter("employee", login_employee)
                .setFirstResult(15 * (page - 1))
                .setMaxResults(15)
                .getResultList();

        Long reports_count = em.createNamedQuery("getMyReportsCount", Long.class)
                .setParameter("employee", login_employee)
                .getSingleResult();

        em.close();

        request.setAttribute("reports", reports);
        request.setAttribute("reports_count", reports_count);
        request.setAttribute("page", page);
        request.setAttribute("attendance", attendance);

        if (request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/topPage/index.jsp");
        rd.forward(request, response);
    }

}
