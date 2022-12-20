package com.solicionio.essential.models.data;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Data
@Table(name = "accounts")
@JsonIgnoreProperties
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

}
