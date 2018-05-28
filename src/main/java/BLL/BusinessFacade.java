package BLL;

import ACQ.*;
import BLL.Inquiry.Inquiry;
import BLL.account_system.Address;
import BLL.account_system.User;
import BLL.account_system.UserManager;
import BLL.eboks.EBoks;
import BLL.getter.address_getter.GetAddress;
import BLL.getter.address_getter.IGetAddress;
import BLL.log_system.LogAspect;
import BLL.mediators.ElucidationServiceMediator;
import BLL.open_case.ICase;
import BLL.security_system.SecurityLevel;
import BLL.security_system.SecuritySystem;
import BLL.theme_manager.IThemeManager;
import BLL.theme_manager.ThemeManager;
import BLL.getter.user_getter.GetUser;
import BLL.getter.user_getter.IGetUser;
import DAL.IPersistent;

import java.util.Set;

public class BusinessFacade implements IBusiness {
	private IPersistent persistent;
	private IUserManager userManager;
	private IDefaultService defaultService;
	private IAdminService adminService;

	private IElucidationService elucidationServiceMediator;

	public BusinessFacade() { }

	/**
	 * {@inheritDoc}
	 */
	@SecurityLevel(1000)
	@Override
	public IAdminService getAdminService() {
		return adminService;
	}

	/**
	 * {@inheritDoc}
	 */
	@SecurityLevel(0)
	@Override
	public IDefaultService getDefaultService() {
		return defaultService;
	}

	@Override
	public IElucidationService getElucidationService() {
		return elucidationServiceMediator;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IElucidation getElucidation(int ID) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IUser getUser(String ssn) {
		IUser user = null;

		if(persistent.getDatabaseService().getDefaultService().userExists(ssn)) {
			user = persistent.getDatabaseService().getDefaultService().getUser(ssn);
		}

		return user;
	}

	@Override
	public IUser createUser(String ssn, String firstName, String lastName, IAddress address, String phone, String email) {
		User user = new User(ssn, firstName, lastName);
		user.setAddress((Address)address);
		user.setPhoneNumber(phone);
		user.setEmail(email);

		userManager.signUpUser(ssn, firstName, lastName, phone, email);

		return user;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IUserManager getUserManager() {
		return userManager;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSecurityEventListener(IEventListener<SecurityException> eventListener) {
		SecuritySystem.getInstance().setEventListener(eventListener);
	}

	/**
	 * {@inheritDoc}
	 */
	@SecurityLevel(0)
	@Override
	public Set<IElucidation> getMyElucidations() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@SecurityLevel(500)
	@Override
	public IInquiry createInquiry(String description, String source) {
		return new Inquiry(description, source);
	}

	/**
	 * {@inheritDoc}
	 */
	@SecurityLevel(500)
	@Override
	public Set<IUser> searchUsersContaining(String query) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@SecurityLevel(500)
	@Override
	public void addCaseworkerToCase(IUser user) {

	}

	/**
	 * {@inheritDoc}
	 */
	@SecurityLevel(500)
	@Override
	public IThemeManager getThemeManager(ICase aCase) {
		return new ThemeManager(aCase);
	}

	/**
	 * {@inheritDoc}
	 */
	@SecurityLevel(500)
	@Override
	public IGetUser getGetUser() {
		return new GetUser(persistent.getHttp(), User.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@SecurityLevel(500)
	@Override
	public IGetAddress getGetAddress() {
		return new GetAddress(persistent.getHttp(), Address.class);
	}

	@Override
	public void injectPersistent(IPersistent persistent) {
		this.persistent = persistent;

		IDatabaseService service = persistent.getDatabaseService();

		this.defaultService = service.getDefaultService();
		this.adminService = service.getAdminService();
		this.userManager = new UserManager(this.defaultService, service.getSigningService());
		this.elucidationServiceMediator = new ElucidationServiceMediator(service.getElucidationService(), persistent.getHttp(), new EBoks(persistent.getHttp()));

		LogAspect.setPersistent(persistent);
	}
}