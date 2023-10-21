package edu.school21.info21.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class P2pDTO extends AbstractDTO {

    private long id;

    private int checkId;

    private String checkingPeer;

    private Object state;

    private Time checkTime;

}
