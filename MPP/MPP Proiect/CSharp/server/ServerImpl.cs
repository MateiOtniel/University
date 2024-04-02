using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using model;
using persistance.Interfaces;
using services;

namespace server{
    public class ServerImpl: IServices{
        private readonly IDictionary<int, IObserver> _loggedClients;
        private readonly IParticipantRepository _participantRepository;
        private readonly IRefereeRepository _refereeRepository;
        private readonly ITestRepository _testRepository;

        public ServerImpl(IParticipantRepository participantRepository,
            IRefereeRepository refereeRepository,
            ITestRepository testRepository){
            _participantRepository = participantRepository;
            _refereeRepository = refereeRepository;
            _testRepository = testRepository;
            _loggedClients = new Dictionary<int, IObserver>();
        }

        public Referee Login(Referee referee, IObserver client){
            var refereeOk = _refereeRepository.GetRefereeByEmailAndPassword(
                referee.Email,
                referee.Password);
            if(refereeOk != null){
                if(_loggedClients.ContainsKey(referee.id))
                    throw new MyException("Referee already logged in.");
                _loggedClients.Add(refereeOk.id, client);
                Console.WriteLine("Login succeeded for referee: " + refereeOk);
            } else
                throw new MyException("Authentication failed.");

            return refereeOk;
        }

        private void NotifyClients(Test test){
            foreach(var client in _loggedClients.Values){
                Task.Run(() => client.RefreshAll(test));
            }
        }

        public IEnumerable<Participant> GetAllParticipantsSortedByName(){
            Console.WriteLine("GetAllParticipantsSortedByName");
            return _participantRepository
                .GetAll()
                .OrderBy(participant => participant.Name)
                .ToList();
        }

        public IEnumerable<Participant> GetAllActiveParticipants(int refereeId){
            Console.WriteLine("GetAllActiveParticipants");
            var tests = _testRepository.GetAll();
            //.Where(test => test.IdReferee == refereeId) as List<Test>;

            var ids = new List<int>();
            var uniqueParticipants = new List<Participant>();
            if(tests != null)
                foreach(var test in tests){
                    if(test.idReferee == refereeId &&
                       !ids.Contains(test.idParticipant)){
                        ids.Add(test.idParticipant);
                        uniqueParticipants
                            .Add(_participantRepository.GetById(test.idParticipant));
                    }
                }

            return uniqueParticipants
                .OrderBy(participant => participant.TotalPoints)
                .Reverse()
                .ToList();
        }

        public void Logout(Referee referee){
            var localClient = _loggedClients[referee.id];
            if(localClient == null)
                throw new MyException("Referee " + referee.id + " is not logged in.");
            _loggedClients.Remove(referee.id);
            Console.WriteLine("Logout succeeded for referee: " + referee);
        }

        public void AddTest(Test test){
            _testRepository.Add(test);
            var participant = _participantRepository.GetById(test.idParticipant);
            participant.TotalPoints += test.points;
            _participantRepository.Update(participant);
            NotifyClients(test);
            Console.WriteLine("AddTest succeeded for test: " + test);
        }
    }
}