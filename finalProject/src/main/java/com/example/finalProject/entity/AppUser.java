package com.example.finalProject.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class AppUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique=true)
	private String userName;
	private String password;
	@Column(unique=true)
	private String email;
	@OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	Set<Likes> likes;

	@OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	Set<Share> shares;

	public AppUser(String userName, String password, String email,Parent parent) {
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.parent = parent;
	}

	@ManyToOne
	private Parent parent;

	@ManyToOne
	private UserStorage userStorage;

	public AppUser() {
		
	}

	@OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Post> posts;

	@OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Comment> comments;

	@OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Event> events;

	@OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<EventAttendees> eventAttendees;

	@OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	Set<UsersFollowers> users;

	@OneToMany(mappedBy = "appUserFollower", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	Set<UsersFollowers> followers;

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

	public UserStorage getUserStorage() {
		return userStorage;
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

	public void setUserStorage(UserStorage userStorage) {
		this.userStorage = userStorage;
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
}
