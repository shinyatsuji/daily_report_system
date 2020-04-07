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

@Table(name = "follow")
@NamedQueries({
        @NamedQuery(name = "getAllFollowers", query = "SELECT f FROM Follow AS f WHERE f.follow = :follow"),
        @NamedQuery(name = "getFollowCountCheck", query = "SELECT COUNT(f) FROM Follow AS f WHERE f.follow = :follow AND f.follower = :follower"),
        @NamedQuery(name = "getFollowCheck", query = "SELECT f FROM Follow AS f WHERE f.follow = :follow AND f.follower = :follower")
})
@Entity
public class Follow {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "follow_id", nullable = false)
    private Employee follow;

    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private Employee follower;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getFollow() {
        return follow;
    }

    public void setFollow(Employee follow) {
        this.follow = follow;
    }

    public Employee getFollower() {
        return follower;
    }

    public void setFollower(Employee follower) {
        this.follower = follower;
    }

}