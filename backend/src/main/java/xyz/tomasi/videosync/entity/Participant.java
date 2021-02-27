package xyz.tomasi.videosync.entity;

import java.time.ZonedDateTime;

import org.springframework.data.annotation.Id;

public class Participant {
    public Participant(long id, String name, ZonedDateTime createdAt) {
        this.id = id;
        this.setName(name);
        this.setCreatedAt(createdAt);
    }

    @Id
    private long id;
    private String name;
    private ZonedDateTime createdAt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
