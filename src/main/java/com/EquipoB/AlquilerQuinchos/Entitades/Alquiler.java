package com.EquipoB.AlquilerQuinchos.Entitades;

/* Verifica */

import java.sql.Date;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "alquiler")
@Getter @Setter @ToString
public class Alquiler {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long id_Property;
  private Long id_Inquiline;
  private Date fecIni;
  private Date fecTer;
  private Long valRentDay;
}
