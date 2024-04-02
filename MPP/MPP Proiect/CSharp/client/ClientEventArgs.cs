using System;

namespace client{
    public enum ClientUserEvent{
        TestAdded
    };

    public class ClientEventArgs: EventArgs{
        private readonly ClientUserEvent _userEvent;
        private readonly Object _data;

        public ClientEventArgs(ClientUserEvent userEvent, object data){
            this._userEvent = userEvent;
            this._data = data;
        }

        public ClientUserEvent UserEventType{
            get{ return _userEvent; }
        }

        public object Data{
            get{ return _data; }
        }
    }
}