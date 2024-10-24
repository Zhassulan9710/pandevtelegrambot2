package com.Pandev.pandevtelegrambot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class BotCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<BotCategory> children = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parend_id")
    private BotCategory parent;

    public BotCategory(Long id, String name, List<BotCategory> children, BotCategory parent) {
        this.id = id;
        this.name = name;
        this.children = children;
        this.parent = parent;
    }

    public BotCategory() {

    }
}
