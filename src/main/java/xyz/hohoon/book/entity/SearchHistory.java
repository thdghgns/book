package xyz.hohoon.book.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "search")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchHistory extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "user_name")
    private String username;
    @Column(name = "keyword")
    private String keyword;
}
