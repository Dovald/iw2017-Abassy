package com.abassy.tables;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "cierre_caja", catalog = "abassy_db")
public class CierreCaja implements java.io.Serializable {

	private static final long serialVersionUID = 7603498124949768441L;
	
	private Long id;
	private Local local;
	private Float importe;
	private Float importe_real;
	private Date fecha;

	public CierreCaja() {
	}

	public CierreCaja(Local local, Float importe, Float importe_real, Date fecha) {
		this.local = local;
		this.importe = importe;
		this.importe_real = importe_real;
		this.fecha = fecha;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_local")
	public Local getLocal() {
		return this.local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public Float getImporte() {
		return this.importe;
	}

	public void setImporte(Float importe) {
		this.importe = importe;
	}
	
	public Float getImporte_real() {
		return this.importe_real;
	}

	public void setImporte_real(Float importe_real) {
		this.importe_real = importe_real;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha", nullable = false, length = 19)
	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}
