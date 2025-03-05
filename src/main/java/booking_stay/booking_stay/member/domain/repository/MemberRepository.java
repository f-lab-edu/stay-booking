package booking_stay.booking_stay.member.domain.repository;

import booking_stay.booking_stay.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findUserByUserId(String userId);

    Boolean existsMemberByUserId(String userId);

}
