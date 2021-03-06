package BLL;

import ACQ.*;
import BLL.getter.address_getter.IGetAddress;
import BLL.getter.user_getter.IGetUser;
import BLL.theme_manager.IThemeManager;
import DAL.IPersistent;

import java.util.Set;

public interface IBusiness {
	/**
	 * Injects a persistent layer into the business layer.
	 * @param persistent any persistent
	 */
	void injectPersistent(IPersistent persistent);

	/**
	 * Get admin service, which grants access for admin commands.
	 * It can change password for a user, change security level and more.
	 * @return admin service
	 */
	IAdminService getAdminService();

	/**
	 * Get default service, which contains common services.
	 * It can get all users, get all accounts, search and more.
	 * @return default service
	 */
	IDefaultService getDefaultService();

	/**
	 * Get the elucidation service. It contains services with elucidation as topic.
	 * It can create an elucidation, update an elucidation or get all open/closed elucidations based on the ssn.
	 * @return elucidation service
	 */
	IElucidationService getElucidationService();

	/**
	 * Get the logging service, which can make and get logs;
	 * @return logging service
	 */
	ILoggingService getLoggingService();

	/**
	 * Get the logging service, which can make, not get logs
	 * @return log maker service
	 */
	ILogMakerService getLogMakerService();

	/**
	 * Get the logging service, which can get logs, not make logs
	 * @return log getter service
	 */
	ILogGetterService getLogGetterService();

	/**
	 * Get the User Manager.
	 * It can receive the signed account and user (if any).
	 * It can sign in, sign out and sign up.
	 * @return user manager
	 */
	IUserManager getUserManager();

	/**
	 * Get a user from the ssn.
	 * @param ssn any ssn
	 * @return user with that ssn
	 */
	IUser getUser(String ssn);

	IUser createUser(String ssn, String firstName, String lastName, IAddress address, String phone, String email);

	/**
	 * Set an event listener for when the security system 'throws' out an exception.
	 * This exception will only occur if the user does not the required security level.
	 * @param eventListener any event listener
	 */
	void setSecurityEventListener(IEventListener<SecurityException> eventListener);

	/**
	 * Get the theme manager, for adding new themes to a case
	 * @return theme manager;
	 */
	IThemeManager getThemeManager(ICase aCase);

	/**
	 * Get the user getter object, to get users from a cpr number
	 * @return GetUser object
	 */
	IGetUser getGetUser();


	/**
	 * Get the address getter object, to get address from a cpr number
	 * @return GetAddress object
	 */
	IGetAddress getGetAddress();

	/**
	 * Creates a new inquiry.
	 */
	IInquiry createInquiry(String description, String source);

}
