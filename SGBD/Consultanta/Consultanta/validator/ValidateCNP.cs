using System;
using System.Text.RegularExpressions;

namespace Consultanta.validator{
    public class ValidateCNP{
        public static void Validate(string CNP){
            if(Regex.IsMatch(CNP, "^[0-9]+$") == false)
                throw new Exception("CNP trebuie sa contina doar cifre!");
        }
    }
}