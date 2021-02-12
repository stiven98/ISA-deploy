package ftn.isa.team12.pharmacy.domain.common;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalTime;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class TimeRange implements Serializable {
    @Basic
    @Column(name = "starttime", nullable = false)
    private LocalTime startTime;
    @Basic
    @Column(name = "endtime", nullable = false)
    private LocalTime endTime;
}
