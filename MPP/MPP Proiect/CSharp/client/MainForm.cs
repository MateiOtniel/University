using System;
using System.Collections.Generic;
using System.Windows.Forms;
using CSharp.exception;
using CSharp.Validator;
using model;

namespace client{
    public partial class MainForm: Form{
        private readonly ClientController _clientController;

        private readonly Referee _referee;

        private IList<Participant> _participantsSortedByName;

        private IList<Participant> _activeParticipants;

        public MainForm(ClientController clientController, Referee referee){
            InitializeComponent();
            Load += LoadMainForm;
            _clientController = clientController;
            _referee = referee;
            InitializeList();
            _clientController.UpdateEvent += UserUpdate;
        }

        private void LoadMainForm(object sender, EventArgs e){
            selectedRefereeLabel.Text = _referee.ToString();
            addResultPanel.Visible = false;
        }

        private void InitializeList(){
            _participantsSortedByName =
                _clientController.GetAllParticipantsSortedByName() as
                    IList<Participant>;
            participantsDataGridView.DataSource = _participantsSortedByName;
            _activeParticipants =
                _clientController.GetAllActiveParticipants(_referee.id)
                    as IList<Participant>;
            raportDataGridView.DataSource = _activeParticipants;
        }

        private void UserUpdate(object sender, ClientEventArgs e){
            if(e.UserEventType == ClientUserEvent.TestAdded){
                Test currentTest = e.Data as Test;
                foreach(Participant participant in _participantsSortedByName){
                    if(participant.id == currentTest.idParticipant){
                        participant.TotalPoints += currentTest.points;
                    }
                }
                foreach(Participant activeParticipant in _activeParticipants){
                    if(activeParticipant.id == currentTest.idParticipant){
                        activeParticipant.TotalPoints += currentTest.points;
                    }
                }
                participantsDataGridView.BeginInvoke(
                    new UpdateListBoxCallback(UpdateList),
                    participantsDataGridView,
                    _participantsSortedByName);
                raportDataGridView.BeginInvoke(
                    new UpdateListBoxCallback(UpdateList), raportDataGridView,
                    _activeParticipants);
            }
        }

        //define the method for updating the DataGridView
        private static void UpdateList(DataGridView list,
            IList<Participant> data){
            list.DataSource = data;
        }

        //define the delegate to be called back by the GUI thread
        private delegate void UpdateListBoxCallback(DataGridView list,
            IList<Participant> data);

        private void AddButtonAction(object sender, EventArgs e){
            pointsTextBox.Text = "";
            dateTimePicker.Text = "";
            addResultPanel.Visible = true;
        }

        private void AddResultToDatabase(object sender, EventArgs e){
            try{
                ValidatePoints.Validate(pointsTextBox.Text);
                if(!dateTimePicker.Checked){
                    throw new InputException("Date picked is null");
                }

                if(participantsDataGridView.CurrentRow == null){
                    throw new InputException("No participant selected");
                }

                var row = participantsDataGridView.CurrentRow.Index;
                var participantId = int.Parse(participantsDataGridView.Rows[row]
                    .Cells[2].Value.ToString());
                Test test = new Test(participantId, _referee.id,
                    int.Parse(pointsTextBox.Text),
                    dateTimePicker.Value.ToString("yyyy-MM-dd"));
                _clientController.AddTest(test);
                //--------------------------------
                //RefreshAll();
                //--------------------------------
                pointsTextBox.Text = "";
                dateTimePicker.Text = "";
            } catch(InputException ex){
                MessageBox.Show(ex.Message);
            }
        }

        private void Logout(object sender, EventArgs e){
            _clientController.Logout();
            _clientController.UpdateEvent -= UserUpdate;
            var loginForm = new LoginForm(_clientController);
            loginForm.Show();
            Hide();
        }
    }
}