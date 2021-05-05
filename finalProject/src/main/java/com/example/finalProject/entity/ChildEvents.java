package com.example.finalProject.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ChildEvents {
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

    @ManyToOne
    @JoinColumn(name = "parent_id")
    Parent parent;

    @ManyToOne
    @JoinColumn(name = "eventToAccept_id")
    EventToAccept eventsToAccept;

    public Boolean isSeen;


    public ChildEvents(Parent parent, EventToAccept eventToAccept) {
        this.parent = parent;
        this.eventsToAccept = eventToAccept;
    }

    public ChildEvents() {

    }

    public EventToAccept getEventsToAccept() {
        return eventsToAccept;
    }

    public void setEventsToAccept(EventToAccept eventsToAccept) {
        this.eventsToAccept = eventsToAccept;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public Boolean getSeen() {
        return isSeen;
    }

    public void setSeen(Boolean seen) {
        isSeen = seen;
    }
}
