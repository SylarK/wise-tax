package pt.amado.wisetax.model.enitities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class BillingAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String msisdn;
    private long bucket1;
    private long bucket2;
    private long bucket3;
    private long counterA;
    private long counterB;
    private long counterC;
    private Date lastRequestDate;

    //TODO: implement the "tarifário"

}
