package supperSolver.Models;


import jakarta.persistence.*;

@Entity
@Table(name = "tbl_friend")
public class MFriend
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    //Host user?
    @ManyToOne
    @JoinColumn(name = "user1")
    private MUser user1;

    //Person they added?
    @ManyToOne
    @JoinColumn(name = "user2")
    private MUser user2;

    public MFriend() { }

    public MFriend(MUser user1, MUser user2)
    {
        this.user1 = user1;
        this.user2 = user2;
    }

    public int getID() { return ID; }
    public void setID(int ID) { this.ID = ID; }

    public MUser getUser1() { return user1; }
    public void setUser1(MUser user1) { this.user1 = user1; }

    public MUser getUser2() { return user2; }
    public void setUser2(MUser user2) { this.user2 = user2; }
}
