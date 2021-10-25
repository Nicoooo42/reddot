package com.reddot.core.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.reddot.core.domain.Thread} entity.
 */
public class ThreadDTO implements Serializable {

    private Long id;

    private String name;

    private BoardDTO board;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BoardDTO getBoard() {
        return board;
    }

    public void setBoard(BoardDTO board) {
        this.board = board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ThreadDTO)) {
            return false;
        }

        ThreadDTO threadDTO = (ThreadDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, threadDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ThreadDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", board=" + getBoard() +
            "}";
    }
}
