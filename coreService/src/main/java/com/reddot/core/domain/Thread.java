package com.reddot.core.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A Thread.
 */
@Entity
@Table(name = "thread")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Thread implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "thread")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "comments", "thread" }, allowSetters = true)
    private Set<Post> posts = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "threads" }, allowSetters = true)
    private Board board;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Thread id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Thread name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Post> getPosts() {
        return this.posts;
    }

    public void setPosts(Set<Post> posts) {
        if (this.posts != null) {
            this.posts.forEach(i -> i.setThread(null));
        }
        if (posts != null) {
            posts.forEach(i -> i.setThread(this));
        }
        this.posts = posts;
    }

    public Thread posts(Set<Post> posts) {
        this.setPosts(posts);
        return this;
    }

    public Thread addPost(Post post) {
        this.posts.add(post);
        post.setThread(this);
        return this;
    }

    public Thread removePost(Post post) {
        this.posts.remove(post);
        post.setThread(null);
        return this;
    }

    public Board getBoard() {
        return this.board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Thread board(Board board) {
        this.setBoard(board);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Thread)) {
            return false;
        }
        return id != null && id.equals(((Thread) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Thread{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
