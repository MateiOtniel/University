﻿using System;

namespace services{
    public class MyException: Exception{
        public MyException(string message): base(message){}
    }
}