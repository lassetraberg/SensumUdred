package ACQ;

import BLL.case_opening.third_party_information.IAttachment;

import java.util.Set;

public interface ICase extends IInquiry{

    /**
     * Accessor method for citizen's consent to case opening.
     * @return citizen's consent.
     */
    boolean getCitizenConsent();

    /**
     * Accessor method for case's special circumstances.
     * @return  special circumstances.
     */
    String getSpecialCircumstances();

    /**
     * Accessor method for guardian's authority-
     * @return  guardian's authority-
     */
    String getGuardianAuthority();

    /**
     * Accessor method for total level of function.
     * @return  total level of function.
     */
    char getTotalLevelOfFunction();

    /**
     * Accessor method for case's themes.
     * @return
     */
    Set<ITheme> getThemes();

    /**
     * Accessor method for case's grantings.
     * @return case's grantings.
     */
    Set<IGranting> getGrantings();

    /**
     * Accessor method for case's offers.
     * @return  case's offers.
     */
    Set<IOffer> getOffers();

    /**
     * Accessor method for third party informations.
     * @return  third party informations.
     */
    Set<IAttachment> getThirdPartyInformations();



}