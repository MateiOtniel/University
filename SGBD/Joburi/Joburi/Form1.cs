using System.Data;
using Microsoft.Data.SqlClient;
namespace Joburi{

    public partial class Form1 : Form {

        private string connectionString = @"Server=DESKTOP-8PPMB1T;Database=Laborator1;
        Integrated Security=true; TrustServerCertificate=true;";

        private DataSet ds = new DataSet();

        private SqlDataAdapter adapter = new SqlDataAdapter();

        public Form1() {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e) {
            try {
                using(SqlConnection connection = new SqlConnection(connectionString)) {
                    connection.Open();
                    MessageBox.Show(connection.State.ToString());
                    adapter.SelectCommand = new SqlCommand("SELECT * FROM Joburi;", connection);
                    adapter.Fill(ds, "Joburi");
                    dataGridView1.DataSource = ds.Tables["Joburi"];
                }
            } catch(Exception ex) {
                MessageBox.Show(ex.Message);
            }
        }

        private void button1_Click(object sender, EventArgs e) {
            try {
                using(SqlConnection connection = new SqlConnection(connectionString)) {
                    adapter.SelectCommand.Connection = connection;
                    if(ds.Tables.Contains("Joburi")) {
                        ds.Tables["Joburi"].Clear();
                    }
                    adapter.Fill(ds, "Joburi");
                }
            } catch(Exception ex) {
                MessageBox.Show(ex.Message);
            }
        }

        private void dataGridView1_CellContentClick(object sender, DataGridViewCellEventArgs e) {

        }
    }
}