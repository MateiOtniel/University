using System;
using System.Data;
using System.Data.SqlClient;
using System.Windows.Forms;

namespace TestSGBD{
    public partial class MainForm: Form{
        private readonly string _connectionString =
            @"Server=DESKTOP-8PPMB1T;Database=S12023;Integrated Security=true;TrustServerCertificate=true;";

        private readonly DataSet _ds = new DataSet();
        private readonly SqlDataAdapter _parentAdapter = new SqlDataAdapter();
        private readonly SqlDataAdapter _childAdapter = new SqlDataAdapter();
        private readonly BindingSource _parentBs = new BindingSource();
        private readonly BindingSource _childBs = new BindingSource();

        public MainForm(){
            InitializeComponent();
            Load += FormLoad;
        }

        private void FormLoad(object sender, EventArgs e){
            try{
                using(var sqlConnection = new SqlConnection(_connectionString)){
                    sqlConnection.Open();
                    _parentAdapter.SelectCommand =
                        new SqlCommand("SELECT * FROM Autori;", sqlConnection);
                    _childAdapter.SelectCommand =
                        new SqlCommand("SELECT * FROM Proiecte", sqlConnection);
                    _parentAdapter.Fill(_ds, "Autori");
                    _childAdapter.Fill(_ds, "Proiecte");

                    _parentBs.DataSource = _ds.Tables["Autori"];
                    dataGridView1.DataSource = _parentBs;

                    var parentColumn = _ds.Tables["Autori"].Columns["cod_autor"];
                    var childColumn = _ds.Tables["Proiecte"].Columns["cod_autor"];

                    var relation = new DataRelation("FK_Autori_Proiecte", parentColumn,
                        childColumn);
                    _ds.Relations.Add(relation);
                    _childBs.DataSource = _parentBs;
                    _childBs.DataMember = "FK_Autori_Proiecte";
                    dataGridView2.DataSource = _childBs;
                }
            } catch(Exception exception){
                MessageBox.Show(exception.Message);
            }
        }

        private void button1_Click(object sender, EventArgs e){
            try{
                var row = dataGridView1.CurrentCell.RowIndex;
                var codAutor = (int)dataGridView1.Rows[row].Cells[0].Value;
                using(var sqlConnection = new SqlConnection(_connectionString)){
                    sqlConnection.Open();
                    var command = new SqlCommand(
                        "INSERT INTO Proiecte (cod_autor, titlu, descriere, data_depunerii, buget)" +
                        "VALUES (@cod_autor, @titlu, @descriere, @data_depunerii, @buget);",
                        sqlConnection);
                    command.Parameters.AddWithValue("@cod_autor", codAutor);
                    command.Parameters.AddWithValue("@titlu", titluTextBox.Text);
                    command.Parameters.AddWithValue("@descriere",
                        descriereTextBox.Text);
                    command.Parameters.AddWithValue("@data_depunerii",
                        dataDepuneriiTextBox.Text);
                    command.Parameters.AddWithValue("@buget", bugetTextBox.Text);
                    command.ExecuteNonQuery();
                    MessageBox.Show("Proiect adaugat cu succes!");
                }
            } catch(Exception exception){
                MessageBox.Show(exception.Message);
            }
            RefreshChildTable();
        }

        private void button2_Click(object sender, EventArgs e){
            try{
                var row = dataGridView2.CurrentCell.RowIndex;
                var codProiect = (int)dataGridView2.Rows[row].Cells[0].Value;
                using(var sqlConnection = new SqlConnection(_connectionString)){
                    sqlConnection.Open();
                    var command = new SqlCommand(
                        "UPDATE Proiecte SET titlu = @titlu WHERE cod_proiect = @cod_proiect;",
                        sqlConnection);
                    command.Parameters.AddWithValue("@cod_proiect", codProiect);
                    command.Parameters.AddWithValue("@titlu", modificaTitluTextBox.Text);
                    command.ExecuteNonQuery();
                    MessageBox.Show("Proiect modificat cu succes!");
                    RefreshChildTable();
                }
            }catch(Exception exception){
                MessageBox.Show(exception.Message);
            }
        }

        private void button3_Click(object sender, EventArgs e){
            try{
                var row = dataGridView2.CurrentCell.RowIndex;
                var codProiect = (int)dataGridView2.Rows[row].Cells[0].Value;
                using(var sqlConnection = new SqlConnection(_connectionString)){
                    sqlConnection.Open();
                    var command = new SqlCommand(
                        "DELETE FROM Proiecte WHERE cod_proiect = @cod_proiect;",
                        sqlConnection);
                    command.Parameters.AddWithValue("@cod_proiect", codProiect);
                    command.ExecuteNonQuery();
                    MessageBox.Show("Proiect sters cu succes!");
                    RefreshChildTable();
                }
            }catch(Exception exception){
                MessageBox.Show(exception.Message);
            }
        }

        private void RefreshChildTable(){
            using(var sqlConnection = new SqlConnection(_connectionString)){
                sqlConnection.Open();
                _childAdapter.SelectCommand = new SqlCommand("SELECT * FROM Proiecte", sqlConnection);
                _ds.Tables["Proiecte"].Clear();
                _childAdapter.Fill(_ds, "Proiecte");
            }
        }
    }
}