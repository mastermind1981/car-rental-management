package jp.co.worksap.roster.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="T_RESERVATION")
public class Reservation {
	@GeneratedValue @Id
	private int id;

	private long groupId;

	private Customer customer;

	private Inventory inventory;

	private Timestamp inventoryVersion;

	private String cardName;

	private String cardNumber;

	private String cardCIV;

	@Temporal(TemporalType.TIMESTAMP)
	private Date cardExpiryDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public Timestamp getInventoryVersion() {
		return inventoryVersion;
	}

	public void setInventoryVersion(Timestamp inventoryVersion) {
		this.inventoryVersion = inventoryVersion;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardCIV() {
		return cardCIV;
	}

	public void setCardCIV(String cardCIV) {
		this.cardCIV = cardCIV;
	}

	public Date getCardExpiryDate() {
		return cardExpiryDate;
	}

	public void setCardExpiryDate(Date cardExpiryDate) {
		this.cardExpiryDate = cardExpiryDate;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}


}
