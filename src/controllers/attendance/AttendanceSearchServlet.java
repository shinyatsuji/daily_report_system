package controllers.attendance;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
 * Servlet implementation class AttendanceSearchServlet
 */
@WebServlet("/search")
public class AttendanceSearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AttendanceSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/attendance/list.jsp");
        rd.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Employee login_employee = (Employee) request.getSession().getAttribute("login_employee");

        String str = request.getParameter("search_start");
        String str1 = request.getParameter("search_end");

        System.out.println("開始日は：" + str);
        System.out.println("終了日は：" + str1);

        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }

        if (!(login_employee.getAdmin_flag() == 0)) {

            try {

                String code = request.getParameter("employee_code");
                List<Employee> list = em.createNamedQuery("getEmployeeByCode", Employee.class)
                        .setParameter("code", code)
                        .getResultList();

                Employee e = list.get(0);

                System.out.println("Employee:" + e);

                Timestamp begin = new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(str).getTime());
                Timestamp end = new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(str1).getTime());

                System.out.println("開始日は：" + begin);
                System.out.println("終了日は：" + end);

                List<Attendance> lists = em.createNamedQuery("getAttendancesByMonth", Attendance.class)
                        .setParameter("begin", begin)
                        .setParameter("end", end)
                        .setParameter("employee", e)
                        .setFirstResult(15 * (page - 1))
                        .setMaxResults(15)
                        .getResultList();

                long attendanceCount = em.createNamedQuery("getAttendancesCountByMonth", Long.class)
                        .setParameter("begin", begin)
                        .setParameter("end", end)
                        .setParameter("employee", login_employee)
                        .getSingleResult();

                em.close();
                request.setAttribute("lists", lists);
                request.setAttribute("employee", e.getName());
                request.setAttribute("search_start", str);
                request.setAttribute("search_end", str1);
                request.setAttribute("code", code);
                request.setAttribute("attendanceCount", attendanceCount);
                request.setAttribute("page", page);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            try {

                Timestamp begin = new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(str).getTime());
                Timestamp end = new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(str1).getTime());
                List<Attendance> lists = em.createNamedQuery("getAttendancesByMonth", Attendance.class)
                        .setParameter("begin", begin)
                        .setParameter("end", end)
                        .setParameter("employee", login_employee)
                        .setFirstResult(15 * (page - 1))
                        .setMaxResults(15)
                        .getResultList();

                long attendanceCount = em.createNamedQuery("getAttendancesCountByMonth", Long.class)
                        .setParameter("begin", begin)
                        .setParameter("end", end)
                        .setParameter("employee", login_employee)
                        .getSingleResult();
                em.close();
                request.setAttribute("lists", lists);
                request.setAttribute("employee", login_employee.getName());
                request.setAttribute("search_start", str);
                request.setAttribute("search_end", str1);
                request.setAttribute("code", login_employee.getCode());
                request.setAttribute("attendanceCount", attendanceCount);
                request.setAttribute("page", page);

            } catch (ParseException e) {

            }
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/attendance/list.jsp");
        rd.forward(request, response);

    }

}
