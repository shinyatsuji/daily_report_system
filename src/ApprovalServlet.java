
import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ApprovalServlet
 */
@WebServlet("/approval")
public class ApprovalServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApprovalServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Report report = em.find(Report.class, Integer.parseInt(request.getParameter("id")));
        report.setApproval_flag(1);

        em.getTransaction().begin();
        em.persist(report);
        em.getTransaction().commit();

        em.close();

        response.sendRedirect(request.getContextPath() + "/reports/index");

    }

}
