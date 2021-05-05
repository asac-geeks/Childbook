package com.example.finalProject.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class AppUser implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique=true,nullable = false)
	private String userName;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String email;
	@OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	Set<Likes> likes;

	@OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	Set<Share> shares;

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	Set<GroupAttendees> attendees;

	//===> added by salah ================================================================
	@OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	Set<Groups> groups;
	public Set<Groups> getGroups() {
		return groups;
	}
	public void setGroups(Set<Groups> groups) {
		this.groups = groups;
	}
	//===> added by salah ================================================================

	//===> added by Yazan ================================================================
	@OneToMany(mappedBy = "GroupPostUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	Set<GroupPost> groupPosts;
	public Set<GroupPost> getGroupPosts() {
		return this.groupPosts;
	}

	public void setGroupPosts(Set<GroupPost> groupPosts) {
		this.groupPosts = groupPosts;
	}

	//===> added by Yazan ================================================================

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdAt")
	private Date createdAt;

	private String location;

	private LocalDate dateOfBirth;

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public AppUser(String userName, String password, String email, Parent parent ,LocalDate dateOfBirth,String location) {
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.parent = parent;
		this.dateOfBirth = dateOfBirth;
		this.location = location;
	}

	@ManyToOne
	private Parent parent;

	public AppUser() {}

	@OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Post> posts;

	@OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Comment> comments;

	@OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<Event> events;

	@OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<EventAttendees> eventAttendees;

	@OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	Set<UsersFollowers> users;

	@OneToMany(mappedBy = "appUserFollower", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	Set<UsersFollowers> followers;


	@OneToMany(mappedBy = "senderUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	Set<Message> SentFromUserMessage;

	@OneToMany(mappedBy = "toUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	Set<Message> SentToUserMessage;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<GamesApi> FavouriteGames;

	@OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TemporaryComment> temporaryComments;

	@OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TemporaryPost> temporaryPosts;

	@OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TemporaryShare> temporaryShares;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Parent getParent() {
		return parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public Set<Likes> getLikes() {
		return likes;
	}

	public void setLikes(Set<Likes> likes) {
		this.likes = likes;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Set<Share> getShares() {
		return shares;
	}

	public void setShares(Set<Share> shares) {
		this.shares = shares;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public List<EventAttendees> getEventAttendees() {
		return eventAttendees;
	}

	public void setEventAttendees(List<EventAttendees> eventAttendees) {
		this.eventAttendees = eventAttendees;
	}

	public Set<UsersFollowers> getUsers() {
		return users;
	}

	public void setUsers(Set<UsersFollowers> users) {
		this.users = users;
	}

	public Set<UsersFollowers> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<UsersFollowers> followers) {
		this.followers = followers;
	}

	public Set<Message> getSentFromUserMessage() {
		return SentFromUserMessage;
	}

	public void setSentFromUserMessage(Set<Message> sentFromUserMessage) {
		SentFromUserMessage = sentFromUserMessage;
	}

	public Set<Message> getSentToUserMessage() {
		return SentToUserMessage;
	}

	public void setSentToUserMessage(Set<Message> sentToUserMessage) {
		SentToUserMessage = sentToUserMessage;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Set<GroupAttendees> getAttendees() {
		return attendees;
	}

	public void setAttendees(Set<GroupAttendees> attendees) {
		this.attendees = attendees;
	}

	public List<GamesApi> getFavouriteGames() {
		return FavouriteGames;
	}

	public void setFavouriteGames(List<GamesApi> favouriteGames) {
		FavouriteGames = favouriteGames;
	}

	public List<TemporaryComment> getTemporaryComments() {
		return temporaryComments;
	}

	public void setTemporaryComments(List<TemporaryComment> temporaryComments) {
		this.temporaryComments = temporaryComments;
	}

	public List<TemporaryPost> getTemporaryPosts() {
		return temporaryPosts;
	}

	public void setTemporaryPosts(List<TemporaryPost> temporaryPosts) {
		this.temporaryPosts = temporaryPosts;
	}

	public List<TemporaryShare> getTemporaryShares() {
		return temporaryShares;
	}

	public void setTemporaryShares(List<TemporaryShare> temporaryShares) {
		this.temporaryShares = temporaryShares;
	}
}
