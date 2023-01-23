using System.Globalization;

namespace NBA.constants.exceptions {
    internal class DateValidator {
        public DateValidator() {
        }

        public void validate(string date) {
            string format = "dd/MM/yyyy";
            DateTime dateValue;
            if(!DateTime.TryParseExact(date, format,
                new CultureInfo("en-US"),
                DateTimeStyles.None,
                out dateValue))
                throw new InvalidInputException("Invalid date!");
        }
    }
}
