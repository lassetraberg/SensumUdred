package BLL.account_system;

import ACQ.IAccount;
import ACQ.ISigningService;
import ACQ.IUser;
import ACQ.IUserManager;
import DAL.database.IDatabaseService;

import java.util.Set;

public class UserManager implements IUserManager, ISigningService {
    private IDatabaseService dbService;
    private Account signedInAccount;
    private User signedInUser;

    public UserManager(IDatabaseService dbService) {
        this.dbService = dbService;
    }

    @Override
    public IAccount getSignedInAccount() {
        return signedInAccount;
    }

    @Override
    public IUser getSignedInUser() {
        return signedInUser;
    }

    @Override
    public IUser signIn(String username, String password) {
        IUser user = dbService.signIn(username, password);

        return user;
    }

    @Override
    public boolean signOut(String accountName) {
        this.signedInAccount = null;
        this.signedInUser = null;

        return dbService.signOut(accountName);
    }

    @Override
    public boolean signUpUser(String SSN) {
        return dbService.signUpUser(SSN);
    }

    @Override
    public boolean signUpUser(String SSN, String firstName, String lastName, String phoneNumber, String email) {
        return dbService.signUpUser(SSN, firstName, lastName, phoneNumber, email);
    }

    @Override
    public boolean signUpUser(IUser user) {
        return dbService.signUpUser(user);
    }

    @Override
    public boolean signUpAccount(String username, String password, int securityLevel) {
        return dbService.signUpAccount(username, password, securityLevel);
    }

    @Override
    public boolean accountExists(String accountName) {
        return dbService.accountExists(accountName);
    }

    @Override
    public boolean userExists(String ssn) {
        return dbService.userExists(ssn);
    }

    @Override
    public boolean lockAccount(String accountName) {
        return dbService.lockAccount(accountName);
    }

    @Override
    public boolean unlockAccount(String accountName) {
        return dbService.unlockAccount(accountName);
    }

    @Override
    public boolean changeSecurityLevel(String accountName, int newSecurityLevel) {
        return dbService.changeSecurityLevel(accountName, newSecurityLevel);
    }

    @Override
    public boolean changePassword(String accountName, String newPassword) {
        return dbService.changePassword(accountName, newPassword);
    }

    @Override
    public Set<IUser> getAllUsers() {
        return null;
    }

    @Override
    public Set<IAccount> getAllAccounts() {
        return null;
    }
}
