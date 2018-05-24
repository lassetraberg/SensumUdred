package BLL.mediators;

import ACQ.*;
import BLL.Elucidation;
import BLL.meeting.Dialog;
import BLL.meeting.Meeting;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class ElucidationServiceMediator implements IElucidationService {

    /**
     * IElucidationService from the DAL
     */
    private IElucidationService dataElucidationService;

    /**
     * HTTP Client used in dialog, which is used in elucidation;
     */
    private IHttp httpClient;

    /**
     * HTTP Client used in dialog, which is used in elucidation;
     */
    private IEBoks eboks;

    public ElucidationServiceMediator(IElucidationService dataElucidationService, IHttp httpClient, IEBoks eboks) {
        this.dataElucidationService = dataElucidationService;
        this.httpClient = httpClient;
        this.eboks = eboks;
    }

    @Override
    public IElucidation createElucidation(IUser citizen, Set<IUser> caseworkers, IInquiry inquiry) {
        IElucidation DALElucidation = dataElucidationService.createElucidation(citizen, caseworkers, inquiry);

        IElucidation realElucidation = this.convertToRealElucidation(DALElucidation);

        return realElucidation;
    }

    @Override
    public boolean updateInquiry(long id, IInquiry inquiry) {
        return dataElucidationService.updateInquiry(id, inquiry);
    }

    @Override
    public boolean updateState(long id, boolean isclosed) {
        return dataElucidationService.updateState(id, isclosed);
    }

    @Override
    public boolean updateCaseworkers(long id, Set<IUser> users) {
        return dataElucidationService.updateCaseworkers(id, users);
    }

    @Override
    public boolean updateCitizenConsent(long id, boolean hasConsent) {
        return dataElucidationService.updateCitizenConsent(id, hasConsent);
    }

    @Override
    public boolean updateActingMunicipality(long id, String municipality) {
        return dataElucidationService.updateActingMunicipality(id, municipality);
    }

    @Override
    public boolean updateSpecialCircumstances(long id, String newDescription) {
        return dataElucidationService.updateSpecialCircumstances(id, newDescription);
    }

    @Override
    public boolean updateGuardianAuthority(long id, String newDescription) {
        return dataElucidationService.updateGuardianAuthority(id, newDescription);
    }

    @Override
    public boolean updateTotalLevelOfFunction(long id, char letter) {
        return dataElucidationService.updateTotalLevelOfFunction(id, letter);
    }

    @Override
    public boolean updateOffers(long id, Set<IOffer> offers) {
        return dataElucidationService.updateOffers(id, offers);
    }

    @Override
    public boolean updateGrantings(long id, Set<IGranting> grantings) {
        return dataElucidationService.updateGrantings(id, grantings);
    }

    @Override
    public boolean updateThemes(long id, Set<ITheme> themes) {
        return dataElucidationService.updateThemes(id, themes);
    }

    @Override
    public boolean updateMeeting(long id, IMeeting meeting) {
        return dataElucidationService.updateMeeting(id, meeting);
    }

    @Override
    public boolean updateThirdPartyAttachments(long id, Set<IAttachment> attachments) {
        return dataElucidationService.updateThirdPartyAttachments(id, attachments);
    }

    @Override
    public IElucidation getElucidation(long id) {
        IElucidation DALElucidation = dataElucidationService.getElucidation(id);

        IElucidation realElucidation = this.convertToRealElucidation(DALElucidation);

        return realElucidation;
    }

    @Override
    public Set<IElucidation> getOpenElucidationsFromSSN(String ssn) {
        Set<IElucidation> realOpenElucidations = new HashSet<>();
        dataElucidationService.getOpenElucidationsFromSSN(ssn).forEach(e -> realOpenElucidations.add(convertToRealElucidation(e)));
        return realOpenElucidations;
    }

    @Override
    public Set<IElucidation> getClosedElucidationsFromSSN(String ssn) {
        Set<IElucidation> realClosedElucidations = new HashSet<>();
        dataElucidationService.getClosedElucidationsFromSSN(ssn).forEach(e -> realClosedElucidations.add(convertToRealElucidation(e)));
        return realClosedElucidations;
    }


    private Elucidation convertToRealElucidation(IElucidation DALElucidation) {
        Set<IMeeting> realMeetings = new HashSet<>();

        for (IMeeting dalMeeting : DALElucidation.getDialog().getMeetings()) {
            BLL.meeting.Meeting bllMeeting = new Meeting(dalMeeting.getCreator(), eboks, dalMeeting.getNumber());
            bllMeeting.setInformation(dalMeeting.getInformation());

            Calendar cal = Calendar.getInstance();
            cal.setTime(dalMeeting.getMeetingDate());

            bllMeeting.setMeetingDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
            bllMeeting.setNumber(dalMeeting.getNumber());

            realMeetings.add(bllMeeting);
        }

        Dialog realDialog = new Dialog(httpClient, eboks);
        realDialog.setMeetings(realMeetings);

        IUser creator = null;
        if (DALElucidation.getCaseworkers().stream().findFirst().isPresent()) {
            creator = DALElucidation.getCaseworkers().stream().findFirst().get();
        }

        Set<IUser> caseworkersExceptCreator = DALElucidation.getCaseworkers();
        caseworkersExceptCreator.remove(creator);

        Elucidation realElucidation = new Elucidation(DALElucidation.getId(), DALElucidation.getCitizen(), creator, realDialog);
        realElucidation.addCaseworker(caseworkersExceptCreator.toArray(new IUser[0]));

        return realElucidation;
    }
}
