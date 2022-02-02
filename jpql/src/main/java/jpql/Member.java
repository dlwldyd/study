package jpql;

import javax.persistence.*;

@Entity
//어플리케이션 로딩 시점에 쿼리를 파싱해서 sql 로 변환해둠
@NamedQueries(
        {@NamedQuery(
                name = "Member.findByUsername",
                query = "select m from Member m where m.username = :username"
        ),
        @NamedQuery(
                name = "Member.findTeamByUsername",
                query = "select m.team from Member m join m.team where m.username = :username"
        )}
)
public class Member {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @Enumerated(value = EnumType.STRING)
    private MemberType type;

    public void changeTeam(Team team) {
        this.team.getMembers().remove(this);
        setTeam(team);
        team.getMembers().add(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public MemberType getType() {
        return type;
    }

    public void setType(MemberType type) {
        this.type = type;
    }
}
