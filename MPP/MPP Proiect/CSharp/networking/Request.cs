﻿using System;

namespace networking{
    public enum RequestType{
        LOGIN,
        LOGOUT,
        GET_ALL_PARTICIPANTS,
        ADD_TEST,
        GET_ALL_ACTIVE_PARTICIPANTS
    }

    [Serializable]
    public class Request{
        private Request(){}

        public RequestType Type{ get; private set; }

        public object Data{ get; private set; }

        public override string ToString(){
            return "Request{" +
                   "type='" + Type + '\'' +
                   ", data='" + Data + '\'' +
                   '}';
        }

        public class Builder{
            private readonly Request request = new Request();

            public Builder Type(RequestType type){
                request.Type = type;
                return this;
            }

            public Builder Data(Object data){
                request.Data = data;
                return this;
            }

            public Request Build(){
                return request;
            }
        }
    }
}