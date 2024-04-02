using System;
using System.Windows.Forms;
using model;
using persistance.exception;
using services;

namespace client{
    public partial class LoginForm: Form{
        private ClientController _clientController;

        public LoginForm(ClientController clientController){
            InitializeComponent();
            _clientController = clientController;
        }

        private void LoginAction(object sender, EventArgs e){
            try{
                var referee =
                    _clientController.Login(emailTextBox.Text, passwordTextBox.Text);
                OpenMainForm(referee);
            } catch(RepositoryException ex){
                MessageBox.Show(ex.Message);
            } catch(MyException ex){
                MessageBox.Show(ex.Message);
            }
        }

        private void OpenMainForm(Referee referee){
            var mainForm = new MainForm(_clientController, referee);
            mainForm.Show();
            Hide();
        }
    }
}