package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "i_i_ne")
@NamedQueries({
        @NamedQuery(name = "getI_i_ne_CountByReport", query = "SELECT COUNT(i) FROM I_i_ne AS i WHERE i.report = :report"),
        @NamedQuery(name = "getI_i_ne_Check_Count", query = "SELECT COUNT(i) FROM I_i_ne AS i WHERE i.report = :report AND i.employee = :employee"),
        @NamedQuery(name = "getI_i_ne", query = "SELECT i FROM I_i_ne AS i WHERE i.report = :report AND i.employee = :employee")
})
@Entity
public class I_i_ne {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "report_id", nullable = false)
    private Report report;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    //    public static long i_i_ne_count(Report report) {
    //        EntityManager em = DBUtil.createEntityManager();
    //        long i_i_ne_count = em.createNamedQuery("getI_i_ne_CountByReport", Long.class)
    //                .setParameter("report", report)
    //                .getSingleResult();
    //        return i_i_ne_count;
}
