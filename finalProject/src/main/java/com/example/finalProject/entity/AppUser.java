package com.example.finalProject.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class AppUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique=true,nullable = false)
	private String userName;
	@Column(nullable = false)
	private String password;
	@Column(unique=true,nullable = false)
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

	public AppUser() {
		
	}

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
}
