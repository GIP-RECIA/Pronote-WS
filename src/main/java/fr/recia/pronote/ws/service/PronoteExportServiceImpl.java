/**
 * Copyright (C) 2020 GIP-RECIA https://www.recia.fr/
 * @Author (C) 2020 Julien Gribonvald <julien.gribonvald@recia.fr>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *                 http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.recia.pronote.ws.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.zip.Deflater;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import fr.recia.pronote.ws.config.bean.AppIndexEducationProperties;
import fr.recia.pronote.ws.dao.ILdapDao;
import fr.recia.pronote.ws.model.conteneurimportchiffre.ImportChiffre;
import fr.recia.pronote.ws.model.rapprochementsso.Etablissement;
import fr.recia.pronote.ws.model.rapprochementsso.Etablissements;
import fr.recia.pronote.ws.model.rapprochementsso.EtablissementsGeres;
import fr.recia.pronote.ws.model.rapprochementsso.Nomenclatures;
import fr.recia.pronote.ws.model.rapprochementsso.PartenaireIndex;
import fr.recia.pronote.ws.service.bean.Civilite;
import fr.recia.pronote.ws.service.bean.IIDMapper;
import fr.recia.pronote.ws.service.bean.impl.IDMapperImpl;
import fr.recia.pronote.ws.service.util.XmlValidatorImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

@Service
@Slf4j
public class PronoteExportServiceImpl implements PronoteExportService {

    /** 128 bits /8 => 16 bytes */
    final static int BYTE_SIZE = 16;

    @Autowired
    private ILdapDao ldapDao;

    @Autowired
    private AppIndexEducationProperties indexEducationProperties;

    @Autowired
    private PublicKey publicKey;

    @Autowired
    private File debugDataPath;

    @Autowired
    private File rapprochementSSOXSD;

    private Nomenclatures nomenclatures;

    private XmlValidatorImpl xmlValidator;

    @PostConstruct
    private void setUp() throws SAXException {
        nomenclatures = new Nomenclatures();
        nomenclatures.setCivilites(Civilite.getNomenclature());
        xmlValidator = new XmlValidatorImpl(rapprochementSSOXSD);
    }

    @Override
    public ImportChiffre getPronoteExport(final String idEtablissement) throws IOException, NoSuchAlgorithmException, SAXException {
        PartenaireIndex partenaireIndex = new PartenaireIndex();
        partenaireIndex.setPartenaire(indexEducationProperties.getPartenaireId());
        partenaireIndex.setDate(ZonedDateTime.now(ZoneId.systemDefault()));
        partenaireIndex.setProtocoleDelegationAuthentification(indexEducationProperties.getProtocol());
        partenaireIndex.setNomenclatures(nomenclatures);

        setContentInfos(partenaireIndex, idEtablissement);

        // set XmlMapper to transform object to XML
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JaxbAnnotationModule());
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true );
        xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true );

        final String xmlContent = xmlMapper.writeValueAsString(partenaireIndex);

        // log content
        if (log.isDebugEnabled()) {
            logIntoFileXmlContent(xmlContent, idEtablissement);
        }
        log.trace("Contenu en clair {}", xmlContent);

        // validating xml
        xmlValidator.validate(xmlContent);


        // Instanciate ImportChiffre for Pronote
        ImportChiffre importChiffre = new ImportChiffre();
        // encrypt content and provide elements
        encrypt(importChiffre, xmlContent);
        importChiffre.setPartenaire(indexEducationProperties.getPartenaireName());
        importChiffre.setDateExport(partenaireIndex.getDate());
        importChiffre.setDescription(indexEducationProperties.getDescription());
        importChiffre.setUai(idEtablissement);
        importChiffre.setVersion(indexEducationProperties.getVersionConteneur());
        return importChiffre;
    }

    private void setContentInfos(PartenaireIndex partenaireIndex, final String idEtablissement) {
        IIDMapper userMapper = new IDMapperImpl();
        IIDMapper structMapper = new IDMapperImpl();

        partenaireIndex.setEtablissements(getEtablissements(idEtablissement, structMapper));

        EtablissementsGeres etablissementsGeres = new EtablissementsGeres();
        etablissementsGeres.getEtablissements().add(
                new EtablissementsGeres.Etablissement(
                        partenaireIndex.getEtablissements().getEtablissements().get(0).getIdent()));
        partenaireIndex.setEtablissementsGeres(etablissementsGeres);


        partenaireIndex.setProfesseurs(ldapDao.findAllProfesseurs(idEtablissement, userMapper));
        partenaireIndex.setPersonnels(ldapDao.findAllPersonnels(idEtablissement, userMapper));
        partenaireIndex.setResponsables(ldapDao.finadAllResponsables(idEtablissement, userMapper));
        partenaireIndex.setEleves(ldapDao.findAllEleves(idEtablissement, userMapper));
    }

    private Etablissements getEtablissements(final String idEtablissement, final IIDMapper structMapper) {
        Etablissements etablissements = new Etablissements();
        Etablissement etab = ldapDao.findOneEtablissementById(idEtablissement, structMapper);
        etablissements.getEtablissements().add(etab);

        return etablissements;
    }

    private void logIntoFileXmlContent(final String xmlContent, final String idEtablissement){
        final String fileLog = String.format("/ResultPronoteUnencryptedFor-%s-%s.xml", idEtablissement,
                Instant.now().truncatedTo(ChronoUnit.SECONDS).atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        try {
            FileOutputStream outputStream = new FileOutputStream(debugDataPath.getPath() + fileLog);
            byte[] strToBytes = xmlContent.getBytes(StandardCharsets.UTF_8);
            outputStream.write(strToBytes);
            outputStream.close();
        } catch (FileNotFoundException ex) {
            log.error("Impossible d'écrire dans le fichier {}", fileLog, ex);
        } catch (IOException ex) {
            log.error("Impossible d'écrire dans le fichier IO exception", ex);
        }
    }

    private void encrypt(ImportChiffre importChiffre, final String content) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        byte[] input = compress(content);

        // Generating IV.
        byte[] iv = new byte[BYTE_SIZE];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        final IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        // Generating AES Key
        KeyGenerator kgen = null;
        kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        final SecretKey key = kgen.generateKey();

        // Encrypt data with AES.
        Cipher cipher = null;
        byte[] encrypted = null;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
            encrypted = cipher.doFinal(input);
        } catch (NoSuchPaddingException | BadPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException | InvalidKeyException e) {
            throw new NoSuchAlgorithmException(e);
        }

        // encrypt to RSA and provide the AES Key to Index Education
        // Index Education requirements : encrypt with RSA (AES key + IV + MD5HASH(AES KEY + IV))
        byte[] encryptedKey = null;
        try {
            byte[] cle = new byte[BYTE_SIZE * 3];
            // copy to the byte array AES KEY
            System.arraycopy(key.getEncoded(), 0, cle, 0, BYTE_SIZE);
            // copy to the byte array IV
            System.arraycopy(iv, 0, cle, BYTE_SIZE, BYTE_SIZE);

            // generate de MD5 HASH of the (AES key + IV)
            byte[] md5Hash = genMD5Hash(cle);

            // copy to the byte array the MD5 HASH
            System.arraycopy(md5Hash, 0, cle, BYTE_SIZE * 2, BYTE_SIZE);

            // finally encrypt the byte array with the RSA asymmetric key
            Cipher cipherRsa = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipherRsa.init(Cipher.ENCRYPT_MODE, publicKey);
            encryptedKey = cipherRsa.doFinal(cle);
        } catch (NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            throw new NoSuchAlgorithmException(e);
        }

        importChiffre.setContenu(Base64.getEncoder().encodeToString(encrypted));
        importChiffre.setVerification(getHash(input));
        importChiffre.setCle(Base64.getEncoder().encodeToString(encryptedKey));
    }

    private byte[] compress(final String content) throws UnsupportedEncodingException {
        // Encode a String into bytes
        byte[] input = content.getBytes(StandardCharsets.UTF_8);

        // Compress the bytes
        byte[] buffer = new byte[input.length];
        Deflater compresser = new Deflater();
        compresser.setInput(input);
        compresser.finish();
        int compressedDataLength = compresser.deflate(buffer);
        compresser.end();
        log.debug("Compressed data, input length is '{}' and compressed length is '{}'", content.length(), compressedDataLength);

        return buffer;
    }

    private byte[] getHash(final byte[] content) throws NoSuchAlgorithmException {
        // calculate HASH verification
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(content);
    }

    private byte[] genMD5Hash(final byte[] content) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        return digest.digest(content);
    }
}


