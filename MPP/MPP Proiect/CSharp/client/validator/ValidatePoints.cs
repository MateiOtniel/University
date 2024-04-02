using CSharp.exception;

namespace CSharp.Validator{
    public class ValidatePoints{
        public static void Validate(string points){
            try{
                int pointsInt = int.Parse(points);
                if (pointsInt < 0){
                    throw new InputException("Points must be a positive integer!");
                }
            } catch (System.FormatException){
                throw new InputException("Points must be an integer!");
            }
        }
    }
}