using System;

namespace persistance.exception{
    public class RepositoryException : Exception{
        public RepositoryException(string message) : base(message){
        }
    }
}