using System;
using System.Windows.Forms;
using networking;
using services;

namespace client{
    static class StartClient{
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main(){
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            IServices server = new ServicesRpcProxy("127.0.0.1", 55556);
            ClientController ctrl = new ClientController(server);
            LoginForm loginForm = new LoginForm(ctrl);
            Application.Run(loginForm);
        }
    }
}