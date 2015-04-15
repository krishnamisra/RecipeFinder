package com.krishna.recipefinder.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Model object for Ingredient
 * 
 * @author krishnamisra
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ingredient {

	private String item;
	private Integer amount;
	private String unit;
	private Date usebyDate;

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Date getUsebyDate() {
		return usebyDate;
	}

	public void setUsebyDate(Date usebyDate) {
		this.usebyDate = usebyDate;
	}

	@Override
	public String toString() {
		return "Ingredient [item=" + item + ", amount=" + amount + ", unit="
				+ unit + ", usebyDate=" + usebyDate + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ingredient other = (Ingredient) obj;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		return true;
	}

}
