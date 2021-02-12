package ftn.isa.team12.pharmacy.config;
import ftn.isa.team12.pharmacy.security.TokenUtils;
import ftn.isa.team12.pharmacy.security.auth.RestAuthenticationEntryPoint;
import ftn.isa.team12.pharmacy.security.auth.TokenAuthenticationFilter;
import ftn.isa.team12.pharmacy.service.UserService;
import ftn.isa.team12.pharmacy.validation.CommonValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter  {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private UserService userService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    public CommonValidation commonValidation;


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }



    @Autowired
    private TokenUtils tokenUtils;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // komunikacija izmedju klijenta i servera je stateless posto je u pitanju REST aplikacija
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                // sve neautentifikovane zahteve obradi uniformno i posalji 401 gresku
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()

                // svim korisnicima dopusti da pristupe putanjama /auth/**, (/h2-console/** ako se koristi H2 baza) i /api/foo
                .authorizeRequests().antMatchers("/auth/**").permitAll().antMatchers("/h2-console/**")
                .permitAll().antMatchers("/api/country/all").permitAll()
                .antMatchers("/api/city/all").permitAll()
                .antMatchers("/api/pharmacy/**").permitAll()
                .antMatchers("/api/medicalStuff/**").permitAll()
                .antMatchers("/api/drugInPharmacy/**").permitAll()
                .antMatchers("/api/user/all").permitAll()
                .antMatchers("/api/user/patient/add").permitAll()
                .antMatchers("/api/user/getById/{id}").permitAll()
                .antMatchers("/api/city/byCountry").permitAll()
                .antMatchers("/api/patient/**").permitAll()
                .antMatchers("/api/patient/accountCategory/{email}").permitAll()
                .antMatchers("/api/drug/all").permitAll()
                .antMatchers("/api/patient/add").permitAll()
                .antMatchers("/api/drug/all").permitAll()
                .antMatchers("/api/drug/allDrugs").permitAll()
                .antMatchers("/api/drug/findByIds").permitAll()
                .antMatchers("/api/drug/name/{name}").permitAll()
                .antMatchers("/api/drugOrder/createOrder").permitAll()
                .antMatchers("/api/patient/sendEmail").permitAll()
                .antMatchers("/api/user/activateAccount/{id}").permitAll()
                .antMatchers("/api/drugReservation/**").permitAll()
                .antMatchers("/api/systemAdministrator/add").permitAll()
                .antMatchers("/api/phadmin/add/{id}").permitAll()
                .antMatchers("/api/erecepie/**").permitAll()
                .antMatchers("/api/drugPrice/**").permitAll()
                .antMatchers("/api/pharmacyMarks/**").permitAll()
                .antMatchers("/api/drugMarks/**").permitAll()
                .antMatchers("/api/medicalStuffMarks/**").permitAll()
                .antMatchers("/api/dermatologist/**").permitAll()
                .antMatchers("/api/examination/**").permitAll()
                .antMatchers("/api/locationMap/get/{name}").permitAll()









                // za svaki drugi zahtev korisnik mora biti autentifikovan
                .anyRequest().authenticated().and()
                // za development svrhe ukljuci konfiguraciju za CORS iz WebConfig klase
                .cors().and()
                .addFilterBefore(new TokenAuthenticationFilter(tokenUtils, userService),
                BasicAuthenticationFilter.class);

        // zbog jednostavnosti primera
        http.csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // TokenAuthenticationFilter ce ignorisati sve ispod navedene putanje
        web.ignoring().antMatchers(HttpMethod.POST, "/auth/login");
        web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html",
                "/**/*.css", "/**/*.js","/api/city/all", "/**/*.png","/**/*.jpg", "/**/*.gif");
    }
}
