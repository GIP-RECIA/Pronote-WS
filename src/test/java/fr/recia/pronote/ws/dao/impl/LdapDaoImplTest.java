package fr.recia.pronote.ws.dao.impl;

import fr.recia.pronote.ws.AbstractPronoteWsApplicationTests;
import fr.recia.pronote.ws.config.TestKeysConfig;
import fr.recia.pronote.ws.model.rapprochementsso.Eleve;
import fr.recia.pronote.ws.model.rapprochementsso.Etablissements;
import fr.recia.pronote.ws.model.rapprochementsso.Personnel;
import fr.recia.pronote.ws.model.rapprochementsso.Professeur;
import fr.recia.pronote.ws.model.rapprochementsso.Responsable;
import fr.recia.pronote.ws.service.bean.IIDMapper;
import fr.recia.pronote.ws.service.bean.impl.IDMapperImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.springframework.util.Assert.hasLength;
import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.noNullElements;
import static org.springframework.util.Assert.notEmpty;
import static org.springframework.util.Assert.notNull;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(TestKeysConfig.class)
public class LdapDaoImplTest  extends AbstractPronoteWsApplicationTests {


    @Autowired LdapDaoImpl ldapDao;

    IIDMapper userMapper ;
    IIDMapper structMapper ;

    @Override
    @BeforeAll
    protected void init() throws Exception {
        super.init();
        userMapper  = new IDMapperImpl();
        structMapper = new IDMapperImpl();
    }

    @Test
    void allPersonNoNullNorEmptyValues(){
        // order is important?
        findAllProfesseursNoNullNorEmptyValues();
        findAllPersonnelsNoNullNorEmptyValues();
        findAllResponsablesNoNullNorEmptyValues();
        findAllElevesNoNullNorEmptyValues();
    }


    final static String MESSAGE_LIST_NULL = "%ss list should not be null";
    final static String MESSAGE_LIST_EMPTY = "%ss list should not be empty";
    final static String MESSAGE_ID_PARTENAIRE_NULL = "%s at index %s should not have null ID_Partenaire";
    final static String MESSAGE_ID_PARTENAIRE_EMPTY = "%s at index %s should not have empty ID_Partenaire";
    final static String MESSAGE_ID_ENT_NULL = "%s with ID_Partenaire %s should not have null ID ENT";
    final static String MESSAGE_PRENOM_NULL = "%s with ID_Partenaire %s should not have null Prenom";
    final static String MESSAGE_PRENOM_EMPTY = "%s with ID_Partenaire %s should not have empty Prenom";
    final static String MESSAGE_NOM_NULL = "%s with ID_Partenaire %s should not have null Nom";
    final static String MESSAGE_NOM_EMPTY = "%s with ID_Partenaire %s should not have empty Nom";
    final static String MESSAGE_DATE_NAISSANCE_NULL = "%s with ID_Partenaire %s should not have null Date Naissance";
    final static String MESSAGE_DATE_NAISSANCE_EMPTY = "%s with ID_Partenaire %s should not have empty Date Naissance";
    final static String MESSAGE_RESPONSABLE_LIST_EMPTY = "Responsable list of [Eleve with ID Partenaire %s] should not contains null elements";
    final static String MESSAGE_RESPONSABLE_ELEVE_ID_ENT_EMPTY = "Responsable list of [Eleve with ID Partenaire %s] should have null ID Ent";

    void findAllProfesseursNoNullNorEmptyValues(){

        String personType = "Professeur";

        List<Professeur> professeurs = ldapDao.findAllProfesseurs(uaiTest, userMapper);
        notNull(professeurs, String.format(MESSAGE_LIST_NULL, personType));
        notEmpty(professeurs, String.format(MESSAGE_LIST_EMPTY, personType));

        for(int i = 0; i < professeurs.size(); i++){
            Professeur professeur = professeurs.get(i);

            // ID Partenaire
            notNull(professeur.getIdPartenaire(), String.format(MESSAGE_ID_PARTENAIRE_NULL, personType, i));
            hasLength(professeur.getIdPartenaire().trim(), String.format(MESSAGE_ID_PARTENAIRE_EMPTY, personType, i));

            String idPartenaire = professeur.getIdPartenaire();

            // ID Ent
            notNull(professeur.getIdent(), String.format(MESSAGE_ID_ENT_NULL, personType, idPartenaire));

            // Prenom
            notNull(professeur.getPrenom(), String.format(MESSAGE_PRENOM_NULL, personType, idPartenaire));
            hasLength(professeur.getPrenom().trim(), String.format(MESSAGE_PRENOM_EMPTY, personType, idPartenaire));

            // Nom
            notNull(professeur.getNom(), String.format(MESSAGE_NOM_NULL, personType, idPartenaire));
            hasLength(professeur.getNom().trim(), String.format(MESSAGE_NOM_EMPTY, personType, idPartenaire));

//            // Date Naissance
//            notNull(professeur.getDateNaissance(), String.format(MESSAGE_DATE_NAISSANCE_NULL, personType, idPartenaire));
        }

    }


    void findAllPersonnelsNoNullNorEmptyValues(){

        String personType = "Personnel";

        List<Personnel> personnels = ldapDao.findAllPersonnels(uaiTest, userMapper);
        notNull(personnels, String.format(MESSAGE_LIST_NULL, personType));
        notEmpty(personnels, String.format(MESSAGE_LIST_EMPTY, personType));

        for(int i = 0; i < personnels.size(); i++){
            Personnel personnel = personnels.get(i);

            // ID Partenaire
            notNull(personnel.getIdPartenaire(), String.format(MESSAGE_ID_PARTENAIRE_NULL, personType, i));
            hasLength(personnel.getIdPartenaire().trim(), String.format(MESSAGE_ID_PARTENAIRE_EMPTY, personType, i));

            String idPartenaire = personnel.getIdPartenaire();

            // ID Ent
            notNull(personnel.getIdent(), String.format(MESSAGE_ID_ENT_NULL, personType, idPartenaire));

            // Prenom
            notNull(personnel.getPrenom(), String.format(MESSAGE_PRENOM_NULL, personType, idPartenaire));
            hasLength(personnel.getPrenom().trim(), String.format(MESSAGE_PRENOM_EMPTY, personType, idPartenaire));

            // Nom
            notNull(personnel.getNom(), String.format(MESSAGE_NOM_NULL, personType, idPartenaire));
            hasLength(personnel.getNom().trim(), String.format(MESSAGE_NOM_EMPTY, personType, idPartenaire));

        }

    }

    void findAllResponsablesNoNullNorEmptyValues(){

        String personType = "Responsable";

        List<Responsable> responsables = ldapDao.finadAllResponsables(uaiTest, userMapper);
        notNull(responsables, String.format(MESSAGE_LIST_NULL, personType));
        notEmpty(responsables, String.format(MESSAGE_LIST_EMPTY, personType));

        for(int i = 0; i < responsables.size(); i++){
            Responsable responsable = responsables.get(i);

            // ID Partenaire
            notNull(responsable.getIdPartenaire(), String.format(MESSAGE_ID_PARTENAIRE_NULL, personType, i));
            hasLength(responsable.getIdPartenaire().trim(), String.format(MESSAGE_ID_PARTENAIRE_EMPTY, personType, i));

            String idPartenaire = responsable.getIdPartenaire();

            // ID Ent
            notNull(responsable.getIdent(), String.format(MESSAGE_ID_ENT_NULL, personType, idPartenaire));

            // Prenom
            notNull(responsable.getPrenom(), String.format(MESSAGE_PRENOM_NULL, personType, idPartenaire));
            hasLength(responsable.getPrenom().trim(), String.format(MESSAGE_PRENOM_EMPTY, personType, idPartenaire));

            // Nom
            notNull(responsable.getNom(), String.format(MESSAGE_NOM_NULL, personType, idPartenaire));
            hasLength(responsable.getNom().trim(), String.format(MESSAGE_NOM_EMPTY, personType, idPartenaire));

        }

    }


    void findAllElevesNoNullNorEmptyValues(){
        String personType = "Eleve";
        List<Eleve> eleves = ldapDao.findAllEleves(uaiTest, userMapper);
        notNull(eleves, String.format(MESSAGE_LIST_NULL, personType));
        notEmpty(eleves, String.format(MESSAGE_LIST_EMPTY, personType));

        for(int i = 0; i < eleves.size(); i++){
            Eleve eleve = eleves.get(i);

            // ID Partenaire
            notNull(eleve.getIdPartenaire(), String.format(MESSAGE_ID_PARTENAIRE_NULL, personType, i));
            hasLength(eleve.getIdPartenaire().trim(), String.format(MESSAGE_ID_PARTENAIRE_EMPTY, personType, i));

            String idPartenaire = eleve.getIdPartenaire();

            // ID Ent
            notNull(eleve.getIdent(), String.format(MESSAGE_ID_ENT_NULL, personType, idPartenaire));

            // Prenom
            notNull(eleve.getPrenom(), String.format(MESSAGE_PRENOM_NULL, personType, idPartenaire));
            hasLength(eleve.getPrenom().trim(), String.format(MESSAGE_PRENOM_EMPTY, personType, idPartenaire));

            // Nom
            notNull(eleve.getNom(), String.format(MESSAGE_NOM_NULL, personType, idPartenaire));
            hasLength(eleve.getNom().trim(), String.format(MESSAGE_NOM_EMPTY, personType, idPartenaire));

            // Date Naissance
            notNull(eleve.getDateNaissance(), String.format(MESSAGE_DATE_NAISSANCE_NULL, personType, idPartenaire));
            hasLength(eleve.getDateNaissance().trim(), String.format(MESSAGE_DATE_NAISSANCE_EMPTY, personType, idPartenaire));

            // Responsables
            List<Eleve.Responsable> responsables = eleve.getResponsable();
            noNullElements(responsables, String.format(MESSAGE_RESPONSABLE_LIST_EMPTY, idPartenaire));
            for(Eleve.Responsable responsable : responsables){
                notNull(responsable.getIdent(), String.format(MESSAGE_RESPONSABLE_ELEVE_ID_ENT_EMPTY, idPartenaire));
               isTrue(responsable.getIdent() != -1, "");
            }
        }
    }
}
