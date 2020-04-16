package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "attendance")
@NamedQueries({
        @NamedQuery(name = "getAttendanceByWorkday", query = "SELECT a FROM Attendance AS a WHERE a.workday = :workday AND a.employee = :employee"),
        @NamedQuery(name = "getAttendancesByEmployee", query = "SELECT a FROM Attendance AS a WHERE a.employee = :employee"),
        @NamedQuery(name = "getAttendancesCountByEmployee", query = "SELECT Count(a) FROM Attendance AS a WHERE a.employee = :employee"),
        @NamedQuery(name = "getAttendancesByMonth", query = "SELECT a FROM Attendance AS a WHERE a.employee = :employee AND a.begin >= :begin AND a.finish <= :end"),
        @NamedQuery(name = "getAttendancesCountByMonth", query = "SELECT Count(a) FROM Attendance AS a WHERE a.employee = :employee AND a.begin >= :begin AND a.finish <= :end")

})
@NamedNativeQuery(name = "getAttendanceByMonth", query = "SELECT * FROM attendance WHERE DATE_FORMAT(workday,'%Y-%m') = :workday")
@Entity
public class Attendance {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "begin", nullable = true)
    private Timestamp begin;

    @Column(name = "finish", nullable = true)
    private Timestamp finish;

    @Column(name = "workday", nullable = true)
    private Timestamp workday;

    @Transient
    Integer workday_begin_flag;

    @Transient
    Integer workday_finish_flag;

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

    public Timestamp getBegin() {
        return begin;
    }

    public void setBegin(Timestamp begin) {
        this.begin = begin;
    }

    public Timestamp getWorkday() {
        return workday;
    }

    public void setWorkday(Timestamp workday) {
        this.workday = workday;
    }

    public Timestamp getFinish() {
        return finish;
    }

    public void setFinish(Timestamp finish) {
        this.finish = finish;
    }

    @Transient
    public Integer getWorkday_begin_flag() {
        return workday_begin_flag;
    }

    @Transient
    public void setWorkday_begin_flag(Integer workday_regist_flag) {
        this.workday_begin_flag = workday_regist_flag;
    }

    @Transient
    public Integer getWorkday_finish_flag() {
        return workday_finish_flag;
    }

    @Transient
    public void setWorkday_finish_flag(Integer workday_finish_flag) {
        this.workday_finish_flag = workday_finish_flag;
    }
}
