package ftn.isa.team12.pharmacy.domain.common;

import ftn.isa.team12.pharmacy.domain.enums.UserCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "loyalty_program")
@Getter
@Setter
@NoArgsConstructor
public class LoyaltyProgram {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false, unique = true)
    public UUID id;

    @Column(name = "min_regular", nullable = false)
    public int minRegular;

    @Column(name = "min_silver", nullable = false)
    public int minSilver;

    @Column(name = "min_golden", nullable = false)
    public int minGold;

    @Column(name = "points_per_examination", nullable = false)
    public int pointsPerExamination;

    @Column(name = "points_per_counseling", nullable = false)
    public int pointsPerCounseling;

    @Column(name = "discount_for_regular", nullable = false)
    public int discountForRegular;

    @Column(name = "discount_for_silver", nullable = false)
    public int discountForSilver;

    @Column(name = "discount_for_gold", nullable = false)
    public int discountForGold;

    @Version
    private Integer version;


    public UserCategory getCategory(int points) {
        if (points < minRegular) {
            return UserCategory.no_category;
        }
        else if ( points < minSilver ) {
            return  UserCategory.bronze;
        }
        else if ( points < minGold ) {
            return UserCategory.silver;
        }
        else {
            return UserCategory.gold;
        }
    }
    public int getDiscountByCategory(UserCategory uc) {
        if( uc == UserCategory.no_category) {
            return  0;
        } else if( uc == UserCategory.bronze) {
            return discountForRegular;
        } else if(uc == UserCategory.silver) {
            return discountForSilver;
        } else if(uc == UserCategory.gold) {
            return  discountForGold;
        }
        return 0;
    }
}
