package ftn.isa.team12.pharmacy.domain.common;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class DateRange implements Serializable {
    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "startdate", nullable = false)
    private Date startDate;
    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "enddate", nullable = false)
    private Date endDate;

}
