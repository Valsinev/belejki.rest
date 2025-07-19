package com.belejki.belejki.restful.friendship.repository;

import com.belejki.belejki.restful.friendship.domain.Friendship;
import com.belejki.belejki.restful.user.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    List<Friendship> findByUser_Username(String username);

    List<Friendship> findByUser(User user);

    Friendship findByFriend_Username(String username);

    Optional<Friendship> findByUser_UsernameAndFriend_Username(String username, @NotBlank(message = "Username cannot be empty.") @Pattern(regexp = "((?!\\.)[\\w\\-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$", message = "Username must be in valid email format.") String friendUsername);

    Page<Friendship> findAllByFriend_Username(String username, Pageable pageable);
    List<Friendship> findAllByFriend_Username(String username);

    Page<Friendship> findAllByUser_Username(String username, Pageable pageable);
    Page<Friendship> findAllByUser_FirstName(String username, Pageable pageable);

    Page<Friendship> findAllByUser_UsernameAndFriend_firstNameContaining(String username, String friendFirstName, Pageable pageable);

    void deleteByIdAndUser_Username(Long id, String username);

    void deleteAllByFriend_Username(String friendUsername);

    void deleteAllByUser_Username(String username);

	void deleteAllByUser_Id(Long id);

    void deleteAllByFriend_Id(Long id);

}
