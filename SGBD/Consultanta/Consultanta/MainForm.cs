using System;
using System.Configuration;
using System.Data;
using System.Windows.Forms;
using Microsoft.Data.SqlClient;

namespace Consultanta{
    public partial class MainForm: Form{
        private readonly string _connectionString =
            ConfigurationManager.AppSettings["ConnectionString"];

        private readonly string _selectFromParent =
            ConfigurationManager.AppSettings["SelectFromParent"];

        private readonly string _selectFromChild =
            ConfigurationManager.AppSettings["SelectFromChild"];

        private readonly string _parent =
            ConfigurationManager.AppSettings["Parent"];

        private readonly string _child =
            ConfigurationManager.AppSettings["Child"];

        private readonly string _foreignKey =
            ConfigurationManager.AppSettings["ForeignKey"];

        private readonly string _key = ConfigurationManager.AppSettings["Key"];

        private readonly DataSet _ds = new DataSet();
        private readonly SqlDataAdapter _clientAdapter = new SqlDataAdapter();
        private readonly SqlDataAdapter _meetingAdapter = new SqlDataAdapter();
        private readonly BindingSource _clientBs = new BindingSource();
        private readonly BindingSource _meetingBs = new BindingSource();

        public MainForm(){
            InitializeComponent();
            addValueButton.Text = "Add " + ConfigurationManager.AppSettings["Child"];
            updateValueButton.Text =
                "Update " + ConfigurationManager.AppSettings["Child"];
            addLabel.Text = ConfigurationManager.AppSettings["labelText"];
            updateLabel.Text = ConfigurationManager.AppSettings["labelText"];
            Load += FormLoad;
        }

        private void FormLoad(object sender, EventArgs eventArgs){
            try{
                using(var sqlConnection = new SqlConnection(_connectionString)){
                    sqlConnection.Open();
                    _clientAdapter.SelectCommand =
                        new SqlCommand(_selectFromParent, sqlConnection);
                    _meetingAdapter.SelectCommand =
                        new SqlCommand(_selectFromChild, sqlConnection);
                    _clientAdapter.Fill(_ds, _parent);
                    _meetingAdapter.Fill(_ds, _child);

                    _clientBs.DataSource = _ds.Tables[_parent];
                    dataGridView1.DataSource = _clientBs;

                    var clientColumn = _ds.Tables[_parent].Columns[_key];
                    var meetingColumn = _ds.Tables[_child].Columns[_key];

                    var relation = new DataRelation(_foreignKey, clientColumn,
                        meetingColumn);
                    _ds.Relations.Add(relation);
                    _meetingBs.DataSource = _clientBs;
                    _meetingBs.DataMember = _foreignKey;
                    dataGridView2.DataSource = _meetingBs;
                }
            } catch(Exception exception){
                MessageBox.Show(exception.Message);
            }

            addPanel.Visible = false;
            updatePanel.Visible = false;
        }

        private void AddAction(object sender, EventArgs e){
            tipMeetingTextBox.Text = "";
            addPanel.Visible = true;
        }

        private void UpdateAction(object sender, EventArgs e){
            tipMeetingTextBox2.Text = "";
            updatePanel.Visible = true;
        }

        private void DeleteAction(object sender, EventArgs e){
            if(dataGridView2.CurrentRow == null) return;
            var row = dataGridView2.CurrentRow.Index;
            // var codI = (int)dataGridView2.Rows[row].Cells[0].Value;
            // var codC = (int)dataGridView2.Rows[row].Cells[1].Value;
            // string value;
            // if(_child.Equals("Note"))
            //     value = dataGridView2.Rows[row].Cells[2].Value.ToString();
            // else
            //     value = dataGridView2.Rows[row].Cells[2].Value as string;
            try{
                using(var sqlConnection = new SqlConnection(_connectionString)){
                    sqlConnection.Open();
                    var command = new SqlCommand(
                        ConfigurationManager.AppSettings["DeleteAction"],
                        sqlConnection);
                    string[] deleteParameters = ConfigurationManager
                        .AppSettings["DeleteParameters"].Split(',');
                    string[] deleteTypes = ConfigurationManager
                        .AppSettings["DeleteTypes"].Split(',');
                    for(int i = 0; i < deleteParameters.Length; i++){
                        if(deleteTypes[i].Equals("int")){
                            int value = (int)dataGridView2.Rows[row].Cells[i].Value;
                            command.Parameters.AddWithValue(deleteParameters[i],
                                value);
                        }
                        else if(deleteTypes[i].Equals("string")){
                            string value = dataGridView2.Rows[row].Cells[i].Value as string;
                            command.Parameters.AddWithValue(deleteParameters[i],
                                value);
                        }
                    }

                    // command.Parameters.AddWithValue(deleteParameters[0], codC);
                    // command.Parameters.AddWithValue(deleteParameters[1], codI);
                    // if(_child.Equals("Note"))
                    //     command.Parameters.AddWithValue(deleteParameters[2],
                    //         int.Parse(value));
                    // else if(_child.Equals("Meeting"))
                    //     command.Parameters.AddWithValue(deleteParameters[2], value);
                    command.ExecuteNonQuery();
                    RefreshMeetingTable();
                    MessageBox.Show(ConfigurationManager.AppSettings["DeleteMessage"]);
                }
            } catch(Exception exception){
                MessageBox.Show(exception.Message);
            }
        }

        private void RefreshMeetingTable(){
            using(var sqlConnection = new SqlConnection(_connectionString)){
                sqlConnection.Open();
                _meetingAdapter.SelectCommand =
                    new SqlCommand(_selectFromChild, sqlConnection);
                _ds.Tables[_child].Clear();
                _meetingAdapter.Fill(_ds, _child);
            }
        }

        private void AddMeetingAction(object sender, EventArgs e){
            try{
                var row = dataGridView1.CurrentCell.RowIndex;
                // var codC = (int)dataGridView1.Rows[row].Cells[0].Value;
                validator.Validator.Validate(tipMeetingTextBox.Text, _child);
                using(var sqlConnection = new SqlConnection(_connectionString)){
                    sqlConnection.Open();
                    var command = new SqlCommand(
                        ConfigurationManager.AppSettings["InsertAction"],
                        sqlConnection);
                    var insertParameters = ConfigurationManager
                        .AppSettings["InsertParameters"].Split(',');
                    string[] insertTypes = ConfigurationManager
                        .AppSettings["InsertTypes"].Split(',');
                    for(int i = 0; i < insertParameters.Length; i++){
                        if(insertTypes[i].Equals("int")){
                            int value = (int)dataGridView1.Rows[row].Cells[0].Value;
                            command.Parameters.AddWithValue(insertParameters[i],
                                value);
                        } else if(insertTypes[i].Equals("string")){
                            string value = dataGridView1.Rows[row].Cells[i].Value as string;
                            command.Parameters.AddWithValue(insertParameters[i],
                                value);
                        }
                        else if(insertTypes[i].Equals("random"))
                            command.Parameters.AddWithValue(insertParameters[i],
                                new Random().Next(1, 10000));
                        else if(insertTypes[i].Equals("stringI")){
                            string value = tipMeetingTextBox.Text;
                            command.Parameters.AddWithValue(insertParameters[i],
                                value);
                        }
                        else if(insertTypes[i].Equals("intI")){
                            int value = Convert.ToInt32(tipMeetingTextBox.Text);
                            command.Parameters.AddWithValue(insertParameters[i],
                                value);
                        }
                    }

                    // command.Parameters.AddWithValue(insertParameters[0], random);
                    // command.Parameters.AddWithValue(insertParameters[1], codC);
                    // if(_child.Equals("Note"))
                    //     command.Parameters.AddWithValue(insertParameters[2],
                    //         int.Parse(tipMeetingTextBox.Text));
                    // else if(_child.Equals("Meeting"))
                    //     command.Parameters.AddWithValue(insertParameters[2],
                    //         tipMeetingTextBox.Text);
                    command.ExecuteNonQuery();
                    MessageBox.Show(ConfigurationManager.AppSettings["InsertMessage"]);
                    RefreshMeetingTable();
                    addPanel.Visible = false;
                }
            } catch(Exception exception){
                MessageBox.Show(exception.Message);
            }
        }

        private void UpdateMeetingAction(object sender, EventArgs e){
            try{
                var row = dataGridView2.CurrentCell.RowIndex;
                // var codI = (int)dataGridView2.Rows[row].Cells[0].Value;
                // var codC = (int)dataGridView2.Rows[row].Cells[1].Value;
                validator.Validator.Validate(tipMeetingTextBox2.Text, _child);
                using(var sqlConnection = new SqlConnection(_connectionString)){
                    sqlConnection.Open();
                    var command = new SqlCommand(
                        ConfigurationManager.AppSettings["UpdateAction"],
                        sqlConnection);
                    var updateParameters = ConfigurationManager
                        .AppSettings["UpdateParameters"].Split(',');
                    var updateTypes = ConfigurationManager
                        .AppSettings["UpdateTypes"].Split(',');
                    for(int i = 0; i < updateParameters.Length; i++){
                        if(updateTypes[i].Equals("int")){
                            int value = (int)dataGridView2.Rows[row].Cells[i].Value;
                            command.Parameters.AddWithValue(updateParameters[i],
                                value);
                        }
                        else if(updateTypes[i].Equals("string")){
                            string value = dataGridView2.Rows[row].Cells[i].Value as string;
                            command.Parameters.AddWithValue(updateParameters[i],
                                value);
                        }
                        else if(updateTypes[i].Equals("stringU")){
                            string value = tipMeetingTextBox2.Text;
                            command.Parameters.AddWithValue(updateParameters[i],
                                value);
                        }
                        else if(updateTypes[i].Equals("intU")){
                            int value = Convert.ToInt32(tipMeetingTextBox2.Text);
                            command.Parameters.AddWithValue(updateParameters[i],
                                value);
                        }
                    }

                    // if(_child.Equals("Note"))
                    //     command.Parameters.AddWithValue(updateParameters[0],
                    //         int.Parse(tipMeetingTextBox2.Text));
                    // else if(_child.Equals("Meeting"))
                    //     command.Parameters.AddWithValue(updateParameters[0],
                    //         tipMeetingTextBox2.Text);
                    // command.Parameters.AddWithValue(updateParameters[1], codC);
                    // command.Parameters.AddWithValue(updateParameters[2], codI);
                    command.ExecuteNonQuery();
                    MessageBox.Show(ConfigurationManager.AppSettings["UpdateMessage"]);
                    RefreshMeetingTable();
                    updatePanel.Visible = false;
                }
            } catch(Exception exception){
                MessageBox.Show(exception.Message);
            }
        }
    }
}