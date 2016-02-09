package com.backend.base.controller.to;

import java.util.Date;

import com.backend.base.controller.to.generic.GenericTO;
import com.backend.base.model.entity.CardEntity;

public class CardTO extends GenericTO {

	private String title;
	private Integer stage;
	private String text;
	private Date nextRevision;
	private boolean changeStage;
	private AccountTO owner;

	public CardTO() {
		super();
	}

	public CardTO(final Long objectId, final String title, final Integer stage, final String text, final Date nextRevision) {
		super(objectId);
		this.title = title;
		this.stage = stage;
		this.text = text;
		this.nextRevision = nextRevision;
		this.changeStage = false;
	}
	
	public CardTO(final CardEntity entity){
		this.title = entity.getTitle();
		this.nextRevision = entity.getNextRevision();
		this.stage = entity.getStage();
		this.text = entity.getText();
		this.changeStage = entity.isChangeStage();
		setObjectId(entity.getObjectId());
		setCreatedAt(entity.getCreatedAt());
		setUpdatedAt(entity.getUpdatedAt());
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getStage() {
		return stage;
	}

	public void setStage(Integer stage) {
		this.stage = stage;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getNextRevision() {
		return nextRevision;
	}

	public void setNextRevision(Date nextRevision) {
		this.nextRevision = nextRevision;
	}

	public boolean isChangeStage() {
		return changeStage;
	}

	public void setChangeStage(boolean changeStage) {
		this.changeStage = changeStage;
	}

	public AccountTO getOwner() {
		return owner;
	}

	public void setOwner(AccountTO owner) {
		this.owner = owner;
	}
	
	
}
