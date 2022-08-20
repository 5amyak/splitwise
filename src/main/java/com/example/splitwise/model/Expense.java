package com.example.splitwise.model;

import com.example.splitwise.dto.SplitType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "expenses",
        indexes = @Index(
                name = "idx_date",
                columnList = "date"
        ))
public class Expense {

    @Id
    @Column(name = "expense_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_group_id", nullable = false)
    private UserGroup userGroup;

    private String title;

    private Double amount;

    @Column(name = "split_type")
    @Enumerated(EnumType.STRING)
    private SplitType splitType;

    private LocalDateTime date;

    @ManyToOne(optional = false)
    @JoinColumn(name = "paid_by_user_id", nullable = false)
    private User paidBy;

    @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL)
    private Set<Transaction> transactionSet;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on")
    private Date createdOn;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_on")
    private Date lastModifiedOn;

}