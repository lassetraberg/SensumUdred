import BLL.BusinessFacade;
import BLL.IBusiness;
import BLL.account_system.ILoginService;
import BLL.account_system.LoginService;
import BLL.account_system.UserManager;
import DAL.IPersistent;
import DAL.PersistentFacade;
import UI.IUserInterface;
import UI.primary_view.UserFacade;

public class Main {
    public static void main(String[] args) {
        IPersistent persistent = new PersistentFacade();
        IBusiness business = new BusinessFacade();
        IUserInterface ui = new UserFacade();

        ui.injectBusiness(business);
        business.injectPersistent(persistent);

        ui.startApplication(args);


    }
}