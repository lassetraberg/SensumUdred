import DAL.database.DatabaseService;
import DAL.database.IDatabaseService;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseTest {
	private IDatabaseService dbService;

	public DatabaseTest() {
		this.dbService = new DatabaseService();
	}

	@Test
	public void connectionTest() {
		boolean isValid = true;

		try {
			dbService.testConnection();
		} catch(SQLException e) {
			isValid = false;
		}

		assert isValid;
	}

	@Test
	public void signInAndOut() {
		assertEquals("tester", dbService.signIn("tester", "tester").getAccount().getUsername());
		assert dbService.signOut("tester");
	}

	@Test
	public void userExist() {
		assert dbService.userExists("99999999");
	}

	@Test
	public void accountExist() {
		assert dbService.accountExists("tester");
	}

	@Test
	public void lockAndUnlockAccount() {
		assert dbService.lockAccount("tester");
		assert dbService.unlockAccount("tester");
	}
}