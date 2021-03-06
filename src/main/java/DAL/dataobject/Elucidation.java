package DAL.dataobject;

import ACQ.IDialog;
import ACQ.IElucidation;
import ACQ.ITask;
import ACQ.IUser;

import java.util.Date;
import java.util.Set;

public class Elucidation implements IElucidation {
	private long id;
	private IUser citizen;
	private Set<IUser> caseworkers;
	private IDialog dialog;
	private ITask task;
	private Date creationTime;

	public Elucidation(long id, IUser citizen, Set<IUser> caseworkers, Date creationTime, IDialog dialog, ITask task) {
		this.id = id;
		this.citizen = citizen;
		this.caseworkers = caseworkers;
		this.creationTime = creationTime;
		this.dialog = dialog;
		this.task = task;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getId() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IUser getCitizen() {
		return citizen;
	}

	/**
	 * {@inheritDoc}
	 * Not implemented.
	 */
	@Override
	public void addCaseworker(IUser... caseworkers) {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<IUser> getCaseworkers() {
		return caseworkers;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ITask getTask() {
		return task;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Date getCreationDate() {
		return creationTime;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IDialog getDialog() {
		return dialog;
	}

	/**
	 * Set the citizen for the elucidation.
	 * @param citizen citizen that applies to the current elucidation
	 */
	public void setCitizen(IUser citizen) {
		this.citizen = citizen;
	}

	/**
	 * Set the caseworkers that are attached to the elucidation.
	 * @param caseworkers the caseworkers of the elucidation
	 */
	public void setCaseworkers(Set<IUser> caseworkers) {
		this.caseworkers = caseworkers;
	}

	/**
	 * Set the task for the elucidation, either an inquiry or case.
	 * @param task {@link ACQ.IInquiry} or {@link ACQ.ICase}
	 */
	public void setTask(ITask task) {
		this.task = task;
	}

	/**
	 * Set the creation time of the elucidation
	 * @param creationTime the creation time
	 */
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	/**
	 * Set the dialog of the elucidation
	 * @param dialog the dialog of the elucidation
	 */
	public void setDialog(IDialog dialog) {
		this.dialog = dialog;
	}
}