package com.backend.base.model.entity;

import java.util.Date;

import org.joda.time.DateTime;

import com.backend.base.controller.to.CardTO;
import com.backend.base.model.entity.generic.GenericEntity;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;

@Entity
public class CardEntity extends GenericEntity {

	@Index
	private String title;
	@Index
	private String stage;
	@Index
	private String text;
	@Index
	private Date nextRevision;
	@Ignore
	private boolean changeStage;

	public CardEntity() {
		super();
	}

	public CardEntity(final Long objectId, final String title, final String stage, final String text,
			final Date nextRevision) {
		super(objectId);
		this.title = title;
		this.stage = stage;
		this.text = text;
		this.nextRevision = nextRevision;
		this.changeStage = false;
	}

	public CardEntity(final CardTO to) {
		this.title = to.getTitle();
		this.stage = to.getStage();
		this.text = to.getText();
		this.changeStage = to.isChangeStage();
		setObjectId(to.getObjectId());
		setCreatedAt(to.getCreatedAt());
		setUpdatedAt(to.getUpdatedAt());
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

}
