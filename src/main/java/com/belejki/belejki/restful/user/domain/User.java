package com.belejki.belejki.restful.user.domain;

import com.belejki.belejki.restful.authority.domain.Authority;
import com.belejki.belejki.restful.wish.domain.Wish;
import com.belejki.belejki.restful.friendship.domain.Friendship;
import com.belejki.belejki.restful.recipe.domain.Recipe;
import com.belejki.belejki.restful.reminder.domain.Reminder;
import com.belejki.belejki.restful.shoppingItem.domain.ShoppingItem;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
@Data
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "username", unique = true, nullable = false)
	private String username;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "password", nullable = false)
	private String password;
	@Column(name = "enabled")
	private boolean enabled = true;
	@Column(name = "last_login")
	private LocalDate lastLogin;
	@Column(name = "is_set_for_deletion")
	private boolean setForDeletion;
	@Column(name = "locale")
	private String locale;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Friendship> friendships = new HashSet<>();
	//TODO: MAKE IT Set<Authority>
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<Authority> authorities = new HashSet<>();
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Reminder> reminders = new ArrayList<>();


	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Wish> wishList = new ArrayList<>();


	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ShoppingItem> shoppingItems = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Recipe> recipes = new ArrayList<>();

	@Column(name = "confirmation_token")
	private String confirmationToken;

	@Column(name = "token_expiry")
	private LocalDateTime tokenExpiry;


	public User(String email, String firstName, String lastName, String password) {
		this();
		this.username = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}

	public User(String email, String firstName, String lastName, String password, Set<Authority> authorities) {
		this();
		this.username = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.authorities = authorities;
	}

	public User() {

		this.friendships = new HashSet<>();
		this.authorities = new HashSet<>();
		this.reminders = new ArrayList<>();
		this.wishList = new ArrayList<>();
		this.shoppingItems = new HashSet<>();
		this.recipes = new ArrayList<>();
	}



	public void addWish(Wish wish) {
		this.wishList.add(wish);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	public void addAuthority(Authority authority) {
		this.authorities.add(authority);
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}


	public void addFriendship(Friendship newFriendship) {
		this.friendships.add(newFriendship);
	}


	public void addRecipe(Recipe recipe) {
		this.recipes.add(recipe);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(id, user.id) && Objects.equals(username, user.username);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username);
	}
}

