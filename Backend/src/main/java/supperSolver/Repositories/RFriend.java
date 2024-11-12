package supperSolver.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import supperSolver.Models.MFriend;
import supperSolver.Models.MImage;
import org.springframework.stereotype.Repository;
import supperSolver.Models.MUser;

import java.util.List;

@Repository
public interface RFriend extends JpaRepository<MFriend, Integer>
{
    // Finds all users that are friends with user
    @Query("SELECT user2 FROM MFriend WHERE user1 = :user UNION SELECT user1 FROM MFriend WHERE user2 = :user")
    List<MUser> findFriends(@Param("user") MUser user);
}
