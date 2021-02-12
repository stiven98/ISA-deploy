package ftn.isa.team12.pharmacy.controller;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import ftn.isa.team12.pharmacy.domain.drugs.Drug;
import ftn.isa.team12.pharmacy.domain.drugs.ERecipe;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.dto.ERecipeFromQrCodeDTO;
import ftn.isa.team12.pharmacy.dto.PharmacyQRDTO;
import ftn.isa.team12.pharmacy.dto.QrCodeItem;
import ftn.isa.team12.pharmacy.dto.QrScannerRetDTO;
import ftn.isa.team12.pharmacy.service.*;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/erecepie", produces = MediaType.APPLICATION_JSON_VALUE )
public class ERecipeController {

    @Autowired
    private ERecipeService eRecipeService;

    @Autowired
    private DrugService drugService;

    @Autowired
    private PharmacyService pharmacyService;

    @Autowired
    private DrugPriceService drugPriceService;

    @Autowired
    private DrugInPharmacyService drugInPharmacyService;


    @GetMapping("/getPatientERecepies/{email}")
    public ResponseEntity<List<ERecipe>> findERecepiesByPatient(@PathVariable String email) {
        List<ERecipe> eRecipes =  this.eRecipeService.findAllERecipesByPatient(email);
        return new ResponseEntity<>(eRecipes, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PATIENT')")
    @PostMapping("/add")
    public ResponseEntity<ERecipe> addERecipe(@RequestBody ERecipeFromQrCodeDTO eRecipeFromQrCodeDTO) {
        System.out.println(eRecipeFromQrCodeDTO);
        ERecipe recipe = eRecipeService.addERecipe(eRecipeFromQrCodeDTO);


        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PATIENT')")
    @PostMapping("/uploadQrCode")
    public ResponseEntity<QrScannerRetDTO> uploadQrCode(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
                File destination = new File("src/images/qr/" + file.getOriginalFilename());
                ImageIO.write(src, "png", destination);
                String decodedText = decodeQRCode(new File("src/images/qr/" + file.getOriginalFilename()));
                System.out.println(decodedText);
                if (decodedText == null) {
                    throw new IllegalArgumentException("Please upload correct QR code!");
                } else {


                    List<QrCodeItem> qrCodeItems = new ArrayList<QrCodeItem>();
                    if (decodedText.contains(";")) {
                        String[] medications = decodedText.split(";");
                        for (String medication : medications) {
                            String[] medicationParts = medication.split(":");
                            qrCodeItems.add(new QrCodeItem(medicationParts[0], Integer.parseInt(medicationParts[1])));
                        }
                    } else {
                        String[] medicationParts = decodedText.split(":");
                        qrCodeItems.add(new QrCodeItem(medicationParts[0], Integer.parseInt(medicationParts[1])));
                    }
                    if(qrCodeItems.size() == 0) {
                        throw new IllegalArgumentException("Invalid qr code!");
                    }

                    List<Drug> drugs = new ArrayList<>();

                    for (QrCodeItem item: qrCodeItems) {
                        Drug drug = drugService.findDrugByName(item.getName());
                        if (drug == null) {
                            throw new IllegalArgumentException("All drugs don't exists in db!");
                        }
                        //
                        drugs.add(drug);
                    }

                    QrScannerRetDTO qrScannerRetDTO = new QrScannerRetDTO(qrCodeItems);
                    qrScannerRetDTO.setDrugs(qrCodeItems);

                    List<Pharmacy> pharmacies = pharmacyService.findAll();
                    for(Pharmacy pharmacy : pharmacies) {

                        List<Drug> drugListWithPharmacy = drugInPharmacyService.findDrugInPharmacyById(pharmacy.getId());
                        if (drugListWithPharmacy != null && drugListWithPharmacy.containsAll(drugs)){

                            double price = 0;
                            for (int i = 0; i < drugs.size(); i++) {
                                if(drugInPharmacyService.findDrugQuantity(drugs.get(i).getDrugId(), pharmacy.getId()) < qrCodeItems.get(i).getQuantity()){
                                    break;
                                }
                                double add = drugPriceService.getPriceForDrug(pharmacy.getId(), drugs.get(i).getDrugId());
                                System.out.println(add);
                               price += (add * qrCodeItems.get(i).getQuantity());
                            }

                            PharmacyQRDTO pharmacyQRDTO = new PharmacyQRDTO();
                            pharmacyQRDTO.setAverageMark(pharmacy.getAverageMark());
                            pharmacyQRDTO.setLocation(pharmacy.getLocation());
                            pharmacyQRDTO.setId(pharmacy.getId());
                            pharmacyQRDTO.setName(pharmacy.getName());
                            pharmacyQRDTO.setPrice(price);
                            qrScannerRetDTO.getPharmacies().add(pharmacyQRDTO);
                        }
                    }
                    return new ResponseEntity<>(qrScannerRetDTO, HttpStatus.OK);

                }


            } catch (Exception e) {
                throw new IllegalArgumentException("Please upload correct QR code!");
            }
        } else {
            throw new IllegalArgumentException("Please upload correct QR code!");
        }





    }




    private static String decodeQRCode(File qrCodeimage) throws IOException, NotFoundException {
        BufferedImage bufferedImage = ImageIO.read(qrCodeimage);
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        try {
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (com.google.zxing.NotFoundException n) {
            System.out.println("There is no QR code in the image");
            return null;
        }
    }


}
