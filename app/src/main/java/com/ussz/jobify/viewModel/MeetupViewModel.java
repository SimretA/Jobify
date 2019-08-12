package com.ussz.jobify.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ussz.jobify.adapters.MeetupSection;
import com.ussz.jobify.data.Job;
import com.ussz.jobify.data.Meetup;
import com.ussz.jobify.network.MeetupRemote;
import com.ussz.jobify.utilities.CustomCallback;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MeetupViewModel extends ViewModel {
    private MutableLiveData<List<Meetup>> persistentMeetups;
    public MutableLiveData<ArrayList<MeetupSection>> meetupSection;
    private boolean started = false;

    private CustomCallback customCallback;
    public void setStarted(){
        started = true;
    }
    public Boolean isStarted(){
        return started;
    }

    private void loadMeetups(){
        MeetupRemote.getMeetups(list -> persistentMeetups.setValue((List<Meetup>) list));

    }

    public LiveData<List<Meetup>> getMeetups(){
        if(persistentMeetups == null){
            persistentMeetups = new MutableLiveData<>();
            loadMeetups();
        }

        return persistentMeetups;
    }

    private void loadMeetupsWithDepartment(String department){
        MeetupRemote.getWithDepartment(department, ((object, string) -> {
            List<Meetup> temp  = ((List)object);
            if (temp.size() == 0){
                if (customCallback !=null)
                    this.customCallback.onCallBack(null);
                return;
            }
            ArrayList<MeetupSection> meetupSectionArrayList = meetupSection.getValue();
            meetupSectionArrayList.add(new MeetupSection("Meetups with "+department+" department", temp));
            meetupSection.setValue(meetupSectionArrayList);
        }));
    }
    private void loadMeetupsWithDepartmentAndOrganization(String department, String organization){
        MeetupRemote.getWithOrganization(department, organization, ((object, string) -> {
            List<Meetup> temp  = ((List)object);
            if (temp.size() == 0){
                if (customCallback !=null)
                    this.customCallback.onCallBack(null);
                return;
            }
            ArrayList<MeetupSection> meetupSectionArrayList = meetupSection.getValue();
            meetupSectionArrayList.add(new MeetupSection("Meetups with "+department+" department by "+ organization, temp));
            meetupSection.setValue(meetupSectionArrayList);
        }));
    }
    private  void loadMeetupsWithDepartmentAndName(String department, String name){
        MeetupRemote.getWithMeetUpName(department, name, ((object, string) -> {
            List<Meetup> temp  = ((List)object);
            if (temp.size() == 0){
                if (customCallback !=null)
                    this.customCallback.onCallBack(null);
                return;
            }
            ArrayList<MeetupSection> meetupSectionArrayList = meetupSection.getValue();
            meetupSectionArrayList.add(new MeetupSection(string, temp));
            meetupSection.setValue(meetupSectionArrayList);
        }));
    }
    private void loadMeetupsWithAllFilters(String department, String organization, String name){
        MeetupRemote.getWithOrganization(department,organization,((object, string) -> {
            ArrayList<MeetupSection> meetupSectionArrayList =  meetupSection.getValue();
            ArrayList<Meetup> temp = (ArrayList<Meetup>) object;
            MeetupRemote.getWithMeetUpName(department, name, ((object1, string1) -> {
                if(temp.size() == 0 && ((List<Meetup>) object1).size() == 0){
                    if (customCallback !=null)
                        this.customCallback.onCallBack(null);
                    return;
                }
                temp.addAll((Collection<? extends Meetup>) object1);
                meetupSectionArrayList.add(new MeetupSection("Meetups by "+organization+" and department "+ department+" and name "+name+". ",
                        temp));
            }));
        }));
    }

    public LiveData<ArrayList<MeetupSection>>  getFilteredMeetups(String filterBy, ArrayList<String> filters){
        if(meetupSection == null){
            meetupSection = new MutableLiveData<>();
            meetupSection.setValue(new ArrayList<>());

        }
        switch (filterBy){
            case Job.FIELD_DEPARTMENT:
                loadMeetupsWithDepartment(filters.get(0));
                break;
            case Job.FIELD_DEPARTMENT+Job.FIELD_ORGINAZATION:
                loadMeetupsWithDepartmentAndOrganization(filters.get(0),filters.get(1));
                break;

            case Job.FIELD_DEPARTMENT+Meetup.FIELD_NAME:
                loadMeetupsWithDepartmentAndName(filters.get(0),filters.get(1));
                break;
            case Job.FIELD_DEPARTMENT+Job.FIELD_ORGINAZATION+Meetup.FIELD_NAME:
                loadMeetupsWithAllFilters(filters.get(0), filters.get(1), filters.get(2));
                break;
                default:
                    break;

        }
        return  meetupSection;
    }
    public void setCustomCallback(CustomCallback customCallback){
        this.customCallback = customCallback;
    }

}

