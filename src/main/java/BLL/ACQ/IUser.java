package BLL.ACQ;

public interface IUser {

	/**
	 * Gets the user's entry level.
	 * @return	int	the user's entry level.
	 */
	AccessLevel getAccessLevel();

	/**
	 * Sets the user's name
	 * @param firstName	user's first name.
	 * @param lastName user's last name.
	 */
	void setName(String firstName, String lastName);

	/**
	 * Sets the user's social security number.
	 * @param ssn user's social security number.
	 */
	void setSocialSecurityNumber(String ssn);

	/**
	 * Sets the user's address.
	 * @param address	the address.
	 */
	void setAddress(IAddress address);

	/**
	 * Sets the user's phone number.
	 * @param phoneNumber	user's phone number.
	 */
	void setPhoneNumber(String phoneNumber);



}