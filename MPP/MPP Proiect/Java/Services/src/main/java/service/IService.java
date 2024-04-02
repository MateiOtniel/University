package service;

import model.Participant;
import model.Referee;
import model.Test;

public interface IService{
    Referee login(Referee referee, IObserver refereeObserver) throws MyException;

    Iterable<Participant> getAllParticipants() throws MyException;

    void addTest(Test test) throws MyException;

    Iterable<Participant> getAllActiveParticipants(int id) throws MyException;

    void logout(Referee referee, IObserver refereeObserver) throws MyException;

}
