using System;

namespace Consultanta.validator{
    public class ValidateName{
        public static void Validate(string name){
            if (name == null || name.Equals("")){
                throw new Exception("Numele nu poate fi vid!");
            }
        }
    }
}