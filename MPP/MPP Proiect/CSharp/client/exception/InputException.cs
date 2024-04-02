using System;

namespace CSharp.exception{
    public class InputException : Exception{
        public InputException(string message) : base(message){
        }
    }
}