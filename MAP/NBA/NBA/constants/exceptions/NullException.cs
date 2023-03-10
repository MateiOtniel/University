using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NBA.constants.exceptions {
    public class NullException : Exception {
        public NullException() {
        }

        public NullException(string message)
            : base(message) {
        }

        public NullException(string message, Exception inner)
            : base(message, inner) {
        }
    }
}
