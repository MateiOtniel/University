using System;

namespace Consultanta.validator{
    public class ValidateAge{
        public static void Validate(string age){
            int intAge;
            if(int.TryParse(age, out intAge) == false){
                throw new Exception("Varsta trebuie sa fie un numar!");
            }
            if(intAge < 0 || intAge > 120){
                throw new Exception("Varsta trebuie sa fie valida!");
            }
        }
    }
}