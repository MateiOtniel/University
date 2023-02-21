namespace NBA.validators {
    internal class InputValidator{
        public InputValidator() {
        }

        public void validate(string id) {
            int idNr;
            if(!int.TryParse(id, out idNr))
                throw new InvalidDataException("Input invalid!");
        }
    }
}
