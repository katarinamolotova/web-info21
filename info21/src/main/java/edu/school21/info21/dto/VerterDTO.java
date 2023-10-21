package edu.school21.info21.dto;

import java.sql.Time;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VerterDTO extends AbstractDTO {

    private long id;

    private int checkId;

    private Object state;

    private Time verterTime;

}
