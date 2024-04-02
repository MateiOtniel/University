using System;
using System.Collections.Generic;
using System.Windows.Forms;
using model;
using services;

namespace client{
    public class ClientController : IObserver{
        
        public event EventHandler<ClientEventArgs> UpdateEvent; 

        private readonly IServices _server;

        private Referee _currentReferee;
        
        public ClientController(IServices server){
            _server = server;
            _currentReferee = null;
        }

        public Referee Login(string email, string password){
            var referee = new Referee("", email, password);
            var loggedReferee = _server.Login(referee, this);
            Console.WriteLine("Login succeeded ....");
            _currentReferee = loggedReferee;
            Console.WriteLine("Current referee {0}", loggedReferee);
            return _currentReferee;
        }

        public IEnumerable<Participant> GetAllParticipantsSortedByName(){
            return _server.GetAllParticipantsSortedByName();
        }

        public void AddTest(Test test){
            _server.AddTest(test);
        }

        public IEnumerable<Participant> GetAllActiveParticipants(int refereeId){
            return _server.GetAllActiveParticipants(refereeId);
        }
        
        protected virtual void OnUserEvent(ClientEventArgs e)
        {
            if (UpdateEvent == null) return;
            UpdateEvent(this, e);
            Console.WriteLine("Update Event called");
        }

        public void Logout(){
            MessageBox.Show(_currentReferee.ToString());
            Console.WriteLine("Logout ....");
            _server.Logout(_currentReferee);
            _currentReferee = null;
        }

        public void RefreshAll(Test test){
            ClientEventArgs clientArgs = new ClientEventArgs(ClientUserEvent.TestAdded, test);
            OnUserEvent(clientArgs);
        }
    }
}