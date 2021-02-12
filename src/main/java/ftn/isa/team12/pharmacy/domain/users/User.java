package ftn.isa.team12.pharmacy.domain.users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import ftn.isa.team12.pharmacy.domain.common.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "USERS")
public abstract class User implements UserDetails {

   @Id
   @GeneratedValue(generator = "uuid2")
   @GenericGenerator(name = "uuid2", strategy = "uuid2")
   @Column(name = "user_id", nullable = false, unique = true)
   private UUID userId;

   @Embedded
   private LoginInfo loginInfo;

   @OneToOne
   @JoinColumn(name = "location_id", referencedColumnName = "location_id", nullable = false, unique = false)
   private Location location;

   @Embedded
   private AccountInfo accountInfo;


   @ManyToMany(fetch = FetchType.EAGER)
   @JoinTable(name = "user_authority",
           joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
           inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
   private List<Authority> authorities = new ArrayList<Authority>() {
   };

   public void setUsername(String username) {
      this.loginInfo.setEmail(username);
   }

   public void setPassword(String password) {
      Timestamp now = new Timestamp(new Date().getTime());
      this.loginInfo.setLastPasswordResetDate(now);
      this.loginInfo.setPassword(password);
   }

   public Timestamp getLastPasswordResetDate() {
      return loginInfo.getLastPasswordResetDate();
   }

   public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
      this.loginInfo.setLastPasswordResetDate(lastPasswordResetDate);
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return this.authorities;
   }

   @Override
   public String getPassword() {
      return this.loginInfo.getPassword();
   }

   @Override
   public String getUsername() {
      return this.loginInfo.getEmail();
   }

   @JsonIgnore
   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @JsonIgnore
   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   @JsonIgnore
   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      return accountInfo.isActive();
   }

   @Version
   private Long version;

}
