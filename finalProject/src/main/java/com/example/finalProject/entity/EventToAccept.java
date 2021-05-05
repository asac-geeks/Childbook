package com.example.finalProject.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Inheritance
@Entity
public class EventToAccept {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdAt")
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modifiedAt")
    private Date modifiedAt;

    public EventToAccept() {
    }

    @OneToMany(mappedBy = "eventsToAccept", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<ChildEvents> childEvents;

    public Set<ChildEvents> getChildEvents() {
        return childEvents;
    }

    public void setChildEvents(Set<ChildEvents> childEvents) {
        this.childEvents = childEvents;
    }
}
