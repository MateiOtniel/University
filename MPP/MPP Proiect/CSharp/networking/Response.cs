using System;

namespace networking{
    public enum ResponseType{
        OK,
        ERROR,
        UPDATE,
        TEST_ADDED,
        GET_ALL_PARTICIPANTS,
        GET_ALL_ACTIVE_PARTICIPANTS,
    }

    [Serializable]
    public class Response{
        private Response(){}

        public ResponseType Type{ get; private set; }

        public object Data{ get; private set; }

        private void SetType(ResponseType type){
            Type = type;
        }

        private void SetData(Object data){
            Data = data;
        }

        public override string ToString(){
            return "Response{" +
                   "type='" + Type + '\'' +
                   ", data='" + Data + '\'' +
                   '}';
        }

        public class Builder{
            private readonly Response response = new Response();

            public Builder Type(ResponseType type){
                response.SetType(type);
                return this;
            }

            public Builder Data(Object data){
                response.SetData(data);
                return this;
            }

            public Response Build(){
                return response;
            }
        }
    }
}