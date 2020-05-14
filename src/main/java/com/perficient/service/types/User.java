package com.perficient.service.types;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.io.Serializable;
import java.util.Date;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User  implements Serializable {

	private static final long serialVersionUID = -4863426630668762683L;


    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    private Long id;

    @Field
    private String username;

    @Field
    private String password;

    @Field
    private Date dateCreated;

    @Field
    private String firstName;

    @Field
    private String lastName;

}
