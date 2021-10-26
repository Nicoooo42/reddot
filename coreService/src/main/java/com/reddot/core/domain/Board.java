package com.reddot.core.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A Board.
 */
@Entity
@Table(name = "board")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Board implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "board")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "posts", "board" }, allowSetters = true)
    private Set<Thread> threads = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Board id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Board name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Thread> getThreads() {
        return this.threads;
    }

    public void setThreads(Set<Thread> threads) {
        if (this.threads != null) {
            this.threads.forEach(i -> i.setBoard(null));
        }
        if (threads != null) {
            threads.forEach(i -> i.setBoard(this));
        }
        this.threads = threads;
    }

    public Board threads(Set<Thread> threads) {
        this.setThreads(threads);
        return this;
    }

    public Board addThread(Thread thread) {
        this.threads.add(thread);
        thread.setBoard(this);
        return this;
    }

    public Board removeThread(Thread thread) {
        this.threads.remove(thread);
        thread.setBoard(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Board)) {
            return false;
        }
        return id != null && id.equals(((Board) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Board{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
