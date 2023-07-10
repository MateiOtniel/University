using System;

namespace Consultanta.validator{
    public class Validator{
        public static void Validate(string tip, string child){
            if(child.Equals("Meeting")){
                if(tip != "Analiza" && tip != "Consultanta" && tip != "Service"){
                    throw new Exception(
                        "Tipul de intalnire trebuie sa fie Analiza, Consultanta sau Service!");
                }
            } else{
                if(int.TryParse(tip, out int result) == false)
                    throw new Exception("Nota trebuie sa fie un numar intreg!");
                if(result < 1 || result > 10)
                    throw new Exception("Nota trebuie sa fie intre 1 si 10!");
            }
        }
    }
}