using System;

namespace model{
    
    [Serializable]
    public class Referee : Entity<int>{
        public string Name{ set; get; }
        
        public string Email{ set; get; }
        
        public string Password{ set; get; }

        public Referee(string name, string email, string password){
            Name = name;
            Email = email;
            Password = password;
        }

        public override string ToString(){
            return "Name: " + Name;
        }
    }
}