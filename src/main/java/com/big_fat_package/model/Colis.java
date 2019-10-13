package com.big_fat_package.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "colis")
public class Colis {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idColis;

    @NotNull(message = "*Veuillez renseignez le poids du colis")
    private Double poidsColis;

    @NotNull(message = "*Veuillez renseignez la valeur du colis")
    private Double valeurColis;

    @NotEmpty(message = "*Veuillez renseignez l'origine du colis")
    private String origineColis;

    @NotEmpty(message = "*Veuillez renseignez la destination du colis")
    private String destinationColis;

    @Builder.Default
    private Double latitude = 0.0;

    @Builder.Default
    private Double longitude = 0.0;

    @Builder.Default
    private String emplacement = "";

    @Builder.Default
    private String etat = "";

    public int getIdColis() {
        return idColis;
    }

    public void setIdColis(int idColis) {
        this.idColis = idColis;
    }

    public Double getPoidsColis() {
        return poidsColis;
    }

    public void setPoidsColis(Double poidsColis) {
        this.poidsColis = poidsColis;
    }

    public Double getValeurColis() {
        return valeurColis;
    }

    public void setValeurColis(Double valeurColis) {
        this.valeurColis = valeurColis;
    }

    public String getOrigineColis() {
        return origineColis;
    }

    public void setOrigineColis(String origineColis) {
        this.origineColis = origineColis;
    }

    public String getDestinationColis() {
        return destinationColis;
    }

    public void setDestinationColis(String destinationColis) {
        this.destinationColis = destinationColis;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}
