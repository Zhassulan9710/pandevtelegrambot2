package com.Pandev.pandevtelegrambot.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class BotCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(nullable = false, unique = true)
    private String name;

    @Getter
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BotCategory> children = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private BotCategory parent;

    public BotCategory(String name) {
        this.name = name;
    }

    public void addChild(BotCategory child) {
        child.parent = this;
        children.add(child);
    }

    public void removeChild(BotCategory child) {
        children.remove(child);
        child.parent = null;
    }
}
