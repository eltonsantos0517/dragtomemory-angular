package com.backend.base.controller.to;

import org.joda.time.DateTime;

import com.backend.base.controller.to.generic.GenericTO;
import com.backend.base.model.entity.CardEntity;

public class CardTO extends GenericTO {

	private String title;
	private String stage;
	private String text;
	private DateTime nextRevision;
	private boolean changeStage;

	public CardTO() {
		super();
	}

	public CardTO(final Long objectId, final String title, final String stage, final String text, final DateTime nextRevision) {
		super(objectId);
		this.title = title;
		this.stage = stage;
		this.text = text;
		this.nextRevision = nextRevision;
		this.changeStage = false;
	}
	
	public CardTO(final CardEntity entity){
		this.title = entity.getTitle();
		this.nextRevision = new DateTime(entity.getNextRevision());
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

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public DateTime getNextRevision() {
		return nextRevision;
	}

	public void setNextRevision(DateTime nextRevision) {
		this.nextRevision = nextRevision;
	}

	public boolean isChangeStage() {
		return changeStage;
	}

	public void setChangeStage(boolean changeStage) {
		this.changeStage = changeStage;
	}
}
