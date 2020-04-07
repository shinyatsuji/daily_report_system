package controllers.reports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsFollowServlet
 */
@WebServlet("/reports/follow")
public class ReportsFollowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsFollowServlet() {
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

        List<Follow> follows = em.createNamedQuery("getAllFollowers", Follow.class)
                .setParameter("follow", request.getSession().getAttribute("login_employee"))
                .getResultList();

        List<Report> f_Reports = new ArrayList<Report>();

        for (Follow f : follows) {
            List<Report> reports = em.createNamedQuery("getReportsByFollow", Report.class)
                    .setParameter("follow", f.getFollower())
                    .getResultList();
            for (Report r : reports) {
                f_Reports.add(r);
            }
        }
        em.close();
        request.setAttribute("login_employee", login_employee);

        request.setAttribute("f_Reports", f_Reports);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/followReports.jsp");
        rd.forward(request, response);
    }

}
