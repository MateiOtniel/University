using System;

namespace model{
    
    [Serializable]
    public class Test : Entity<int>{
        public int idParticipant{ set; get; }

        public int idReferee{ set; get; }

        public int points{ set; get; }
        
        public string date{ set; get; }

        public Test(int idParticipant, int idReferee, int points, string date){
            this.idParticipant = idParticipant;
            this.idReferee = idReferee;
            this.points = points;
            this.date = date;
        }

        public override string ToString(){
            return idParticipant + "," + idReferee + "," + points + "," + date;
        }
    }
}