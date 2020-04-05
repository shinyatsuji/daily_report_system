package controllers.i_i_ne;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.I_i_ne;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class I_i_ne_release
 */
@WebServlet("/i_i_ne_release")
public class I_i_ne_release extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public I_i_ne_release() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));
        Employee e = (Employee) request.getSession().getAttribute("login_employee");
        I_i_ne i = (I_i_ne) em.createNamedQuery("getI_i_ne", I_i_ne.class)
                .setParameter("report", r)
                .setParameter("employee", e)
                .getSingleResult();

        em.getTransaction().begin();
        em.remove(i);
        em.getTransaction().commit();

        long i_i_ne_count = (long) em.createNamedQuery("getI_i_ne_CountByReport", Long.class)
                .setParameter("report", r)
                .getSingleResult();

        long i_i_ne_check = (long) em.createNamedQuery("getI_i_ne_Check_Count", Long.class)
                .setParameter("report", r)
                .setParameter("employee", e)
                .getSingleResult();

        em.close();

        request.setAttribute("i_i_ne_count", i_i_ne_count);
        request.setAttribute("report", r);
        request.setAttribute("_token", request.getSession().getId());
        request.setAttribute("i_i_ne_check", i_i_ne_check);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
        rd.forward(request, response);
    }

}
