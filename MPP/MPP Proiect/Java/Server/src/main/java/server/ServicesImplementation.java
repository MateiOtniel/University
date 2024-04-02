package server;

import model.Participant;
import model.Referee;
import model.Test;
import persistence.repository.interfaces.IParticipantRepository;
import persistence.repository.interfaces.IRefereeRepository;
import persistence.repository.interfaces.ITestRepository;
import service.IObserver;
import service.IService;
import service.MyException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ServicesImplementation implements IService{
    IRefereeRepository refereeRepository;

    IParticipantRepository participantRepository;

    ITestRepository testRepository;

    private final Map<Integer, IObserver> loggedReferees;

    public ServicesImplementation(IParticipantRepository playerRepo,
                                  IRefereeRepository refereeRepo, ITestRepository testRepo){
        this.participantRepository = playerRepo;
        this.refereeRepository = refereeRepo;
        this.testRepository = testRepo;
        loggedReferees = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized Referee login(Referee referee, IObserver refereeObserver) throws MyException{
        Referee autentificatedReferee =
                refereeRepository.get(referee.getEmail(), referee.getPassword());
        if(autentificatedReferee != null){
            if(loggedReferees.get(autentificatedReferee.getId()) != null)
                throw new MyException("Referee already logged in.");
            loggedReferees.put(autentificatedReferee.getId(), refereeObserver);
        } else{
            throw new MyException("Authentication failed.");
        }
        return autentificatedReferee;
    }

    @Override
    public synchronized Iterable<Participant> getAllParticipants() throws MyException{
        return participantRepository.getAll();
    }

    @Override
    public synchronized void addTest(Test test) throws MyException{
        testRepository.add(test);
        Participant participant = participantRepository.getById(test.getIdParticipant());
        participant.setTotalPoints(participant.getTotalPoints() + test.getPoints());
        participantRepository.update(participant);
        for(IObserver refereeObserver : loggedReferees.values())
            refereeObserver.refreshAll(test);
    }

    @Override
    public synchronized Iterable<Participant> getAllActiveParticipants(int id){
        HashSet<Integer> participantsIds = (HashSet<Integer>) testRepository.getAll()
                .stream()
                .filter(x -> x.getIdReferee() == id)
                .map(Test::getIdParticipant)
                .collect(Collectors.toSet());

        ArrayList<Participant> list = new ArrayList<>();
        for(int participantId : participantsIds){
            Participant participant = participantRepository.getById(participantId);
            if(! list.contains(participant))
                list.add(participant);
        }

        return (ArrayList<Participant>) list.stream()
                .sorted(Comparator.comparing(Participant::getTotalPoints).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public synchronized void logout(Referee referee, IObserver refereeObserver)
            throws MyException{
        IObserver localReferee = loggedReferees.remove(referee.getId());
        if(localReferee == null)
            throw new MyException("Referee " + referee.getId() + " is not logged in.");
    }
}
