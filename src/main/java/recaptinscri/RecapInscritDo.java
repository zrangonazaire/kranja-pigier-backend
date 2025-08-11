package recaptinscri;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
@Entity
public class RecapInscritDo {
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String codeClasse;
    private String moisAnnee;
    private Integer nombreInscrits;
    private String anneeAcademique;
}
