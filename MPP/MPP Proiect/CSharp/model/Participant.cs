using System;

namespace model{
    
    [Serializable]
    public class Participant: Entity<int>{
        private string name;

        private int totalPoints;

        public string Name{
            set{ name = value; }
            get{ return name; }
        }

        public int TotalPoints{
            set{ totalPoints = value; }
            get{ return totalPoints; }
        }

        public Participant(string name, int totalPoints){
            this.name = name;
            this.totalPoints = totalPoints;
        }

        public override string ToString(){
            return "Name: " + Name + "Total Points: " + TotalPoints;
        }
    }
}