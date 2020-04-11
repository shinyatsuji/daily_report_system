package controllers.timestamp;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Attendance;
import models.Employee;
import utils.DBUtil;

/**
 * Servlet implementation class WorkFinishServlet
 */
@WebServlet("/finish")
public class WorkFinishServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public WorkFinishServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        Employee login_employee = (Employee) request.getSession().getAttribute("login_employee");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String workdayString = sdf.format(currentTime);
        System.out.println("出勤日は：" + workdayString);

        Timestamp workday = null;
        try {
            workday = new Timestamp(new SimpleDateFormat("yyyy/MM/dd").parse(workdayString).getTime());
        } catch (ParseException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }

        List<Attendance> workdays = em.createNamedQuery("getAttendanceByWorkday", Attendance.class)
                .setParameter("workday", workday)
                .setParameter("employee", login_employee)
                .getResultList();

        System.out.println("AttendanceのList：" + workdays);

        if (workdays.size() > 0) {

            Attendance attendance = workdays.get(0);
            Timestamp finish = attendance.getFinish();

            if (finish != null) {
                return;
            } else {
                attendance.setFinish(currentTime);
                attendance.setWorkday_finish_flag(1);
            }

            em.getTransaction().begin();
            em.persist(attendance);
            em.getTransaction().commit();

            em.close();

            request.getSession().setAttribute("attendance", attendance);

        } else {

            em.close();
            request.getSession().setAttribute("flush", "出勤を先に登録してください");
        }

        response.sendRedirect(request.getContextPath() + "/index.html");

    }

}
