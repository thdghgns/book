package xyz.hohoon.book.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "search_count")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchCount extends BaseEntity {
    @Id
    private String keyword;
    @Column(name = "count")
    private int count;
}
