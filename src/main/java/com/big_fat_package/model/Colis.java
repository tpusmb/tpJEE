package com.big_fat_package.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "colis")
public class Colis {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String idColis;

    @NotEmpty(message = "*Veuillez renseignez le poids du colis")
    private double poidsColis;

    @NotEmpty(message = "*Veuillez renseignez la valeur du colis")
    private double valeurColis;

    @NotEmpty(message = "*Veuillez renseignez l'origine du colis")
    private String origineColis;

    @NotEmpty(message = "*Veuillez renseignez la destination du colis")
    private String destinationColis;

    @Builder.Default
    private double latitude = 0;

    @Builder.Default
    private double longitude = 0;

    @Builder.Default
    private String emplacement = "";

    @Builder.Default
    private String etat = "";


    public String getIdColis() {
        return idColis;
    }

    public void setIdColis(String idColis) {
        this.idColis = idColis;
    }

    public double getPoidsColis() {
        return poidsColis;
    }

    public void setPoidsColis(double poidsColis) {
        this.poidsColis = poidsColis;
    }

    public double getValeurColis() {
        return valeurColis;
    }

    public void setValeurColis(double valeurColis) {
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

    public double getLatitude() { return latitude; }

    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }

    public void setLongitude(double longitude) { this.longitude = longitude; }
}
