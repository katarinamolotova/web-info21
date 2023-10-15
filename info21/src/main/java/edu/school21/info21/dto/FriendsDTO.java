package edu.school21.info21.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.school21.info21.entities.FriendsEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FriendsDTO extends AbstractDTO {

    private long id;

    private String peer1;

    private String peer2;

}
