package edu.school21.info21.dto;

import java.sql.Date;
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
public class TimeTrackingDTO extends AbstractDTO {

    private long id;

    private String peer;

    private Date visitDate;

    private Time visitTime;

    private int state;
}
